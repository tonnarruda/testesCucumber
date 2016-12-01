package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.ClinicaAutorizadaDao;
import com.fortes.rh.dao.sesmt.ExameDao;
import com.fortes.rh.dao.sesmt.ExameSolicitacaoExameDao;
import com.fortes.rh.dao.sesmt.FuncaoDao;
import com.fortes.rh.dao.sesmt.MedicoCoordenadorDao;
import com.fortes.rh.dao.sesmt.SolicitacaoExameDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.MotivoSolicitacaoExame;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoClinica;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoExameRelatorio;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.ClinicaAutorizadaFactory;
import com.fortes.rh.test.factory.sesmt.ExameFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoExameFactory;
import com.fortes.rh.util.DateUtil;

public class SolicitacaoExameDaoHibernateTest_Junit4 extends GenericDaoHibernateTest_JUnit4<SolicitacaoExame>
{
	@Autowired
	private SolicitacaoExameDao solicitacaoExameDao;
	@Autowired
	private ColaboradorDao colaboradorDao;
	@Autowired
	private CandidatoDao candidatoDao;
	@Autowired
	private EmpresaDao empresaDao;
	@Autowired
	private MedicoCoordenadorDao medicoCoordenadorDao;
	@Autowired
	private ExameSolicitacaoExameDao exameSolicitacaoExameDao;
	@Autowired
	private ExameDao exameDao;
	@Autowired
	private HistoricoColaboradorDao historicoColaboradorDao;
	@Autowired
	private SolicitacaoDao solicitacaoDao;
	@Autowired
	private CandidatoSolicitacaoDao candidatoSolicitacaoDao;
	@Autowired
	private FuncaoDao funcaoDao;
	@Autowired
	private ClinicaAutorizadaDao clinicaAutorizadaDao;

	public SolicitacaoExame getEntity()
	{
		return SolicitacaoExameFactory.getEntity();
	}

	public GenericDao<SolicitacaoExame> getGenericDao()
	{
		return solicitacaoExameDao;
	}
	
	@Test
	public void testFindImprimirSolicitacaoExames()
	{
		Date dataSolicitacao = DateUtil.criarDataMesAno(01, 01, 2010);
		Date dataIni = DateUtil.criarDataMesAno(01, 01, 2009);
		
		Funcao funcao = FuncaoFactory.getEntity(1L, "Função");
		funcaoDao.save(funcao);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Candidato candidato = CandidatoFactory.getCandidato("Candidato");
		candidatoDao.save(candidato);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("Colaborador");
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, funcao, dataIni, StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador);
		
		Solicitacao solicitacao = saveSolicitacao(funcao);
		saveCandidatoSolicitacao(candidato, solicitacao, true);

		MedicoCoordenador medicoCoordenador = new MedicoCoordenador();
		medicoCoordenadorDao.save(medicoCoordenador);
		
		SolicitacaoExame solicitacaoExame1 = saveSolicitacaoExame(candidato, null, empresa, dataSolicitacao, medicoCoordenador, MotivoSolicitacaoExame.PERIODICO);
		SolicitacaoExame solicitacaoExame2 = saveSolicitacaoExame(null, colaborador, empresa, dataSolicitacao, medicoCoordenador, MotivoSolicitacaoExame.PERIODICO);

		ClinicaAutorizada clinicaAutorizada = ClinicaAutorizadaFactory.getEntity("Clinica", TipoClinica.CLINICA);
		clinicaAutorizadaDao.save(clinicaAutorizada);
		
		Exame exame = ExameFactory.getEntity(empresa);
		exameDao.save(exame);
		
		saveExameSolicitacaoExame(solicitacaoExame1, exame, clinicaAutorizada);
		saveExameSolicitacaoExame(solicitacaoExame2, exame, clinicaAutorizada);

		Collection<SolicitacaoExameRelatorio> solicitacaoexames1 = solicitacaoExameDao.findImprimirSolicitacaoExames(solicitacaoExame1.getId());
		Collection<SolicitacaoExameRelatorio> solicitacaoexames2 = solicitacaoExameDao.findImprimirSolicitacaoExames(solicitacaoExame2.getId());
		
		assertEquals(1,solicitacaoexames1.size());
		assertEquals(1,solicitacaoexames2.size());
		
		SolicitacaoExameRelatorio SEResult1 = ((SolicitacaoExameRelatorio) solicitacaoexames1.toArray()[0]);
		SolicitacaoExameRelatorio SEResult2 = ((SolicitacaoExameRelatorio) solicitacaoexames2.toArray()[0]);
		
		assertEquals("Candidato",SEResult1.getCandidatoNome());
		assertNull(SEResult1.getColaboradorNome());

		assertEquals("Colaborador",SEResult2.getColaboradorNome());
		assertNull(SEResult2.getCandidatoNome());
	}
	
	@Test
	public void testFindImprimirSolicitacaoExamesCandidatoEmUmaUnicaSolicitacaoPessoalMasEmProcessoDeTriagem(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Funcao funcao = FuncaoFactory.getEntity(1L, "Função I");
		funcaoDao.save(funcao);
		
		Candidato candidato = CandidatoFactory.getCandidato("Candidato");
		candidatoDao.save(candidato);
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setFuncao(funcao);
		solicitacaoDao.save(solicitacao);
		
		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(candidato, solicitacao, true);
		candidatoSolicitacaoDao.save(candidatoSolicitacao);

		MedicoCoordenador medicoCoordenador = new MedicoCoordenador();
		medicoCoordenadorDao.save(medicoCoordenador);

		ClinicaAutorizada clinicaAutorizada = ClinicaAutorizadaFactory.getEntity("Clinica", TipoClinica.CLINICA);
		clinicaAutorizadaDao.save(clinicaAutorizada);
		
		Exame exame = ExameFactory.getEntity(empresa);
		exameDao.save(exame);
		
		SolicitacaoExame solicitacaoExame = saveSolicitacaoExame(candidato, null, empresa, DateUtil.criarDataMesAno(01, 01, 2010), medicoCoordenador, MotivoSolicitacaoExame.PERIODICO);
		saveExameSolicitacaoExame(solicitacaoExame, exame, clinicaAutorizada);
		
		Collection<SolicitacaoExameRelatorio> solicitacaoexames1 = solicitacaoExameDao.findImprimirSolicitacaoExames(solicitacaoExame.getId());
		
		assertEquals(1,solicitacaoexames1.size());
		
		SolicitacaoExameRelatorio SEResult1 = ((SolicitacaoExameRelatorio) solicitacaoexames1.toArray()[0]);
		assertEquals("Candidato",SEResult1.getCandidatoNome());
		assertNull(SEResult1.getCandidatoFuncao());
	}
	
	@Test
	public void testFindImprimirSolicitacaoExamesCandidatoEmProcessoDeTriagemEEmSolicitacaoComTriagemFalse(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Funcao funcao1 = FuncaoFactory.getEntity(1L, "Função I");
		funcaoDao.save(funcao1);
		
		Funcao funcao2 = FuncaoFactory.getEntity(1L, "Função II");
		funcaoDao.save(funcao2);
		
		Candidato candidato = CandidatoFactory.getCandidato("Candidato");
		candidatoDao.save(candidato);
		
		Solicitacao solicitacao1 = saveSolicitacao(funcao1);
		Solicitacao solicitacao2 = saveSolicitacao(funcao2);
		
		saveCandidatoSolicitacao(candidato, solicitacao1, true);
		saveCandidatoSolicitacao(candidato, solicitacao2, false);

		MedicoCoordenador medicoCoordenador = new MedicoCoordenador();
		medicoCoordenadorDao.save(medicoCoordenador);

		ClinicaAutorizada clinicaAutorizada = ClinicaAutorizadaFactory.getEntity("Clinica", TipoClinica.CLINICA);
		clinicaAutorizadaDao.save(clinicaAutorizada);
		
		Exame exame = ExameFactory.getEntity(empresa);
		exameDao.save(exame);
		
		SolicitacaoExame solicitacaoExame = saveSolicitacaoExame(candidato, null, empresa, DateUtil.criarDataMesAno(01, 01, 2010), medicoCoordenador, MotivoSolicitacaoExame.PERIODICO);
		saveExameSolicitacaoExame(solicitacaoExame, exame, clinicaAutorizada);
		
		Collection<SolicitacaoExameRelatorio> solicitacaoexames1 = solicitacaoExameDao.findImprimirSolicitacaoExames(solicitacaoExame.getId());
		
		assertEquals(1,solicitacaoexames1.size());
		
		SolicitacaoExameRelatorio SEResult1 = ((SolicitacaoExameRelatorio) solicitacaoexames1.toArray()[0]);
		assertEquals("Candidato",SEResult1.getCandidatoNome());
		assertEquals(funcao2.getNome(), SEResult1.getCandidatoFuncao());
	}

	private SolicitacaoExame saveSolicitacaoExame(Candidato candidato, Colaborador colaborador, Empresa empresa, Date data, MedicoCoordenador medico, String motivoSolicitacaoExame){
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(null, data, empresa, colaborador, candidato, MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExame.setMedicoCoordenador(medico);
		solicitacaoExameDao.save(solicitacaoExame);
		return solicitacaoExame;
	} 
	
	private CandidatoSolicitacao saveCandidatoSolicitacao(Candidato candidato, Solicitacao solicitacao, boolean triagem){
		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity(candidato, solicitacao, triagem);
		candidatoSolicitacaoDao.save(candidatoSolicitacao);
		return candidatoSolicitacao;
	}
	
	private ExameSolicitacaoExame saveExameSolicitacaoExame(SolicitacaoExame solicitacaoExame, Exame exame, ClinicaAutorizada clinicaAutorizada){
		ExameSolicitacaoExame exameSolicitacaoExame = new ExameSolicitacaoExame();
		exameSolicitacaoExame.setExame(exame);
		exameSolicitacaoExame.setSolicitacaoExame(solicitacaoExame);
		exameSolicitacaoExame.setClinicaAutorizada(clinicaAutorizada);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame);
		return exameSolicitacaoExame;
	}
	
	private Solicitacao saveSolicitacao(Funcao funcao){
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setFuncao(funcao);
		solicitacaoDao.save(solicitacao);
		return solicitacao;
	}
}
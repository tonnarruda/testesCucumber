package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

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
import com.fortes.rh.dao.sesmt.RealizacaoExameDao;
import com.fortes.rh.dao.sesmt.SolicitacaoExameDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.MotivoSolicitacaoExame;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoPessoa;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.model.sesmt.RealizacaoExame;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoExameRelatorio;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.ExameFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoExameFactory;
import com.fortes.rh.util.DateUtil;

public class SolicitacaoExameDaoHibernateTest extends GenericDaoHibernateTest<SolicitacaoExame>
{
	SolicitacaoExameDao solicitacaoExameDao;
	ColaboradorDao colaboradorDao;
	CandidatoDao candidatoDao;
	EmpresaDao empresaDao;
	MedicoCoordenadorDao medicoCoordenadorDao;
	ExameSolicitacaoExameDao exameSolicitacaoExameDao;
	ExameDao exameDao;
	RealizacaoExameDao realizacaoExameDao;
	HistoricoColaboradorDao historicoColaboradorDao;
	SolicitacaoDao solicitacaoDao;
	CandidatoSolicitacaoDao candidatoSolicitacaoDao;
	FuncaoDao funcaoDao;
	ClinicaAutorizadaDao clinicaAutorizadaDao;

	@Override
	public SolicitacaoExame getEntity()
	{
		return SolicitacaoExameFactory.getEntity();
	}

	@Override
	public GenericDao<SolicitacaoExame> getGenericDao()
	{
		return solicitacaoExameDao;
	}
	
	public void testFindByIdProjection()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity();
		solicitacaoExame.setCandidato(candidato);
		solicitacaoExame.setEmpresa(empresa);
		solicitacaoExameDao.save(solicitacaoExame);
		
		assertEquals(solicitacaoExame, solicitacaoExameDao.findByIdProjection(solicitacaoExame.getId()));
	}

	public void testFindByCandidatoOuColaborador()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);

		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity();
		solicitacaoExame.setCandidato(candidato);
		solicitacaoExame.setEmpresa(empresa);
		solicitacaoExameDao.save(solicitacaoExame);

		Collection<SolicitacaoExame> resultado = solicitacaoExameDao.findByCandidatoOuColaborador(TipoPessoa.CANDIDATO, candidato.getId(), null);

		assertEquals(1, resultado.size());
	}
	
	public void testGetCount()
	{
		Date dataSolicitacao = DateUtil.criarDataMesAno(01, 01, 2010);
		Date dataIni = DateUtil.criarDataMesAno(01, 01, 2010);
		Date dataFim = DateUtil.criarDataMesAno(01, 05, 2010);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setCandidato(candidato);
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setData(dataSolicitacao);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaborador.setColaborador(colaborador);
		historicoColaboradorDao.save(historicoColaborador);
		
		MedicoCoordenador medicoCoordenador = new MedicoCoordenador();
		medicoCoordenadorDao.save(medicoCoordenador);
		
		SolicitacaoExame solicitacaoExame1 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame1.setEmpresa(empresa);
		solicitacaoExame1.setData(dataSolicitacao);
		solicitacaoExame1.setCandidato(candidato);
		solicitacaoExame1.setMedicoCoordenador(medicoCoordenador);
		solicitacaoExame1.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExame1);
		
		Exame exame = ExameFactory.getEntity();
		exame.setEmpresa(empresa);
		exameDao.save(exame);
		
		RealizacaoExame realizacaoExame = new RealizacaoExame();
		realizacaoExame.setResultado("NORMAL");
		realizacaoExameDao.save(realizacaoExame);

		ExameSolicitacaoExame exameSolicitacaoExame = new ExameSolicitacaoExame();
		exameSolicitacaoExame.setExame(exame);
		exameSolicitacaoExame.setSolicitacaoExame(solicitacaoExame1);
		exameSolicitacaoExame.setRealizacaoExame(realizacaoExame);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame);
		
		assertEquals(1,solicitacaoExameDao.getCount(empresa.getId(), dataIni, dataFim, TipoPessoa.TODOS, "", "", null, null, null).intValue());
		
		assertEquals(0,solicitacaoExameDao.getCount(empresa.getId(), dataIni, dataFim, TipoPessoa.COLABORADOR, "", "", null, null, null).intValue());
	}
	
	
	public void testFindImprimirSolicitacaoExames()
	{
		Date dataSolicitacao = DateUtil.criarDataMesAno(01, 01, 2010);
		Date dataIni = DateUtil.criarDataMesAno(01, 01, 2009);
		
		Funcao funcao = FuncaoFactory.getEntity();
		funcao.setNome("Função");
		funcaoDao.save(funcao);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setNome("Candidato");
		candidatoDao.save(candidato);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("Colaborador");
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setData(dataIni);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFuncao(funcao);
		historicoColaboradorDao.save(historicoColaborador);
		
		MedicoCoordenador medicoCoordenador = new MedicoCoordenador();
		medicoCoordenadorDao.save(medicoCoordenador);
		
		SolicitacaoExame solicitacaoExame1 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame1.setEmpresa(empresa);
		solicitacaoExame1.setData(dataSolicitacao);
		solicitacaoExame1.setCandidato(candidato);
		solicitacaoExame1.setMedicoCoordenador(medicoCoordenador);
		solicitacaoExame1.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExame1);

		SolicitacaoExame solicitacaoExame2 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame2.setEmpresa(empresa);
		solicitacaoExame2.setData(dataSolicitacao);
		solicitacaoExame2.setColaborador(colaborador);
		solicitacaoExame2.setMedicoCoordenador(medicoCoordenador);
		solicitacaoExame2.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExame2);
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setFuncao(funcao);
		solicitacaoDao.save(solicitacao);
		
		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao.setCandidato(candidato);
		candidatoSolicitacao.setSolicitacao(solicitacao);
		candidatoSolicitacaoDao.save(candidatoSolicitacao);

		ClinicaAutorizada clinicaAutorizada = new ClinicaAutorizada();
		clinicaAutorizada.setNome("Clinica");
		clinicaAutorizada.setTipo("01");
		clinicaAutorizadaDao.save(clinicaAutorizada);
		
		Exame exame = ExameFactory.getEntity();
		exame.setEmpresa(empresa);
		exameDao.save(exame);
		
		ExameSolicitacaoExame exameSolicitacaoExame1 = new ExameSolicitacaoExame();
		exameSolicitacaoExame1.setExame(exame);
		exameSolicitacaoExame1.setSolicitacaoExame(solicitacaoExame1);
		exameSolicitacaoExame1.setClinicaAutorizada(clinicaAutorizada);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame1);
		
		ExameSolicitacaoExame exameSolicitacaoExame2 = new ExameSolicitacaoExame();
		exameSolicitacaoExame2.setExame(exame);
		exameSolicitacaoExame2.setSolicitacaoExame(solicitacaoExame2);
		exameSolicitacaoExame2.setClinicaAutorizada(clinicaAutorizada);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame2);

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
	
	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Date dataIni = DateUtil.criarDataMesAno(01, 01, 2010);
		Date dataFim = DateUtil.criarDataMesAno(01, 05, 2010);
		
		solicitacaoExameDao.findAllSelect(1, 15, empresa.getId(), dataIni, dataFim, TipoPessoa.TODOS, "", "", "", null, null);
	}

	public void testTransferirCandidatoToColaborador()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);

		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity();
		solicitacaoExame.setCandidato(candidato);
		solicitacaoExame.setEmpresa(empresa);
		solicitacaoExameDao.save(solicitacaoExame);

		Colaborador colaboradorContratado = ColaboradorFactory.getEntity();
		colaboradorContratado.setCandidato(candidato);
		colaboradorDao.save(colaboradorContratado);

		solicitacaoExameDao.transferirCandidatoToColaborador(empresa.getId(), candidato.getId(), colaboradorContratado.getId());

		Collection<SolicitacaoExame> resultadoCandidato = solicitacaoExameDao.findByCandidatoOuColaborador(TipoPessoa.CANDIDATO, candidato.getId(), null);
		Collection<SolicitacaoExame> resultadoColaborador = solicitacaoExameDao.findByCandidatoOuColaborador(TipoPessoa.COLABORADOR, colaboradorContratado.getId(), null);

		assertEquals(0, resultadoCandidato.size());
		assertEquals(1, resultadoColaborador.size());
	}

	public void testTransferirColaboradorToCandidato()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setCandidato(candidato);
		colaboradorDao.save(colaborador);

		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity();
		solicitacaoExame.setColaborador(colaborador);
		solicitacaoExame.setEmpresa(empresa);
		solicitacaoExameDao.save(solicitacaoExame);

		solicitacaoExameDao.transferirColaboradorToCandidato(empresa.getId(), candidato.getId(), colaborador.getId());

		Collection<SolicitacaoExame> resultadoCandidato = solicitacaoExameDao.findByCandidatoOuColaborador(TipoPessoa.CANDIDATO, candidato.getId(), null);
		Collection<SolicitacaoExame> resultadoColaborador = solicitacaoExameDao.findByCandidatoOuColaborador(TipoPessoa.COLABORADOR, colaborador.getId(), null);

		assertEquals(1, resultadoCandidato.size());
		assertEquals(0, resultadoColaborador.size());
	}
	
	public void testFindAtendimentosMedicos()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setCandidato(candidato);
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setData(new Date());
		historicoColaborador.setColaborador(colaborador);
		historicoColaboradorDao.save(historicoColaborador);
		
		MedicoCoordenador medicoCoordenador = new MedicoCoordenador();
		medicoCoordenadorDao.save(medicoCoordenador);
		
		SolicitacaoExame solicitacaoExame1 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame1.setEmpresa(empresa);
		solicitacaoExame1.setData(DateUtil.criarDataMesAno(01, 01, 2010));
		solicitacaoExame1.setCandidato(candidato);
		solicitacaoExame1.setMedicoCoordenador(medicoCoordenador);
		solicitacaoExame1.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExame1);
		
		SolicitacaoExame solicitacaoExame2 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame2.setEmpresa(empresa);
		solicitacaoExame2.setData(DateUtil.criarDataMesAno(21, 03, 2010));
		solicitacaoExame2.setColaborador(colaborador);
		solicitacaoExame2.setMedicoCoordenador(medicoCoordenador);
		solicitacaoExame2.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExame2);
		
		Date inicio = DateUtil.criarDataMesAno(01, 01, 2010);
		Date fim = DateUtil.criarDataMesAno(01, 05, 2010);
		String[] motivos = new String[]{MotivoSolicitacaoExame.PERIODICO};
		
		assertEquals(2,solicitacaoExameDao.findAtendimentosMedicos(inicio, fim, motivos, medicoCoordenador, empresa.getId(), false, false, 'T').size());
	}

	public void testRemoveByCandidato() {
		Candidato candidato1 = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato1);
		
		Candidato candidato2 = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato2);
		
		MedicoCoordenador medicoCoordenador = new MedicoCoordenador();
		medicoCoordenadorDao.save(medicoCoordenador);
		
		SolicitacaoExame solicitacaoExame1 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame1.setCandidato(candidato1);
		solicitacaoExame1.setMedicoCoordenador(medicoCoordenador);
		solicitacaoExame1.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExame1);
		
		SolicitacaoExame solicitacaoExame2 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame2.setCandidato(candidato2);
		solicitacaoExame2.setMedicoCoordenador(medicoCoordenador);
		solicitacaoExame2.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExame2);
		
		Exame exame = ExameFactory.getEntity();
		exameDao.save(exame);
		
		RealizacaoExame realizacaoExame = new RealizacaoExame();
		realizacaoExame.setResultado("NORMAL");
		realizacaoExameDao.save(realizacaoExame);

		ExameSolicitacaoExame exameSolicitacaoExame1 = new ExameSolicitacaoExame();
		exameSolicitacaoExame1.setExame(exame);
		exameSolicitacaoExame1.setSolicitacaoExame(solicitacaoExame1);
		exameSolicitacaoExame1.setRealizacaoExame(realizacaoExame);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame1);
		
		ExameSolicitacaoExame exameSolicitacaoExame2 = new ExameSolicitacaoExame();
		exameSolicitacaoExame2.setExame(exame);
		exameSolicitacaoExame2.setSolicitacaoExame(solicitacaoExame2);
		exameSolicitacaoExame2.setRealizacaoExame(realizacaoExame);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame2);
		
		assertEquals(solicitacaoExame1.getId(), ( (SolicitacaoExame)solicitacaoExameDao.findByIdProjection(solicitacaoExame1.getId())).getId()) ;
		assertEquals(solicitacaoExame2.getId(), ( (SolicitacaoExame)solicitacaoExameDao.findByIdProjection(solicitacaoExame2.getId())).getId()) ;
		
		solicitacaoExameDao.removeByCandidato(candidato1.getId());
		
		assertNull(solicitacaoExameDao.findByIdProjection(solicitacaoExame1.getId())) ;
		assertEquals(solicitacaoExame2.getId(), ( (SolicitacaoExame)solicitacaoExameDao.findByIdProjection(solicitacaoExame2.getId())).getId()) ;
	}
	
	public void testRemoveByColaborador() {
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador2);
		
		MedicoCoordenador medicoCoordenador = new MedicoCoordenador();
		medicoCoordenadorDao.save(medicoCoordenador);
		
		SolicitacaoExame solicitacaoExame1 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame1.setColaborador(colaborador1);
		solicitacaoExame1.setMedicoCoordenador(medicoCoordenador);
		solicitacaoExame1.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExame1);
		
		SolicitacaoExame solicitacaoExame2 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame2.setColaborador(colaborador2);
		solicitacaoExame2.setMedicoCoordenador(medicoCoordenador);
		solicitacaoExame2.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExameDao.save(solicitacaoExame2);
		
		Exame exame = ExameFactory.getEntity();
		exameDao.save(exame);
		
		RealizacaoExame realizacaoExame = new RealizacaoExame();
		realizacaoExame.setResultado("NORMAL");
		realizacaoExameDao.save(realizacaoExame);

		ExameSolicitacaoExame exameSolicitacaoExame1 = new ExameSolicitacaoExame();
		exameSolicitacaoExame1.setExame(exame);
		exameSolicitacaoExame1.setSolicitacaoExame(solicitacaoExame1);
		exameSolicitacaoExame1.setRealizacaoExame(realizacaoExame);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame1);
		
		ExameSolicitacaoExame exameSolicitacaoExame2 = new ExameSolicitacaoExame();
		exameSolicitacaoExame2.setExame(exame);
		exameSolicitacaoExame2.setSolicitacaoExame(solicitacaoExame2);
		exameSolicitacaoExame2.setRealizacaoExame(realizacaoExame);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame2);
		
		assertEquals(solicitacaoExame1.getId(), ( (SolicitacaoExame)solicitacaoExameDao.findByIdProjection(solicitacaoExame1.getId())).getId()) ;
		assertEquals(solicitacaoExame2.getId(), ( (SolicitacaoExame)solicitacaoExameDao.findByIdProjection(solicitacaoExame2.getId())).getId()) ;
		
		solicitacaoExameDao.removeByColaborador(colaborador1.getId());
		
		assertNull(solicitacaoExameDao.findByIdProjection(solicitacaoExame1.getId())) ;
		assertEquals(solicitacaoExame2.getId(), ( (SolicitacaoExame)solicitacaoExameDao.findByIdProjection(solicitacaoExame2.getId())).getId()) ;
	}
	
	public void testFindProximaOrdem() 
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		
		MedicoCoordenador medicoCoordenador = new MedicoCoordenador();
		medicoCoordenadorDao.save(medicoCoordenador);
		
		Date data = DateUtil.criarDataMesAno(1, 1, 2012);
		Date data2 = DateUtil.criarDataMesAno(2, 1, 2012);
		
		SolicitacaoExame solicitacaoExame1 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame1.setCandidato(candidato);
		solicitacaoExame1.setMedicoCoordenador(medicoCoordenador);
		solicitacaoExame1.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExame1.setData(data);
		solicitacaoExame1.setOrdem(5);
		solicitacaoExameDao.save(solicitacaoExame1);
		
		SolicitacaoExame solicitacaoExame2 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame2.setCandidato(candidato);
		solicitacaoExame2.setMedicoCoordenador(medicoCoordenador);
		solicitacaoExame2.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExame2.setData(data);
		solicitacaoExame2.setOrdem(6);
		solicitacaoExameDao.save(solicitacaoExame2);
		
		SolicitacaoExame solicitacaoExame3 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame3.setCandidato(candidato);
		solicitacaoExame3.setMedicoCoordenador(medicoCoordenador);
		solicitacaoExame3.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExame3.setData(data2);
		solicitacaoExame3.setOrdem(2);
		solicitacaoExameDao.save(solicitacaoExame3);
		
		assertEquals(new Integer(7), solicitacaoExameDao.findProximaOrdem(data));
	}
	
	public void testAjustaOrdem() 
	{
		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		
		MedicoCoordenador medicoCoordenador = new MedicoCoordenador();
		medicoCoordenadorDao.save(medicoCoordenador);
		
		Date data = DateUtil.criarDataMesAno(1, 1, 2012);
		Date data2 = DateUtil.criarDataMesAno(2, 1, 2012);
		
		SolicitacaoExame solicitacaoExame1 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame1.setCandidato(candidato);
		solicitacaoExame1.setMedicoCoordenador(medicoCoordenador);
		solicitacaoExame1.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExame1.setData(data);
		solicitacaoExame1.setOrdem(5);
		solicitacaoExameDao.save(solicitacaoExame1);
		
		SolicitacaoExame solicitacaoExame2 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame2.setCandidato(candidato);
		solicitacaoExame2.setMedicoCoordenador(medicoCoordenador);
		solicitacaoExame2.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExame2.setData(data);
		solicitacaoExame2.setOrdem(6);
		solicitacaoExameDao.save(solicitacaoExame2);
		
		SolicitacaoExame solicitacaoExame3 = SolicitacaoExameFactory.getEntity();
		solicitacaoExame3.setCandidato(candidato);
		solicitacaoExame3.setMedicoCoordenador(medicoCoordenador);
		solicitacaoExame3.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		solicitacaoExame3.setData(data2);
		solicitacaoExame3.setOrdem(2);
		solicitacaoExameDao.save(solicitacaoExame3);
		
		solicitacaoExameDao.ajustaOrdem(data, solicitacaoExame1.getOrdem(), solicitacaoExame2.getOrdem(), 1);
		solicitacaoExameDao.getHibernateTemplateByGenericDao().flush();
		
		solicitacaoExame1 = solicitacaoExameDao.findByIdProjection(solicitacaoExame1.getId());
		solicitacaoExame2 = solicitacaoExameDao.findByIdProjection(solicitacaoExame2.getId());
		
		assertEquals(new Integer(6), solicitacaoExame1.getOrdem());
		assertEquals(new Integer(7), solicitacaoExame2.getOrdem());
	}
	
	public void setMedicoCoordenadorDao(MedicoCoordenadorDao medicoCoordenadorDao) {
		this.medicoCoordenadorDao = medicoCoordenadorDao;
	}
	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}
	public void setCandidatoDao(CandidatoDao candidatoDao)
	{
		this.candidatoDao = candidatoDao;
	}
	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}
	public void setSolicitacaoExameDao(SolicitacaoExameDao solicitacaoExameDao)
	{
		this.solicitacaoExameDao = solicitacaoExameDao;
	}

	public void setExameSolicitacaoExameDao(ExameSolicitacaoExameDao exameSolicitacaoExameDao) {
		this.exameSolicitacaoExameDao = exameSolicitacaoExameDao;
	}

	public void setExameDao(ExameDao exameDao) {
		this.exameDao = exameDao;
	}

	public void setRealizacaoExameDao(RealizacaoExameDao realizacaoExameDao) {
		this.realizacaoExameDao = realizacaoExameDao;
	}

	public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao) {
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

	public void setSolicitacaoDao(SolicitacaoDao solicitacaoDao) {
		this.solicitacaoDao = solicitacaoDao;
	}

	public void setCandidatoSolicitacaoDao(
			CandidatoSolicitacaoDao candidatoSolicitacaoDao) {
		this.candidatoSolicitacaoDao = candidatoSolicitacaoDao;
	}

	public void setFuncaoDao(FuncaoDao funcaoDao) {
		this.funcaoDao = funcaoDao;
	}

	public void setClinicaAutorizadaDao(ClinicaAutorizadaDao clinicaAutorizadaDao) {
		this.clinicaAutorizadaDao = clinicaAutorizadaDao;
	}
}
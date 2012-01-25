package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.ExameDao;
import com.fortes.rh.dao.sesmt.ExameSolicitacaoExameDao;
import com.fortes.rh.dao.sesmt.MedicoCoordenadorDao;
import com.fortes.rh.dao.sesmt.RealizacaoExameDao;
import com.fortes.rh.dao.sesmt.SolicitacaoExameDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.MotivoSolicitacaoExame;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoPessoa;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.model.sesmt.RealizacaoExame;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
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
	
	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Date dataIni = DateUtil.criarDataMesAno(01, 01, 2010);
		Date dataFim = DateUtil.criarDataMesAno(01, 05, 2010);
		
		solicitacaoExameDao.findAllSelect(1, 15, empresa.getId(), dataIni, dataFim, TipoPessoa.TODOS, "", "", "", null, null);
	}

	public void testTransferir()
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

		solicitacaoExameDao.transferir(empresa.getId(), candidato.getId(), colaboradorContratado.getId());

		Collection<SolicitacaoExame> resultadoCandidato = solicitacaoExameDao.findByCandidatoOuColaborador(TipoPessoa.CANDIDATO, candidato.getId(), null);
		Collection<SolicitacaoExame> resultadoColaborador = solicitacaoExameDao.findByCandidatoOuColaborador(TipoPessoa.COLABORADOR, colaboradorContratado.getId(), null);

		assertEquals(0, resultadoCandidato.size());
		assertEquals(1, resultadoColaborador.size());
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
		
		assertEquals(2,solicitacaoExameDao.findAtendimentosMedicos(inicio, fim, motivos, medicoCoordenador, empresa.getId(), false, false).size());
//		assertEquals(2,solicitacaoExameDao.findAtendimentosMedicos(inicio, fim, motivo, medicoCoordenador, empresa.getId(), true).size());
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
}
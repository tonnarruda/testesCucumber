package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.ctc.wstx.util.DataUtil;
import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.ExameDao;
import com.fortes.rh.dao.sesmt.ExameSolicitacaoExameDao;
import com.fortes.rh.dao.sesmt.RealizacaoExameDao;
import com.fortes.rh.dao.sesmt.SolicitacaoExameDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.RealizacaoExame;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.ExameFactory;
import com.fortes.rh.test.factory.sesmt.RealizacaoExameFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoExameFactory;
import com.fortes.rh.util.DateUtil;

public class ExameSolicitacaoExameDaoHibernateTest extends GenericDaoHibernateTest<ExameSolicitacaoExame>
{
	ExameSolicitacaoExameDao exameSolicitacaoExameDao;
	ExameDao exameDao;
	SolicitacaoExameDao solicitacaoExameDao;
	RealizacaoExameDao realizacaoExameDao;
	EmpresaDao empresaDao;
	ColaboradorDao colaboradorDao;
	CandidatoDao candidatoDao;

	public ExameSolicitacaoExame getEntity()
	{
		ExameSolicitacaoExame exameSolicitacaoExame = new ExameSolicitacaoExame();
		exameSolicitacaoExame.setId(null);

		return exameSolicitacaoExame;
	}

	@Override
	public GenericDao<ExameSolicitacaoExame> getGenericDao()
	{
		return exameSolicitacaoExameDao;
	}

	public void setExameSolicitacaoExameDao(ExameSolicitacaoExameDao exameSolicitacaoExameDao)
	{
		this.exameSolicitacaoExameDao = exameSolicitacaoExameDao;
	}

	public void testFindBySolicitacaoExame()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Exame exame = ExameFactory.getEntity();
		exame.setEmpresa(empresa);
		exameDao.save(exame);

		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity();
		solicitacaoExame.setEmpresa(empresa);
		solicitacaoExameDao.save(solicitacaoExame);

		RealizacaoExame realizacaoExame = new RealizacaoExame();
		realizacaoExame.setResultado("NORMAL");
		realizacaoExameDao.save(realizacaoExame);

		ExameSolicitacaoExame exameSolicitacaoExame = getEntity();
		exameSolicitacaoExame.setExame(exame);
		exameSolicitacaoExame.setSolicitacaoExame(solicitacaoExame);
		exameSolicitacaoExame.setRealizacaoExame(realizacaoExame);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame);

		Collection<ExameSolicitacaoExame> resultado = exameSolicitacaoExameDao.findBySolicitacaoExame(solicitacaoExame.getId());

		assertEquals(1, resultado.size());
		assertEquals(solicitacaoExame.getId(), ((ExameSolicitacaoExame)resultado.toArray()[0]).getSolicitacaoExame().getId());

		resultado.clear();
		resultado = exameSolicitacaoExameDao.findBySolicitacaoExame(new Long[]{solicitacaoExame.getId()});
		assertEquals(1, resultado.size());
	}

	public void testRemoveAllBySolicitacaoExame()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Exame exame = ExameFactory.getEntity();
		exame.setEmpresa(empresa);
		exameDao.save(exame);

		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity();
		solicitacaoExame.setEmpresa(empresa);
		solicitacaoExameDao.save(solicitacaoExame);

		ExameSolicitacaoExame exameSolicitacaoExame = getEntity();
		exameSolicitacaoExame.setExame(exame);
		exameSolicitacaoExame.setSolicitacaoExame(solicitacaoExame);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame);

		exameSolicitacaoExameDao.removeAllBySolicitacaoExame(solicitacaoExame.getId());
	}
	
	public void testFindDataSolicitacaoExameComColaborador()
	{
		Exame exame = ExameFactory.getEntity();
		exame.setNome("ColaboradorExame");
		exameDao.save(exame);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("Colaborador");
		colaboradorDao.save(colaborador);
		
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity();
		solicitacaoExame.setColaborador(colaborador);
		solicitacaoExameDao.save(solicitacaoExame);
		
		RealizacaoExame realizacaoExame = new RealizacaoExame();
		realizacaoExame.setData(DateUtil.criarAnoMesDia(2010, 01, 01));
		realizacaoExame.setResultado("Resultado");
		realizacaoExameDao.save(realizacaoExame);
		
		ExameSolicitacaoExame exameSolicitacaoExame = getEntity();
		exameSolicitacaoExame.setPeriodicidade(12);
		exameSolicitacaoExame.setExame(exame);
		exameSolicitacaoExame.setSolicitacaoExame(solicitacaoExame);
		exameSolicitacaoExame.setRealizacaoExame(realizacaoExame);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame);
				
		ExameSolicitacaoExame resultadoColaborador = exameSolicitacaoExameDao.findDataSolicitacaoExame(colaborador.getId(), null, exame.getId());
		assertEquals("Colaborador", resultadoColaborador.getColaboradorNome());
	}

	public void testFindDataSolicitacaoExameComCandidato()
	{
		Exame exame = ExameFactory.getEntity();
		exame.setNome("CandidatoExame");
		exameDao.save(exame);
		
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setNome("Candidato");
		candidatoDao.save(candidato);
		
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity();
		solicitacaoExame.setCandidato(candidato);
		solicitacaoExameDao.save(solicitacaoExame);
		
		RealizacaoExame realizacaoExame = new RealizacaoExame();
		realizacaoExame.setData(DateUtil.criarAnoMesDia(2010, 01, 01));
		realizacaoExame.setResultado("Resultado");
		realizacaoExameDao.save(realizacaoExame);
		
		ExameSolicitacaoExame exameSolicitacaoExame = getEntity();
		exameSolicitacaoExame.setPeriodicidade(12);
		exameSolicitacaoExame.setExame(exame);
		exameSolicitacaoExame.setSolicitacaoExame(solicitacaoExame);
		exameSolicitacaoExame.setRealizacaoExame(realizacaoExame);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame);
		
		ExameSolicitacaoExame resultadoCandidato = exameSolicitacaoExameDao.findDataSolicitacaoExame(null,candidato.getId(),exame.getId());
		assertEquals("Candidato", resultadoCandidato.getCandidatoNome());
	}

	public void testFindIdColaboradorOUCandidato()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Exame exame = ExameFactory.getEntity();
		exame.setEmpresa(empresa);
		exame.setNome("teste");
		exameDao.save(exame);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("Colaborador");
		colaboradorDao.save(colaborador);
		
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setNome("Candidato");
		candidatoDao.save(candidato);
		
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity();
		solicitacaoExame.setEmpresa(empresa);
		solicitacaoExame.setColaborador(colaborador);
		solicitacaoExame.setCandidato(candidato);
		solicitacaoExameDao.save(solicitacaoExame);

		ExameSolicitacaoExame exameSolicitacaoExame = getEntity();
		exameSolicitacaoExame.setExame(exame);
		exameSolicitacaoExame.setSolicitacaoExame(solicitacaoExame);
		exameSolicitacaoExameDao.save(exameSolicitacaoExame);
		
		ExameSolicitacaoExame resultado = exameSolicitacaoExameDao.findIdColaboradorOUCandidato(solicitacaoExame.getId(), exame.getId());
		
		assertEquals(colaborador.getId(), resultado.getColaboradorId());
		assertEquals(candidato.getId(), resultado.getCandidatoId());
	}

	public void setSolicitacaoExameDao(SolicitacaoExameDao solicitacaoExameDao)
	{
		this.solicitacaoExameDao = solicitacaoExameDao;
	}

	public void setExameDao(ExameDao exameDao)
	{
		this.exameDao = exameDao;
	}

	public void setRealizacaoExameDao(RealizacaoExameDao realizacaoExameDao)
	{
		this.realizacaoExameDao = realizacaoExameDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public EmpresaDao getEmpresaDao() {
		return empresaDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}

	public void setCandidatoDao(CandidatoDao candidatoDao) {
		this.candidatoDao = candidatoDao;
	}
}
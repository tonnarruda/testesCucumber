package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.ExameDao;
import com.fortes.rh.dao.sesmt.ExameSolicitacaoExameDao;
import com.fortes.rh.dao.sesmt.RealizacaoExameDao;
import com.fortes.rh.dao.sesmt.SolicitacaoExameDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.RealizacaoExame;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.ExameFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoExameFactory;

public class ExameSolicitacaoExameDaoHibernateTest extends GenericDaoHibernateTest<ExameSolicitacaoExame>
{
	ExameSolicitacaoExameDao exameSolicitacaoExameDao;
	ExameDao exameDao;
	SolicitacaoExameDao solicitacaoExameDao;
	RealizacaoExameDao realizacaoExameDao;
	EmpresaDao empresaDao;

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
}
package com.fortes.rh.test.dao.hibernate.pesquisa;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.dao.pesquisa.EntrevistaDao;
import com.fortes.rh.dao.pesquisa.QuestionarioDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Entrevista;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.EntrevistaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;

public class EntrevistaDaoHibernateTest extends GenericDaoHibernateTest<Entrevista>
{
	private EntrevistaDao entrevistaDao;
	private QuestionarioDao questionarioDao;
	private EmpresaDao empresaDao;
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;

	public Entrevista getEntity()
	{
		return EntrevistaFactory.getEntity();
	}
	
	public void testGetIdByQuestionario() throws Exception
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);
		
		Entrevista entrevista = EntrevistaFactory.getEntity();
		entrevista.setQuestionario(questionario);
		entrevista = entrevistaDao.save(entrevista);
		
		assertEquals(entrevista.getId(), entrevistaDao.getIdByQuestionario(questionario.getId()));
	}
		
	public void testFindToList() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setEmpresa(empresa);
		questionario = questionarioDao.save(questionario);
		
		Entrevista entrevista = EntrevistaFactory.getEntity();
		entrevista.setQuestionario(questionario);
		entrevista = entrevistaDao.save(entrevista);
		
		ColaboradorQuestionario colaboradorQuestionario1 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario1.setQuestionario(questionario);
		colaboradorQuestionario1 = colaboradorQuestionarioDao.save(colaboradorQuestionario1);
		
		ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario2.setQuestionario(questionario);
		colaboradorQuestionario2 = colaboradorQuestionarioDao.save(colaboradorQuestionario2);
		
		assertEquals(1, entrevistaDao.findToList(empresa.getId(), 1, 10).size());
	}
	
	public void testFindByIdProjection() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setEmpresa(empresa);
		questionario = questionarioDao.save(questionario);
		
		Entrevista entrevista = EntrevistaFactory.getEntity();
		entrevista.setQuestionario(questionario);
		entrevista = entrevistaDao.save(entrevista);
		
		assertEquals(entrevista, entrevistaDao.findByIdProjection(entrevista.getId()));
	}
	
	public void testVerificaEmpresaDoQuestionario() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setEmpresa(empresa);
		questionario = questionarioDao.save(questionario);
		
		Entrevista entrevista = EntrevistaFactory.getEntity();
		entrevista.setQuestionario(questionario);
		entrevista = entrevistaDao.save(entrevista);
		
		assertTrue(entrevistaDao.verificaEmpresaDoQuestionario(entrevista.getId(), empresa.getId()));
	}
	
	public void testFindAllSelect() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setEmpresa(empresa);
		questionario = questionarioDao.save(questionario);
		
		Entrevista entrevista = EntrevistaFactory.getEntity();
		entrevista.setQuestionario(questionario);
		entrevista.setAtiva(true);
		entrevista = entrevistaDao.save(entrevista);
		
		Entrevista entrevista2 = EntrevistaFactory.getEntity();
		entrevista2.setQuestionario(questionario);
		entrevista2.setAtiva(false);
		entrevista2 = entrevistaDao.save(entrevista2);
		
		assertEquals(1, entrevistaDao.findAllSelect(empresa.getId(), new Boolean(true)).size());
	}
	
	public void testGetCount() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setEmpresa(empresa);
		questionario = questionarioDao.save(questionario);
		
		Entrevista entrevista = EntrevistaFactory.getEntity();
		entrevista.setQuestionario(questionario);
		entrevista = entrevistaDao.save(entrevista);
		
		assertEquals(new Integer(1), entrevistaDao.getCount(empresa.getId()));
	}
	
	public GenericDao<Entrevista> getGenericDao()
	{
		return entrevistaDao;
	}

	public void setEntrevistaDao(EntrevistaDao entrevistaDao)
	{
		this.entrevistaDao = entrevistaDao;
	}

	public void setQuestionarioDao(QuestionarioDao questionarioDao)
	{
		this.questionarioDao = questionarioDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setColaboradorQuestionarioDao(ColaboradorQuestionarioDao colaboradorQuestionarioDao)
	{
		this.colaboradorQuestionarioDao = colaboradorQuestionarioDao;
	}
}
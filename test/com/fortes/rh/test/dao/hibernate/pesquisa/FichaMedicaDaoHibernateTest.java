package com.fortes.rh.test.dao.hibernate.pesquisa;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.dao.pesquisa.FichaMedicaDao;
import com.fortes.rh.dao.pesquisa.QuestionarioDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.FichaMedica;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.FichaMedicaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;

public class FichaMedicaDaoHibernateTest extends GenericDaoHibernateTest<FichaMedica>
{
	private FichaMedicaDao fichaMedicaDao;
	private QuestionarioDao questionarioDao;
	private EmpresaDao empresaDao;
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;

	public FichaMedica getEntity()
	{
		return FichaMedicaFactory.getEntity();
	}

	public void testGetIdByQuestionario() throws Exception
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);

		FichaMedica fichaMedica = FichaMedicaFactory.getEntity();
		fichaMedica.setQuestionario(questionario);
		fichaMedica = fichaMedicaDao.save(fichaMedica);

		assertEquals(fichaMedica.getId(), fichaMedicaDao.getIdByQuestionario(questionario.getId()));
	}

	public void testFindToList() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setEmpresa(empresa);
		questionario = questionarioDao.save(questionario);

		FichaMedica fichaMedica = FichaMedicaFactory.getEntity();
		fichaMedica.setQuestionario(questionario);
		fichaMedica = fichaMedicaDao.save(fichaMedica);

		ColaboradorQuestionario colaboradorQuestionario1 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario1.setQuestionario(questionario);
		colaboradorQuestionario1 = colaboradorQuestionarioDao.save(colaboradorQuestionario1);

		ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario2.setQuestionario(questionario);
		colaboradorQuestionario2 = colaboradorQuestionarioDao.save(colaboradorQuestionario2);

		assertEquals(1, fichaMedicaDao.findToList(empresa.getId(), 1, 10).size());
	}

	public void testFindByIdProjection() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setEmpresa(empresa);
		questionario = questionarioDao.save(questionario);

		FichaMedica fichaMedica = FichaMedicaFactory.getEntity();
		fichaMedica.setQuestionario(questionario);
		fichaMedica = fichaMedicaDao.save(fichaMedica);

		assertEquals(fichaMedica, fichaMedicaDao.findByIdProjection(fichaMedica.getId()));
	}
	
	public void testFindByQuestionario() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setEmpresa(empresa);
		questionario = questionarioDao.save(questionario);
		
		FichaMedica fichaMedica = FichaMedicaFactory.getEntity();
		fichaMedica.setQuestionario(questionario);
		fichaMedica = fichaMedicaDao.save(fichaMedica);
		
		assertEquals(fichaMedica, fichaMedicaDao.findByQuestionario(questionario.getId()));
	}

	public void testVerificaEmpresaDoQuestionario() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setEmpresa(empresa);
		questionario = questionarioDao.save(questionario);

		FichaMedica fichaMedica = FichaMedicaFactory.getEntity();
		fichaMedica.setQuestionario(questionario);
		fichaMedica = fichaMedicaDao.save(fichaMedica);

		assertTrue(fichaMedicaDao.verificaEmpresaDoQuestionario(fichaMedica.getId(), empresa.getId()));
	}

	public void testFindAllSelect() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setEmpresa(empresa);
		questionario = questionarioDao.save(questionario);

		FichaMedica fichaMedica = FichaMedicaFactory.getEntity();
		fichaMedica.setQuestionario(questionario);
		fichaMedica.setAtiva(true);
		fichaMedica = fichaMedicaDao.save(fichaMedica);

		FichaMedica fichaMedica2 = FichaMedicaFactory.getEntity();
		fichaMedica2.setQuestionario(questionario);
		fichaMedica2.setAtiva(false);
		fichaMedica2 = fichaMedicaDao.save(fichaMedica2);

		assertEquals(1, fichaMedicaDao.findAllSelect(empresa.getId(), new Boolean(true)).size());
	}

	public void testGetCount() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setEmpresa(empresa);
		questionario = questionarioDao.save(questionario);

		FichaMedica fichaMedica = FichaMedicaFactory.getEntity();
		fichaMedica.setQuestionario(questionario);
		fichaMedica = fichaMedicaDao.save(fichaMedica);

		assertEquals(new Integer(1), fichaMedicaDao.getCount(empresa.getId()));
	}

//	public void testFindByColaborador()
//	{
//		Empresa empresa = EmpresaFactory.getEmpresa();
//		empresa = empresaDao.save(empresa);
//
//		Questionario questionario = QuestionarioFactory.getEntity();
//		questionario.setEmpresa(empresa);
//		questionario = questionarioDao.save(questionario);
//
//		Colaborador colaborador = ColaboradorFactory.getEntity();
//
//
//		FichaMedica fichaMedica = FichaMedicaFactory.getEntity();
//		fichaMedica.setQuestionario(questionario);
//		fichaMedica = fichaMedicaDao.save(fichaMedica);
//
//	}

	public GenericDao<FichaMedica> getGenericDao()
	{
		return fichaMedicaDao;
	}

	public void setFichaMedicaDao(FichaMedicaDao fichaMedicaDao)
	{
		this.fichaMedicaDao = fichaMedicaDao;
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
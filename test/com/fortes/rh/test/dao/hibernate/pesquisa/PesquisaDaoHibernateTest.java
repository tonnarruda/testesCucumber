package com.fortes.rh.test.dao.hibernate.pesquisa;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.pesquisa.PesquisaDao;
import com.fortes.rh.dao.pesquisa.QuestionarioDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.PesquisaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;

public class PesquisaDaoHibernateTest extends GenericDaoHibernateTest<Pesquisa>
{
	private PesquisaDao pesquisaDao;
	private QuestionarioDao questionarioDao;
	private EmpresaDao empresaDao;

	public Pesquisa getEntity()
	{
		return PesquisaFactory.getEntity();
	}

	public void testFindByIdProjection() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setEmpresa(empresa);
		questionario = questionarioDao.save(questionario);

		Pesquisa pesquisa = PesquisaFactory.getEntity();
		pesquisa.setQuestionario(questionario);
		pesquisa = pesquisaDao.save(pesquisa);

		assertEquals(pesquisa, pesquisaDao.findByIdProjection(pesquisa.getId()));
	}

	public void testFindByQuestionario() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setEmpresa(empresa);
		questionario = questionarioDao.save(questionario);

		Pesquisa pesquisa = PesquisaFactory.getEntity();
		pesquisa.setQuestionario(questionario);
		pesquisa = pesquisaDao.save(pesquisa);

		assertEquals(pesquisa, pesquisaDao.findByQuestionario(questionario.getId()));
	}

	public void testGetIdByQuestionario() throws Exception
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);

		Pesquisa pesquisa = PesquisaFactory.getEntity();
		pesquisa.setQuestionario(questionario);
		pesquisa = pesquisaDao.save(pesquisa);

		assertEquals(pesquisa.getId(), pesquisaDao.getIdByQuestionario(questionario.getId()));
	}

	public void testVerificaEmpresaDoQuestionario() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setEmpresa(empresa);
		questionario = questionarioDao.save(questionario);

		Pesquisa pesquisa = PesquisaFactory.getEntity();
		pesquisa.setQuestionario(questionario);
		pesquisa = pesquisaDao.save(pesquisa);

		assertTrue(pesquisaDao.verificaEmpresaDoQuestionario(pesquisa.getId(), empresa.getId()));
	}

	public void testFindToList() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setEmpresa(empresa);
		questionario = questionarioDao.save(questionario);

		Pesquisa pesquisa = PesquisaFactory.getEntity();
		pesquisa.setQuestionario(questionario);
		pesquisa = pesquisaDao.save(pesquisa);

		assertTrue(pesquisaDao.findToList(empresa.getId(), 1, 10, "", null).size() > 0);
	}

	public void testRemoverPesquisaDoQuestionario()
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionarioDao.save(questionario);

		Pesquisa pesquisa = PesquisaFactory.getEntity();
		pesquisa.setQuestionario(questionario);
		pesquisa = pesquisaDao.save(pesquisa);

		pesquisaDao.removerPesquisaDoQuestionario(questionario.getId());
	}

	public void testGetCount() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setEmpresa(empresa);
		questionario = questionarioDao.save(questionario);

		Pesquisa pesquisa = PesquisaFactory.getEntity();
		pesquisa.setQuestionario(questionario);
		pesquisa = pesquisaDao.save(pesquisa);

		assertEquals(new Integer(1), pesquisaDao.getCount(empresa.getId(), null));
	}

	public GenericDao<Pesquisa> getGenericDao()
	{
		return pesquisaDao;
	}

	public void setPesquisaDao(PesquisaDao pesquisaDao)
	{
		this.pesquisaDao = pesquisaDao;
	}

	public void setQuestionarioDao(QuestionarioDao questionarioDao)
	{
		this.questionarioDao = questionarioDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}
}
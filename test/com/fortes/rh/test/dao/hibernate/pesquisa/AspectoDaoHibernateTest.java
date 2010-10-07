package com.fortes.rh.test.dao.hibernate.pesquisa;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.pesquisa.AspectoDao;
import com.fortes.rh.dao.pesquisa.QuestionarioDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.AspectoFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;

public class AspectoDaoHibernateTest extends GenericDaoHibernateTest<Aspecto>
{
	private AspectoDao aspectoDao;
	private QuestionarioDao questionarioDao;
	private EmpresaDao empresaDao;
	private AvaliacaoDao avaliacaoDao;

	public Aspecto getEntity()
	{
		Aspecto aspecto = new Aspecto();

		aspecto.setId(null);
		aspecto.setNome("aspecto");

		return aspecto;
	}

	public GenericDao<Aspecto> getGenericDao()
	{
		return aspectoDao;
	}

	public void setAspectoDao(AspectoDao aspectoDao)
	{
		this.aspectoDao = aspectoDao;
	}

	public void testFindByIdProjection()
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);

		Aspecto aspecto = getEntity();
		aspecto.setQuestionario(questionario);
		aspecto = aspectoDao.save(aspecto);

		Aspecto retorno = aspectoDao.findByIdProjection(aspecto.getId());

		assertEquals(aspecto.getId(), retorno.getId());
	}
	
	public void testRemoverAspectosDoQuestionario()
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);
		
		Aspecto aspecto = getEntity();
		aspecto.setQuestionario(questionario);
		aspecto = aspectoDao.save(aspecto);
		
		aspectoDao.removerAspectosDoQuestionario(questionario.getId());
		
		assertEquals(0, aspectoDao.findByQuestionario(questionario.getId()).size());
	}

	public void testFindByQuestionario()
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);

		Aspecto aspecto1 = AspectoFactory.getEntity();
		aspecto1.setQuestionario(questionario);
		aspecto1 = aspectoDao.save(aspecto1);

		Aspecto aspecto2 = AspectoFactory.getEntity();
		aspecto2.setQuestionario(questionario);
		aspecto2 = aspectoDao.save(aspecto2);

		Collection<Aspecto> retorno = aspectoDao.findByQuestionario(questionario.getId());

		assertEquals(2, retorno.size());
	}
	
	public void testGetNomesByAvaliacao()
	{
		Avaliacao avaliacao1 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao1);
		
		Avaliacao avaliacao2 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao2);
		
		Aspecto aspecto1 = AspectoFactory.getEntity();
		aspecto1.setNome("Liderança");
		aspecto1.setAvaliacao(avaliacao1);
		aspectoDao.save(aspecto1);

		Aspecto aspecto2 = AspectoFactory.getEntity();
		aspecto2.setNome("Comunicação");
		aspecto2.setAvaliacao(avaliacao1);
		aspectoDao.save(aspecto2);
		
		Aspecto aspecto3 = AspectoFactory.getEntity();
		aspecto3.setNome("Conhecimento");
		aspecto3.setAvaliacao(avaliacao2);
		aspectoDao.save(aspecto3);
		
		Collection<String> retorno = aspectoDao.getNomesByAvaliacao(avaliacao1.getId());
		
		assertEquals(2, retorno.size());
		assertEquals("Comunicação", retorno.toArray()[0]);
	}
	
	public void testFindByNomeAvaliacao()
	{
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao);
		
		Aspecto aspecto = AspectoFactory.getEntity();
		aspecto.setNome("Liderança");
		aspecto.setAvaliacao(avaliacao);
		aspectoDao.save(aspecto);
		
		assertEquals(aspecto, aspectoDao.findByNomeAvaliacao("Liderança", avaliacao.getId()));
	}

	public void testVerificaEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setEmpresa(empresa);
		questionario = questionarioDao.save(questionario);
		
		Aspecto aspecto = AspectoFactory.getEntity();
		aspecto.setQuestionario(questionario);
		aspecto = aspectoDao.save(aspecto);
		
		assertEquals(true, aspectoDao.verificaEmpresa(aspecto.getId(), empresa.getId()));
	}
	
	public void testVerificaEmpresaFalse()
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);
		
		Aspecto aspecto = AspectoFactory.getEntity();
		aspecto.setQuestionario(questionario);
		aspecto = aspectoDao.save(aspecto);
		
		assertEquals(false, aspectoDao.verificaEmpresa(aspecto.getId(), 3L));
	}
	
	public void testFindByNomeQuestionario()
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);

		Questionario questionario2 = QuestionarioFactory.getEntity();
		questionario2 = questionarioDao.save(questionario2);

		Aspecto aspecto1 = AspectoFactory.getEntity();
		aspecto1.setNome("aaa");
		aspecto1.setQuestionario(questionario);
		aspecto1 = aspectoDao.save(aspecto1);

		Aspecto aspecto2 = AspectoFactory.getEntity();
		aspecto2.setNome("bbb");
		aspecto2.setQuestionario(questionario);
		aspecto2 = aspectoDao.save(aspecto2);

		Aspecto aspecto3 = AspectoFactory.getEntity();
		aspecto3.setNome("aaa");
		aspecto3.setQuestionario(questionario2);
		aspecto3 = aspectoDao.save(aspecto3);

		Aspecto retorno = aspectoDao.findByNomeQuestionario("aaa",questionario.getId());

		assertEquals(aspecto1.getId(), retorno.getId());
	}

	public void setQuestionarioDao(QuestionarioDao questionarioDao)
	{
		this.questionarioDao = questionarioDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setAvaliacaoDao(AvaliacaoDao avaliacaoDao)
	{
		this.avaliacaoDao = avaliacaoDao;
	}
}
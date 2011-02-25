package com.fortes.rh.test.dao.hibernate.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.dao.pesquisa.AspectoDao;
import com.fortes.rh.dao.pesquisa.PerguntaDao;
import com.fortes.rh.dao.pesquisa.PesquisaDao;
import com.fortes.rh.dao.pesquisa.QuestionarioDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.pesquisa.AspectoFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.PesquisaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;

public class PerguntaDaoHibernateTest extends GenericDaoHibernateTest<Pergunta>
{
	private PerguntaDao perguntaDao;
	private PesquisaDao pesquisaDao;
	private QuestionarioDao questionarioDao;
	private AspectoDao aspectoDao;
	private AvaliacaoDao avaliacaoDao;

	public Pergunta getEntity()
	{
		Pergunta pergunta = new Pergunta();

		pergunta.setId(null);
		pergunta.setComentario(false);
		pergunta.setOrdem(1);
		pergunta.setRespostas(null);
		pergunta.setTexto("texto da pergunta");
		pergunta.setTextoComentario("comentário do texto");
		pergunta.setQuestionario(null);
		pergunta.setAspecto(null);
		pergunta.setTipo(1);

		return pergunta;
	}

	public GenericDao<Pergunta> getGenericDao()
	{
		return perguntaDao;
	}

	public void testFindByQuestionario() throws Exception
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);
		questionario = questionarioDao.save(questionario);

		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setQuestionario(questionario);
		pergunta1 = perguntaDao.save(pergunta1);

		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2.setQuestionario(questionario);
		pergunta2 = perguntaDao.save(pergunta2);

		Pergunta pergunta3 = PerguntaFactory.getEntity();
		pergunta3.setQuestionario(questionario);
		pergunta3 = perguntaDao.save(pergunta3);

		Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
		perguntas.add(pergunta1);
		perguntas.add(pergunta2);
		perguntas.add(pergunta3);

		Pesquisa pesquisa = PesquisaFactory.getEntity();
		pesquisa.setQuestionario(questionario);
		pesquisa = pesquisaDao.save(pesquisa);

		Collection<Pergunta> retorno = perguntaDao.findByQuestionario(questionario.getId());

		assertEquals(3, retorno.size());
	}

	public void testFindByQuestionarioComAspecto() throws Exception
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);
		questionario = questionarioDao.save(questionario);
		
		Aspecto aspectoA = new Aspecto();
		aspectoA.setNome("Aspecto A");
		aspectoDao.save(aspectoA);
		
		Aspecto aspectoB = new Aspecto();
		aspectoB.setNome("Aspecto B");
		aspectoDao.save(aspectoB);
		
		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setQuestionario(questionario);
		pergunta1.setAspecto(aspectoA);
		pergunta1 = perguntaDao.save(pergunta1);
		
		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2.setQuestionario(questionario);
		pergunta2.setAspecto(aspectoB);
		pergunta2 = perguntaDao.save(pergunta2);
		
		Pergunta pergunta3 = PerguntaFactory.getEntity();
		pergunta3.setQuestionario(questionario);
		pergunta3 = perguntaDao.save(pergunta3);
		
		Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
		perguntas.add(pergunta1);
		perguntas.add(pergunta2);
		perguntas.add(pergunta3);
		
		Pesquisa pesquisa = PesquisaFactory.getEntity();
		pesquisa.setQuestionario(questionario);
		pesquisa = pesquisaDao.save(pesquisa);
		
		Collection<Pergunta> retorno = perguntaDao.findByQuestionarioAgrupadoPorAspecto(questionario.getId(), true);
		
		assertEquals(3, retorno.size());
	}

	public void testFindByQuestionarioAspecto() throws Exception
	{
		Aspecto aspecto = AspectoFactory.getEntity();
		aspecto = aspectoDao.save(aspecto);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);

		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setAspecto(aspecto);
		pergunta1.setQuestionario(questionario);
		pergunta1 = perguntaDao.save(pergunta1);

		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2.setAspecto(aspecto);
		pergunta2.setQuestionario(questionario);
		pergunta2 = perguntaDao.save(pergunta2);

		Pergunta pergunta3 = PerguntaFactory.getEntity();
		pergunta3.setQuestionario(questionario);
		pergunta3 = perguntaDao.save(pergunta3);

		Collection<Pergunta> retorno = perguntaDao.findByQuestionarioAspecto(questionario.getId(), new Long[]{aspecto.getId()});

		assertEquals(2, retorno.size());
	}

	public void testFindByQuestionarioAspectoPergunta() throws Exception
	{
		Aspecto aspecto = AspectoFactory.getEntity();
		aspecto = aspectoDao.save(aspecto);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);

		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setAspecto(aspecto);
		pergunta1.setQuestionario(questionario);
		pergunta1 = perguntaDao.save(pergunta1);

		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2.setAspecto(aspecto);
		pergunta2.setQuestionario(questionario);
		pergunta2 = perguntaDao.save(pergunta2);

		Pergunta pergunta3 = PerguntaFactory.getEntity();
		pergunta3.setQuestionario(questionario);
		pergunta3 = perguntaDao.save(pergunta3);

		Collection<Pergunta> retorno = perguntaDao.findByQuestionarioAspectoPergunta(questionario.getId(), new Long[]{aspecto.getId()}, new Long[]{pergunta1.getId(), pergunta2.getId()}, true);

		assertEquals(2, retorno.size());
	}
	
	public void testFindByQuestionarioAspectoPerguntaOrder() throws Exception
	{
		Aspecto aspecto = AspectoFactory.getEntity();
		aspecto = aspectoDao.save(aspecto);
		
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);
		
		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setAspecto(aspecto);
		pergunta1.setQuestionario(questionario);
		pergunta1 = perguntaDao.save(pergunta1);
		
		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2.setAspecto(aspecto);
		pergunta2.setQuestionario(questionario);
		pergunta2 = perguntaDao.save(pergunta2);
		
		Pergunta pergunta3 = PerguntaFactory.getEntity();
		pergunta3.setQuestionario(questionario);
		pergunta3 = perguntaDao.save(pergunta3);
		
		Collection<Pergunta> retorno = perguntaDao.findByQuestionarioAspectoPergunta(questionario.getId(), null, new Long[]{pergunta1.getId(), pergunta2.getId()}, false);
		
		assertEquals(2, retorno.size());
	}

	public void testFindByQuestionarioAspectoPerguntaFalse() throws Exception
	{
		Aspecto aspecto = AspectoFactory.getEntity();
		aspecto = aspectoDao.save(aspecto);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);

		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setAspecto(aspecto);
		pergunta1.setQuestionario(questionario);
		pergunta1 = perguntaDao.save(pergunta1);

		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2.setAspecto(aspecto);
		pergunta2.setQuestionario(questionario);
		pergunta2 = perguntaDao.save(pergunta2);

		Pergunta pergunta3 = PerguntaFactory.getEntity();
		pergunta3.setQuestionario(questionario);
		pergunta3 = perguntaDao.save(pergunta3);

		Collection<Pergunta> retorno = perguntaDao.findByQuestionarioAspectoPergunta(questionario.getId(), new Long[]{aspecto.getId()}, new Long[]{pergunta1.getId(), pergunta2.getId()}, false);

		assertEquals(2, retorno.size());
	}
	
	public void testFindByQuestionarioAspectoPerguntaComAvaliacao() throws Exception
	{
		Aspecto aspecto = AspectoFactory.getEntity();
		aspecto = aspectoDao.save(aspecto);

		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao);

		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setAspecto(aspecto);
		pergunta1.setAvaliacao(avaliacao);
		pergunta1 = perguntaDao.save(pergunta1);

		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2.setAvaliacao(avaliacao);
		pergunta2 = perguntaDao.save(pergunta2);

		Pergunta pergunta3 = PerguntaFactory.getEntity();
		pergunta3.setAvaliacao(avaliacao);
		pergunta3 = perguntaDao.save(pergunta3);

		Collection<Pergunta> retorno = perguntaDao.findByQuestionarioAspectoPergunta(avaliacao.getId(), new Long[]{aspecto.getId()}, new Long[]{pergunta1.getId(), pergunta2.getId()}, false);

		assertEquals(1, retorno.size());
	}

	public void testGetUltimaOrdenacao() throws Exception
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);

		Questionario questionario2 = QuestionarioFactory.getEntity();
		questionario2 = questionarioDao.save(questionario2);

		Pergunta pergunta = PerguntaFactory.getEntity();
		pergunta.setQuestionario(questionario);
		pergunta.setOrdem(3);
		pergunta = perguntaDao.save(pergunta);

		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2.setQuestionario(questionario);
		pergunta2.setOrdem(6);
		pergunta2 = perguntaDao.save(pergunta2);

		int ultimaOrdem = perguntaDao.getUltimaOrdenacao(questionario.getId());

		assertEquals(6, ultimaOrdem);

		int ultimaOrdem2 = perguntaDao.getUltimaOrdenacao(questionario2.getId());

		assertEquals(0, ultimaOrdem2);
	}

	public void testFindByIdProjection()
	{
		Pergunta pergunta = PerguntaFactory.getEntity();
		pergunta = perguntaDao.save(pergunta);

		Pergunta perguntaRetorno = perguntaDao.findByIdProjection(pergunta.getId());

		assertEquals(pergunta.getId(), perguntaRetorno.getId());
	}

	public void testFindUltimaPerguntaObjetiva()
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);

		Questionario questionario2 = QuestionarioFactory.getEntity();
		questionario2 = questionarioDao.save(questionario2);

		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setTipo(TipoPergunta.OBJETIVA);
		pergunta1.setQuestionario(questionario);
		pergunta1.setOrdem(1);
		pergunta1 = perguntaDao.save(pergunta1);

		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2.setTipo(TipoPergunta.OBJETIVA);
		pergunta2.setQuestionario(questionario);
		pergunta2.setOrdem(2);
		pergunta2 = perguntaDao.save(pergunta2);

		Pergunta pergunta3 = PerguntaFactory.getEntity();
		pergunta3.setTipo(TipoPergunta.SUBJETIVA);
		pergunta3.setQuestionario(questionario);
		pergunta3.setOrdem(3);
		pergunta3 = perguntaDao.save(pergunta3);

		Pergunta pergunta4 = PerguntaFactory.getEntity();
		pergunta4.setTipo(TipoPergunta.OBJETIVA);
		pergunta4.setQuestionario(questionario2);
		pergunta4.setOrdem(4);
		pergunta4 = perguntaDao.save(pergunta4);

		Long ultimaPerguntaObjetivaId = perguntaDao.findUltimaPerguntaObjetiva(questionario.getId());

		assertEquals(pergunta2.getId(), ultimaPerguntaObjetivaId);

	}

	public void testFindUltimaPerguntaObjetivaNull()
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);

		Questionario questionario2 = QuestionarioFactory.getEntity();
		questionario2 = questionarioDao.save(questionario2);

		Questionario questionario3 = QuestionarioFactory.getEntity();
		questionario3 = questionarioDao.save(questionario3);

		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setTipo(TipoPergunta.OBJETIVA);
		pergunta1.setQuestionario(questionario);
		pergunta1.setOrdem(1);
		pergunta1 = perguntaDao.save(pergunta1);

		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2.setTipo(TipoPergunta.OBJETIVA);
		pergunta2.setQuestionario(questionario);
		pergunta2.setOrdem(2);
		pergunta2 = perguntaDao.save(pergunta2);

		Pergunta pergunta3 = PerguntaFactory.getEntity();
		pergunta3.setTipo(TipoPergunta.SUBJETIVA);
		pergunta3.setQuestionario(questionario);
		pergunta3.setOrdem(3);
		pergunta3 = perguntaDao.save(pergunta3);

		Pergunta pergunta4 = PerguntaFactory.getEntity();
		pergunta4.setTipo(TipoPergunta.OBJETIVA);
		pergunta4.setQuestionario(questionario2);
		pergunta4.setOrdem(4);
		pergunta4 = perguntaDao.save(pergunta4);

		Long ultimaPerguntaObjetivaId = perguntaDao.findUltimaPerguntaObjetiva(questionario3.getId());

		assertNull(ultimaPerguntaObjetivaId);

	}

	public void testReordenaPergunta()
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);

		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setOrdem(1);
		pergunta1.setQuestionario(questionario);
		pergunta1 = perguntaDao.save(pergunta1);

		assertTrue(perguntaDao.reordenaPergunta(pergunta1.getId(), '+'));

		pergunta1 = perguntaDao.findByIdProjection(pergunta1.getId());

		assertTrue(pergunta1.getOrdem().equals(2));
	}
	
	public void testRemoverPerguntasDoQuestionario()
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);
		
		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setQuestionario(questionario);
		pergunta1 = perguntaDao.save(pergunta1);
		
		perguntaDao.removerPerguntasDoQuestionario(questionario.getId());
		assertEquals(0, perguntaDao.findByQuestionario(questionario.getId()).size());
	}
	
	public void testFindPerguntasDoQuestionario()
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);
		
		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setQuestionario(questionario);
		pergunta1 = perguntaDao.save(pergunta1);
		
		assertEquals(1, perguntaDao.findPerguntasDoQuestionario(questionario.getId()).size());
	}
	
	public void testExistsOrdem()
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);
		
		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setQuestionario(questionario);
		pergunta1.setOrdem(5);
		pergunta1 = perguntaDao.save(pergunta1);
		
		assertEquals(true, perguntaDao.existsOrdem(questionario.getId(), 5));
		assertEquals(false, perguntaDao.existsOrdem(questionario.getId(), 10));
	}

	public void testFindIdByOrdem()
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);

		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setOrdem(1);
		pergunta1.setQuestionario(questionario);
		pergunta1 = perguntaDao.save(pergunta1);

		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2.setOrdem(2);
		pergunta2.setQuestionario(questionario);
		pergunta2 = perguntaDao.save(pergunta2);

		Long perguntaId = perguntaDao.findIdByOrdem(questionario.getId(), pergunta2.getOrdem());

		assertEquals(pergunta2.getId(), perguntaId);
	}

	public void testFindIdByOrdemNull()
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);

		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setOrdem(1);
		pergunta1.setQuestionario(questionario);
		pergunta1 = perguntaDao.save(pergunta1);

		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2.setOrdem(2);
		pergunta2.setQuestionario(questionario);
		pergunta2 = perguntaDao.save(pergunta2);

		Long perguntaId = perguntaDao.findIdByOrdem(questionario.getId(), 654);

		assertNull(perguntaId);
	}

	public void testReposicionarPerguntas()
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);

		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setOrdem(1);
		pergunta1.setQuestionario(questionario);
		pergunta1 = perguntaDao.save(pergunta1);

		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2.setOrdem(3);
		pergunta2.setQuestionario(questionario);
		pergunta2 = perguntaDao.save(pergunta2);

		Pergunta pergunta3 = PerguntaFactory.getEntity();
		pergunta3.setOrdem(4);
		pergunta3.setQuestionario(questionario);
		pergunta3 = perguntaDao.save(pergunta3);

		assertTrue(perguntaDao.reposicionarPerguntas(questionario.getId(), pergunta2.getOrdem(), '-'));

		pergunta1 = perguntaDao.findByIdProjection(pergunta1.getId());
		pergunta2 = perguntaDao.findByIdProjection(pergunta2.getId());
		pergunta3 = perguntaDao.findByIdProjection(pergunta3.getId());

		assertTrue(pergunta1.getOrdem().equals(1));
		assertTrue(pergunta2.getOrdem().equals(2));
		assertTrue(pergunta3.getOrdem().equals(3));
	}

	public void testUpdateOrdem()
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);

		Pergunta pergunta = PerguntaFactory.getEntity();
		pergunta.setOrdem(1);
		pergunta.setQuestionario(questionario);
		pergunta = perguntaDao.save(pergunta);

		int novaOrdem = 5;

		Exception exception = null;
		try
		{
			perguntaDao.updateOrdem(pergunta.getId(),novaOrdem);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			exception = e;
		}

		pergunta = perguntaDao.findByIdProjection(pergunta.getId());

		assertNull(exception);
		assertTrue(pergunta.getOrdem().equals(novaOrdem));
	}

	public void testGetTotalPerguntas()
	{
		Questionario questionario = QuestionarioFactory.getEntity();
		questionario = questionarioDao.save(questionario);

		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setQuestionario(questionario);
		pergunta1 = perguntaDao.save(pergunta1);

		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2.setQuestionario(questionario);
		pergunta2 = perguntaDao.save(pergunta2);

		assertEquals(2, perguntaDao.getTotalPerguntas(questionario.getId()));

	}

	public void setPesquisaDao(PesquisaDao pesquisaDao)
	{
		this.pesquisaDao = pesquisaDao;
	}

	public void setQuestionarioDao(QuestionarioDao questionarioDao)
	{
		this.questionarioDao = questionarioDao;
	}

	public void setPerguntaDao(PerguntaDao perguntaDao)
	{
		this.perguntaDao = perguntaDao;
	}

	public void setAspectoDao(AspectoDao aspectoDao)
	{
		this.aspectoDao = aspectoDao;
	}

	public void setAvaliacaoDao(AvaliacaoDao avaliacaoDao) {
		this.avaliacaoDao = avaliacaoDao;
	}

}
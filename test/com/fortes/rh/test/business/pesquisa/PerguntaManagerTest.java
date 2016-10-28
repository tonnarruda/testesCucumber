package com.fortes.rh.test.business.pesquisa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.pesquisa.AspectoManager;
import com.fortes.rh.business.pesquisa.PerguntaManagerImpl;
import com.fortes.rh.business.pesquisa.RespostaManager;
import com.fortes.rh.dao.pesquisa.PerguntaDao;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.test.factory.pesquisa.AspectoFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.PesquisaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.RespostaFactory;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.web.tags.CheckBox;

public class PerguntaManagerTest extends MockObjectTestCase
{
	private PerguntaManagerImpl perguntaManager = new PerguntaManagerImpl();
	private Mock perguntaDao;
	private Mock respostaManager;
	private Mock aspectoManager;
	private Mock transactionManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        perguntaDao = new Mock(PerguntaDao.class);
        perguntaManager.setDao((PerguntaDao) perguntaDao.proxy());

        transactionManager = new Mock(PlatformTransactionManager.class);
        perguntaManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());

        respostaManager = new Mock(RespostaManager.class);
        perguntaManager.setRespostaManager((RespostaManager)respostaManager.proxy());

        aspectoManager = new Mock(AspectoManager.class);
        perguntaManager.setAspectoManager((AspectoManager)aspectoManager.proxy());
    }
/*
	public void testGetPerguntasRespostaByPesquisa()
	{
		Pesquisa pesquisa = PesquisaFactory.getEntity(1L);

		Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
		pergunta1.setOrdem(1);
		Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
		pergunta2.setOrdem(2);
		Pergunta pergunta3 = PerguntaFactory.getEntity(3L);
		pergunta3.setOrdem(3);
		Pergunta pergunta4 = PerguntaFactory.getEntity(4L);
		pergunta3.setOrdem(4);

		Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
		perguntas.add(pergunta1);
		perguntas.add(pergunta2);
		perguntas.add(pergunta3);

		Resposta resposta1 = RespostaFactory.getEntity(1L);
		resposta1.setPergunta(pergunta1);

		Resposta resposta2 = RespostaFactory.getEntity(2L);
		resposta2.setPergunta(pergunta2);

		Resposta resposta3 = RespostaFactory.getEntity(3L);
		resposta3.setPergunta(pergunta2);

		Resposta resposta4 = RespostaFactory.getEntity(4L);
		resposta4.setPergunta(pergunta4);

		Collection<Resposta> respostas = new ArrayList<Resposta>();

		respostas.add(resposta1);
		respostas.add(resposta2);
		respostas.add(resposta3);
		respostas.add(resposta4);

		CollectionUtil<Pergunta> clu = new CollectionUtil<Pergunta>();
		Long[] perguntaIds = clu.convertCollectionToArrayIds(perguntas);

		perguntaDao.expects(once()).method("findByPesquisa").with(eq(pesquisa.getId())).will(returnValue(perguntas));
		respostaManager.expects(once()).method("findInPerguntaIds").with(eq(perguntaIds)).will(returnValue(respostas));

		Collection<Pergunta> perguntasRespostas = perguntaManager.getPerguntasRespostaByPesquisa(pesquisa.getId());

		Pergunta perguntaRetorno1 = (Pergunta) perguntasRespostas.toArray()[0];
		assertEquals(1, perguntaRetorno1.getRespostas().size());

		Pergunta perguntaRetorno2 = (Pergunta) perguntasRespostas.toArray()[1];
		assertEquals(2, perguntaRetorno2.getRespostas().size());
	}
*/
	public void testGetPerguntasRespostaByQuestionario()
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);

		Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
		pesquisa.setQuestionario(questionario);

		Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
		pergunta1.setQuestionario(questionario);
		pergunta1.setOrdem(1);
		Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
		pergunta2.setQuestionario(questionario);
		pergunta2.setOrdem(2);
		Pergunta pergunta3 = PerguntaFactory.getEntity(3L);
		pergunta3.setQuestionario(questionario);
		pergunta3.setOrdem(3);

		Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
		perguntas.add(pergunta1);
		perguntas.add(pergunta2);
		perguntas.add(pergunta3);

		Resposta resposta1 = RespostaFactory.getEntity(1L);
		resposta1.setPergunta(pergunta1);

		Resposta resposta2 = RespostaFactory.getEntity(2L);
		resposta2.setPergunta(pergunta2);

		Resposta resposta3 = RespostaFactory.getEntity(3L);
		resposta3.setPergunta(pergunta2);

		Collection<Resposta> respostas = new ArrayList<Resposta>();

		respostas.add(resposta1);
		respostas.add(resposta2);
		respostas.add(resposta3);

		CollectionUtil<Pergunta> clu = new CollectionUtil<Pergunta>();
		Long[] perguntaIds = clu.convertCollectionToArrayIds(perguntas);

		perguntaDao.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(perguntas));
		respostaManager.expects(once()).method("findInPerguntaIds").with(eq(perguntaIds)).will(returnValue(respostas));

		Collection<Pergunta> retorno = perguntaManager.getPerguntasRespostaByQuestionario(questionario.getId());

		assertEquals(3, retorno.size());
	}
/*
	public void testGetPerguntasRespostaByPesquisaAspecto()
	{
		Pesquisa pesquisa = PesquisaFactory.getEntity(1L);

		Aspecto aspecto1 = AspectoFactory.getEntity(1L);
		Aspecto aspecto2 = AspectoFactory.getEntity(2L);

		Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
		pergunta1.setAspecto(aspecto1);
		pergunta1.setOrdem(1);
		Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
		pergunta2.setAspecto(aspecto1);
		pergunta2.setOrdem(2);
		Pergunta pergunta3 = PerguntaFactory.getEntity(3L);
		pergunta3.setAspecto(aspecto1);
		pergunta3.setOrdem(3);
		Pergunta pergunta4 = PerguntaFactory.getEntity(4L);
		pergunta4.setAspecto(aspecto2);
		pergunta3.setOrdem(4);

		Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
		perguntas.add(pergunta1);
		perguntas.add(pergunta2);
		perguntas.add(pergunta3);

		Resposta resposta1 = RespostaFactory.getEntity(1L);
		resposta1.setPergunta(pergunta1);

		Resposta resposta2 = RespostaFactory.getEntity(2L);
		resposta2.setPergunta(pergunta2);

		Resposta resposta3 = RespostaFactory.getEntity(3L);
		resposta3.setPergunta(pergunta2);

		Resposta resposta4 = RespostaFactory.getEntity(4L);
		resposta4.setPergunta(pergunta4);

		Collection<Resposta> respostas = new ArrayList<Resposta>();

		respostas.add(resposta1);
		respostas.add(resposta2);
		respostas.add(resposta3);
		respostas.add(resposta4);

		CollectionUtil<Pergunta> clu = new CollectionUtil<Pergunta>();
		Long[] perguntaIds = clu.convertCollectionToArrayIds(perguntas);

		Long[] aspectosIds = new Long[]{aspecto1.getId()};

		perguntaDao.expects(once()).method("findByPesquisaAspecto").with(eq(pesquisa.getId()),eq(aspectosIds)).will(returnValue(perguntas));
		respostaManager.expects(once()).method("findInPerguntaIds").with(eq(perguntaIds)).will(returnValue(respostas));

		Collection<Pergunta> perguntasRespostas = perguntaManager.getPerguntasRespostaByPesquisaAspecto(pesquisa.getId(), aspectosIds);

		assertEquals(3, perguntasRespostas.size());

		Pergunta perguntaRetorno1 = (Pergunta) perguntasRespostas.toArray()[0];
		assertEquals(1, perguntaRetorno1.getRespostas().size());

		Pergunta perguntaRetorno2 = (Pergunta) perguntasRespostas.toArray()[1];
		assertEquals(2, perguntaRetorno2.getRespostas().size());
	}
*/
	public void testGetPerguntasSemAspecto()
	{
		Aspecto aspecto = AspectoFactory.getEntity(1L);

		Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
		pergunta1.setAspecto(aspecto);

		Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
		pergunta2.setAspecto(null);

		Pergunta pergunta3 = PerguntaFactory.getEntity(3L);
		pergunta3.setAspecto(null);

		Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
		perguntas.add(pergunta1);
		perguntas.add(pergunta2);
		perguntas.add(pergunta3);

		Collection<Pergunta> retorno = perguntaManager.getPerguntasSemAspecto(perguntas);

		assertEquals(2, retorno.size());
	}

	public void testGetPerguntasSemAspectoNull()
	{
		Aspecto aspecto = AspectoFactory.getEntity(1L);

		Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
		pergunta1.setAspecto(aspecto);

		Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
		pergunta2.setAspecto(aspecto);

		Pergunta pergunta3 = PerguntaFactory.getEntity(3L);
		pergunta3.setAspecto(aspecto);

		Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
		perguntas.add(pergunta1);
		perguntas.add(pergunta2);
		perguntas.add(pergunta3);

		Collection<Pergunta> retorno = perguntaManager.getPerguntasSemAspecto(perguntas);

		assertNull(retorno);
	}

	public void testSalvarPergunta()
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);

		Aspecto aspecto = AspectoFactory.getEntity(1L);

		Pesquisa pesquisa = PesquisaFactory.getEntity(1L);

		Pergunta pergunta = PerguntaFactory.getEntity();

		pergunta.setQuestionario(questionario);
		pergunta.setPesquisa(pesquisa);
		pergunta.setTipo(TipoPergunta.SUBJETIVA);
		pergunta.setAspecto(aspecto);

		Exception exception = null;

		try
		{
			aspectoManager.expects(once()).method("saveOrGetAspectoByNome").with(eq(aspecto.getNome()),eq(questionario.getId())).will(returnValue(aspecto));
			perguntaDao.expects(once()).method("getUltimaOrdenacao").with(eq(questionario.getId())).will(returnValue(pergunta.getOrdem()));
			perguntaDao.expects(once()).method("save").with(eq(pergunta)).will(returnValue(pergunta));
			perguntaManager.salvarPergunta(pergunta, null, null, null);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testSalvarPerguntaSemAspecto()
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);
		Pergunta pergunta = PerguntaFactory.getEntity(1L);
		Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
		Aspecto aspecto = new Aspecto();

		pergunta.setQuestionario(questionario);
		pergunta.setPesquisa(pesquisa);
		pergunta.setTipo(TipoPergunta.OBJETIVA);
		pergunta.setAspecto(aspecto);

		String[] respostas = new String[]{"","resposta1","resposta2"};
		String[] respostasLimpas = new String[]{"resposta1","resposta2"};

		Integer ordemSugerida = 2;

		Exception exception = null;

		try
		{
			perguntaDao.expects(once()).method("existsOrdem").with(ANYTHING, ANYTHING).will(returnValue(true));
			perguntaDao.expects(once()).method("reposicionarPerguntas").with(eq(pergunta.getQuestionario().getId()),eq(ordemSugerida),eq('+')).will(returnValue(true));
			perguntaDao.expects(once()).method("save").with(eq(pergunta)).will(returnValue(pergunta));
			respostaManager.expects(once()).method("salvarRespostas").with(eq(pergunta.getId()),eq(respostasLimpas), ANYTHING).will(returnValue(RespostaFactory.getCollection(1L)));
			perguntaManager.salvarPergunta(pergunta, respostas, null, ordemSugerida);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testSalvarPerguntaRespostasInsuficiente()
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);
		Pergunta pergunta = PerguntaFactory.getEntity();
		Pesquisa pesquisa = PesquisaFactory.getEntity(1L);

		pergunta.setQuestionario(questionario);
		pergunta.setPesquisa(pesquisa);
		pergunta.setTipo(TipoPergunta.OBJETIVA);

		String[] respostas = new String[]{"resposta1"};

		Exception exception = null;

		try
		{
			perguntaManager.salvarPergunta(pergunta, respostas, null, null);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}
	public void testSalvarPerguntaException()
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);
		Pergunta pergunta = PerguntaFactory.getEntity();
		Pesquisa pesquisa = PesquisaFactory.getEntity(1L);

		pergunta.setQuestionario(questionario);
		pergunta.setPesquisa(pesquisa);
		pergunta.setTipo(TipoPergunta.SUBJETIVA);
		pergunta.setAspecto(null);

		Integer ordemSugerida = 2;

		Exception exception = null;

		try
		{
			perguntaDao.expects(once()).method("existsOrdem").with(ANYTHING, ANYTHING).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(pergunta.getId(),""))));
			perguntaManager.salvarPergunta(pergunta, null, null, ordemSugerida);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testGetUltimaOrdenacao() throws Exception
	{
		Long questionarioId = 1L ;

		perguntaDao.expects(once()).method("getUltimaOrdenacao").with(eq(questionarioId)).will(returnValue(2));
		int ultimaOrdenacao = perguntaManager.getUltimaOrdenacao(questionarioId);

		assertNotNull(ultimaOrdenacao);
	}

	public void testRemoverPergunta()
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);

		Pergunta pergunta = PerguntaFactory.getEntity(1L);
		pergunta.setOrdem(2);
		pergunta.setQuestionario(questionario);

		Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
		pergunta1.setOrdem(3);
		pergunta1.setQuestionario(questionario);

		Resposta resposta1 = RespostaFactory.getEntity(1L);
		resposta1.setPergunta(pergunta);

		Resposta resposta2 = RespostaFactory.getEntity(2L);
		resposta2.setPergunta(pergunta);

		Collection<Resposta> respostas = new ArrayList<Resposta>();
		respostas.add(resposta1);
		respostas.add(resposta2);

		Exception exception = null;
		try
		{
			respostaManager.expects(once()).method("findByPergunta").with(eq(pergunta.getId())).will(returnValue(respostas));
			perguntaDao.expects(once()).method("findByIdProjection").with(eq(pergunta.getId())).will(returnValue(pergunta));
			respostaManager.expects(once()).method("remove").with(eq(new CollectionUtil<Resposta>().convertCollectionToArrayIds(respostas)));
			perguntaDao.expects(once()).method("getUltimaOrdenacao").with(eq(pergunta.getQuestionario().getId())).will(returnValue(pergunta1.getOrdem()));
			perguntaDao.expects(once()).method("reposicionarPerguntas").with(eq(pergunta.getQuestionario().getId()),eq(pergunta.getOrdem()),eq('-')).will(returnValue(true));
			perguntaDao.expects(once()).method("remove").with(eq(pergunta));
			perguntaManager.removerPergunta(pergunta);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testFindByIdProjection()
	{
		Pergunta pergunta = PerguntaFactory.getEntity(1L);

		perguntaDao.expects(once()).method("findByIdProjection").with(eq(pergunta.getId())).will(returnValue(pergunta));
		Pergunta perguntaRetorno = perguntaManager.findByIdProjection(pergunta.getId());

		assertEquals(pergunta, perguntaRetorno);
	}

	public void testAtualizaPergunta()
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);

		Pergunta pergunta = PerguntaFactory.getEntity(1L);
		Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
		Aspecto aspecto = AspectoFactory.getEntity(1L);

		pergunta.setPesquisa(pesquisa);
		pergunta.setQuestionario(questionario);
		pergunta.setTipo(TipoPergunta.OBJETIVA);
		pergunta.setAspecto(aspecto);

		String[] respostas = new String[]{"resp1","resp2"};

		Exception exception = null;

		try
		{
			aspectoManager.expects(once()).method("saveOrGetAspectoByNome").with(eq(aspecto.getNome()),eq(questionario.getId())).will(returnValue(aspecto));
			perguntaDao.expects(once()).method("updateAndReorder").with(ANYTHING);
			respostaManager.expects(once()).method("findByPergunta").with(eq(pergunta.getId())).will(returnValue(RespostaFactory.getCollection(1L)));
			respostaManager.expects(once()).method("remove").with(eq(new CollectionUtil<Resposta>().convertCollectionToArrayIds(RespostaFactory.getCollection(1L))));
			respostaManager.expects(once()).method("salvarRespostas").with(eq(pergunta.getId()),eq(respostas), ANYTHING).will(returnValue(RespostaFactory.getCollection(1L)));
			perguntaManager.atualizarPergunta(pergunta, respostas, null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			exception = e;
		}

		assertNull(exception);
	}

	public void testAtualizaPerguntaRespostasInsuficiente()
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);

		Pergunta pergunta = PerguntaFactory.getEntity(1L);
		Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
		Aspecto aspecto = AspectoFactory.getEntity(1L);

		pergunta.setPesquisa(pesquisa);
		pergunta.setQuestionario(questionario);
		pergunta.setTipo(TipoPergunta.OBJETIVA);
		pergunta.setAspecto(aspecto);

		String[] respostas = new String[]{"resp1"};

		Exception exception = null;

		try
		{
			perguntaManager.atualizarPergunta(pergunta, respostas, null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testAtualizaPerguntaSemAspecto()
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);

		Pergunta pergunta = PerguntaFactory.getEntity(1L);
		Pesquisa pesquisa = PesquisaFactory.getEntity(1L);

		pergunta.setPesquisa(pesquisa);
		pergunta.setQuestionario(questionario);
		pergunta.setTipo(TipoPergunta.SUBJETIVA);
		pergunta.setAspecto(null);


		Exception exception = null;

		try
		{
			perguntaDao.expects(once()).method("updateAndReorder").with(ANYTHING);
			perguntaManager.atualizarPergunta(pergunta, null, null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			exception = e;
		}

		assertNull(exception);
	}

	public void testFindPergunta()
	{
		Pergunta pergunta = PerguntaFactory.getEntity(1L);
		pergunta.setTipo(TipoPergunta.OBJETIVA);

		perguntaDao.expects(once()).method("findByIdProjection").with(eq(pergunta.getId())).will(returnValue(pergunta));
		respostaManager.expects(once()).method("findByPergunta").with(eq(pergunta.getId())).will(returnValue(RespostaFactory.getCollection(1L)));

		Pergunta perguntaRetorno = perguntaManager.findPergunta(pergunta.getId());

		assertEquals(1, perguntaRetorno.getRespostas().size());
	}

	public void testFindUltimaPerguntaObjetiva()
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);

		Pergunta pergunta = PerguntaFactory.getEntity(1L);
		pergunta.setTipo(TipoPergunta.OBJETIVA);
		pergunta.setQuestionario(questionario);

		perguntaDao.expects(once()).method("findUltimaPerguntaObjetiva").with(eq(questionario.getId())).will(returnValue(pergunta.getId()));

		Long perguntaId = perguntaManager.findUltimaPerguntaObjetiva(questionario.getId());

		assertEquals(pergunta.getId(), perguntaId);
	}

	public void testReordenarPerguntaMenos()
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);

		Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
		pergunta1.setQuestionario(questionario);
		pergunta1.setOrdem(1);

		Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
		pergunta2.setQuestionario(questionario);
		pergunta2.setOrdem(2);

		perguntaDao.expects(once()).method("findByIdProjection").with(eq(pergunta2.getId())).will(returnValue(pergunta2));
		perguntaDao.expects(once()).method("findIdByOrdem").with(eq(questionario.getId()),eq(pergunta2.getOrdem()-1)).will(returnValue(pergunta1.getId()));
		perguntaDao.expects(once()).method("reordenaPergunta").with(eq(pergunta2.getId()),eq('-')).will(returnValue(true));
		perguntaDao.expects(once()).method("reordenaPergunta").with(eq(pergunta1.getId()),eq('+')).will(returnValue(true));

		Exception exception = null;
		try
		{
			perguntaManager.reordenarPergunta(pergunta2,'-');
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testReordenarPerguntaMenosException()
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);

		Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
		pergunta1.setQuestionario(questionario);
		pergunta1.setOrdem(3);

		Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
		pergunta2.setQuestionario(questionario);
		pergunta2.setOrdem(1);

		perguntaDao.expects(once()).method("findByIdProjection").with(eq(pergunta2.getId())).will(returnValue(pergunta2));
		perguntaDao.expects(once()).method("findIdByOrdem").with(eq(questionario.getId()),eq(pergunta2.getOrdem()-1)).will(returnValue(pergunta1.getId()));

		Exception exception = null;
		try
		{
			perguntaManager.reordenarPergunta(pergunta2,'-');
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testReordenarPerguntaMais()
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);

		Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
		pergunta1.setQuestionario(questionario);
		pergunta1.setOrdem(1);

		Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
		pergunta2.setQuestionario(questionario);
		pergunta2.setOrdem(2);

		perguntaDao.expects(once()).method("findByIdProjection").with(eq(pergunta1.getId())).will(returnValue(pergunta1));
		perguntaDao.expects(once()).method("findIdByOrdem").with(eq(questionario.getId()),eq(pergunta1.getOrdem()+1)).will(returnValue(pergunta2.getId()));
		perguntaDao.expects(once()).method("reordenaPergunta").with(eq(pergunta2.getId()),eq('-')).will(returnValue(true));
		perguntaDao.expects(once()).method("reordenaPergunta").with(eq(pergunta1.getId()),eq('+')).will(returnValue(true));

		Exception exception = null;
		try
		{
			perguntaManager.reordenarPergunta(pergunta1,'+');
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testReordenarPerguntaMaisException()
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);

		Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
		pergunta1.setQuestionario(questionario);
		pergunta1.setOrdem(3);

		perguntaDao.expects(once()).method("findByIdProjection").with(eq(pergunta1.getId())).will(returnValue(pergunta1));
		perguntaDao.expects(once()).method("findIdByOrdem").with(eq(questionario.getId()),eq(pergunta1.getOrdem()+1)).will(returnValue(null));

		Exception exception = null;
		try
		{
			perguntaManager.reordenarPergunta(pergunta1,'+');
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testAlterarPosicao()
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);

		Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
		pergunta1.setOrdem(2);
		pergunta1.setQuestionario(questionario);

		int novaPosicao = 5;
		int total = 6;

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
		transactionManager.expects(once()).method("commit").with(ANYTHING);
		perguntaDao.expects(once()).method("findByIdProjection").with(eq(pergunta1.getId())).will(returnValue(pergunta1));
		perguntaDao.expects(once()).method("reposicionarPerguntas").with(eq(pergunta1.getQuestionario().getId()),eq(pergunta1.getOrdem()),eq('-')).will(returnValue(true));
		perguntaDao.expects(once()).method("getTotalPerguntas").with(ANYTHING).will(returnValue(total));
		perguntaDao.expects(once()).method("reposicionarPerguntas").with(eq(pergunta1.getQuestionario().getId()),eq(novaPosicao),eq('+')).will(returnValue(true));
		perguntaDao.expects(once()).method("updateOrdem").with(eq(pergunta1.getId()),eq(novaPosicao));

		Exception exception = null;

		try
		{
			perguntaManager.alterarPosicao(pergunta1,novaPosicao);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testAlterarPosicaoException()
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);

		Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
		pergunta1.setOrdem(2);
		pergunta1.setQuestionario(questionario);

		int novaPosicao = 5;
		int total = 6;

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
		transactionManager.expects(once()).method("rollback").with(ANYTHING);
		perguntaDao.expects(once()).method("findByIdProjection").with(eq(pergunta1.getId())).will(returnValue(pergunta1));
		perguntaDao.expects(once()).method("reposicionarPerguntas").with(eq(pergunta1.getQuestionario().getId()),eq(pergunta1.getOrdem()),eq('-')).will(returnValue(true));
		perguntaDao.expects(once()).method("getTotalPerguntas").with(ANYTHING).will(returnValue(total));
		perguntaDao.expects(once()).method("reposicionarPerguntas").with(eq(pergunta1.getQuestionario().getId()),eq(novaPosicao),eq('+')).will(returnValue(false));

		Exception exception = null;

		try
		{
			perguntaManager.alterarPosicao(pergunta1,novaPosicao);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testAlterarPosicaoException2()
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);

		Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
		pergunta1.setOrdem(2);
		pergunta1.setQuestionario(questionario);

		int novaPosicao = 5;

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
		transactionManager.expects(once()).method("rollback").with(ANYTHING);
		perguntaDao.expects(once()).method("findByIdProjection").with(eq(pergunta1.getId())).will(returnValue(pergunta1));
		perguntaDao.expects(once()).method("reposicionarPerguntas").with(eq(pergunta1.getQuestionario().getId()),eq(pergunta1.getOrdem()),eq('-')).will(returnValue(false));

		Exception exception = null;

		try
		{
			perguntaManager.alterarPosicao(pergunta1,novaPosicao);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testEmbaralharPerguntas()
	{
		Pesquisa pesquisa = PesquisaFactory.getEntity(1L);

		Questionario questionario = QuestionarioFactory.getEntity(1L);

		Collection<Pergunta> perguntas = new ArrayList<Pergunta>();

		Pergunta p1 = PerguntaFactory.getEntity(1L);
		p1.setQuestionario(questionario);
		p1.setOrdem(1);
		Pergunta p2 = PerguntaFactory.getEntity(2L);
		p2.setQuestionario(questionario);
		p2.setOrdem(2);
		Pergunta p3 = PerguntaFactory.getEntity(3L);
		p3.setQuestionario(questionario);
		p3.setOrdem(3);
		Pergunta p4 = PerguntaFactory.getEntity(4L);
		p4.setQuestionario(questionario);
		p4.setOrdem(4);
		Pergunta p5 = PerguntaFactory.getEntity(5L);
		p5.setQuestionario(questionario);
		p5.setOrdem(5);

		perguntas.add(p1);
		perguntas.add(p2);
		perguntas.add(p3);
		perguntas.add(p4);
		perguntas.add(p5);

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
		transactionManager.expects(once()).method("commit").with(ANYTHING);
		perguntaDao.expects(once()).method("getUltimaOrdenacao").with(eq(p1.getQuestionario().getId())).will(returnValue(p5.getOrdem()));
		perguntaDao.expects(once()).method("findByQuestionario").with(eq(pesquisa.getId())).will(returnValue(perguntas));
		perguntaDao.expects(once()).method("updateOrdem").with(ANYTHING,ANYTHING);
		perguntaDao.expects(once()).method("updateOrdem").with(ANYTHING,ANYTHING);
		perguntaDao.expects(once()).method("updateOrdem").with(ANYTHING,ANYTHING);
		perguntaDao.expects(once()).method("updateOrdem").with(ANYTHING,ANYTHING);
		perguntaDao.expects(once()).method("updateOrdem").with(ANYTHING,ANYTHING);

		Exception exception = null;

		try
		{
			perguntaManager.embaralharPerguntas(pesquisa.getId());
		}
		catch (Exception e)
		{
			exception = e;
		}
		assertNull(exception);
	}

	public void testEmbaralharPerguntasException()
	{
		Pesquisa pesquisa = PesquisaFactory.getEntity(1L);

		Questionario questionario = QuestionarioFactory.getEntity(1L);

		Collection<Pergunta> perguntas = new ArrayList<Pergunta>();

		Pergunta p1 = PerguntaFactory.getEntity(1L);
		p1.setQuestionario(questionario);
		p1.setOrdem(1);
		Pergunta p2 = PerguntaFactory.getEntity(2L);
		p2.setQuestionario(questionario);
		p2.setOrdem(2);
		Pergunta p3 = PerguntaFactory.getEntity(3L);
		p3.setQuestionario(questionario);
		p3.setOrdem(3);
		Pergunta p4 = PerguntaFactory.getEntity(4L);
		p4.setQuestionario(questionario);
		p4.setOrdem(4);
		Pergunta p5 = PerguntaFactory.getEntity(5L);
		p5.setQuestionario(questionario);
		p5.setOrdem(5);

		perguntas.add(p1);
		perguntas.add(p2);
		perguntas.add(p3);
		perguntas.add(p4);
		perguntas.add(p5);

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
		transactionManager.expects(once()).method("rollback").with(ANYTHING);
		perguntaDao.expects(once()).method("getUltimaOrdenacao").with(eq(p1.getQuestionario().getId())).will(returnValue(p5.getOrdem()));
		perguntaDao.expects(once()).method("findByQuestionario").with(eq(pesquisa.getId())).will(returnValue(perguntas));
		perguntaDao.expects(once()).method("updateOrdem").with(ANYTHING,ANYTHING).will(throwException(new Exception("erro")));

		Exception exception = null;

		try
		{
			perguntaManager.embaralharPerguntas(pesquisa.getId());
		}
		catch (Exception e)
		{
			exception = e;
		}
		assertNotNull(exception);
	}

	public void testSalvarPerguntasByOrdem()
	{
		Collection<Pergunta> perguntas = new ArrayList<Pergunta>();

		Pergunta p1 = PerguntaFactory.getEntity(1L);
		p1.setOrdem(1);
		Pergunta p2 = PerguntaFactory.getEntity(2L);
		p2.setOrdem(2);
		Pergunta p3 = PerguntaFactory.getEntity(3L);
		p3.setOrdem(3);
		Pergunta p4 = PerguntaFactory.getEntity(4L);
		p4.setOrdem(4);
		Pergunta p5 = PerguntaFactory.getEntity(5L);
		p5.setOrdem(5);

		perguntas.add(p1);
		perguntas.add(p2);
		perguntas.add(p3);
		perguntas.add(p4);
		perguntas.add(p5);

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
		transactionManager.expects(once()).method("commit").with(ANYTHING);
		perguntaDao.expects(once()).method("updateOrdem").with(ANYTHING,ANYTHING);
		perguntaDao.expects(once()).method("updateOrdem").with(ANYTHING,ANYTHING);
		perguntaDao.expects(once()).method("updateOrdem").with(ANYTHING,ANYTHING);
		perguntaDao.expects(once()).method("updateOrdem").with(ANYTHING,ANYTHING);
		perguntaDao.expects(once()).method("updateOrdem").with(ANYTHING,ANYTHING);

		Exception exception = null;

		try
		{
			perguntaManager.salvarPerguntasByOrdem(perguntas);
		}
		catch (Exception e)
		{
			exception = e;
		}
		assertNull(exception);
	}

	public void testSalvarPerguntasByOrdemException()
	{
		Collection<Pergunta> perguntas = new ArrayList<Pergunta>();

		Pergunta p1 = PerguntaFactory.getEntity(1L);
		p1.setOrdem(1);
		Pergunta p2 = PerguntaFactory.getEntity(2L);
		p2.setOrdem(2);
		Pergunta p3 = PerguntaFactory.getEntity(3L);
		p3.setOrdem(3);
		Pergunta p4 = PerguntaFactory.getEntity(4L);
		p4.setOrdem(4);
		Pergunta p5 = PerguntaFactory.getEntity(5L);
		p5.setOrdem(5);

		perguntas.add(p1);
		perguntas.add(p2);
		perguntas.add(p3);
		perguntas.add(p4);
		perguntas.add(p5);

		transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
		transactionManager.expects(once()).method("rollback").with(ANYTHING);
		perguntaDao.expects(once()).method("updateOrdem").with(ANYTHING,ANYTHING).will(throwException(new Exception("erro")));

		Exception exception = null;

		try
		{
			perguntaManager.salvarPerguntasByOrdem(perguntas);
		}
		catch (Exception e)
		{
			exception = e;
		}
		assertNotNull(exception);
	}


    public void testPopulaCheckOrderTexto()
	{
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
		Collection<Pergunta> perguntas = new ArrayList<Pergunta>();

		perguntaDao.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(perguntas));

		assertEquals(perguntaManager.populaCheckOrderTexto(questionario.getId()), new ArrayList<CheckBox>());
	}

    public void testPopulaCheckOrderTextoException()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	perguntaManager.setDao(null);

    	assertEquals(perguntaManager.populaCheckOrderTexto(questionario.getId()), new ArrayList<CheckBox>());
    }
    
    public void testGetPerguntasRespostaByQuestionarioAspecto()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	Long[] aspectosIds = new Long[]{1L};
    	Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
    	
		Collection<Resposta> respostas = new ArrayList<Resposta>();
		CollectionUtil<Pergunta> clu = new CollectionUtil<Pergunta>();
		Long[] perguntaIds = clu.convertCollectionToArrayIds(perguntas);

		perguntaDao.expects(once()).method("findByQuestionarioAspecto").with(eq(questionario.getId()), eq(aspectosIds)).will(returnValue(perguntas));
		respostaManager.expects(once()).method("findInPerguntaIds").with(eq(perguntaIds)).will(returnValue(respostas));
    	
		assertEquals(0, perguntaManager.getPerguntasRespostaByQuestionarioAspecto(questionario.getId(), aspectosIds).size());
    }
    
    public void testFindByQuestionarioAspectoPergunta()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	Long[] aspectosIds = new Long[]{1L};
    	Long[] perguntasIds = new Long[]{1L};
    	boolean agruparPorAspectos = true;
    	
    	Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
    	
    	perguntaDao.expects(once()).method("findByQuestionarioAspectoPergunta").with(eq(questionario.getId()), eq(aspectosIds), eq(perguntasIds), eq(agruparPorAspectos)).will(returnValue(perguntas));
    	
    	assertEquals(0, perguntaManager.findByQuestionarioAspectoPergunta(questionario.getId(), aspectosIds, perguntasIds, agruparPorAspectos).size());
    }
    
    public void testRemoverPerguntasDoQuestionario()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	
    	Collection<Long> perguntasIds = new ArrayList<Long>();
    	Pergunta pergunta = PerguntaFactory.getEntity(1L);
    	perguntasIds.add(pergunta.getId());
    	
    	perguntaDao.expects(once()).method("findPerguntasDoQuestionario").with(eq(questionario.getId())).will(returnValue(perguntasIds));
    	respostaManager.expects(once()).method("removerRespostasDasPerguntas").with(eq(perguntasIds));
    	perguntaDao.expects(once()).method("removerPerguntasDoQuestionario").with(eq(questionario.getId()));
    	
    	perguntaManager.removerPerguntasDoQuestionario(questionario.getId());
    }
    
    public void testFindPerguntasDoQuestionario()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	
    	perguntaDao.expects(once()).method("findPerguntasDoQuestionario").with(eq(questionario.getId())).will(returnValue(new ArrayList<Long>()));
    	
    	assertEquals(0, perguntaManager.findPerguntasDoQuestionario(questionario.getId()).size());
    }
    
    public void testFindByQuestionario()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	
    	perguntaDao.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(new ArrayList<Pergunta>()));
    	
    	assertEquals(0, perguntaManager.findByQuestionario(questionario.getId()).size());
    }

    public void testModificarOrdens()
    {
    	Collection<Pergunta> perguntas = new ArrayList<Pergunta>();

    	Pergunta p1 = PerguntaFactory.getEntity(1L);
    	p1.setOrdem(1);
    	Pergunta p2 = PerguntaFactory.getEntity(2L);
    	p2.setOrdem(2);
    	Pergunta p3 = PerguntaFactory.getEntity(3L);
    	p3.setOrdem(3);

    	perguntas.add(p1);
    	perguntas.add(p2);
    	perguntas.add(p3);

    	Collection<Pergunta> retorno = perguntaManager.modificarOrdens(perguntas, 3);

    	Integer ordem = 4;
    	for (Pergunta pergunta : retorno)
		{
    		assertEquals(ordem, pergunta.getOrdem());
    		ordem++;
		}
    }

    public void testClonarPergunta() throws Exception
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	Questionario questionarioClonado = QuestionarioFactory.getEntity(2L);

    	Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
    	pergunta1.setQuestionario(questionario);

    	Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
    	pergunta2.setQuestionario(questionario);

    	Pergunta pergunta3 = PerguntaFactory.getEntity(3L);
    	pergunta3.setQuestionario(questionario);
    	pergunta3.setAspecto(new Aspecto());

    	Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
    	perguntas.add(pergunta1);
    	perguntas.add(pergunta2);
    	perguntas.add(pergunta3);

    	Pergunta perguntaClonada = new Pergunta();

    	Collection<Resposta> respostas = RespostaFactory.getCollection(3L);

    	perguntaDao.expects(once()).method("findByQuestionario").with(ANYTHING).will(returnValue(perguntas));
    	aspectoManager.expects(once()).method("clonarAspectos").with(eq(questionario.getId()), eq(questionarioClonado), eq(null)).will(returnValue(new HashMap<Long, Aspecto>()));
    	
    	respostaManager.expects(once()).method("findInPerguntaIds").with(ANYTHING).will(returnValue(respostas));
    	perguntaDao.expects(atLeastOnce()).method("save").with(ANYTHING).will(returnValue(perguntaClonada));
    	respostaManager.expects(atLeastOnce()).method("clonarResposta").with(ANYTHING, ANYTHING);

    	perguntaManager.clonarPerguntas(questionario.getId(), questionarioClonado, null);
    }
    
    public void testSetAvaliadoNaPerguntaDeAvaliacaoDesempenho()
    {
    	Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
    	pergunta1.setTexto("O que acha do #AVALIADO#?");
    	
    	perguntaManager.setAvaliadoNaPerguntaDeAvaliacaoDesempenho(pergunta1, "João de Deus");
    	assertEquals("O que acha do João de Deus?", pergunta1.getTexto());
    }

}

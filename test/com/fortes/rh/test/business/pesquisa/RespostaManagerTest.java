package com.fortes.rh.test.business.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.RespostaManagerImpl;
import com.fortes.rh.dao.pesquisa.RespostaDao;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.RespostaFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.SpringUtil;

public class RespostaManagerTest extends MockObjectTestCase
{
	private RespostaManagerImpl respostaManager = new RespostaManagerImpl();
	private Mock respostaDao;
	private Mock perguntaManager;

    protected void setUp() throws Exception
    {
        super.setUp();

        respostaDao = new Mock(RespostaDao.class);
        respostaManager.setDao((RespostaDao) respostaDao.proxy());
        
        perguntaManager = new Mock(PerguntaManager.class);
		MockSpringUtil.mocks.put("perguntaManager", perguntaManager);
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
    }


	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();
		super.tearDown();
	}

	public void testFindInPerguntaIds() throws Exception
	{
		Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
		Pergunta pergunta2 = PerguntaFactory.getEntity(2L);

		Resposta resposta1 = RespostaFactory.getEntity();
		resposta1.setPergunta(pergunta1);

		Resposta resposta2 = RespostaFactory.getEntity();
		resposta2.setPergunta(pergunta1);

		Resposta resposta3 = RespostaFactory.getEntity();
		resposta3.setPergunta(pergunta2);

		Collection<Resposta> respostas = new ArrayList<Resposta>();
		respostas.add(resposta1);
		respostas.add(resposta2);

		respostaDao.expects(once()).method("findInPerguntaIds").with(eq(new Long[]{pergunta1.getId()})).will(returnValue(respostas));
		Collection<Resposta> retorno = respostaManager.findInPerguntaIds(new Long[]{pergunta1.getId()});

		assertEquals(2, retorno.size());
	}

	public void testFindInPerguntaIdsReturnoVazio() throws Exception
	{

		Collection<Resposta> retorno = respostaManager.findInPerguntaIds(null);

		assertEquals(0, retorno.size());
	}

	public void testSalvarRespostas()
	{
		String[] respostas = new String[]{"aaa","bbb"};
		Long perguntaId = 1L;

		respostaDao.expects(atLeastOnce()).method("save").with(ANYTHING).will(returnValue(new Resposta()));
		Collection<Resposta> respostaRetorno = respostaManager.salvarRespostas(perguntaId, respostas, null);

		assertEquals(2, respostaRetorno.size());
	}

	public void testFindByPergunta()
	{
		Pergunta pergunta = PerguntaFactory.getEntity(1L);

		respostaDao.expects(once()).method("findByPergunta").with(eq(pergunta.getId())).will(returnValue(RespostaFactory.getCollection(1L)));
		Collection<Resposta> respostasRetorno = respostaManager.findByPergunta(pergunta.getId());

		assertEquals(1, respostasRetorno.size());
	}

	public void testFindRespostasSugeridas()
	{
		Questionario questionario = QuestionarioFactory.getEntity(1L);
		Pergunta pergunta = PerguntaFactory.getEntity(1L);

		perguntaManager.expects(once()).method("findUltimaPerguntaObjetiva").with(eq(questionario.getId())).will(returnValue(pergunta.getId()));
		respostaDao.expects(once()).method("findByPergunta").with(eq(pergunta.getId())).will(returnValue(RespostaFactory.getCollection(1L)));
		
		Collection<Resposta> respostasRetorno = respostaManager.findRespostasSugeridas(questionario.getId());

		assertEquals(1, respostasRetorno.size());
	}
	
	public void testClonarResposta()
	{
		Pergunta pergunta = PerguntaFactory.getEntity(1L);
		
		Resposta resposta = RespostaFactory.getEntity();
		resposta.setPergunta(pergunta);
		
		Collection<Resposta> respostas = new ArrayList<Resposta>();
		respostas.add(resposta);
		
		respostaDao.expects(once()).method("save").with(ANYTHING);
		
		respostaManager.clonarResposta(pergunta, respostas);
	}
	
	public void testRemoverRespostasDasPerguntas()
	{
		Collection<Long> perguntaIds = new ArrayList<Long>();
		respostaDao.expects(once()).method("removerRespostasDasPerguntas").with(eq(perguntaIds));
		
		respostaManager.removerRespostasDasPerguntas(perguntaIds);
	}
}

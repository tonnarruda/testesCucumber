package com.fortes.rh.test.business.avaliacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.avaliacao.AvaliacaoManagerImpl;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.business.pesquisa.RespostaManager;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioResultadoPerguntaObjetiva;
import com.fortes.rh.model.pesquisa.relatorio.ResultadoQuestionario;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorRespostaFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.RespostaFactory;

public class AvaliacaoManagerTest extends MockObjectTestCase
{
	private AvaliacaoManagerImpl avaliacaoManager = new AvaliacaoManagerImpl();
	private Mock avaliacaoDao;
	private Mock perguntaManager;
	
	private Mock respostaManager;
	private Mock colaboradorRespostaManager;
	private Mock questionarioManager;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        avaliacaoDao = new Mock(AvaliacaoDao.class);
        avaliacaoManager.setDao((AvaliacaoDao) avaliacaoDao.proxy());
        perguntaManager = new Mock(PerguntaManager.class);
        avaliacaoManager.setPerguntaManager((PerguntaManager) perguntaManager.proxy());
        respostaManager = mock(RespostaManager.class);
        avaliacaoManager.setRespostaManager((RespostaManager) respostaManager.proxy());
        colaboradorRespostaManager = mock (ColaboradorRespostaManager.class);
        avaliacaoManager.setColaboradorRespostaManager((ColaboradorRespostaManager) colaboradorRespostaManager.proxy());
        questionarioManager= mock(QuestionarioManager.class);
        avaliacaoManager.setQuestionarioManager((QuestionarioManager) questionarioManager.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<Avaliacao> avaliacaos = AvaliacaoFactory.getCollection(1L);

		avaliacaoDao.expects(once()).method("findAllSelect").with(new Constraint[]{ eq(null), eq(null), eq(empresaId), eq(null), eq(TipoModeloAvaliacao.DESEMPENHO), ANYTHING }).will(returnValue(avaliacaos));
		assertEquals(avaliacaos, avaliacaoManager.findAllSelect(null, null, empresaId, null, TipoModeloAvaliacao.DESEMPENHO, null));
	}
	
	public void testFindPeriodoExperienciaIsNull()
	{
		Long empresaId = 1L;
		
		Collection<Avaliacao> avaliacaos = AvaliacaoFactory.getCollection(1L);
		
		avaliacaoDao.expects(once()).method("findPeriodoExperienciaIsNull").with(eq(TipoModeloAvaliacao.DESEMPENHO), eq(empresaId)).will(returnValue(avaliacaos));
		assertEquals(avaliacaos, avaliacaoManager.findPeriodoExperienciaIsNull(TipoModeloAvaliacao.DESEMPENHO, empresaId));
	}
	
	public void testGetQuestionarioRelatorio()
    {
    	Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);

    	Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
    	pergunta1.setAvaliacao(avaliacao);

    	Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
    	pergunta2.setAvaliacao(avaliacao);

    	Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
    	perguntas.add(pergunta1);
    	perguntas.add(pergunta2);

    	perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionarioAgrupadosPorAspecto").with(eq(avaliacao.getId()),ANYTHING).will(returnValue(perguntas));
    	perguntaManager.expects(atLeastOnce()).method("setAvaliadoNaPerguntaDeAvaliacaoDesempenho").with(ANYTHING, ANYTHING);

    	avaliacaoManager.getQuestionarioRelatorioCollection(avaliacao, false);
    }
	
	public void testgetPontuacaoMaximaDaPerformance()
	{
		avaliacaoDao.expects(once()).method("getPontuacaoMaximaDaPerformance").with(eq(1L), ANYTHING).will(returnValue(210));
		assertEquals(210, avaliacaoManager.getPontuacaoMaximaDaPerformance(1L, null).intValue());
	}
	
	public void testMontaResultado() throws Exception
    {
    	Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
    	pergunta1.setTipo(TipoPergunta.SUBJETIVA);
    	Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
    	pergunta2.setTipo(TipoPergunta.SUBJETIVA);

    	Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
    	perguntas.add(pergunta1);
    	perguntas.add(pergunta2);

    	Resposta resposta1 = RespostaFactory.getEntity(1L);
    	resposta1.setPergunta(pergunta1);

    	Collection<Resposta> respostas = new ArrayList<Resposta>();
    	respostas.add(resposta1);

    	ColaboradorResposta colaboradorResposta1 = ColaboradorRespostaFactory.getEntity(1L);
    	colaboradorResposta1.setPergunta(pergunta1);

    	ColaboradorResposta colaboradorResposta2 = ColaboradorRespostaFactory.getEntity(2L);
    	colaboradorResposta2.setPergunta(pergunta2);

    	Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();
    	colaboradorRespostas.add(colaboradorResposta1);
    	colaboradorRespostas.add(colaboradorResposta2);
    	
    	Collection<ColaboradorResposta> colabRespostas1 = Arrays.asList(colaboradorResposta1);
    	
    	Avaliacao avaliacao = AvaliacaoFactory.getEntity(3L);
    	
    	respostaManager.expects(once()).method("findInPerguntaIds").with(ANYTHING).will(returnValue(respostas));
    	colaboradorRespostaManager.expects(once()).method("findInPerguntaIdsAvaliacao").will(returnValue(colaboradorRespostas));
    	colaboradorRespostaManager.expects(once()).method("calculaPercentualRespostas").will(returnValue(new ArrayList<QuestionarioResultadoPerguntaObjetiva>()));
    	colaboradorRespostaManager.expects(once()).method("calculaPercentualRespostasMultipla").will(returnValue(new ArrayList<QuestionarioResultadoPerguntaObjetiva>()));
    	
    	questionarioManager.expects(once()).method("countColaborador").with(eq(colaboradorRespostas)).will(returnValue(10));
    	
    	ResultadoQuestionario resultadoQuestionario1 = new ResultadoQuestionario();
    	resultadoQuestionario1.setColabRespostas(colabRespostas1);
    	Collection<ResultadoQuestionario> resultadoQuestionarios = new ArrayList<ResultadoQuestionario>();
    	resultadoQuestionarios.add(resultadoQuestionario1);
    	resultadoQuestionarios.add(new ResultadoQuestionario());
    	
    	questionarioManager.expects(once()).method("montaResultadosQuestionarios").will(returnValue(resultadoQuestionarios));
    	
    	Collection<ResultadoQuestionario> resultado = avaliacaoManager.montaResultado(perguntas, null, null, null, null, avaliacao, null, null, false);
    	assertEquals(2, resultado.size());
    	assertEquals(1, ((ResultadoQuestionario)resultado.toArray()[0]).getColabRespostas().size());
    }
	public void testMontaResultadoExceptionSemPerguntas() 
	{
		Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
		Avaliacao avaliacao = AvaliacaoFactory.getEntity(3L);
		
		respostaManager.expects(once()).method("findInPerguntaIds").with(ANYTHING).will(returnValue(new ArrayList<Resposta>()));
		colaboradorRespostaManager.expects(once()).method("findInPerguntaIdsAvaliacao").will(returnValue(new ArrayList<ColaboradorResposta>()));
		Exception exception=null;
		
		try {
			avaliacaoManager.montaResultado(perguntas, null, null, null, null, avaliacao, null, null, false);
		} catch (Exception e) {
			exception=e;
		}
		
		assertEquals("Nenhuma pergunta foi respondida.", exception.getMessage());
	}

	public void testMontaObsAvaliadores() 
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("Rubinha");
		colaborador.setNomeComercial("Rubinha");

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setNome("Escocia");
		colaborador2.setNomeComercial("Escocia");
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setId(1L);
		colaboradorQuestionario.setObservacao("obs avaliado1");
		colaboradorQuestionario.setAvaliador(colaborador);

		ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario2.setId(2L);
		colaboradorQuestionario2.setObservacao("obs avaliado1");
		colaboradorQuestionario2.setAvaliador(colaborador2);
		
		ColaboradorResposta colaboradorResposta = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta.setColaboradorQuestionario(colaboradorQuestionario);

		ColaboradorResposta colaboradorResposta2 = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta2.setColaboradorQuestionario(colaboradorQuestionario);

		ColaboradorResposta colaboradorResposta3 = ColaboradorRespostaFactory.getEntity();
		colaboradorResposta3.setColaboradorQuestionario(colaboradorQuestionario2);
		
		ColaboradorResposta colaboradorResposta4 = ColaboradorRespostaFactory.getEntity();
		
		Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();
		colaboradorRespostas.add(colaboradorResposta);
		colaboradorRespostas.add(colaboradorResposta2);
		colaboradorRespostas.add(colaboradorResposta3);
		colaboradorRespostas.add(colaboradorResposta4);
		
		assertEquals("Rubinha: obs avaliado1\n\nEscocia: obs avaliado1\n\n", avaliacaoManager.montaObsAvaliadores(colaboradorRespostas));
	}
	
}

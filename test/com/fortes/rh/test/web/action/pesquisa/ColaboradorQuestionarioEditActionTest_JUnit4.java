package com.fortes.rh.test.web.action.pesquisa;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManagerImpl;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.RespostaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.RespostaFactory;
import com.fortes.rh.web.action.pesquisa.ColaboradorQuestionarioEditAction;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtil.class)
public class ColaboradorQuestionarioEditActionTest_JUnit4
{
	private ColaboradorQuestionarioEditAction action = new ColaboradorQuestionarioEditAction();
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private ColaboradorManager colaboradorManager;
	private AvaliacaoManager avaliacaoManager;
	private PerguntaManager perguntaManager;
	private RespostaManager respostaManager;
	private ColaboradorRespostaManager colaboradorRespostaManager;

	@Before
	public void setUp() throws Exception
	{
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		colaboradorQuestionarioManager = mock(ColaboradorQuestionarioManager.class);
		action.setColaboradorQuestionarioManager(colaboradorQuestionarioManager);
		
		colaboradorManager = mock(ColaboradorManager.class);
		action.setColaboradorManager(colaboradorManager);
		
		avaliacaoManager = mock(AvaliacaoManager.class);
		action.setAvaliacaoManager(avaliacaoManager);
		
		perguntaManager = mock(PerguntaManager.class);
		action.setPerguntaManager(perguntaManager);
		
		respostaManager = mock(RespostaManager.class);
		action.setRespostaManager(respostaManager);
		
		colaboradorRespostaManager = mock(ColaboradorRespostaManager.class);
		action.setColaboradorRespostaManager(colaboradorRespostaManager);
		
		PowerMockito.mockStatic(SecurityUtil.class);
	}

	@Test
	public void testPrepareInsertAvaliacaoExperiencia()
	{
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(111L);
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setAvaliacao(AvaliacaoFactory.getEntity(10L));
		action.setColaboradorQuestionario(colaboradorQuestionario);
		
		// Perguntas e respostas
		
		Pergunta perguntaObj = PerguntaFactory.getEntity(1L, TipoPergunta.OBJETIVA, "Como é o avaliado?");
		Resposta resposta1 = RespostaFactory.getEntity("bom", null, null);
		Resposta resposta2 = RespostaFactory.getEntity("ruim", null, null);
		
		Pergunta perguntaMult = PerguntaFactory.getEntity(2L, TipoPergunta.MULTIPLA_ESCOLHA, "Quais habilidades?");
		Resposta respostaMulti1 = RespostaFactory.getEntity("Karatê", null, null);
		Resposta respostaMulti2 = RespostaFactory.getEntity("Judô", null, null);
		Resposta respostaMulti3 = RespostaFactory.getEntity("Futebol", null, null);
		
		Collection<Pergunta> perguntas = Arrays.asList(perguntaObj, perguntaMult);
		Collection<Resposta> respostasDaPerguntaObj = Arrays.asList(resposta1,resposta2);
		Collection<Resposta> respostasDaPerguntaMulti = Arrays.asList(respostaMulti1,respostaMulti2,respostaMulti3);
		
		// Montando ColaboradorResposta do jeito que vem do banco:
		// um ColaboradorResposta para cada resposta múltipla escolha
		
		ColaboradorResposta colaboradorResposta1 = new ColaboradorResposta();
		colaboradorResposta1.setPergunta(perguntaObj);
		colaboradorResposta1.setResposta(resposta2);
		ColaboradorResposta colaboradorResposta2 = new ColaboradorResposta();
		colaboradorResposta2.setPergunta(perguntaMult);
		colaboradorResposta2.setResposta(respostaMulti2);
		ColaboradorResposta colaboradorResposta3 = new ColaboradorResposta();
		colaboradorResposta3.setPergunta(perguntaMult);
		colaboradorResposta3.setResposta(respostaMulti3);
		
		Collection<ColaboradorResposta> colaboradorRespostas = Arrays.asList(colaboradorResposta1, colaboradorResposta2, colaboradorResposta3);
		
		ColaboradorQuestionario colaboradorQuestionarioTmp = null;
		Boolean possuiRole = Boolean.TRUE;
		boolean ordenarPorAspecto = Boolean.FALSE;
		
		when(colaboradorQuestionarioManager.findByColaboradorAvaliacao(colaboradorQuestionario.getColaborador(), colaboradorQuestionario.getAvaliacao())).thenReturn(colaboradorQuestionarioTmp );
		when(avaliacaoManager.findEntidadeComAtributosSimplesById(colaboradorQuestionario.getAvaliacao().getId())).thenReturn(colaboradorQuestionario.getAvaliacao());
		when(colaboradorManager.findByIdProjectionEmpresa(colaboradorQuestionario.getColaborador().getId())).thenReturn(colaborador);
		when(SecurityUtil.getColaboradorSession(ActionContext.getContext().getSession())).thenReturn(colaborador);
		when(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_AV_GESTOR_RECEBER_NOTIFICACAO_PROPRIA_AVALIACAO_ACOMP_DE_EXPERIENCIA"})).thenReturn(Boolean.TRUE);
		when(avaliacaoManager.findModelosAcompanhamentoPeriodoExperiencia(eq(action.getEmpresaSistema().getId()), eq(colaborador.getId()), eq(colaborador), eq(possuiRole), any(AreaOrganizacionalManagerImpl.class))).thenReturn(new ArrayList<Avaliacao>());
		when(colaboradorQuestionarioManager.populaQuestionario(colaboradorQuestionario.getAvaliacao())).thenReturn(colaboradorRespostas);
		when(perguntaManager.getPerguntasRespostaByQuestionarioAgrupadosPorAspecto(colaboradorQuestionario.getAvaliacao().getId(), ordenarPorAspecto)).thenReturn(perguntas);
		doNothing().when(perguntaManager).setAvaliadoNaPerguntaDeAvaliacaoDesempenho(any(Pergunta.class), eq(colaborador.getNome()));
		when(respostaManager.findByPergunta(anyLong())).thenReturn(respostasDaPerguntaObj);
		when(respostaManager.findByPergunta(anyLong())).thenReturn(respostasDaPerguntaMulti);
		
		Assert.assertEquals(Action.SUCCESS, action.prepareInsertAvaliacaoExperiencia());
		
		Assert.assertEquals(2, action.getPerguntas().size());
		// 1 resposta para perg objetiva
		Assert.assertEquals(1,((Pergunta)action.getPerguntas().toArray()[0]).getColaboradorRespostas().size());
		// 2 respostas para perg multi
		Assert.assertEquals(2,((Pergunta)action.getPerguntas().toArray()[1]).getColaboradorRespostas().size());
	}
	
	@Test
	public void testPrepareInsertAvaliacaoExperienciaComUpdate()
	{
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(111L);
		colaboradorQuestionario.setColaborador(colaborador);
		
		action.setColaboradorQuestionario(colaboradorQuestionario);
		
		ArrayList<Avaliacao> avaliacoesVazias = new ArrayList<Avaliacao>();
		ArrayList<ColaboradorResposta> colaboradorRespostasVazios = new ArrayList<ColaboradorResposta>();
		
		when(colaboradorQuestionarioManager.findByColaboradorAvaliacao(colaboradorQuestionario.getColaborador(), colaboradorQuestionario.getAvaliacao())).thenReturn(colaboradorQuestionario);
		when(colaboradorQuestionarioManager.findById(colaboradorQuestionario.getId())).thenReturn(colaboradorQuestionario);
		when(colaboradorManager.findByIdProjectionEmpresa(colaboradorQuestionario.getColaborador().getId())).thenReturn(colaborador);
		when(avaliacaoManager.find(new String[]{"ativo", "tipoModeloAvaliacao", "empresa.id"}, new Object[]{true, TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA, action.getEmpresaSistema().getId()}, new String[]{"titulo"})).thenReturn(avaliacoesVazias);
		when(colaboradorRespostaManager.findByColaboradorQuestionario(colaboradorQuestionario.getId())).thenReturn(colaboradorRespostasVazios);
		
		
		Assert.assertEquals(Action.SUCCESS, action.prepareInsertAvaliacaoExperiencia());
	}
}
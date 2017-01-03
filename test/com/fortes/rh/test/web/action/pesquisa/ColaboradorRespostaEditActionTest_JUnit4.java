package com.fortes.rh.test.web.action.pesquisa;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.web.action.pesquisa.ColaboradorRespostaEditAction;
import com.opensymphony.xwork.Action;

public class ColaboradorRespostaEditActionTest_JUnit4
{
	private ColaboradorRespostaEditAction colaboradorRespostaEditAction;
	private ColaboradorRespostaManager colaboradorRespostaManager;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;

	@Before
	public void setUp() throws Exception
	{
		colaboradorRespostaEditAction = new ColaboradorRespostaEditAction();
		colaboradorRespostaEditAction.setVoltarPara("Voltar Para");
		
		colaboradorRespostaManager = mock(ColaboradorRespostaManager.class);
		colaboradorRespostaEditAction.setColaboradorRespostaManager(colaboradorRespostaManager);
		
		colaboradorQuestionarioManager = mock(ColaboradorQuestionarioManager.class);
		colaboradorRespostaEditAction.setColaboradorQuestionarioManager(colaboradorQuestionarioManager);
	}

	@Test
	public void testsalvaQuestionarioRespondidoVoltarParaFichaMedica() throws Exception
	{
		colaboradorRespostaEditAction.setVoltarPara("../../sesmt/fichaMedica/listPreenchida.action");
		colaboradorRespostaEditAction.setColaboradorQuestionario(ColaboradorQuestionarioFactory.getEntity(1L));
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaboradorRespostaEditAction.setColaborador(colaborador);
		
		Questionario questionario = QuestionarioFactory.getEntity(1L);
		questionario.setTipo(TipoQuestionario.PESQUISA);
		colaboradorRespostaEditAction.setQuestionario(questionario);
		
		when(colaboradorQuestionarioManager.isRespondeuPesquisaByColaboradorIdAndQuestionarioId(colaborador.getId(), questionario.getId())).thenReturn(false);
		
		assertEquals(Action.SUCCESS, colaboradorRespostaEditAction.salvaQuestionarioRespondido());
		assertEquals("Respostas&nbsp;gravadas&nbsp;com&nbsp;sucesso.", colaboradorRespostaEditAction.getActionMsg());
		assertEquals("../../sesmt/fichaMedica/listPreenchida.action?actionMsg=Respostas&nbsp;gravadas&nbsp;com&nbsp;sucesso.", colaboradorRespostaEditAction.getRetorno());
	}
	
	@Test
	public void testsalvaQuestionarioRespondidoIndex() throws Exception
	{
		colaboradorRespostaEditAction.setTela("index");
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaboradorRespostaEditAction.setColaborador(colaborador);
		
		Questionario questionario = QuestionarioFactory.getEntity(1L);
		questionario.setTipo(TipoQuestionario.PESQUISA);
		colaboradorRespostaEditAction.setQuestionario(questionario);
		
		when(colaboradorQuestionarioManager.isRespondeuPesquisaByColaboradorIdAndQuestionarioId(colaborador.getId(), questionario.getId())).thenReturn(false);
		
		assertEquals(Action.SUCCESS, colaboradorRespostaEditAction.salvaQuestionarioRespondido());
		assertEquals("Respostas%20gravadas%20com%20sucesso.", colaboradorRespostaEditAction.getActionMsg());
		assertEquals("../../index.action?actionMsg=Respostas%20gravadas%20com%20sucesso.", colaboradorRespostaEditAction.getRetorno());
	}
	
	@Test
	public void testsalvaQuestionarioRespondidoPesquisaJaRespondida() throws Exception
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaboradorRespostaEditAction.setColaborador(colaborador);
		
		Questionario questionario = QuestionarioFactory.getEntity(1L);
		questionario.setTipo(TipoQuestionario.PESQUISA);
		colaboradorRespostaEditAction.setQuestionario(questionario);
		
		when(colaboradorQuestionarioManager.isRespondeuPesquisaByColaboradorIdAndQuestionarioId(colaborador.getId(), questionario.getId())).thenReturn(true);
		
		assertEquals("colaboradorQuestionario", colaboradorRespostaEditAction.salvaQuestionarioRespondido());
		assertEquals("Não%20foi%20possível%20gravar%20as%20respostas,%20pois%20a%20pesquisa%20já%20possui%20resposta.", colaboradorRespostaEditAction.getActionMsg());
	}
}

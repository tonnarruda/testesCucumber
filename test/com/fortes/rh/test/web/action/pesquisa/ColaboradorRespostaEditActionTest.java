package com.fortes.rh.test.web.action.pesquisa;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.PesquisaManager;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorRespostaFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.pesquisa.ColaboradorRespostaEditAction;

public class ColaboradorRespostaEditActionTest extends MockObjectTestCase
{
	private ColaboradorRespostaEditAction colaboradorRespostaEditAction;
	private Mock colaboradorRespostaManager;
	private Mock pesquisaManager;

	protected void setUp() throws Exception
	{
		colaboradorRespostaManager = new Mock(ColaboradorRespostaManager.class);

		colaboradorRespostaEditAction = new ColaboradorRespostaEditAction();
		colaboradorRespostaEditAction.setColaboradorRespostaManager((ColaboradorRespostaManager) colaboradorRespostaManager.proxy());

		pesquisaManager = new Mock(PesquisaManager.class);
        colaboradorRespostaEditAction.setPesquisaManager((PesquisaManager) pesquisaManager.proxy());

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}

	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();

		colaboradorRespostaManager = null;
		colaboradorRespostaEditAction = null;

        MockSecurityUtil.verifyRole = false;
		
		super.tearDown();
	}

	public void testExecute() throws Exception
	{
		assertEquals("success", colaboradorRespostaEditAction.execute());
	}

	public void testGetsSets()
	{
		colaboradorRespostaEditAction.getColaborador();
		colaboradorRespostaEditAction.getColaboradorQuestionario();
		colaboradorRespostaEditAction.getColaboradorResposta();
		colaboradorRespostaEditAction.getColaboradorRespostaManager();
		colaboradorRespostaEditAction.getEmpresaSistema();
		colaboradorRespostaEditAction.getModel();
		colaboradorRespostaEditAction.getTela();
		colaboradorRespostaEditAction.getTipoPergunta();
		colaboradorRespostaEditAction.getPesquisa();
		colaboradorRespostaEditAction.getPesquisaManager();
		colaboradorRespostaEditAction.getRespostas();
		colaboradorRespostaEditAction.getRetorno();
		colaboradorRespostaEditAction.getVoltarPara();
		colaboradorRespostaEditAction.setColaboradorResposta(ColaboradorRespostaFactory.getEntity(1L));
		colaboradorRespostaEditAction.setVoltarPara("ewew");
		colaboradorRespostaEditAction.setColaboradorQuestionario(ColaboradorQuestionarioFactory.getEntity(1L));
		colaboradorRespostaEditAction.setRetorno("ewew");
	}

}

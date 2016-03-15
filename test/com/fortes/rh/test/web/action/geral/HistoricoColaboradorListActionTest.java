package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.cargosalario.HistoricoColaboradorListAction;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.ActionContext;

public class HistoricoColaboradorListActionTest extends MockObjectTestCase
{
	private HistoricoColaboradorListAction action;
	private Mock historicoColaboradorManager;
	private Mock areaOrganizacionalManager;
	private Mock estabelecimentoManager;

	protected void setUp () throws Exception
	{
		action = new HistoricoColaboradorListAction();

		historicoColaboradorManager = new Mock(HistoricoColaboradorManager.class);
		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
		estabelecimentoManager = new Mock(EstabelecimentoManager.class);
		
		action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
		action.setHistoricoColaboradorManager((HistoricoColaboradorManager) historicoColaboradorManager.proxy());
		action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
	}

	protected void tearDown() throws Exception
    {
        historicoColaboradorManager = null;
        action = null;
        MockSecurityUtil.verifyRole = false;
        Mockit.restoreAllOriginalDefinitions();
        super.tearDown();
    }

	public void testPrepareRelatorioSituacoes()
	{
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").will(returnValue(new ArrayList<CheckBox>()));
		estabelecimentoManager.expects(once()).method("populaCheckBox").will(returnValue(new ArrayList<CheckBox>()));
		assertEquals("success",action.prepareRelatorioSituacoes());
	}
	
	public void testRelatorioSituacoes()
	{
		historicoColaboradorManager.expects(once()).method("montaRelatorioSituacoes").will(returnValue(new ArrayList<HistoricoColaborador>()));
		assertEquals("successAgruparPorArea",action.relatorioSituacoes());
	}
	public void testRelatorioSituacoesColecaoVaziaException()
	{
		historicoColaboradorManager.expects(once()).method("montaRelatorioSituacoes").will(throwException(new ColecaoVaziaException("Não existem dados para o filtro informado.")));
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").will(returnValue(new ArrayList<CheckBox>()));
		estabelecimentoManager.expects(once()).method("populaCheckBox").will(returnValue(new ArrayList<CheckBox>()));
		assertEquals("input",action.relatorioSituacoes());
	}
	public void testRelatorioSituacoesException()
	{
		historicoColaboradorManager.expects(once()).method("montaRelatorioSituacoes").will(throwException(new Exception()));
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").will(returnValue(new ArrayList<CheckBox>()));
		estabelecimentoManager.expects(once()).method("populaCheckBox").will(returnValue(new ArrayList<CheckBox>()));
		assertEquals("input",action.relatorioSituacoes());
	}
	
	public void testGetSet()
	{
		action.setColaborador(null);
		action.getColaborador();
		action.getParametros();
		action.getAreasCheck();
		action.setAreasCheck(null);
		action.getAreasCheckList();
		action.getEstabelecimentosCheck();
		action.setEstabelecimentosCheck(null);
		action.getEstabelecimentosCheckList();
		action.getDataFim();
		action.getDataIni();
		action.setDataIni(new Date());
		action.setDataFim(new Date());
	}
}

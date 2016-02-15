package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiItemEntregaManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiManager;
import com.fortes.rh.business.sesmt.TipoEPIManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemDevolucao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;
import com.fortes.rh.model.sesmt.TipoEPI;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoEpiItemVO;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiFactory;
import com.fortes.rh.test.util.mockObjects.MockCheckListBoxUtil;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.sesmt.SolicitacaoEpiListAction;
import com.fortes.web.tags.CheckBox;

public class SolicitacaoEpiListActionTest extends MockObjectTestCase
{
	private SolicitacaoEpiListAction action;
	private Mock solicitacaoEpiManager;
	private Mock solicitacaoEpiItemEntregaManager;
	private Mock areaOrganizacionalManager;
	private Mock colaboradorManager;
	private Mock epiManager;
	private Mock estabelecimentoManager;
	private Mock tipoEPIManager;
	
	protected void setUp() throws Exception
	{
		action = new SolicitacaoEpiListAction();
		
		solicitacaoEpiManager = mock(SolicitacaoEpiManager.class);
		action.setSolicitacaoEpiManager((SolicitacaoEpiManager) solicitacaoEpiManager.proxy());
		
		solicitacaoEpiItemEntregaManager = mock(SolicitacaoEpiItemEntregaManager.class);
		action.setSolicitacaoEpiItemEntregaManager((SolicitacaoEpiItemEntregaManager) solicitacaoEpiItemEntregaManager.proxy());
	
		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());

		colaboradorManager = mock(ColaboradorManager.class);
		action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
		
		epiManager = mock(EpiManager.class);
		action.setEpiManager((EpiManager) epiManager.proxy());
		
		estabelecimentoManager = mock(EstabelecimentoManager.class);
		action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
		
		tipoEPIManager = mock(TipoEPIManager.class);
		action.setTipoEPIManager((TipoEPIManager) tipoEPIManager.proxy());
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
		Mockit.redefineMethods(CheckListBoxUtil.class, MockCheckListBoxUtil.class);
	}

	protected void tearDown() throws Exception
	{
		action = null;
		solicitacaoEpiManager = null;
		Mockit.restoreAllOriginalDefinitions();
	}

	public void testPrepareRelatorioEpi()
	{
		epiManager.expects(once()).method("populaCheckToEpi").with(eq(action.getEmpresaSistema().getId()), eq(null)).will(returnValue(new ArrayList<CheckBox>()));
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(eq(action.getEmpresaSistema().getId())).will(returnValue(new ArrayList<CheckBox>()));
		colaboradorManager.expects(once()).method("populaCheckBox").with(eq(action.getEmpresaSistema().getId())).will(returnValue(new ArrayList<CheckBox>()));
		
		assertEquals("success", action.prepareRelatorioEpi());
	}		
	
	public void testRelatorioDevolucaoEpiAgrupadoPorEPI()
	{
		action.setAgruparPor('E');
		Collection<SolicitacaoEpiItemDevolucao> dataSourceDevolucao = Arrays.asList(new SolicitacaoEpiItemDevolucao());

		solicitacaoEpiManager.expects(once()).method("findRelatorioDevolucaoEpi").withAnyArguments().will(returnValue(dataSourceDevolucao));
		assertEquals("success", action.relatorioDevolucaoEpi());
	}
	
	public void testRelatorioDevolucaoEpiAgrupadoPorColaborador()
	{
		action.setAgruparPor('C');
		Collection<SolicitacaoEpiItemDevolucao> dataSourceDevolucao = Arrays.asList(new SolicitacaoEpiItemDevolucao());
		
		solicitacaoEpiManager.expects(once()).method("findRelatorioDevolucaoEpi").withAnyArguments().will(returnValue(dataSourceDevolucao));
		assertEquals("success_agrupar_colaborador", action.relatorioDevolucaoEpi());
	}

	public void testRelatorioDevolucaoEpiException()
	{
		solicitacaoEpiManager.expects(once()).method("findRelatorioDevolucaoEpi").withAnyArguments().will(throwException(new ColecaoVaziaException("Não existem EPIs a serem listados para os filtros informados.")));
		epiManager.expects(once()).method("populaCheckToEpi").with(eq(action.getEmpresaSistema().getId()), eq(null)).will(returnValue(new ArrayList<CheckBox>()));
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(eq(action.getEmpresaSistema().getId())).will(returnValue(new ArrayList<CheckBox>()));
		colaboradorManager.expects(once()).method("populaCheckBox").with(eq(action.getEmpresaSistema().getId())).will(returnValue(new ArrayList<CheckBox>()));
		assertEquals("input", action.relatorioDevolucaoEpi());
		assertEquals("Não existem EPIs a serem listados para os filtros informados.", action.getActionMessages().iterator().next());
	}
	
	public void testRelatorioDevolucaoEpiXlsAgrupadoPorEPI()
	{
		action.setAgruparPor('E');
		Collection<SolicitacaoEpiItemDevolucao> dataSourceDevolucao = Arrays.asList(new SolicitacaoEpiItemDevolucao());

		solicitacaoEpiManager.expects(once()).method("findRelatorioDevolucaoEpi").withAnyArguments().will(returnValue(dataSourceDevolucao));
		assertEquals("successXls", action.relatorioDevolucaoEpiXls());
	}
	
	public void testRelatorioDevolucaoEpiXlsAgrupadoPorColaborador()
	{
		action.setAgruparPor('C');
		Collection<SolicitacaoEpiItemDevolucao> dataSourceDevolucao = Arrays.asList(new SolicitacaoEpiItemDevolucao());

		solicitacaoEpiManager.expects(once()).method("findRelatorioDevolucaoEpi").withAnyArguments().will(returnValue(dataSourceDevolucao));
		assertEquals("success_agrupar_colaboradorXls", action.relatorioDevolucaoEpiXls());
	}
	
	public void testRelatorioDevolucaoEpiXlsException()
	{
		solicitacaoEpiManager.expects(once()).method("findRelatorioDevolucaoEpi").withAnyArguments().will(throwException(new ColecaoVaziaException("Não existem EPIs a serem listados para os filtros informados.")));
		epiManager.expects(once()).method("populaCheckToEpi").with(eq(action.getEmpresaSistema().getId()), eq(null)).will(returnValue(new ArrayList<CheckBox>()));
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(eq(action.getEmpresaSistema().getId())).will(returnValue(new ArrayList<CheckBox>()));
		colaboradorManager.expects(once()).method("populaCheckBox").with(eq(action.getEmpresaSistema().getId())).will(returnValue(new ArrayList<CheckBox>()));
		assertEquals("inputXls", action.relatorioDevolucaoEpiXls());
		assertEquals("Não existem EPIs a serem listados para os filtros informados.", action.getActionMessages().iterator().next());
	}
	
	public void testRelatorioEntregaEpiAgrupadoPorEpi()
	{
		action.setAgruparPor('E');
		Collection<SolicitacaoEpiItemEntrega> dataSourceEntrega = Arrays.asList(new SolicitacaoEpiItemEntrega());

		solicitacaoEpiManager.expects(once()).method("findRelatorioEntregaEpi").withAnyArguments().will(returnValue(dataSourceEntrega));
		assertEquals("success", action.relatorioEntregaEpi());
	}
	
	public void testRelatorioEntregaEpiAgrupadoPorColaborador()
	{
		action.setAgruparPor('C');
		Collection<SolicitacaoEpiItemEntrega> dataSourceEntrega = Arrays.asList(new SolicitacaoEpiItemEntrega());

		solicitacaoEpiManager.expects(once()).method("findRelatorioEntregaEpi").withAnyArguments().will(returnValue(dataSourceEntrega));
		assertEquals("success_agrupar_colaborador", action.relatorioEntregaEpi());
	}
	
	public void testRelatorioEntregaEpiException()
	{
		solicitacaoEpiManager.expects(once()).method("findRelatorioEntregaEpi").withAnyArguments().will(throwException(new ColecaoVaziaException("Não existem EPIs a serem listados para os filtros informados.")));
		epiManager.expects(once()).method("populaCheckToEpi").with(eq(action.getEmpresaSistema().getId()), eq(null)).will(returnValue(new ArrayList<CheckBox>()));
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(eq(action.getEmpresaSistema().getId())).will(returnValue(new ArrayList<CheckBox>()));
		colaboradorManager.expects(once()).method("populaCheckBox").with(eq(action.getEmpresaSistema().getId())).will(returnValue(new ArrayList<CheckBox>()));
		assertEquals("input", action.relatorioEntregaEpi());
		assertEquals("Não existem EPIs a serem listados para os filtros informados.", action.getActionMessages().iterator().next());
	}
	
	public void testRelatorioEntregaXlsEpiAgrupadoPorEpi()
	{
		action.setAgruparPor('E');
		Collection<SolicitacaoEpiItemEntrega> dataSourceEntrega = Arrays.asList(new SolicitacaoEpiItemEntrega());

		solicitacaoEpiManager.expects(once()).method("findRelatorioEntregaEpi").withAnyArguments().will(returnValue(dataSourceEntrega));
		assertEquals("successXls", action.relatorioEntregaEpiXls());
	}
	
	public void testRelatorioEntregaEpiXlsAgrupadoPorColaborador()
	{
		action.setAgruparPor('C');
		Collection<SolicitacaoEpiItemEntrega> dataSourceEntrega = Arrays.asList(new SolicitacaoEpiItemEntrega());

		solicitacaoEpiManager.expects(once()).method("findRelatorioEntregaEpi").withAnyArguments().will(returnValue(dataSourceEntrega));
		assertEquals("success_agrupar_colaboradorXls", action.relatorioEntregaEpiXls());
	}
	
	public void testRelatorioEntregaEpiXlsException()
	{
		solicitacaoEpiManager.expects(once()).method("findRelatorioEntregaEpi").withAnyArguments().will(throwException(new ColecaoVaziaException("Não existem EPIs a serem listados para os filtros informados.")));
		epiManager.expects(once()).method("populaCheckToEpi").with(eq(action.getEmpresaSistema().getId()), eq(null)).will(returnValue(new ArrayList<CheckBox>()));
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(eq(action.getEmpresaSistema().getId())).will(returnValue(new ArrayList<CheckBox>()));
		colaboradorManager.expects(once()).method("populaCheckBox").with(eq(action.getEmpresaSistema().getId())).will(returnValue(new ArrayList<CheckBox>()));
		assertEquals("inputXls", action.relatorioEntregaEpiXls());
		assertEquals("Não existem EPIs a serem listados para os filtros informados.", action.getActionMessages().iterator().next());
	}
	
	public void testPrepareRelatorioVencimentoEpi()
	{
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(eq(action.getEmpresaSistema().getId())).will(returnValue(new ArrayList<CheckBox>()));
    	estabelecimentoManager.expects(once()).method("populaCheckBox").with(eq(action.getEmpresaSistema().getId())).will(returnValue(new ArrayList<CheckBox>()));
		tipoEPIManager.expects(once()).method("getByEmpresa").with(eq(action.getEmpresaSistema().getId())).will(returnValue(new ArrayList<CheckBox>()));
		
		assertEquals("success", action.prepareRelatorioVencimentoEpi());
	}
	
	public void testRelatorioVencimentoEpiAgrupadoPorEpi()
	{
		action.setAgruparPor('E');
		Collection<SolicitacaoEpi> dataSourceSolicitacaoEpi = Arrays.asList(new SolicitacaoEpi());

		solicitacaoEpiManager.expects(once()).method("findRelatorioVencimentoEpi").withAnyArguments().will(returnValue(dataSourceSolicitacaoEpi));
		assertEquals("success", action.relatorioVencimentoEpi());
	}
	
	public void testRelatorioVencimentoEpiAgrupadoPorColaborador()
	{
		action.setAgruparPor('C');
		Collection<SolicitacaoEpi> dataSourceSolicitacaoEpi = Arrays.asList(new SolicitacaoEpi());

		solicitacaoEpiManager.expects(once()).method("findRelatorioVencimentoEpi").withAnyArguments().will(returnValue(dataSourceSolicitacaoEpi));
		assertEquals("success_agrupar_colaborador", action.relatorioVencimentoEpi());
	}
	
	public void testRelatorioVencimentoEpiException()
	{
		solicitacaoEpiManager.expects(once()).method("findRelatorioVencimentoEpi").withAnyArguments().will(throwException(new ColecaoVaziaException("Não existem EPIs a serem listados para os filtros informados.")));
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(eq(action.getEmpresaSistema().getId())).will(returnValue(new ArrayList<CheckBox>()));
    	estabelecimentoManager.expects(once()).method("populaCheckBox").with(eq(action.getEmpresaSistema().getId())).will(returnValue(new ArrayList<CheckBox>()));
		tipoEPIManager.expects(once()).method("getByEmpresa").with(eq(action.getEmpresaSistema().getId())).will(returnValue(new ArrayList<CheckBox>()));
		assertEquals("input", action.relatorioVencimentoEpi());
		assertEquals("Não existem EPIs a serem listados para os filtros informados.", action.getActionMessages().iterator().next());
	}
	
	public void testDeleteSolicitacaoEpiQueJaFoiEntrega() throws Exception
	{
		action.setSolicitacaoEpi(SolicitacaoEpiFactory.getEntity(1L));
		
		solicitacaoEpiItemEntregaManager.expects(once()).method("existeEntrega").with(ANYTHING).will(returnValue(true));
		assertEquals("success", action.delete());
		assertEquals("O Gerenciamento não pôde ser excluído porque já foi entregue.", action.getActionMessages().iterator().next());
	}
	
	public void testDelete() throws Exception
	{
		action.setSolicitacaoEpi(SolicitacaoEpiFactory.getEntity(1L));
		
		solicitacaoEpiItemEntregaManager.expects(once()).method("existeEntrega").with(ANYTHING).will(returnValue(false));
		solicitacaoEpiManager.expects(once()).method("remove").with(ANYTHING).isVoid();
		assertEquals("success", action.delete());
		assertEquals("Gerenciamento de EPIs excluído com sucesso.", action.getActionSuccess().iterator().next());
	}
	
	public void testImprimir() throws Exception
	{
		Collection<SolicitacaoEpiItemVO> dataSourceLista = Arrays.asList(new SolicitacaoEpiItemVO());
		solicitacaoEpiManager.expects(once()).method("findEpisWithItens").withAnyArguments().will(returnValue(dataSourceLista));
		assertEquals("success", action.imprimir());
	}
	
	public void testListSemSolicitacoesASeremListadas() throws Exception
	{
		estabelecimentoManager.expects(once()).method("populaCheckBox").with(eq(action.getEmpresaSistema().getId())).will(returnValue(new ArrayList<CheckBox>()));
		tipoEPIManager.expects(once()).method("find").with(eq(new String[]{"empresa.id"}), eq(new Object[]{action.getEmpresaSistema().getId()})).will(returnValue(new ArrayList<TipoEPI>()));
		
		solicitacaoEpiManager.expects(once()).method("getCount").withAnyArguments().will(returnValue(0));
		solicitacaoEpiManager.expects(once()).method("findAllSelect").withAnyArguments();
		assertEquals("success", action.list());
		assertEquals("Nenhuma solicitação de EPIs a ser listada.", action.getActionMessages().iterator().next());
	}
	
	public void testList() throws Exception
	{
		estabelecimentoManager.expects(once()).method("populaCheckBox").with(eq(action.getEmpresaSistema().getId())).will(returnValue(new ArrayList<CheckBox>()));
		tipoEPIManager.expects(once()).method("find").with(eq(new String[]{"empresa.id"}), eq(new Object[]{action.getEmpresaSistema().getId()})).will(returnValue(new ArrayList<TipoEPI>()));
		
		solicitacaoEpiManager.expects(once()).method("getCount").withAnyArguments().will(returnValue(1));
		solicitacaoEpiManager.expects(once()).method("findAllSelect").withAnyArguments().will(returnValue(Arrays.asList(new SolicitacaoEpi())));
		assertEquals("success", action.list());
		assertEquals(0, action.getActionMessages().size());
	}
}

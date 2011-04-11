package com.fortes.rh.test.web.action.captacao.indicador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.relatorio.TurnOver;
import com.fortes.rh.model.geral.relatorio.TurnOverCollection;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.action.captacao.indicador.IndicadorTurnOverListAction;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionContext;

public class IndicadorTurnOverListActionTest extends MockObjectTestCase
{
	private IndicadorTurnOverListAction action;
	private Mock areaOrganizacionalManager;
	private Mock estabelecimentoManager;
	private Mock colaboradorManager;
	private Mock parametrosDoSistemaManager;
	private Mock cargoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new IndicadorTurnOverListAction();
        estabelecimentoManager = new Mock(EstabelecimentoManager.class);
        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        colaboradorManager = new Mock(ColaboradorManager.class);
        parametrosDoSistemaManager= new Mock(ParametrosDoSistemaManager.class);
        cargoManager= new Mock(CargoManager.class);

        action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
        action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
        action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
        action.setCargoManager((CargoManager) cargoManager.proxy());

        MockSpringUtil.mocks.put("parametrosDoSistemaManager", parametrosDoSistemaManager);
        
        Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
        Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
        Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
    }

    protected void tearDown() throws Exception
    {
        estabelecimentoManager = null;
        areaOrganizacionalManager = null;
        action = null;
        super.tearDown();
    }

    public void testPrepare() throws Exception
    {
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
    	cargoManager.expects(once()).method("populaCheckBox").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
    	estabelecimentoManager.expects(once()).method("populaCheckBox").will(returnValue(new ArrayList<CheckBox>()));

    	assertEquals("success", action.prepare());
    }

    public void testList() throws Exception
    {
    	action.setDataDe("01/2008");
    	action.setDataAte("12/2008");

    	Collection<TurnOver> turnOvers = new ArrayList<TurnOver>();
    	turnOvers.add(new TurnOver());
    	colaboradorManager.expects(once()).method("montaTurnOver").withAnyArguments().will(returnValue(turnOvers));
    	
    	assertEquals("success", action.list());
    }

    public void testListVazio() throws Exception
    {
    	action.setDataDe("01/2008");
    	action.setDataAte("12/2008");

    	ColecaoVaziaException colecaoVaziaException = new ColecaoVaziaException();
    	colaboradorManager.expects(once()).method("montaTurnOver").withAnyArguments().will(throwException(colecaoVaziaException));
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
    	cargoManager.expects(once()).method("populaCheckBox").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
    	estabelecimentoManager.expects(once()).method("populaCheckBox").will(returnValue(new ArrayList<CheckBox>()));

    	assertEquals("input", action.list());
    }

    public void testListPeriodoGrande() throws Exception
    {
    	action.setDataDe("01/2008");
    	action.setDataAte("01/2010");

    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
    	cargoManager.expects(once()).method("populaCheckBox").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
    	estabelecimentoManager.expects(once()).method("populaCheckBox").will(returnValue(new ArrayList<CheckBox>()));

    	assertEquals("input", action.list());
    }

    public void testGetsSets()
    {
    	action.getDataAte();
    	action.getDataDe();
    	action.getAreasCheck();
    	action.setAreasCheck(new String[]{});
    	action.getAreasCheckList();
    	action.setAreasCheckList(new ArrayList<CheckBox>());
    	action.getParametros();
    	action.setParametros(new HashMap<Object, Object>());
    	action.getEstabelecimentosCheck();
    	action.getEstabelecimentosCheckList();
    	action.setEstabelecimentosCheck(new String[]{});
    	action.setEstabelecimentosCheckList(new ArrayList<CheckBox>());
    	action.getCargosCheck();
    	action.getCargosCheckList();
    	action.setCargosCheck(new String[]{});
    	action.setCargosCheckList(new ArrayList<CheckBox>());
    	action.getDataSource();
    	action.setDataSource(new ArrayList<TurnOverCollection>());
    }
}
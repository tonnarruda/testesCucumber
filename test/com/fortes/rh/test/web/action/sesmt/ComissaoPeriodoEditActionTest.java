package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import javax.persistence.PersistenceException;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.sesmt.ComissaoMembroManager;
import com.fortes.rh.business.sesmt.ComissaoPeriodoManager;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoPeriodoFactory;
import com.fortes.rh.web.action.sesmt.ComissaoPeriodoEditAction;

public class ComissaoPeriodoEditActionTest extends MockObjectTestCase
{
	private ComissaoPeriodoEditAction action;
	private Mock manager;
	private Mock areaOrganizacionalManager;
	private Mock comissaoMembroManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        manager = new Mock(ComissaoPeriodoManager.class);
        action = new ComissaoPeriodoEditAction();
        action.setComissaoPeriodoManager((ComissaoPeriodoManager) manager.proxy());

        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());

        comissaoMembroManager = new Mock(ComissaoMembroManager.class);
        action.setComissaoMembroManager((ComissaoMembroManager) comissaoMembroManager.proxy());

        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
        action.setComissao(ComissaoFactory.getEntity(11L));
    }

    protected void tearDown() throws Exception
    {
        manager = null;
        action = null;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

    public void testList() throws Exception
    {
    	manager.expects(once()).method("findByComissao").will(returnValue(new ArrayList<ComissaoPeriodo>()));

    	assertEquals("success", action.list());
    	assertNotNull(action.getComissaoPeriodos());
    }
    public void testListInput() throws Exception
    {
    	action.setComissao(null);
    	assertEquals("input", action.list());
    }

    public void testDelete() throws Exception
    {
    	action.setComissaoPeriodo(ComissaoPeriodoFactory.getEntity(1L));
    	manager.expects(once()).method("remove");

    	manager.expects(once()).method("findByComissao").will(returnValue(new ArrayList<ComissaoPeriodo>()));
    	assertEquals(action.delete(), "success");
    }

    public void testDeleteException() throws Exception
    {
    	action.setComissaoPeriodo(ComissaoPeriodoFactory.getEntity(1L));
    	manager.expects(once()).method("remove").will(throwException(new PersistenceException()));
    	manager.expects(once()).method("findByComissao").will(returnValue(new ArrayList<ComissaoPeriodo>()));

    	assertEquals("success", action.delete());
    	assertEquals(1, action.getActionErrors().size());
    }

    public void testPrepare() throws Exception
    {

    	ComissaoPeriodo comissaoPeriodo = ComissaoPeriodoFactory.getEntity(1L);
    	action.setComissaoPeriodo(comissaoPeriodo);

    	manager.expects(once()).method("findByIdProjection").will(returnValue(comissaoPeriodo));
    	comissaoMembroManager.expects(once()).method("findByComissaoPeriodo").will(returnValue(new ArrayList<ComissaoMembro>()));
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao");

    	assertEquals("success", action.prepare());
    }

    public void testInsert() throws Exception
    {
    	manager.expects(once()).method("save");
    	assertEquals("success", action.insert());
    }

    public void testUpdate() throws Exception
    {
    	action.setComissaoPeriodo(ComissaoPeriodoFactory.getEntity(1L));
    	manager.expects(once()).method("atualiza");
    	manager.expects(once()).method("findByComissao").will(returnValue(new ArrayList<ComissaoPeriodo>()));
    	assertEquals("success", action.update());
    }
    public void testUpdateException() throws Exception
    {
    	action.setComissaoPeriodo(ComissaoPeriodoFactory.getEntity(1L));
    	manager.expects(once()).method("atualiza").with(ANYTHING,ANYTHING,ANYTHING,ANYTHING).will(throwException(new RuntimeException()));
    	manager.expects(once()).method("findByIdProjection").will(returnValue(action.getComissaoPeriodo()));
    	comissaoMembroManager.expects(once()).method("findByComissaoPeriodo").will(returnValue(new ArrayList<ComissaoMembro>()));
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao");

    	assertEquals("input", action.update());
    }

    public void testGetSet() throws Exception
    {
    	assertNotNull(action.getComissaoPeriodo());

    	ComissaoPeriodo eleicaol = new ComissaoPeriodo();
    	action.setComissaoPeriodo(eleicaol);
    	assertTrue(action.getComissaoPeriodo() instanceof ComissaoPeriodo);

    	action.getAreasCheckList();
    	action.getComissaoPeriodos();
    	action.setColaboradorsCheck(new String[]{""});
    	action.setFuncaoComissaos(new String[]{""});
    	action.getColaboradorsCheckList();
    	action.setComissaoMembro(new ComissaoMembro());
    	action.getComissaoMembro();

    	action.setComissaoMembroIds(new String[]{"1"});
    	action.getComissao();
    	action.getComissaoMembros();
    	action.getFuncoes();
    	action.getTipos();

    	action.setNomeBusca("Jéssica");
    	assertEquals("Jéssica",action.getNomeBusca());

    	assertEquals(new String[]{""}[0],action.getFuncaoComissaos()[0]);
    }



}
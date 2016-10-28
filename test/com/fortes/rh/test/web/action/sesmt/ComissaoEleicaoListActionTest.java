package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.sesmt.ComissaoEleicaoManager;
import com.fortes.rh.business.sesmt.EleicaoManager;
import com.fortes.rh.model.sesmt.ComissaoEleicao;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoEleicaoFactory;
import com.fortes.rh.test.factory.sesmt.EleicaoFactory;
import com.fortes.rh.web.action.sesmt.ComissaoEleicaoListAction;

public class ComissaoEleicaoListActionTest extends MockObjectTestCase
{
	private ComissaoEleicaoListAction action;
	private Mock manager;
	private Mock areaOrganizacionalManager;
	private Mock comissaoEleicaoManager;
	private Mock eleicaoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        manager = new Mock(ComissaoEleicaoManager.class);
        action = new ComissaoEleicaoListAction();
        action.setComissaoEleicaoManager((ComissaoEleicaoManager) manager.proxy());

        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());

        comissaoEleicaoManager = new Mock(ComissaoEleicaoManager.class);
        action.setComissaoEleicaoManager((ComissaoEleicaoManager) comissaoEleicaoManager.proxy());
        
        eleicaoManager = mock(EleicaoManager.class);
        action.setEleicaoManager((EleicaoManager) eleicaoManager.proxy());

        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
        action.setEleicao(EleicaoFactory.getEntity(1L));
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
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao");
    	comissaoEleicaoManager.expects(once()).method("findByEleicao").will(returnValue(new ArrayList<ComissaoEleicao>()));
    	eleicaoManager.expects(once()).method("findByIdProjection").with(eq(1L)).will(returnValue(action.getEleicao()));

    	assertEquals(action.list(), "success");
    	assertNotNull(action.getComissaoEleicaos());
    }

    public void testDelete() throws Exception
    {
    	action.setComissaoEleicao(ComissaoEleicaoFactory.getEntity(1L));
    	comissaoEleicaoManager.expects(once()).method("remove");
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao");
    	comissaoEleicaoManager.expects(once()).method("findByEleicao").will(returnValue(new ArrayList<ComissaoEleicao>()));
    	//list
    	eleicaoManager.expects(once()).method("findByIdProjection").with(eq(1L)).will(returnValue(action.getEleicao()));

    	assertEquals(action.delete(), "success");
    }

    public void testDeleteException()
    {
    	action.setComissaoEleicao(ComissaoEleicaoFactory.getEntity(1L));
    	comissaoEleicaoManager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));

    	Exception exception = null;
    	try
		{
			action.delete();
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
    }

    public void testSaveFuncao() throws Exception
	{
    	comissaoEleicaoManager.expects(once()).method("updateFuncao");
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao");
    	comissaoEleicaoManager.expects(once()).method("findByEleicao").will(returnValue(new ArrayList<ComissaoEleicao>()));
    	//list
    	eleicaoManager.expects(once()).method("findByIdProjection").with(eq(1L)).will(returnValue(action.getEleicao()));

    	assertEquals("success", action.saveFuncao());
	}

    public void testSaveFuncaoException() throws Exception
    {
    	comissaoEleicaoManager.expects(once()).method("updateFuncao").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao");
    	comissaoEleicaoManager.expects(once()).method("findByEleicao").will(returnValue(new ArrayList<ComissaoEleicao>()));
    	//list
    	eleicaoManager.expects(once()).method("findByIdProjection").with(eq(1L)).will(returnValue(action.getEleicao()));

    	assertEquals("success", action.saveFuncao());
    	assertEquals(1, action.getActionErrors().size());
    }

    public void testPrepareInsert() throws Exception
    {
    	assertEquals("success", action.prepareInsert());
    }
    public void testPrepareUpdate() throws Exception
    {
    	action.setComissaoEleicao(ComissaoEleicaoFactory.getEntity(1L));
    	comissaoEleicaoManager.expects(once()).method("findById");

    	assertEquals("success", action.prepareUpdate());
    }
    public void testInsert() throws Exception
    {
    	comissaoEleicaoManager.expects(once()).method("save");
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao");
    	comissaoEleicaoManager.expects(once()).method("findByEleicao").will(returnValue(new ArrayList<ComissaoEleicao>()));
    	//list
    	eleicaoManager.expects(once()).method("findByIdProjection").with(eq(1L)).will(returnValue(action.getEleicao()));

    	assertEquals("success", action.insert());
    }
    public void testInsertException() throws Exception
    {
    	comissaoEleicaoManager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));;
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao");
    	comissaoEleicaoManager.expects(once()).method("findByEleicao").will(returnValue(new ArrayList<ComissaoEleicao>()));
    	//list
    	eleicaoManager.expects(once()).method("findByIdProjection").with(eq(1L)).will(returnValue(action.getEleicao()));

    	assertEquals("success", action.insert());
    	assertEquals(1, action.getActionErrors().size());
    }
    public void testUpdate() throws Exception
    {
    	comissaoEleicaoManager.expects(once()).method("update");
    	assertEquals("success", action.update());
    }

    public void testGetSet() throws Exception
    {
    	assertNotNull(action.getComissaoEleicao());

    	ComissaoEleicao eleicaol = new ComissaoEleicao();
    	action.setComissaoEleicao(eleicaol);
    	assertTrue(action.getComissaoEleicao() instanceof ComissaoEleicao);

    	action.getAreasCheckList();
    	action.getComissaoEleicaos();
    	action.setColaboradorsCheck(new String[]{""});
    	action.setFuncaoComissaos(new String[]{""});
    	action.setComissaoEleicaoIds(new String[]{""});
    	action.getFuncaoComissaoEleitoral();
    	action.getColaboradorsCheckList();
    	action.getEleicao();

    	action.setNomeBusca("Jéssica");
    	assertEquals("Jéssica",action.getNomeBusca());

    	assertEquals(new String[]{""}[0],action.getFuncaoComissaos()[0]);
    }



}
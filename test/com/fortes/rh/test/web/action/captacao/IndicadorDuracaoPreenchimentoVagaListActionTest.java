//package com.fortes.rh.test.web.action.captacao;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//import org.jmock.Mock;
//import org.jmock.MockObjectTestCase;
//
//import com.fortes.rh.business.acesso.PerfilManager;
//import com.fortes.rh.model.acesso.Perfil;
//import com.fortes.rh.web.action.acesso.PerfilListAction;
//
//public class IndicadorDuracaoPreenchimentoVagaListActionTest extends MockObjectTestCase
//{
//	private PerfilListAction action;
//	private Mock manager;
//
//    protected void setUp() throws Exception
//    {
//        super.setUp();
//        action = new PerfilListAction();
//        manager = new Mock(PerfilManager.class);
//        action.setPerfilManager((PerfilManager) manager.proxy());
//    }
//
//    protected void tearDown() throws Exception
//    {
//        manager = null;
//        action = null;
//        super.tearDown();
//    }
//
//    public void testExecute() throws Exception
//    {
//    	assertEquals(action.execute(), "success");
//    }
//
//    public void testList() throws Exception
//    {
//    	Perfil testData = new Perfil();
//    	testData.setId(1L);
//    	testData.setNome("Perfil 1");
//    	testData.setPapeis(null);
//
//    	Perfil testData2 = new Perfil();
//    	testData2.setId(2L);
//    	testData2.setNome("Perfil 2");
//    	testData.setPapeis(null);
//
//    	Collection<Perfil> perfilsAux = new ArrayList<Perfil>();
//    	perfilsAux.add(testData);
//    	perfilsAux.add(testData2);
//
//    	manager.expects(once()).method("findAll").will(returnValue(perfilsAux));
//
//    	assertEquals(action.list(), "success");
//    	assertEquals(action.getPerfils(), perfilsAux);
//    	manager.verify();
//    }
//}
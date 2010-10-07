package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.acesso.PerfilManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.web.action.geral.ParametrosDoSistemaEditAction;

public class ParametrosDoSistemaEditActionTest extends MockObjectTestCase
{
	private ParametrosDoSistemaEditAction action;
	private Mock manager;
	private Mock perfilManager;
	private Mock exameManager;

	protected void setUp()
	{
		action = new ParametrosDoSistemaEditAction();
		manager = new Mock(ParametrosDoSistemaManager.class);
		perfilManager = new Mock(PerfilManager.class);
		exameManager = new Mock(ExameManager.class);

		action.setParametrosDoSistemaManager((ParametrosDoSistemaManager) manager.proxy());
		action.setPerfilManager((PerfilManager) perfilManager.proxy());
		action.setExameManager((ExameManager) exameManager.proxy());
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
	}

    public void testPrepareUpdate() throws Exception
    {
    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
    	
    	manager.expects(once()).method("findById").with(eq(1L)).will(returnValue(parametrosDoSistema));
    	perfilManager.expects(once()).method("findAll").withNoArguments().will(returnValue(new ArrayList<Perfil>()));
    	exameManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Exame>()));

    	assertEquals(action.prepareUpdate(), "success");
    	
    }
  
    public void testUpdate() throws Exception
    {
    	
    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
    	parametrosDoSistema.setExame(new Exame());
    	
    	action.setParametrosDoSistema(parametrosDoSistema);
    	
    	manager.expects(once()).method("findById").with(eq(1L)).will(returnValue(parametrosDoSistema));
    	
    	manager.expects(once()).method("update");
    	
    	perfilManager.expects(once()).method("findAll").withNoArguments().will(returnValue(new ArrayList<Perfil>()));
    	exameManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Exame>()));

    	assertEquals("success", action.update());
    	assertEquals("Configurações do Sistema atualizadas com sucesso!", action.getActionMessages().toArray()[0]);
    }
    
    public void testGetsSets() throws Exception
    {
    	action.getExames();
    	action.getPerfils();
    	action.setParametrosDoSistema(null);
    	assertNotNull(action.getParametrosDoSistema());
    }
}
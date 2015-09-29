package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.acesso.PerfilManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.web.action.geral.ParametrosDoSistemaEditAction;
import com.opensymphony.webwork.ServletActionContext;

public class ParametrosDoSistemaEditActionTest extends MockObjectTestCase
{
	private ParametrosDoSistemaEditAction action;
	private Mock manager;
	private Mock perfilManager;

	protected void setUp()
	{
		action = new ParametrosDoSistemaEditAction();
		manager = new Mock(ParametrosDoSistemaManager.class);
		perfilManager = new Mock(PerfilManager.class);

		action.setParametrosDoSistemaManager((ParametrosDoSistemaManager) manager.proxy());
		action.setPerfilManager((PerfilManager) perfilManager.proxy());
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
	}

    public void testPrepareUpdate() throws Exception
    {
    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
    	parametrosDoSistema.setModulosPermitidosSomatorio(21);// número 21 representa 3 modulos.
    	
    	manager.expects(once()).method("findById").with(eq(1L)).will(returnValue(parametrosDoSistema));
    	perfilManager.expects(once()).method("findAll").withNoArguments().will(returnValue(new ArrayList<Perfil>()));

    	assertEquals(action.prepareUpdate(), "success");
    	assertEquals(8, action.getModulosSistema().size());
    	assertEquals(3, action.getModulosSistemaCheck().length);
    }
  
    public void testUpdate() throws Exception
    {
    	
    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
    	parametrosDoSistema.setSessionTimeout(90);
    	
    	action.setParametrosDoSistema(parametrosDoSistema);
    	action.setUsuarioLogado(UsuarioFactory.getEntity(1L));
//    	action.setHorariosBackup("02");
    	
    	manager.expects(once()).method("findById").with(eq(1L)).will(returnValue(parametrosDoSistema));
    	
    	manager.expects(once()).method("update");
    	
    	perfilManager.expects(once()).method("findAll").withNoArguments().will(returnValue(new ArrayList<Perfil>()));

    	assertEquals("success", action.update());
    	assertEquals("Configurações do sistema atualizadas com sucesso.", action.getActionSuccess().toArray()[0]);
    }
    
    public void testGetsSets() throws Exception
    {
    	action.getPerfils();
    	action.setParametrosDoSistema(null);
    	assertNotNull(action.getParametrosDoSistema());
    }
}
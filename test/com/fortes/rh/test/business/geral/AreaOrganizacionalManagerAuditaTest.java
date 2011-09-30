package com.fortes.rh.test.business.geral;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.spring.aop.callback.AreaOrganizacionalAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;

public class AreaOrganizacionalManagerAuditaTest extends MockObjectTestCase
{
    public void testMetodosAuditados() 
    {
    	Class<AreaOrganizacionalManager> manager = AreaOrganizacionalManager.class;
    	Method[] metodosManager = manager.getDeclaredMethods();

    	Class<AreaOrganizacionalAuditorCallbackImpl> auditorCallback = AreaOrganizacionalAuditorCallbackImpl.class;
    	Method[] metodosAuditorCallback = auditorCallback.getDeclaredMethods();
    	
    	Collection<String> nomeMetodosCallback = new ArrayList<String>();
    	for (Method methodCallback : metodosAuditorCallback) 
    		nomeMetodosCallback.add(methodCallback.getName());

    	for (Method methodManager : metodosManager) 
    	{
    		if(methodManager.isAnnotationPresent(Audita.class))
    			assertTrue(nomeMetodosCallback.contains(methodManager.getName()));
		}
    }
    
    /**
     *  Verifica as assinaturas dos metodos devido a auditoria
     *  @see AreaOrganizacionalAuditorCallbackImpl
     */
    public void testOrdemArgumentosMetodosAuditados()
    {
    	Class<AreaOrganizacionalManager> manager = AreaOrganizacionalManager.class;
    	
    	Exception exception = null;
    	
    	try {
			manager.getMethod("insertLotacaoAC", AreaOrganizacional.class, Empresa.class);
			manager.getMethod("editarLotacaoAC", AreaOrganizacional.class, Empresa.class);
			manager.getMethod("deleteLotacaoAC", AreaOrganizacional.class, Empresa.class);
		
    	} catch (NoSuchMethodException e) {
			exception = e;
		}
		
    	assertNull(exception);
    }
}

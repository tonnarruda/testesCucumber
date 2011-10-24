package com.fortes.rh.test.business.geral;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.spring.aop.callback.EtapaSeletivaAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;

public class EtapaSeletivaManagerAuditaTest extends MockObjectTestCase
{
    public void testMetodosAuditados() 
    {
    	Class<EtapaSeletivaManager> manager = EtapaSeletivaManager.class;
    	Method[] metodosManager = manager.getDeclaredMethods();

    	Class<EtapaSeletivaAuditorCallbackImpl> auditorCallback = EtapaSeletivaAuditorCallbackImpl.class;
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
     *  @see EtapaSeletivaAuditorCallbackImpl
     */
    public void testOrdemArgumentosMetodosAuditados()
    {
    	Class<EtapaSeletivaManager> manager = EtapaSeletivaManager.class;
    	
    	Exception exception = null;
    	
    	try {
			manager.getMethod("update", EtapaSeletiva.class, Empresa.class);
			manager.getMethod("remove", EtapaSeletiva.class, Empresa.class);
		
    	} catch (NoSuchMethodException e) {
			exception = e;
		}
		
    	assertNull(exception);
    }
}

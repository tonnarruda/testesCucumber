package com.fortes.rh.test.business.geral;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.security.spring.aop.callback.IndiceAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;

public class IndiceManagerAuditaTest extends MockObjectTestCase
{
    public void testMetodosAuditados() 
    {
    	Class<IndiceManager> manager = IndiceManager.class;
    	Method[] metodosManager = manager.getDeclaredMethods();

    	Class<IndiceAuditorCallbackImpl> auditorCallback = IndiceAuditorCallbackImpl.class;
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
     *  @see IndiceAuditorCallbackImpl
     */
    public void testOrdemArgumentosMetodosAuditados()
    {
    	Class<IndiceManager> manager = IndiceManager.class;
    	
    	Exception exception = null;
    	
    	try {
    		// a ordem dos argumentos é importante para a auditoria
			manager.getMethod("save", Indice.class, IndiceHistorico.class);
			manager.getMethod("removeIndice", Long.class);
		
    	} catch (NoSuchMethodException e) {
			exception = e;
		}
		
    	assertNull(exception);
    }
}

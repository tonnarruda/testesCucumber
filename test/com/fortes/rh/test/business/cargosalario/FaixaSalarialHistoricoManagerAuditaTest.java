package com.fortes.rh.test.business.cargosalario;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.spring.aop.callback.FaixaSalarialHistoricoAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;

public class FaixaSalarialHistoricoManagerAuditaTest extends MockObjectTestCase
{
    public void testMetodosAuditados() 
    {
    	Class<FaixaSalarialHistoricoManager> manager = FaixaSalarialHistoricoManager.class;
    	Method[] metodosManager = manager.getDeclaredMethods();

    	Class<FaixaSalarialHistoricoAuditorCallbackImpl> auditorCallback = FaixaSalarialHistoricoAuditorCallbackImpl.class;
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
     *  @see FaixaSalarialHistoricoAuditorCallbackImpl
     */
    public void testOrdemArgumentosMetodosAuditados()
    {
    	Class<FaixaSalarialHistoricoManager> manager = FaixaSalarialHistoricoManager.class;
    	
    	Exception exception = null;
    	
    	try {
			manager.getMethod("save", FaixaSalarialHistorico.class, FaixaSalarial.class, Empresa.class, boolean.class);
			manager.getMethod("update", FaixaSalarialHistorico.class, FaixaSalarial.class, Empresa.class);
			manager.getMethod("remove", Long.class, Empresa.class);
		
    	} catch (NoSuchMethodException e) {
    		e.printStackTrace();
			exception = e;
		}
		
    	assertNull(exception);
    }
}

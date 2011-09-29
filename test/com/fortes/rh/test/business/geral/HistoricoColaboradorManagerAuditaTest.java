package com.fortes.rh.test.business.geral;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.spring.aop.callback.HistoricoColaboradorAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;

public class HistoricoColaboradorManagerAuditaTest extends MockObjectTestCase
{
    public void testMetodosAuditados() 
    {
    	Class<HistoricoColaboradorManager> manager = HistoricoColaboradorManager.class;
    	Method[] metodosManager = manager.getDeclaredMethods();

    	Class<HistoricoColaboradorAuditorCallbackImpl> auditorCallback = HistoricoColaboradorAuditorCallbackImpl.class;
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
     *  @see HistoricoColaboradorAuditorCallbackImpl
     */
    public void testOrdemArgumentosMetodosAuditados()
    {
    	Class<HistoricoColaboradorManager> manager = HistoricoColaboradorManager.class;
    	
    	Exception exception = null;
    	
    	try {
    		manager.getMethod("insertHistorico", HistoricoColaborador.class, Empresa.class);
			manager.getMethod("updateHistorico", HistoricoColaborador.class, Empresa.class);
			manager.getMethod("removeHistoricoAndReajuste", Long.class, Long.class, Empresa.class);
		
    	} catch (NoSuchMethodException e) {
			exception = e;
		}
		
    	assertNull(exception);
    }
}

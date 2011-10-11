package com.fortes.rh.test.business.cargosalario;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.TabelaReajusteColaboradorManager;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.spring.aop.callback.TabelaReajusteColaboradorAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;

public class TabelaReajusteColaboradorManagerAuditaTest extends MockObjectTestCase
{
    public void testMetodosAuditados() 
    {
    	Class<TabelaReajusteColaboradorManager> manager = TabelaReajusteColaboradorManager.class;
    	Method[] metodosManager = manager.getDeclaredMethods();

    	Class<TabelaReajusteColaboradorAuditorCallbackImpl> auditorCallback = TabelaReajusteColaboradorAuditorCallbackImpl.class;
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
     *  @see TabelaReajusteColaboradorAuditorCallbackImpl
     */
    public void testOrdemArgumentosMetodosAuditados()
    {
    	Class<TabelaReajusteColaboradorManager> manager = TabelaReajusteColaboradorManager.class;
    	
    	Exception exception = null;
    	
    	try {
			manager.getMethod("aplicar", TabelaReajusteColaborador.class, Empresa.class, Collection.class);
			manager.getMethod("cancelar", Long.class, Empresa.class);
			manager.getMethod("remove", TabelaReajusteColaborador.class);
			manager.getMethod("update", TabelaReajusteColaborador.class);
		
    	} catch (NoSuchMethodException e) {
    		e.printStackTrace();
			exception = e;
		}
		
    	assertNull(exception);
    }
}

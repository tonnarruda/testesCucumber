package com.fortes.rh.test.business.geral;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.spring.aop.callback.ColaboradorAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;

public class ColaboradorManagerAuditaTest extends MockObjectTestCase
{
    public void testMetodosAuditados() 
    {
    	Class<ColaboradorManager> manager = ColaboradorManager.class;
    	Method[] metodosManager = manager.getDeclaredMethods();

    	Class<ColaboradorAuditorCallbackImpl> auditorCallback = ColaboradorAuditorCallbackImpl.class;
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
     *  @see ColaboradorAuditorCallbackImpl
     */
    public void testOrdemArgumentosMetodosAuditados()
    {
    	Class<ColaboradorManager> manager = ColaboradorManager.class;
    	
    	Exception exception = null;
    	
    	try {
			manager.getMethod("insert", Colaborador.class, Double.class, Long.class, Collection.class, Collection.class, Collection.class, Solicitacao.class, Empresa.class);
			manager.getMethod("update", Colaborador.class, Collection.class, Collection.class, Collection.class, Empresa.class, boolean.class, Double.class);
			manager.getMethod("remove", Colaborador.class, Empresa.class);
			manager.getMethod("desligaColaborador", boolean.class, Date.class, String.class, Long.class, Long.class);
			manager.getMethod("religaColaborador", Long.class);
		
    	} catch (NoSuchMethodException e) {
			exception = e;
		}
		
    	assertNull(exception);
    }
}

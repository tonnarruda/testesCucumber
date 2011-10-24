package com.fortes.rh.test.business.geral;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.security.spring.aop.callback.CandidatoAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;

public class CandidatoManagerAuditaTest extends MockObjectTestCase
{
    public void testMetodosAuditados() 
    {
    	Class<CandidatoManager> manager = CandidatoManager.class;
    	Method[] metodosManager = manager.getDeclaredMethods();

    	Class<CandidatoAuditorCallbackImpl> auditorCallback = CandidatoAuditorCallbackImpl.class;
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
     *  @see CandidatoAuditorCallbackImpl
     */
    public void testOrdemArgumentosMetodosAuditados()
    {
    	Class<CandidatoManager> manager = CandidatoManager.class;
    	
    	Exception exception = null;
    	
    	try {
			manager.getMethod("update", Candidato.class);
			manager.getMethod("removeCandidato", Candidato.class);
		
    	} catch (NoSuchMethodException e) {
			exception = e;
		}
		
    	assertNull(exception);
    }
}

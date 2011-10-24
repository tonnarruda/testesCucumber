package com.fortes.rh.test.business.geral;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.security.spring.aop.callback.PerguntaAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;

public class PerguntaManagerAuditaTest extends MockObjectTestCase
{
    public void testMetodosAuditados() 
    {
    	Class<PerguntaManager> manager = PerguntaManager.class;
    	Method[] metodosManager = manager.getDeclaredMethods();

    	Class<PerguntaAuditorCallbackImpl> auditorCallback = PerguntaAuditorCallbackImpl.class;
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
     *  @see PerguntaAuditorCallbackImpl
     */
    public void testOrdemArgumentosMetodosAuditados()
    {
    	Class<PerguntaManager> manager = PerguntaManager.class;
    	
    	Exception exception = null;
    	
    	try {
			manager.getMethod("salvarPergunta", Pergunta.class, String[].class, Integer[].class, Integer.class);
			manager.getMethod("atualizarPergunta", Pergunta.class, String[].class, Integer[].class);
			manager.getMethod("removerPergunta", Pergunta.class);
		
    	} catch (NoSuchMethodException e) {
			exception = e;
		}
		
    	assertNull(exception);
    }
}

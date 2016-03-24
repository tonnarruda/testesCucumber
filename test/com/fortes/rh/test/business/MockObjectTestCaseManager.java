package com.fortes.rh.test.business;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.annotations.TesteAutomatico;

@SuppressWarnings("rawtypes")
public class MockObjectTestCaseManager<M extends GenericManagerImpl> extends MockObjectTestCase
{
    public M manager;

    ArrayList<String> tiposPrimitivos = new ArrayList<String>(Arrays.asList("boolean","byte","char","double","float","int","long","short"));
    Object[] valores = {true,0,0,0,0,0,0.0,0.0};
    
	private Object[] geraParametros(Class<?>[] tipoParametros) 
    {
    	int i = 0;
    	Object[] r = new Object[tipoParametros.length];
    	for (Class<?> tipoParametro : tipoParametros) {
			if (tiposPrimitivos.contains(tipoParametro.getName())) 
				r[i++] = valores[tiposPrimitivos.indexOf(tipoParametro.getName())];
			else
				r[i++] = null;
		}
    	return r;
	}
    
    public void testeAutomatico(Mock dao)
    {
    	for (Method method : manager.getClass().getDeclaredMethods()) {
    		if (method.isAnnotationPresent(TesteAutomatico.class)){
    			try {
    				TesteAutomatico testeAutomatico = method.getAnnotation(TesteAutomatico.class);
    				String metodoMock = StringUtils.defaultIfEmpty((testeAutomatico).metodoMock(), method.getName());
    				
    				if(tiposPrimitivos.contains(method.getReturnType().toString()))
    					dao.expects(once()).method(metodoMock).withAnyArguments().will(returnValue(valores[tiposPrimitivos.indexOf(method.getReturnType().toString())]));
    				else
    					dao.expects(once()).method(metodoMock).withAnyArguments();

    				Object[] parametros = geraParametros(method.getParameterTypes()) ;
    				method.invoke(manager, parametros);
    				
    				assertTrue("Método executado sem erro",true);
    			} catch (Exception e) {
    				e.printStackTrace();
    				
    				assertTrue("Método executado com erro: "+method.getName(),false);
    			}
    		}
		}
    }
}

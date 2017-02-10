package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.pesquisa.AspectoManager;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.util.CollectionUtil;

@Component
@RemoteProxy(name="AspectoDWR")
@SuppressWarnings("unchecked")
public class AspectoDWR
{
	@Autowired private AspectoManager aspectoManager;
	
	// Só a descrição, Usado apenas para autocomplete
	@RemoteMethod
	public String[] getAspectos(Long questionarioId)
	{
		if(questionarioId != null)
		{
			Collection<Aspecto> aspectos =  aspectoManager.findByQuestionario(questionarioId);

			return  new CollectionUtil<Aspecto>().convertCollectionToArrayString(aspectos, "getNome");
		}

		return new String[0];
	}
	
	/** Id + Descrição **/
	@RemoteMethod
	public Map<Object, Object> getAspectosId(Long questionarioId)
	{
		if(questionarioId != null)
		{
			Collection<Aspecto> aspectos =  aspectoManager.findByQuestionario(questionarioId);
			
			return  new CollectionUtil<Aspecto>().convertCollectionToMap(aspectos, "getId", "getNome");
		}
		
		return new HashMap<Object, Object>();
	}
}
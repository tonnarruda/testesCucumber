package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.pesquisa.AspectoManager;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.util.CollectionUtil;

@Component
public class AspectoDWR
{
	@Autowired
	private AspectoManager aspectoManager;
	
	// Só a descrição, Usado apenas para autocomplete
	public String[] getAspectos(Long questionarioId)
	{
		if(questionarioId != null)
		{
			Collection<Aspecto> aspectos =  aspectoManager.findByQuestionario(questionarioId);

			return  new CollectionUtil<Aspecto>().convertCollectionToArrayString(aspectos, "getNome");
		}

		return new String[0];
	}
	
	// Id + Descrição
	@SuppressWarnings("unchecked")
	public Map<Object, Object> getAspectosId(Long questionarioId)
	{
		if(questionarioId != null)
		{
			Collection<Aspecto> aspectos =  aspectoManager.findByQuestionario(questionarioId);
			
			return  new CollectionUtil<Aspecto>().convertCollectionToMap(aspectos, "getId", "getNome");
		}
		
		return new HashMap<Object, Object>();
	}

	public void setAspectoManager(AspectoManager aspectoManager)
	{
		this.aspectoManager = aspectoManager;
	}
}

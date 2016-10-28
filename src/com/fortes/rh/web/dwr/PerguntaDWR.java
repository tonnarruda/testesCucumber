package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;

@Component
@SuppressWarnings("unchecked")
public class PerguntaDWR
{
	@Autowired
	private PerguntaManager perguntaManager;

	public Map<Object, Object> getPerguntas(Long questionarioId)
	{
		if(questionarioId != null)
		{
			Collection<Pergunta> perguntas =  perguntaManager.findByQuestionario(questionarioId);
			return new CollectionUtil<Pergunta>().convertCollectionToMap(perguntas, "getId", "getOrdemMaisTexto");
		}

		return new HashMap<Object, Object>();
	}

	public Map getPerguntasByAspecto(Long questionarioId, String[] aspectosCheck)
	{
		Collection<Pergunta> perguntas =  perguntaManager.findByQuestionarioAspectoPergunta(questionarioId,
				LongUtil.arrayStringToArrayLong(aspectosCheck), null, false);

		return  new CollectionUtil<Pergunta>().convertCollectionToMap(perguntas,"getId","getOrdemMaisTexto");
	}

	public void setPerguntaManager(PerguntaManager perguntaManager)
	{
		this.perguntaManager = perguntaManager;
	}
}

package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;

@Component
@RemoteProxy(name="PerguntaDWR")
@SuppressWarnings({"unchecked", "rawtypes"})
public class PerguntaDWR
{
	@Autowired private PerguntaManager perguntaManager;

	@RemoteMethod
	public Map<Object, Object> getPerguntas(Long questionarioId)
	{
		if(questionarioId != null)
		{
			Collection<Pergunta> perguntas =  perguntaManager.findByQuestionario(questionarioId);
			return new CollectionUtil<Pergunta>().convertCollectionToMap(perguntas, "getId", "getOrdemMaisTexto");
		}

		return new HashMap<Object, Object>();
	}

	@RemoteMethod
	public Map getPerguntasByAspecto(Long questionarioId, String[] aspectosCheck)
	{
		Collection<Pergunta> perguntas =  perguntaManager.findByQuestionarioAspectoPergunta(questionarioId,
				LongUtil.arrayStringToArrayLong(aspectosCheck), null, false);

		return  new CollectionUtil<Pergunta>().convertCollectionToMap(perguntas,"getId","getOrdemMaisTexto");
	}
}
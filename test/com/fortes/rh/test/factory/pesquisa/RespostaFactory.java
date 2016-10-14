package com.fortes.rh.test.factory.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Resposta;

public class RespostaFactory
{
	public static Resposta getEntity()
	{
		Resposta pergunta = new Resposta();

		pergunta.setId(null);
		pergunta.setOrdem(1);
		pergunta.setTexto("texto");

		return pergunta;
	}

	public static Resposta getEntity(Long id)
	{
		Resposta resposta = getEntity();
		resposta.setId(id);

		return resposta;
	}

	public static Collection<Resposta> getCollection(Long id)
	{
		Collection<Resposta> respostas = new ArrayList<Resposta>();
		respostas.add(getEntity(id));

		return respostas;
	}
	
	public static Resposta getEntity(Long id, Pergunta pergunta, Integer peso)
	{
		Resposta resposta = getEntity();
		resposta.setId(id);
		resposta.setPergunta(pergunta);
		resposta.setPeso(peso);

		return resposta;
	}
	
	public static Resposta getEntity(String texto, Pergunta pergunta, Integer ordem)
	{
		Resposta resposta = getEntity();
		resposta.setTexto(texto);
		resposta.setPergunta(pergunta);
		
		if(ordem != null)
			resposta.setOrdem(ordem);

		return resposta;
	}
}

package com.fortes.rh.test.factory.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

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
		Resposta pergunta = getEntity();
		pergunta.setId(id);

		return pergunta;
	}

	public static Collection<Resposta> getCollection(Long id)
	{
		Collection<Resposta> respostas = new ArrayList<Resposta>();
		respostas.add(getEntity(id));

		return respostas;
	}
}

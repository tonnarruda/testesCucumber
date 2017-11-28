package com.fortes.rh.test.factory.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.pesquisa.Pergunta;

public class PerguntaFactory
{
	public static Pergunta getEntity()
	{
		Pergunta pergunta = new Pergunta();

		pergunta.setId(null);
		pergunta.setOrdem(1);
		pergunta.setTexto("texto");
		pergunta.setTextoComentario("textoComentario");
		pergunta.setComentario(true);
		pergunta.setTipo(TipoPergunta.OBJETIVA);

		return pergunta;
	}

	public static Pergunta getEntity(Long id)
	{
		Pergunta pergunta = getEntity();
		pergunta.setId(id);

		return pergunta;
	}

	public static Collection<Pergunta> getCollection(Long id)
	{
		Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
		perguntas.add(getEntity(id));

		return perguntas;
	}
	
	public static Pergunta getEntity(Long id, Integer tipoPergunta, Integer peso)
	{
		Pergunta pergunta = getEntity(id);
		pergunta.setTipo(tipoPergunta);
		pergunta.setPeso(peso);
		return pergunta;
	}
	
	public static Pergunta getEntity(Long id, Integer tipoPergunta, String texto)
	{
		Pergunta pergunta = getEntity(id);
		pergunta.setTipo(tipoPergunta);
		pergunta.setTexto(texto);
		return pergunta;
	}
}

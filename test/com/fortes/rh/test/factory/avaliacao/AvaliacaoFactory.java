package com.fortes.rh.test.factory.avaliacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.avaliacao.Avaliacao;

public class AvaliacaoFactory
{
	public static Avaliacao getEntity()
	{
		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setId(null);
		return avaliacao;
	}

	public static Avaliacao getEntity(Long id)
	{
		Avaliacao avaliacao = getEntity();
		avaliacao.setId(id);

		return avaliacao;
	}

	public static Collection<Avaliacao> getCollection()
	{
		Collection<Avaliacao> avaliacaos = new ArrayList<Avaliacao>();
		avaliacaos.add(getEntity());

		return avaliacaos;
	}
	
	public static Collection<Avaliacao> getCollection(Long id)
	{
		Collection<Avaliacao> avaliacaos = new ArrayList<Avaliacao>();
		avaliacaos.add(getEntity(id));
		
		return avaliacaos;
	}
}
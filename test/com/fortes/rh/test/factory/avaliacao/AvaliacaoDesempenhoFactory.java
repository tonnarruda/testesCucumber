package com.fortes.rh.test.factory.avaliacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;

public class AvaliacaoDesempenhoFactory
{
	public static AvaliacaoDesempenho getEntity()
	{
		AvaliacaoDesempenho avaliacaoDesempenho = new AvaliacaoDesempenho();
		avaliacaoDesempenho.setId(null);
		return avaliacaoDesempenho;
	}

	public static AvaliacaoDesempenho getEntity(Long id)
	{
		AvaliacaoDesempenho avaliacaoDesempenho = getEntity();
		avaliacaoDesempenho.setId(id);

		return avaliacaoDesempenho;
	}

	public static Collection<AvaliacaoDesempenho> getCollection()
	{
		Collection<AvaliacaoDesempenho> avaliacaoDesempenhos = new ArrayList<AvaliacaoDesempenho>();
		avaliacaoDesempenhos.add(getEntity());

		return avaliacaoDesempenhos;
	}
	
	public static Collection<AvaliacaoDesempenho> getCollection(Long id)
	{
		Collection<AvaliacaoDesempenho> avaliacaoDesempenhos = new ArrayList<AvaliacaoDesempenho>();
		avaliacaoDesempenhos.add(getEntity(id));
		
		return avaliacaoDesempenhos;
	}
	
	public static AvaliacaoDesempenho getEntity(String titulo)
	{
		AvaliacaoDesempenho avaliacaoDesempenho = getEntity();
		avaliacaoDesempenho.setTitulo(titulo);

		return avaliacaoDesempenho;
	}
}

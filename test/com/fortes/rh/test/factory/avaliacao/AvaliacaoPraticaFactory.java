package com.fortes.rh.test.factory.avaliacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.avaliacao.AvaliacaoPratica;

public class AvaliacaoPraticaFactory
{
	public static AvaliacaoPratica getEntity()
	{
		AvaliacaoPratica avaliacaoPratica = new AvaliacaoPratica();
		avaliacaoPratica.setId(null);
		avaliacaoPratica.setNotaMinima(7.0);
		avaliacaoPratica.setTitulo("Avaliação Pratica");
		return avaliacaoPratica;
	}

	public static AvaliacaoPratica getEntity(Long id)
	{
		AvaliacaoPratica avaliacaoPratica = getEntity();
		avaliacaoPratica.setId(id);

		return avaliacaoPratica;
	}
	
	public static AvaliacaoPratica getEntity(Long id, Long certificacaoId)
	{
		AvaliacaoPratica avaliacaoPratica = getEntity();
		avaliacaoPratica.setId(id);
		avaliacaoPratica.setCertificacaoId(certificacaoId);

		return avaliacaoPratica;
	}

	public static Collection<AvaliacaoPratica> getCollection()
	{
		Collection<AvaliacaoPratica> avaliacaoPraticas = new ArrayList<AvaliacaoPratica>();
		avaliacaoPraticas.add(getEntity());

		return avaliacaoPraticas;
	}
	
	public static Collection<AvaliacaoPratica> getCollection(Long id)
	{
		Collection<AvaliacaoPratica> avaliacaoPraticas = new ArrayList<AvaliacaoPratica>();
		avaliacaoPraticas.add(getEntity(id));
		
		return avaliacaoPraticas;
	}
	
	public static AvaliacaoPratica getEntity(Double nota)
	{
		AvaliacaoPratica avaliacaoPratica = getEntity();
		avaliacaoPratica.setNotaMinima(nota);

		return avaliacaoPratica;
	}
}

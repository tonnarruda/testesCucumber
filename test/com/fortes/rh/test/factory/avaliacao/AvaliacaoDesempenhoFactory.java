package com.fortes.rh.test.factory.avaliacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.geral.Empresa;

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
	
	public static AvaliacaoDesempenho getEntity(Long id, String titulo, Date dataInicio, Date dataFim, boolean liberada, boolean anonima, Avaliacao avaliacao, Empresa empresa )
	{
		AvaliacaoDesempenho avaliacaoDesempenho = getEntity(id, titulo, liberada, avaliacao, empresa);
		avaliacaoDesempenho.setInicio(dataInicio);
		avaliacaoDesempenho.setFim(dataFim);
		avaliacaoDesempenho.setAnonima(anonima);
		return avaliacaoDesempenho;
	}
	
	public static AvaliacaoDesempenho getEntity(Long id, String titulo, boolean liberada, Avaliacao avaliacao, Empresa empresa )
	{
		AvaliacaoDesempenho avaliacaoDesempenho = getEntity(id);
		avaliacaoDesempenho.setTitulo(titulo);
		avaliacaoDesempenho.setAvaliacao(avaliacao);
		avaliacaoDesempenho.setLiberada(liberada);
		avaliacaoDesempenho.setEmpresa(empresa);
		return avaliacaoDesempenho;
	}
}

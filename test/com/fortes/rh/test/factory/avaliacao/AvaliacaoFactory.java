package com.fortes.rh.test.factory.avaliacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.geral.Empresa;

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

	public static Avaliacao getEntity(Long id, Empresa empresa)
	{
		Avaliacao avaliacao = getEntity(id);
		avaliacao.setEmpresa(empresa);

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
	
	public static Avaliacao getEntity(char tipoModeloAvaliacao, PeriodoExperiencia periodoExperiencia)
	{
		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setPeriodoExperiencia(periodoExperiencia);
		avaliacao.setTipoModeloAvaliacao(tipoModeloAvaliacao);
		return avaliacao;
	}
	
	public static Avaliacao getEntity(Empresa empresa, String titulo, char tipoModeloAvaliacao, PeriodoExperiencia periodoExperiencia, boolean ativo)
	{
		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setEmpresa(empresa);
		avaliacao.setTitulo(titulo);
		avaliacao.setPeriodoExperiencia(periodoExperiencia);
		avaliacao.setTipoModeloAvaliacao(tipoModeloAvaliacao);
		avaliacao.setAtivo(ativo);
		return avaliacao;
	}
}
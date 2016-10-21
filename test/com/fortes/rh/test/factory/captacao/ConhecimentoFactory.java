package com.fortes.rh.test.factory.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.geral.Empresa;

public class ConhecimentoFactory
{
	public static Conhecimento getConhecimento()
	{
		Conhecimento conhecimento = new Conhecimento();
		conhecimento.setAreaOrganizacionals(null);
		conhecimento.setId(null);
		conhecimento.setNome("programador cobol");
		conhecimento.setEmpresa(null);
		return conhecimento;
	}

	public static Collection<Conhecimento> getCollection()
	{
		Collection<Conhecimento> conhecimentos = new ArrayList<Conhecimento>();

		conhecimentos.add(getConhecimento());

		return conhecimentos;
	}

	public static Conhecimento getConhecimento(Long id)
	{
		Conhecimento conhecimento = getConhecimento();
		conhecimento.setId(id);
		
		return conhecimento;
	}
	
	public static Conhecimento getConhecimento(Long id, String nome, Empresa empresa)
	{
		Conhecimento conhecimento = getConhecimento(id);
		conhecimento.setNome(nome);
		conhecimento.setEmpresa(empresa);
		
		return conhecimento;
	}
	
	public static Conhecimento getConhecimento(String nome)
	{
		Conhecimento conhecimento = getConhecimento();
		conhecimento.setNome(nome);
		return conhecimento;
	}
}

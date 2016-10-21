package com.fortes.rh.test.factory.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.geral.Empresa;

public class HabilidadeFactory
{
	public static Habilidade getEntity()
	{
		Habilidade habilidade = new Habilidade();
		habilidade.setId(null);
		return habilidade;
	}

	public static Habilidade getEntity(Long id)
	{
		Habilidade habilidade = getEntity();
		habilidade.setId(id);

		return habilidade;
	}

	public static Habilidade getEntity(Long id, String nome, Empresa empresa)
	{
		Habilidade habilidade = getEntity(1L);
		habilidade.setNome(nome);
		habilidade.setEmpresa(empresa);
		
		return habilidade;
	}
	public static Collection<Habilidade> getCollection()
	{
		Collection<Habilidade> habilidades = new ArrayList<Habilidade>();
		habilidades.add(getEntity());

		return habilidades;
	}
	
	public static Collection<Habilidade> getCollection(Long id)
	{
		Collection<Habilidade> habilidades = new ArrayList<Habilidade>();
		habilidades.add(getEntity(id));
		
		return habilidades;
	}
}

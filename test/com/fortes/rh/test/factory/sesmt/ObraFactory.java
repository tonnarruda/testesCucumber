package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.sesmt.Obra;

public class ObraFactory
{
	public static Obra getEntity()
	{
		Obra obra = new Obra();
		obra.setId(null);
		obra.setNome("Obra");
		obra.setTipoObra("tipoObra");
		
		Endereco endereco = new Endereco();
		endereco.setLogradouro("logradouro");
		endereco.setNumero("00");
		endereco.setComplemento("complemento");
		endereco.setBairro("bairro");
		endereco.setCidade(null);
		endereco.setUf(null);
		endereco.setCep("0000000");
		obra.setEndereco(endereco);
		
		return obra;
	}

	public static Obra getEntity(Long id)
	{
		Obra obra = getEntity();
		obra.setId(id);

		return obra;
	}

	public static Collection<Obra> getCollection()
	{
		Collection<Obra> obras = new ArrayList<Obra>();
		obras.add(getEntity());

		return obras;
	}
	
	public static Collection<Obra> getCollection(Long id)
	{
		Collection<Obra> obras = new ArrayList<Obra>();
		obras.add(getEntity(id));
		
		return obras;
	}
}

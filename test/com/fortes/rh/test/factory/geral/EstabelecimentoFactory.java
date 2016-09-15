package com.fortes.rh.test.factory.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Estabelecimento;

public class EstabelecimentoFactory
{
	public static Estabelecimento getEntity()
	{
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua A");
		endereco.setNumero("65");
		endereco.setComplemento("Apto 10");
		endereco.setBairro("Bairro da Paz");
		endereco.setUf(null);
		endereco.setCidade(null);
		endereco.setCep("60713590");

		Estabelecimento estabelecimento = new Estabelecimento();
		estabelecimento.setId(null);
		estabelecimento.setNome("nome do estabelecimento");
		estabelecimento.setEndereco(endereco);
		estabelecimento.setComplementoCnpj("0001");
		estabelecimento.setEmpresa(null);

		return estabelecimento;
	}

	public static Estabelecimento getEntity(Long id)
	{
		Estabelecimento estabelecimento = getEntity();
		estabelecimento.setId(id);

		return estabelecimento;
	}

	public static Collection<Estabelecimento> getCollection()
	{
		Collection<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();
		estabelecimentos.add(getEntity());

		return estabelecimentos;
	}
	
	public static Estabelecimento getEntity(String nome)
	{
		Estabelecimento estabelecimento = getEntity();
		estabelecimento.setNome(nome);

		return estabelecimento;
	}

}

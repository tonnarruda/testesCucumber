package com.fortes.rh.test.factory.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;


public class AmbienteFactory
{
	public static Ambiente getEntity()
	{
		Ambiente Ambiente = new Ambiente();

		Ambiente.setId(null);
		Ambiente.setNome("nome do ambiente");

		return Ambiente;
	}

	public static Ambiente getEntity(Long id)
	{
		Ambiente Ambiente = getEntity();
		Ambiente.setId(id);

		return Ambiente;
	}

	public static Collection<Ambiente> getCollection()
	{
		Collection<Ambiente> Ambientes = new ArrayList<Ambiente>();
		Ambientes.add(getEntity());

		return Ambientes;
	}

	public static Ambiente getEntity(String nome, Empresa empresa, Estabelecimento estabelecimento) {
		Ambiente ambiente = new Ambiente();
		ambiente.setNome(nome);
		ambiente.setEmpresa(empresa);
		ambiente.setEstabelecimento(estabelecimento);
		return ambiente;
	}
}

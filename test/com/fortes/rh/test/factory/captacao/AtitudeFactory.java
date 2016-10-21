package com.fortes.rh.test.factory.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.geral.Empresa;

public class AtitudeFactory
{
	public static Atitude getEntity()
	{
		Atitude atitude = new Atitude();
		atitude.setId(null);
		return atitude;
	}

	public static Atitude getEntity(Long id)
	{
		Atitude atitude = getEntity();
		atitude.setId(id);

		return atitude;
	}
	
	public static Atitude getEntity(Long id, String nome, Empresa empresa)
	{
		Atitude atitude = getEntity(id);
		atitude.setNome(nome);
		atitude.setEmpresa(empresa);

		return atitude;
	}

	public static Collection<Atitude> getCollection()
	{
		Collection<Atitude> atitudes = new ArrayList<Atitude>();
		atitudes.add(getEntity());

		return atitudes;
	}
	
	public static Collection<Atitude> getCollection(Long id)
	{
		Collection<Atitude> atitudes = new ArrayList<Atitude>();
		atitudes.add(getEntity(id));
		
		return atitudes;
	}

	public static Atitude getEntity(String nome) {
		Atitude atitude = getEntity();
		atitude.setNome(nome);

		return atitude;
	}
}

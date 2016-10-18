package com.fortes.rh.test.factory.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.Funcao;

public class FuncaoFactory
{
	public static Funcao getEntity()
	{
		Funcao Funcao = new Funcao();

		Funcao.setId(null);
		Funcao.setNome("nome da funcao");

		return Funcao;
	}

	public static Funcao getEntity(Long id)
	{
		Funcao Funcao = getEntity();
		Funcao.setId(id);

		return Funcao;
	}
	
	public static Funcao getEntity(Long id, String nome)
	{
		Funcao Funcao = getEntity(id);
		Funcao.setNome(nome);
		
		return Funcao;
	}

	public static Collection<Funcao> getCollection()
	{
		Collection<Funcao> Funcaos = new ArrayList<Funcao>();
		Funcaos.add(getEntity());

		return Funcaos;
	}
}

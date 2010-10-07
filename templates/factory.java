package com.fortes.rh.test.factory.#NOME_PACOTE#;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.#NOME_PACOTE#.#NOME_CLASSE#;

public class #NOME_CLASSE#Factory
{
	public static #NOME_CLASSE# getEntity()
	{
		#NOME_CLASSE# #NOME_CLASSE_MINUSCULO# = new #NOME_CLASSE#();
		#NOME_CLASSE_MINUSCULO#.setId(null);
		return #NOME_CLASSE_MINUSCULO#;
	}

	public static #NOME_CLASSE# getEntity(Long id)
	{
		#NOME_CLASSE# #NOME_CLASSE_MINUSCULO# = getEntity();
		#NOME_CLASSE_MINUSCULO#.setId(id);

		return #NOME_CLASSE_MINUSCULO#;
	}

	public static Collection<#NOME_CLASSE#> getCollection()
	{
		Collection<#NOME_CLASSE#> #NOME_CLASSE_MINUSCULO#s = new ArrayList<#NOME_CLASSE#>();
		#NOME_CLASSE_MINUSCULO#s.add(getEntity());

		return #NOME_CLASSE_MINUSCULO#s;
	}
	
	public static Collection<#NOME_CLASSE#> getCollection(Long id)
	{
		Collection<#NOME_CLASSE#> #NOME_CLASSE_MINUSCULO#s = new ArrayList<#NOME_CLASSE#>();
		#NOME_CLASSE_MINUSCULO#s.add(getEntity(id));
		
		return #NOME_CLASSE_MINUSCULO#s;
	}
}

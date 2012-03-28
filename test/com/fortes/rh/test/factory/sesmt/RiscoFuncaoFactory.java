package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.RiscoFuncao;

public class RiscoFuncaoFactory
{
	public static RiscoFuncao getEntity()
	{
		RiscoFuncao riscoFuncao = new RiscoFuncao();
		riscoFuncao.setId(null);
		return riscoFuncao;
	}

	public static RiscoFuncao getEntity(Long id)
	{
		RiscoFuncao riscoFuncao = getEntity();
		riscoFuncao.setId(id);

		return riscoFuncao;
	}

	public static Collection<RiscoFuncao> getCollection()
	{
		Collection<RiscoFuncao> riscoFuncaos = new ArrayList<RiscoFuncao>();
		riscoFuncaos.add(getEntity());

		return riscoFuncaos;
	}
	
	public static Collection<RiscoFuncao> getCollection(Long id)
	{
		Collection<RiscoFuncao> riscoFuncaos = new ArrayList<RiscoFuncao>();
		riscoFuncaos.add(getEntity(id));
		
		return riscoFuncaos;
	}
}

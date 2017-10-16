package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.TipoEPI;

public class EpiFactory
{
	public static Epi getEntity()
	{
		Epi epi = new Epi();
		epi.setNome("nome");
		epi.setFabricante("fabricante");
		epi.setFardamento(true);
		epi.setEmpresa(null);
		return epi;
	}

	public static Epi getEntity(Long id)
	{
		Epi epi = getEntity();
		epi.setId(id);
		return epi;
	}
	
	public static Epi getEntity(Long id, String nome)
	{
		Epi epi = getEntity(id);
		epi.setNome(nome);
		return epi;
	}
	
	public static Epi getEntity(Long id, String nome, Empresa empresa)
	{
		Epi epi = getEntity(id);
		epi.setNome(nome);
		epi.setEmpresa(empresa);
		
		return epi;
	}
	
	public static Epi getEntity(TipoEPI tipoEPI)
	{
		Epi epi = getEntity();
		epi.setTipoEPI(tipoEPI);
		return epi;
	}

	
}
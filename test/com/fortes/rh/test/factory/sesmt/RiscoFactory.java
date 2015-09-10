package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.sesmt.Risco;

public class RiscoFactory 
{
	public static Risco getEntity()
	{
		Risco risco = new Risco();

		risco.setDescricao("descricao");
		risco.setEmpresa(null);
		risco.setEpis(null);
		risco.setGrupoRisco("grupo");

		return risco;
	}
	
	public static Risco getEntity(Long id)
	{
		Risco risco= getEntity();
		risco.setId(id);
		
		return risco;
	}
	
	public static Risco getEntity(Long id, String descricao, String grupoRisco)
	{
		Risco risco= getEntity();
		risco.setId(id);
		risco.setDescricao(descricao);
		risco.setGrupoRisco(grupoRisco);
		
		return risco;
	}
}

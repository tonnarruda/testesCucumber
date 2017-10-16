package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.FatorDeRisco;
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
	
	public static Risco getEntity(String descricao, String grupoRisco, String grupoRiscoESocial, Empresa empresa, FatorDeRisco fatorDeRisco)
	{
		Risco risco= getEntity(null, descricao, grupoRisco);
		risco.setGrupoRiscoESocial(grupoRiscoESocial);
		risco.setEmpresa(empresa);
		risco.setFatorDeRisco(fatorDeRisco);
		return risco;
	}
}
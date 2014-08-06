package com.fortes.rh.model.portalcolaborador;

import com.fortes.rh.model.cargosalario.SituacaoColaborador;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class HistoricoColaboradorPC extends AbstractAdapterPC
{
	public HistoricoColaboradorPC() 
	{

	}

	public HistoricoColaboradorPC(SituacaoColaborador situacaoColaborador) 
	{

	}
		
	public String toJson()
	{
		Gson gson = new Gson();
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("colaborador", gson.toJsonTree(this));
		
		return jsonObject.toString();
	}
}

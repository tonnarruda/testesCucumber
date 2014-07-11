package com.fortes.rh.model.portalcolaborador;

import com.fortes.rh.model.geral.Empresa;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class EmpresaPC extends AbstractAdapterPC 
{
	private String nome;
	@SerializedName("base_cnpj")
	private String baseCnpj; 

	public EmpresaPC() {

	}

	public EmpresaPC(Empresa empresa) 
	{
		this.nome 		= empresa.getNome();
		this.baseCnpj 	= empresa.getCnpj();
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getBaseCnpj() {
		return baseCnpj;
	}
	
	public void setBaseCnpj(String baseCnpj) {
		this.baseCnpj = baseCnpj;
	}
	
	public String toJson()
	{
		Gson gson = new Gson();
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("empresa", gson.toJsonTree(this));
		
		return jsonObject.toString();
	}
}

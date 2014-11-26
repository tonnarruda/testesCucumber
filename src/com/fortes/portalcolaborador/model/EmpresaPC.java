package com.fortes.portalcolaborador.model;

import com.fortes.rh.model.geral.Empresa;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class EmpresaPC extends AbstractAdapterPC 
{
	private Long id;
	private String nome;
	@SerializedName("base_cnpj")
	private String baseCnpj;
	@SerializedName("email_responsavel")
	private String emailResponsavel;

	public EmpresaPC() {

	}

	public EmpresaPC(Empresa empresa) 
	{
		this.id 		= empresa.getId();
		this.nome 		= empresa.getNome();
		this.baseCnpj 	= empresa.getCnpj();
		this.emailResponsavel 	= empresa.getEmailRespRH();		
	}
	
	public String toJson()
	{
		Gson gson = new Gson();
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("empresa", gson.toJsonTree(this));
		
		return jsonObject.toString();
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}

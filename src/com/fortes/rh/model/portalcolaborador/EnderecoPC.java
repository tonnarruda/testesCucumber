package com.fortes.rh.model.portalcolaborador;

import com.fortes.rh.model.geral.Endereco;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class EnderecoPC extends AbstractAdapterPC 
{
	private String cep;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	@SerializedName("cidade_id")
	private Integer cidadeCodigoIBGE;

	public EnderecoPC() {

	}

	public EnderecoPC(Endereco endereco) {
		this.cep 			= endereco.getCep();
		this.logradouro 	= endereco.getLogradouro();
		this.numero 		= endereco.getNumero();
		this.complemento 	= endereco.getComplemento();
		this.bairro 		= endereco.getBairro();
		
		if (endereco.getCidade() != null)
			this.cidadeCodigoIBGE = endereco.getCidade().getCodigoIBGE();
	}
	
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public Integer getCidadeCodigoIBGE() {
		return cidadeCodigoIBGE;
	}

	public void setCidadeCodigoIBGE(Integer cidadeCodigoIBGE) {
		this.cidadeCodigoIBGE = cidadeCodigoIBGE;
	}

	public String toJson()
	{
		Gson gson = new Gson();
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("endereco", gson.toJsonTree(this));
		
		return jsonObject.toString();
	}
}

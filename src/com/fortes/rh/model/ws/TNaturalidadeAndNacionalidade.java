package com.fortes.rh.model.ws;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TNaturalidadeAndNacionalidade implements Serializable
{
	private String codigo;
	private String naturalidade;
	private String nacionalidade;
	
	public String getNaturalidade() {
		return naturalidade;
	}
	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}
	public String getNacionalidade() {
		return nacionalidade;
	}
	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
}
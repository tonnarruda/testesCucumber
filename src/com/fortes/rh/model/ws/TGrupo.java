package com.fortes.rh.model.ws;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TGrupo implements Serializable
{

	private String codigo;
	private String descricao;
	
	public TGrupo(){
	}
	
	public TGrupo(String codigo, String descricao) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
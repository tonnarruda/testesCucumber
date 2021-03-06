package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class CodigoCBO implements Serializable
{
	@Id
	@Column(length=6)
	private String codigo;
	@Column(length=200)
	private String descricao;
	
	public CodigoCBO() {}
	
	public CodigoCBO(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getdescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}

package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@SuppressWarnings("serial")
@Entity
public class Cid implements Serializable
{
	@Id
	@Column(length=4)
	private String codigo;
	
	private String descricao;
	
	public Cid() {}
	
	public Cid(String codigo, String descricao) {
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

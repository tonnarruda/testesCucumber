package com.fortes.rh.model.geral;

import java.io.Serializable;

import com.fortes.rh.model.geral.GrupoAC;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="grupoAC_sequence", allocationSize=1)
public class GrupoAC extends AbstractModel implements Serializable
{
	@Column(length=20)
	private String descricao;
	@Column(length=3)
	private String codigo;
	
	public String getCodigoDescricao() {
		return codigo + " - " + descricao;
	}

	public String getDescricao() {
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

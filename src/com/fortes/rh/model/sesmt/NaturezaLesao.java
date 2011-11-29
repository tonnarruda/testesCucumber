package com.fortes.rh.model.sesmt;

import java.io.Serializable;

import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.NaturezaLesao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="naturezaLesao_sequence", allocationSize=1)
public class NaturezaLesao extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String descricao;
	@ManyToOne
	private Empresa empresa;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}

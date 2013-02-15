package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="providencia_sequence", allocationSize=1)
public class Providencia extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String descricao;

	@ManyToOne(fetch=FetchType.LAZY)
	private Empresa empresa;
	
	public String getDescricao() {
		return descricao;
	}

	public String getDescricaoSemProvidencia() {
		return descricao == null ? "Sem Providência" : descricao;
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

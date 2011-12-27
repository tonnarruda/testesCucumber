package com.fortes.rh.model.geral;

import java.io.Serializable;

import com.fortes.rh.model.geral.TipoDespesa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="tipoDespesa_sequence", allocationSize=1)
public class TipoDespesa extends AbstractModel implements Serializable
{
	@Column(length=50)
	public String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}

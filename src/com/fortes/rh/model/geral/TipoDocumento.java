package com.fortes.rh.model.geral;

import java.io.Serializable;

import com.fortes.rh.model.geral.TipoDocumento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="tipoDocumento_sequence", allocationSize=1)
public class TipoDocumento extends AbstractModel implements Serializable
{
	@Column(length=50)
    private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}

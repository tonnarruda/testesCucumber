package com.fortes.rh.model.sesmt.eSocialTabelas;

import java.io.Serializable;

import com.fortes.rh.model.sesmt.eSocialTabelas.SituacaoGeradoraAcidenteTrabalho;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="situacaoGeradoraAcidenteTrabalho_sequence", allocationSize=1)
public class SituacaoGeradoraAcidenteTrabalho extends AbstractModel implements Serializable
{
	@Column(length=9)
	private String codigo;
	
	@Lob
	private String descricao;

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

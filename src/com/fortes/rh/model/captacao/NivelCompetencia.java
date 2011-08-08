package com.fortes.rh.model.captacao;

import java.io.Serializable;

import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.geral.Empresa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="nivelCompetencia_sequence", allocationSize=1)
public class NivelCompetencia extends AbstractModel implements Serializable
{
	@Column(length=15)
	private String descricao;
	private Integer ordem;
	@ManyToOne
	private Empresa empresa;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getOrdem() {
		return ordem;
	}
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}

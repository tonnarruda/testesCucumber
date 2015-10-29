package com.fortes.rh.model.captacao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="nivelCompetencia_sequence", allocationSize=1)
public class NivelCompetencia extends AbstractModel implements Serializable
{
	@Column(length=15)
	private String descricao;
	@ManyToOne
	private Empresa empresa;

	@Transient
	private NivelCompetenciaHistorico nivelCompetenciaHistoricoAtual;
	
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
	public NivelCompetenciaHistorico getNivelCompetenciaHistoricoAtual() {
		return nivelCompetenciaHistoricoAtual;
	}
	public void setNivelCompetenciaHistoricoAtual(
			NivelCompetenciaHistorico nivelCompetenciaHistoricoAtual) {
		this.nivelCompetenciaHistoricoAtual = nivelCompetenciaHistoricoAtual;
	}
}
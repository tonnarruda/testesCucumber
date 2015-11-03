package com.fortes.rh.model.captacao;

import java.io.Serializable;

import com.fortes.rh.model.captacao.ConfigHistoricoNivel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="configHistoricoNivel_sequence", allocationSize=1)
public class ConfigHistoricoNivel extends AbstractModel implements Serializable
{
	private Integer ordem;
	private Double percentual;
	
	@ManyToOne
	private NivelCompetencia nivelCompetencia;
	
	@ManyToOne
	private NivelCompetenciaHistorico nivelCompetenciaHistorico;
	
	public Integer getOrdem() {
		return ordem;
	}
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
	public Double getPercentual() {
		return percentual;
	}
	public void setPercentual(Double percentual) {
		this.percentual = percentual;
	}
	public NivelCompetencia getNivelCompetencia() {
		return nivelCompetencia;
	}
	public void setNivelCompetencia(NivelCompetencia nivelCompetencia) {
		this.nivelCompetencia = nivelCompetencia;
	}
	public NivelCompetenciaHistorico getNivelCompetenciaHistorico() {
		return nivelCompetenciaHistorico;
	}
	public void setNivelCompetenciaHistorico(NivelCompetenciaHistorico nivelCompetenciaHistorico) {
		this.nivelCompetenciaHistorico = nivelCompetenciaHistorico;
	}
}

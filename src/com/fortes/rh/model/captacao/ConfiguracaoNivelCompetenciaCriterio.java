package com.fortes.rh.model.captacao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="configuracaonivelcompetenciacriterio_sequence", allocationSize=1)
public class ConfiguracaoNivelCompetenciaCriterio extends AbstractModel implements Serializable
{
	@ManyToOne
	private ConfiguracaoNivelCompetencia configuracaoNivelCompetencia;

	@ManyToOne
	private NivelCompetencia nivelCompetencia;

	@Column(name="criterio_id")
	private Long criterioId;

	@Column(name="criterio_descricao")
	private String criterioDescricao;

	public ConfiguracaoNivelCompetencia getConfiguracaoNivelCompetencia() {
		return configuracaoNivelCompetencia;
	}

	public void setConfiguracaoNivelCompetencia(
			ConfiguracaoNivelCompetencia configuracaoNivelCompetencia) {
		this.configuracaoNivelCompetencia = configuracaoNivelCompetencia;
	}

	public NivelCompetencia getNivelCompetencia() {
		return nivelCompetencia;
	}

	public void setNivelCompetencia(NivelCompetencia nivelCompetencia) {
		this.nivelCompetencia = nivelCompetencia;
	}

	public Long getCriterioId() {
		return criterioId;
	}

	public void setCriterioId(Long criterioId) {
		this.criterioId = criterioId;
	}

	public String getCriterioDescricao() {
		return criterioDescricao;
	}

	public void setCriterioDescricao(String criterioDescricao) {
		this.criterioDescricao = criterioDescricao;
	}
	
	public void setConfiguracaoNivelCompetenciaId(Long configuracaoNivelCompetenciaId) {
		if(configuracaoNivelCompetencia == null)
			this.configuracaoNivelCompetencia = new ConfiguracaoNivelCompetencia();
		this.configuracaoNivelCompetencia.setId(configuracaoNivelCompetenciaId);
	}
	
	public void setNivelCompetenciaId(Long nivelCompetenciaId) {
		iniciaNivelCompetencia();
		this.nivelCompetencia.setId(nivelCompetenciaId);
	}
	
	public void setNivelCompetenciaDescricao(String nivelCompetenciaDescricao) {
		iniciaNivelCompetencia();
		this.nivelCompetencia.setDescricao(nivelCompetenciaDescricao);
	}
	
	public void setNivelCompetenciaPercentual(Double percentual) {
		iniciaNivelCompetencia();
		this.nivelCompetencia.setPercentual(percentual);
	}

	private void iniciaNivelCompetencia() {
		if(nivelCompetencia == null)
			this.nivelCompetencia = new NivelCompetencia();
	}
}
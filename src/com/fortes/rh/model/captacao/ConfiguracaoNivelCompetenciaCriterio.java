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

	@Column(name="criterio_id", nullable=false)
	private Long criterioId;

	@Column(name="criterio_descricao", nullable=false)
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
}
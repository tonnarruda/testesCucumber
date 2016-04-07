package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="configuracaoNivelCompetenciaCandidato_sequence", allocationSize=1)
public class ConfiguracaoNivelCompetenciaCandidato extends AbstractModel implements Serializable
{
	@Temporal(TemporalType.DATE)
	private Date data;
	@ManyToOne
	private Candidato candidato;
	@ManyToOne
	private Solicitacao solicitacao;
	@ManyToOne
	private ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="configuracaoNivelCompetenciaCandidato")
	private Collection<ConfiguracaoNivelCompetencia> configuracoesNivelCompetencia;
	
	public ConfiguracaoNivelCompetenciaCandidato()
	{
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Candidato getCandidato() {
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public ConfiguracaoNivelCompetenciaFaixaSalarial getConfiguracaoNivelCompetenciaFaixaSalarial() {
		return configuracaoNivelCompetenciaFaixaSalarial;
	}

	public void setConfiguracaoNivelCompetenciaFaixaSalarial(
			ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial) {
		this.configuracaoNivelCompetenciaFaixaSalarial = configuracaoNivelCompetenciaFaixaSalarial;
	}

	public Collection<ConfiguracaoNivelCompetencia> getConfiguracoesNivelCompetencia() {
		return configuracoesNivelCompetencia;
	}

	public void setConfiguracoesNivelCompetencia(
			Collection<ConfiguracaoNivelCompetencia> configuracoesNivelCompetencia) {
		this.configuracoesNivelCompetencia = configuracoesNivelCompetencia;
	}
}

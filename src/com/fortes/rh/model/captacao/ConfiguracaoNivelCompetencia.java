package com.fortes.rh.model.captacao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.cargosalario.FaixaSalarial;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="configuracaonivelcompetencia_sequence", allocationSize=1)
public class ConfiguracaoNivelCompetencia extends AbstractModel implements Serializable
{
	@ManyToOne
	private FaixaSalarial faixaSalarial;
	@ManyToOne
	private Candidato candidato;
	@ManyToOne
	private ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador;
	@ManyToOne
	private NivelCompetencia nivelCompetencia;
	@Column
	private Character tipoCompetencia;
	@Column(name="competencia_id", nullable=false)
	private Long competenciaId;
	@Transient
	private String competenciaDescricao;	
	
	public ConfiguracaoNivelCompetencia()
	{
	}

	public ConfiguracaoNivelCompetencia(Long id, Long faixaSalarialId, Long nivelCompetenciaId, Character tipoCompetencia, Long competenciaId, String competenciaDescricao)
	{
		this.setId(id);
		this.setFaixaSalarialIdProjection(faixaSalarialId);
		this.setNivelCompetenciaIdProjection(nivelCompetenciaId);
		this.setTipoCompetencia(tipoCompetencia);
		this.setCompetenciaId(competenciaId);
		this.setCompetenciaDescricao(competenciaDescricao);
	}
	
	public ConfiguracaoNivelCompetencia(Character tipoCompetencia, Long competenciaId, String competenciaDescricao)
	{
		this.setTipoCompetencia(tipoCompetencia);
		this.setCompetenciaId(competenciaId);
		this.setCompetenciaDescricao(competenciaDescricao);
	}
	
	public ConfiguracaoNivelCompetencia(Long id, String competenciaDescricao)
	{
		this.setId(id);
		this.setCompetenciaDescricao(competenciaDescricao);
	}
	
	public FaixaSalarial getFaixaSalarial() 
	{
		return faixaSalarial;
	}
	
	public void setFaixaSalarial(FaixaSalarial faixaSalarial) 
	{
		this.faixaSalarial = faixaSalarial;
	}
	
	public void setFaixaSalarialIdProjection(Long faixaSalarialId) 
	{
		if (this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();
		
		this.faixaSalarial.setId(faixaSalarialId);
	}
	
	public void setCandidatoIdProjection(Long candidatoId) 
	{
		if (this.candidato == null)
			this.candidato = new Candidato();
		
		this.candidato.setId(candidatoId);
	}
	
	public NivelCompetencia getNivelCompetencia() 
	{
		return nivelCompetencia;
	}
	
	public void setNivelCompetencia(NivelCompetencia nivelCompetencia) 
	{
		this.nivelCompetencia = nivelCompetencia;
	}
	
	public void setNivelCompetenciaIdProjection(Long nivelCompetenciaId) 
	{
		if (this.nivelCompetencia == null)
			this.nivelCompetencia = new NivelCompetencia();
		
		this.nivelCompetencia.setId(nivelCompetenciaId);
	}
	
	public void setProjectionConfiguracaoNivelCompetenciaColaboradorId(Long configuracaoNivelCompetenciaColaboradorId) 
	{
		if (this.configuracaoNivelCompetenciaColaborador == null)
			this.configuracaoNivelCompetenciaColaborador = new ConfiguracaoNivelCompetenciaColaborador();
		
		this.configuracaoNivelCompetenciaColaborador.setId(configuracaoNivelCompetenciaColaboradorId);
	}
	
	public void setProjectionNivelCompetenciaDescricao(String descricao) 
	{
		if (this.nivelCompetencia == null)
			this.nivelCompetencia = new NivelCompetencia();
		
		this.nivelCompetencia.setDescricao(descricao);
	}
	
	public Long getCompetenciaId() 
	{
		return competenciaId;
	}
	
	public void setCompetenciaId(Long competenciaId) 
	{
		this.competenciaId = competenciaId;
	}
	
	public Character getTipoCompetencia() 
	{
		return tipoCompetencia;
	}
	
	public void setTipoCompetencia(Character tipoCompetencia) 
	{
		this.tipoCompetencia = tipoCompetencia;
	}

	public String getCompetenciaDescricao() 
	{
		return competenciaDescricao;
	}

	public void setCompetenciaDescricao(String competenciaDescricao) 
	{
		this.competenciaDescricao = competenciaDescricao;
	}

	public Candidato getCandidato() {
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	public ConfiguracaoNivelCompetenciaColaborador getConfiguracaoNivelCompetenciaColaborador() {
		return configuracaoNivelCompetenciaColaborador;
	}

	public void setConfiguracaoNivelCompetenciaColaborador(
			ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador) {
		this.configuracaoNivelCompetenciaColaborador = configuracaoNivelCompetenciaColaborador;
	}
}
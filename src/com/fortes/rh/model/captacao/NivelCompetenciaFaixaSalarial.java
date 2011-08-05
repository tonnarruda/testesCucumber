package com.fortes.rh.model.captacao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.cargosalario.FaixaSalarial;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="nivelcompetenciafaixasalarial_sequence", allocationSize=1)
public class NivelCompetenciaFaixaSalarial extends AbstractModel implements Serializable
{
	@ManyToOne
	private FaixaSalarial faixaSalarial;
	@ManyToOne
	private NivelCompetencia nivelCompetencia;
	private Character tipoCompetencia;
	
	private Long competenciaId;
	@Transient
	private String competenciaDescricao;	
	
	public NivelCompetenciaFaixaSalarial()
	{
	}

	public NivelCompetenciaFaixaSalarial(Long id, Long faixaSalarialId, Long nivelCompetenciaId, Character tipoCompetencia, Long competenciaId, String competenciaDescricao)
	{
		this.setId(id);
		this.setFaixaSalarialIdProjection(faixaSalarialId);
		this.setNivelCompetenciaIdProjection(nivelCompetenciaId);
		this.setTipoCompetencia(tipoCompetencia);
		this.setCompetenciaId(competenciaId);
		this.setCompetenciaDescricao(competenciaDescricao);
	}
	
	public NivelCompetenciaFaixaSalarial(Character tipoCompetencia, Long competenciaId, String competenciaDescricao)
	{
		this.setTipoCompetencia(tipoCompetencia);
		this.setCompetenciaId(competenciaId);
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
}
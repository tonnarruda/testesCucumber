package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="nivelCompetenciaHistorico_sequence", allocationSize=1)
public class NivelCompetenciaHistorico extends AbstractModel implements Serializable
{
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@OneToMany(fetch=FetchType.LAZY)
	private Collection<configNivelCompetenciaHistorico> nivelCompetenciaHistoricoNivelCompetencias;

	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Collection<configNivelCompetenciaHistorico> getNivelCompetenciaHistoricoNivelCompetencias() {
		return nivelCompetenciaHistoricoNivelCompetencias;
	}
	public void setNivelCompetenciaHistoricoNivelCompetencias(Collection<configNivelCompetenciaHistorico> nivelCompetenciaHistoricoNivelCompetencias) 
	{
		this.nivelCompetenciaHistoricoNivelCompetencias = nivelCompetenciaHistoricoNivelCompetencias;
	}
}
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
import com.fortes.rh.model.cargosalario.FaixaSalarial;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="configuracaoNivelCompetenciaFaixaSalarial_sequence", allocationSize=1)
public class ConfiguracaoNivelCompetenciaFaixaSalarial extends AbstractModel implements Serializable
{
	@ManyToOne
	private FaixaSalarial faixaSalarial;
	@Temporal(TemporalType.DATE)
	private Date data;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="configuracaoNivelCompetenciaFaixaSalarial")
	private Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias;
	@ManyToOne
	private NivelCompetenciaHistorico nivelCompetenciaHistorico;
	
	public FaixaSalarial getFaixaSalarial() {
		return faixaSalarial;
	}
	public void setFaixaSalarial(FaixaSalarial faixaSalarial) {
		this.faixaSalarial = faixaSalarial;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Collection<ConfiguracaoNivelCompetencia> getConfiguracaoNivelCompetencias() {
		return configuracaoNivelCompetencias;
	}
	public void setConfiguracaoNivelCompetencias(Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias) {
		this.configuracaoNivelCompetencias = configuracaoNivelCompetencias;
	}
	public NivelCompetenciaHistorico getNivelCompetenciaHistorico() {
		return nivelCompetenciaHistorico;
	}
	public void setNivelCompetenciaHistorico(NivelCompetenciaHistorico nivelCompetenciaHistorico) {
		this.nivelCompetenciaHistorico = nivelCompetenciaHistorico;
	}
	
	public void setNivelCompetenciaHistoricoId(Long nivelCompetenciaHistoricoId){
		inicializaNivelCompetenciaHistorico();
		this.nivelCompetenciaHistorico.setId(nivelCompetenciaHistoricoId);
	}
	
	private void inicializaNivelCompetenciaHistorico(){
		if(this.nivelCompetenciaHistorico == null)
			this.nivelCompetenciaHistorico = new NivelCompetenciaHistorico();
	}
}

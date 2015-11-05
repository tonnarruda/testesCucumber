package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.DateUtil;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="nivelCompetenciaHistorico_sequence", allocationSize=1)
public class NivelCompetenciaHistorico extends AbstractModel implements Serializable
{
	private Date data;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="nivelCompetenciaHistorico", cascade=CascadeType.ALL)
	private Collection<ConfigHistoricoNivel> configHistoricoNiveis;
	
	@ManyToOne
	private Empresa empresa;
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getDataFormatada() 
	{
		return DateUtil.formataDiaMesAno(data);
	}
	public Collection<ConfigHistoricoNivel> getConfigHistoricoNiveis() {
		return configHistoricoNiveis;
	}
	public void setConfigHistoricoNiveis(
			Collection<ConfigHistoricoNivel> configHistoricoNiveis) {
		this.configHistoricoNiveis = configHistoricoNiveis;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}

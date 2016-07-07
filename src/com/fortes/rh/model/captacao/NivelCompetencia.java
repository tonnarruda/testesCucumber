package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy="nivelCompetencia")
	private Collection<ConfigHistoricoNivel> configHistoricoNiveis;
	
	@Transient
	private Double percentual;
	
	@Transient
	private Integer ordem;
	
	public NivelCompetencia(){}
	
	public NivelCompetencia(String descricao, Integer ordem){
		this.descricao = descricao;
		this.ordem = ordem;
	}
	
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
	public Collection<ConfigHistoricoNivel> getConfigHistoricoNiveis() {
		return configHistoricoNiveis;
	}
	public void setConfigHistoricoNiveis(
			Collection<ConfigHistoricoNivel> configHistoricoNiveis) {
		this.configHistoricoNiveis = configHistoricoNiveis;
	}

	public Double getPercentual() {
		return percentual;
	}
	
	public String getPercentualString() {
		if(percentual != null)
			return percentual.toString().replace(",", ".");
		else
			return "";
	}

	public void setPercentual(Double percentual) {
		this.percentual = percentual;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
}
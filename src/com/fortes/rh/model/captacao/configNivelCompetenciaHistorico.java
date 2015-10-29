package com.fortes.rh.model.captacao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.util.MathUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="nivelCompetenciaHistoricoNivelCompetencia_sequence", allocationSize=1)
public class configNivelCompetenciaHistorico extends AbstractModel implements Serializable
{
	private Integer ordem;
	private Double percentual;
	
	@ManyToOne
	private NivelCompetencia nivelcompetencia;
	
	@ManyToOne
	private NivelCompetenciaHistorico nivelcompetenciaHistorico;
	
	public String getPercentualFormatado(){
		if(percentual != null)
			return MathUtil.formataPercentual(percentual/100);
		
		return "";
	}
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
	public NivelCompetencia getNivelcompetencia() {
		return nivelcompetencia;
	}
	public void setNivelcompetencia(NivelCompetencia nivelcompetencia) {
		this.nivelcompetencia = nivelcompetencia;
	}
	public NivelCompetenciaHistorico getNivelcompetenciaHistorico() {
		return nivelcompetenciaHistorico;
	}
	public void setNivelcompetenciaHistorico(
			NivelCompetenciaHistorico nivelcompetenciaHistorico) {
		this.nivelcompetenciaHistorico = nivelcompetenciaHistorico;
	}
}
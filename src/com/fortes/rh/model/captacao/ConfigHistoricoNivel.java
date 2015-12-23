package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.util.MathUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="configHistoricoNivel_sequence", allocationSize=1)
public class ConfigHistoricoNivel extends AbstractModel implements Serializable
{
	private Integer ordem;
	private Double percentual;
	
	@ManyToOne
	private NivelCompetencia nivelCompetencia;
	
	@ManyToOne
	private NivelCompetenciaHistorico nivelCompetenciaHistorico;
	
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
	
	public String getPercentualFormatado(){
		if(percentual != null)
			return MathUtil.formataValor(percentual);
		
		return "";
	}
	
	public NivelCompetencia getNivelCompetencia() {
		return nivelCompetencia;
	}
	
	public void setNivelCompetencia(NivelCompetencia nivelCompetencia) {
		this.nivelCompetencia = nivelCompetencia;
	}
	
	public NivelCompetenciaHistorico getNivelCompetenciaHistorico() {
		return nivelCompetenciaHistorico;
	}
	
	public void setNivelCompetenciaHistorico(NivelCompetenciaHistorico nivelCompetenciaHistorico) {
		this.nivelCompetenciaHistorico = nivelCompetenciaHistorico;
	}
	
	public void setNivelCompetenciaHistoricoId(Long id){
		if(nivelCompetenciaHistorico == null)
			nivelCompetenciaHistorico = new NivelCompetenciaHistorico();
		
		nivelCompetenciaHistorico.setId(id);
	}
	
	public void setNivelCompetenciaHistoricoData(Date data){
		if(nivelCompetenciaHistorico == null)
			nivelCompetenciaHistorico = new NivelCompetenciaHistorico();
		
		nivelCompetenciaHistorico.setData(data);
	}
	
	public void setNivelCompetenciaId(Long id){
		if(nivelCompetencia == null)
			nivelCompetencia = new NivelCompetencia();
		
		nivelCompetencia.setId(id);
	} 
	
	public void setNivelCompetenciaDescricao(String descricao){
		if(nivelCompetencia == null)
			nivelCompetencia = new NivelCompetencia();
		
		nivelCompetencia.setDescricao(descricao);
	}
}

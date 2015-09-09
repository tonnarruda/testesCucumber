package com.fortes.rh.model.sesmt;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@Table(name="tipo_tamanhoepi")
@SequenceGenerator(name="sequence", sequenceName="tipo_tamanhoepi_sequence", allocationSize=1)
public class TipoTamanhoEPI extends AbstractModel implements Serializable, Cloneable
{
	@ManyToOne
	private TipoEPI tipoEPI; 
	
	@ManyToOne
	private TamanhoEPI tamanhoEPIs;
	
	private Boolean ativo;
	
	public TipoEPI getTipoEPI() {
		return tipoEPI;
	}

	public void setTipoEPI(TipoEPI tipoEPI) {
		this.tipoEPI = tipoEPI;
	}
	
	public TamanhoEPI getTamanhoEPIs() {
		return tamanhoEPIs;
	}

	public void setTamanhoEPIs(TamanhoEPI tamanhoEPIs) {
		this.tamanhoEPIs = tamanhoEPIs;
	}

	public String getAtivoString() {
		return ativo.toString();
	}
	
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public void setProjectionTipoEPIId(Long tipoEPIId){
		if ( tipoEPI == null )
			tipoEPI = new TipoEPI();
		
		tipoEPI.setId(tipoEPIId);
	}
	
	public void setProjectionTamanhoEPIsId(Long tamanhoEPIId){
		if ( tamanhoEPIs == null )
			tamanhoEPIs = new TamanhoEPI();
		
		tamanhoEPIs.setId(tamanhoEPIId);
	}
}
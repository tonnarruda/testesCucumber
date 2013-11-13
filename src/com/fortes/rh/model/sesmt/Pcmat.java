package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="pcmat_sequence", allocationSize=1)
public class Pcmat extends AbstractModel implements Serializable
{
	private Date apartirDe;
	private String tipoObra;
	private Date dataIniObra;
	private Date dataFimObra;
	
	@ManyToOne
	private Obra obra;
	
	public void setProjectionIdObra(Long idObra){
		inicializaObra();
		obra.setId(idObra);
	}
	public void setProjectionNomeObra(String nomeObra){
		inicializaObra();
		obra.setNome(nomeObra);
	}
	private void inicializaObra() {
		if(obra == null)
			obra = new Obra();
	}
	public Date getApartirDe() {
		return apartirDe;
	}
	public void setApartirDe(Date apartirDe) {
		this.apartirDe = apartirDe;
	}
	public String getTipoObra() {
		return tipoObra;
	}
	public void setTipoObra(String tipoObra) {
		this.tipoObra = tipoObra;
	}
	public Date getDataIniObra() {
		return dataIniObra;
	}
	public void setDataIniObra(Date dataIniObra) {
		this.dataIniObra = dataIniObra;
	}
	public Date getDataFimObra() {
		return dataFimObra;
	}
	public void setDataFimObra(Date dataFimObra) {
		this.dataFimObra = dataFimObra;
	}
	public Obra getObra() {
		return obra;
	}
	public void setObra(Obra obra) {
		this.obra = obra;
	}
}
package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="fasepcmat_sequence", allocationSize=1)
public class FasePcmat extends AbstractModel implements Serializable
{
	@ManyToOne
	private Fase fase;
	@ManyToOne
	private Pcmat pcmat;
	
	private Integer mesIni;
	
	private Integer mesFim;

	@OneToMany(mappedBy="fasePcmat")
	private Collection<RiscoFasePcmat> riscosFasePcmat;
	
	public Fase getFase() {
		return fase;
	}
	
	public void setFase(Fase fase) {
		this.fase = fase;
	}
	
	public Pcmat getPcmat() {
		return pcmat;
	}
	
	public void setPcmat(Pcmat pcmat) {
		this.pcmat = pcmat;
	}
	
	public Integer getMesIni() {
		return mesIni;
	}

	public void setMesIni(Integer mesIni) {
		this.mesIni = mesIni;
	}

	public Integer getMesFim() {
		return mesFim;
	}

	public void setMesFim(Integer mesFim) {
		this.mesFim = mesFim;
	}
	
	public Collection<RiscoFasePcmat> getRiscosFasePcmat() {
		return riscosFasePcmat;
	}

	public void setRiscosFasePcmat(Collection<RiscoFasePcmat> riscosFasePcmat) {
		this.riscosFasePcmat = riscosFasePcmat;
	}
	
	public void setFaseId(Long faseId)
	{
		if (this.fase == null)
			this.fase = new Fase();
		
		this.fase.setId(faseId);
	}
	
	public void setFaseDescricao(String faseDescricao)
	{
		if (this.fase == null)
			this.fase = new Fase();
		
		this.fase.setDescricao(faseDescricao);
	}
	
	public void setPcmatId(Long pcmatId)
	{
		if (this.pcmat == null)
			this.pcmat = new Pcmat();
		
		this.pcmat.setId(pcmatId);
	}
}
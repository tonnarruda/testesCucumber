package com.fortes.rh.model.sesmt;

import java.io.Serializable;

import com.fortes.rh.model.sesmt.ParteAtingida;
import com.fortes.rh.model.sesmt.eSocialTabelas.ParteCorpoAtingida;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="parteAtingida_sequence", allocationSize=1)
public class ParteAtingida extends AbstractModel implements Serializable
{
	@ManyToOne
	private ParteCorpoAtingida parteCorpoAtingida;
	
	private Long lateralidade;

	public ParteAtingida() {
	}
	
	public ParteAtingida(Long parteAtingidaId, Long lateralidade) {
		setParteCorpoAtingida(new ParteCorpoAtingida());;
		this.getParteCorpoAtingida().setId(parteAtingidaId);
		this.setLateralidade(lateralidade);
	}

	public ParteCorpoAtingida getParteCorpoAtingida() {
		return parteCorpoAtingida;
	}

	public void setParteCorpoAtingida(ParteCorpoAtingida parteCorpoAtingida) {
		this.parteCorpoAtingida = parteCorpoAtingida;
	}

	public Long getLateralidade() {
		return lateralidade;
	}

	public void setLateralidade(Long lateralidade) {
		this.lateralidade = lateralidade;
	}
}

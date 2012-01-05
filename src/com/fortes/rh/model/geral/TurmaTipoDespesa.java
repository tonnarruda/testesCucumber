package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.desenvolvimento.Turma;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="turmaTipoDespesa_sequence", allocationSize=1)
public class TurmaTipoDespesa extends AbstractModel implements Serializable
{
	@ManyToOne
	private Turma turma;
	
	@ManyToOne
	private TipoDespesa tipoDespesa;
	@Transient
	private Long tipoDespesaId;
	private Double despesa;
	
	public TurmaTipoDespesa() {
		super();
	}

	public TurmaTipoDespesa(Turma turma, Double despesa, Long tipoDespesaId) {
		super();
		this.turma = turma;
		this.despesa = despesa;
		
		this.tipoDespesa = new TipoDespesa();
		this.tipoDespesa.setId(tipoDespesaId);
	}
	
	public Turma getTurma() {
		return turma;
	}
	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	public TipoDespesa getTipoDespesa() {
		return tipoDespesa;
	}
	public void setTipoDespesa(TipoDespesa tipoDespesa) {
		this.tipoDespesa = tipoDespesa;
	}
	public Double getDespesa() {
		return despesa;
	}
	public void setDespesa(Double despesa) {
		this.despesa = despesa;
	}
	public void setDespesa(String despesa) {
		this.despesa = Double.parseDouble(despesa);
	}
	public void setProjectionTipoDespesaId(Long tipoDespesaId) {
		
		if(tipoDespesa == null)
			tipoDespesa = new TipoDespesa();
		
		tipoDespesa.setId(tipoDespesaId);
	}
	public void setProjectionTurmaId(Long turmaId) {
		if(turma == null)
			turma = new Turma();
		
		turma.setId(turmaId);
	}
	public Long getTipoDespesaId() {
		return tipoDespesaId;
	}
	public void setTipoDespesaId(Long tipoDespesaId) {
		this.tipoDespesaId = tipoDespesaId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null || !(object instanceof TurmaTipoDespesa))
			return false;

		TurmaTipoDespesa turmaTipoDespesa = (TurmaTipoDespesa)object;
		if (turmaTipoDespesa.getId() != null && this.getId() != null)
			return this.getId().equals(turmaTipoDespesa.getId());

		return false;
	}
}

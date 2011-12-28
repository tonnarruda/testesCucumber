package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

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
	
	private Double despesa;
	
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
		this.despesa = Double.valueOf(despesa.replace(".","").replace(",","."));
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
}

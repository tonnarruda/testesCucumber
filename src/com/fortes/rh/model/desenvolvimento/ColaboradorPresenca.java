package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;

@Entity
@SequenceGenerator(name="sequence", sequenceName="colaboradorpresenca_sequence", allocationSize=1)
public class ColaboradorPresenca extends AbstractModel implements Serializable
{
	@ManyToOne
	private ColaboradorTurma colaboradorTurma;
	@ManyToOne
	private DiaTurma diaTurma;
	@Transient
	private Integer diasPresente;
	@Transient
	private Long turmaId;
	@Transient
	private Long colaboradorId;

	private boolean presenca;
	
	public ColaboradorPresenca()
	{
	}
	
	public ColaboradorPresenca(ColaboradorTurma colaboradorTurma, DiaTurma diaTurma, boolean presenca)
	{
		this.colaboradorTurma = colaboradorTurma;
		this.diaTurma = diaTurma;
		this.presenca = presenca;
	}

	public ColaboradorPresenca(Long turmaId, Integer diasPresente)
	{
		this.turmaId = turmaId;
		if(diasPresente == null)
			this.diasPresente = 0;
		else
			this.diasPresente = diasPresente;
	}
	 
	public ColaboradorPresenca(Long colaboradorId, Long turmaId, Integer diasPresente)
	{
		this.colaboradorId = colaboradorId;
		this.turmaId= turmaId;
		
		if(diasPresente == null)
			this.diasPresente = 0;
		else
			this.diasPresente = diasPresente;
	}
	
	public void setProjectionColaboradorTurmaId(Long colaboradorTurmaId)
	{
		if(this.colaboradorTurma == null)
			this.colaboradorTurma = new ColaboradorTurma();
		
		this.colaboradorTurma.setId(colaboradorTurmaId);
	}
	
	public void setProjectionTurmaId(Long turmaId)
	{
		if(this.colaboradorTurma == null)
			this.colaboradorTurma = new ColaboradorTurma();
		
		this.colaboradorTurma.setTurmaId(turmaId);
	}
	
	public void setProjectionDiaTurmaId(Long diaTurmaId)
	{
		if(this.diaTurma == null)
			this.diaTurma = new DiaTurma();
		
		this.diaTurma.setId(diaTurmaId);
	}
	
	public ColaboradorTurma getColaboradorTurma()
	{
		return colaboradorTurma;
	}
	public void setColaboradorTurma(ColaboradorTurma colaboradorTurma)
	{
		this.colaboradorTurma = colaboradorTurma;
	}
	public DiaTurma getDiaTurma()
	{
		return diaTurma;
	}
	public void setDiaTurma(DiaTurma diaTurma)
	{
		this.diaTurma = diaTurma;
	}
	public boolean isPresenca()
	{
		return presenca;
	}
	public void setPresenca(boolean presenca)
	{
		this.presenca = presenca;
	}

	public Integer getDiasPresente() {
		return diasPresente;
	}

	public void setTurmaId(Long turmaId) {
		this.turmaId = turmaId;
	}

	public Long getTurmaId() {
		return turmaId;
	}

	public void setDiasPresente(Integer diasPresente) {
		this.diasPresente = diasPresente;
	}

	public Long getColaboradorId() {
		return colaboradorId;
	}
}
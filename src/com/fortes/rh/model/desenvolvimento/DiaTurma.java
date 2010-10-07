package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.util.DateUtil;

@Entity
@SequenceGenerator(name="sequence", sequenceName="diaTurma_sequence", allocationSize=1)
public class DiaTurma extends AbstractModel implements Serializable
{
    @Temporal(TemporalType.DATE)
    private Date dia;
    @Transient
    private String descricao;

    @ManyToOne
    private Turma turma;

	public Date getDia()
	{
		return dia;
	}
	public void setDia(Date dia)
	{
		this.dia = dia;
	}
	public Turma getTurma()
	{
		return turma;
	}
	public void setTurma(Turma turma)
	{
		this.turma = turma;
	}
	public String getDescricao()
	{
		return DateUtil.formataDiaMesAno(this.dia) + " - " + DateUtil.getDiaSemanaDescritivo(this.dia);
	}

	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
}
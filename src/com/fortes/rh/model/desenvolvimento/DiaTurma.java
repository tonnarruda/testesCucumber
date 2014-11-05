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
import com.fortes.rh.model.dicionario.TipoTurno;
import com.fortes.rh.util.DateUtil;

@Entity
@SequenceGenerator(name="sequence", sequenceName="diaTurma_sequence", allocationSize=1)
public class DiaTurma extends AbstractModel implements Serializable, Cloneable
{
    @Temporal(TemporalType.DATE)
    private Date dia;
    @ManyToOne
    private Turma turma;
    private char turno = 'D';
    private String horaIni;
    private String horaFim;
    
    @Transient
    private String descricao;

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
		String descricao = DateUtil.formataDiaMesAno(this.dia) + " - " + DateUtil.getDiaSemanaDescritivo(this.dia);
		
		if(turno != TipoTurno.DIA)
			descricao += " - " + TipoTurno.getDescricao(turno);
		
		return descricao;
	}

	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
	
	public String getDiaSemanaDescricao() 
	{
		return DateUtil.getDiaSemanaDescritivo(dia);
	}
	
	public String getTurnoDescricao() 
	{
		return TipoTurno.getDescricao(turno);
	}
	
	public char getTurno() 
	{
		return turno;
	}
	
	public void setTurno(char turno) 
	{
		this.turno = turno;
	}
	
	public String getHoraIni() 
	{
		return horaIni;
	}
	
	public void setHoraIni(String horaIni) 
	{
		this.horaIni = horaIni;
	}
	
	public String getHoraFim() 
	{
		return horaFim;
	}
	
	public void setHoraFim(String horaFim) 
	{
		this.horaFim = horaFim;
	}
}
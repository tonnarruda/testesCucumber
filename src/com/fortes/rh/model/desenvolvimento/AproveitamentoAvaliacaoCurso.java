package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="curso_sequence", allocationSize=1)
public class AproveitamentoAvaliacaoCurso extends AbstractModel implements Serializable
{
    @OneToOne(fetch = FetchType.LAZY)
	private ColaboradorTurma colaboradorTurma;
    @OneToOne(fetch = FetchType.LAZY)
    private AvaliacaoCurso avaliacaoCurso;
    private Double valor;

    public AproveitamentoAvaliacaoCurso()
	{
	}

    public AproveitamentoAvaliacaoCurso(ColaboradorTurma colaboradorTurma, AvaliacaoCurso avaliacaoCurso, Double valor)
	{
		super();
		this.colaboradorTurma = colaboradorTurma;
		this.avaliacaoCurso = avaliacaoCurso;
		this.valor = valor;
	}


	public void setProjectionColaboradorTurmaId(Long colaboradorTurmaId)
	{
		if (this.colaboradorTurma == null)
			this.colaboradorTurma = new ColaboradorTurma();
		this.colaboradorTurma.setId(colaboradorTurmaId);
	}
	
	public void setProjectionAvaliacaoCursoId(Long avaliacaoCursoId)
	{
		if (this.avaliacaoCurso == null)
			this.avaliacaoCurso = new AvaliacaoCurso();
		this.avaliacaoCurso.setId(avaliacaoCursoId);
	}
	
    public AvaliacaoCurso getAvaliacaoCurso()
	{
		return avaliacaoCurso;
	}
	public void setAvaliacaoCurso(AvaliacaoCurso avaliacaoCurso)
	{
		this.avaliacaoCurso = avaliacaoCurso;
	}
	public ColaboradorTurma getColaboradorTurma()
	{
		return colaboradorTurma;
	}
	public void setColaboradorTurma(ColaboradorTurma colaboradorTurma)
	{
		this.colaboradorTurma = colaboradorTurma;
	}
	public Double getValor()
	{
		return valor;
	}
	public void setValor(Double valor)
	{
		this.valor = valor;
	}
}
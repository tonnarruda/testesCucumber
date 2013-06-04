package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.dicionario.TipoAvaliacaoCurso;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name = "sequence", sequenceName = "avaliacaocurso_sequence", allocationSize = 1)
public class AvaliacaoCurso extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String titulo;
	private char tipo = TipoAvaliacaoCurso.NOTA;
	private Double minimoAprovacao;
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="avaliacaoCursos")
	private Collection<Curso> cursos;
	@ManyToOne
	private Avaliacao avaliacao;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="avaliacaoCurso")
	private Collection<AproveitamentoAvaliacaoCurso> aproveitamentoAvaliacaoCursos;

	public Collection<AproveitamentoAvaliacaoCurso> getAproveitamentoAvaliacaoCursos()
	{
		return aproveitamentoAvaliacaoCursos;
	}

	public void setAproveitamentoAvaliacaoCursos(Collection<AproveitamentoAvaliacaoCurso> aproveitamentoAvaliacaoCursos)
	{
		this.aproveitamentoAvaliacaoCursos = aproveitamentoAvaliacaoCursos;
	}

	public Collection<Curso> getCursos()
	{
		return cursos;
	}

	public void setCursos(Collection<Curso> cursos)
	{
		this.cursos = cursos;
	}

	public Double getMinimoAprovacao()
	{
		return minimoAprovacao;
	}

	public void setMinimoAprovacao(Double minimoAprovacao)
	{
		this.minimoAprovacao = minimoAprovacao;
	}

	public char getTipo()
	{
		return tipo;
	}

	public void setTipo(char tipo)
	{
		this.tipo = tipo;
	}

	public String getTitulo()
	{
		return titulo;
	}

	public void setTitulo(String titulo)
	{
		this.titulo = titulo;
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

}
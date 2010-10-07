package com.fortes.rh.model.desenvolvimento.relatorio;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;

// Utilizado para gerar relatório de gastos empresas.
public class ColaboradorSemCursoRelatorio implements Serializable
{
	private Colaborador colaborador;
	private Estabelecimento estabelecimento;
	private Cargo cargo;
	private Curso curso;
	private Turma turma;
	private String tempoSemCurso;

	public Cargo getCargo()
	{
		return cargo;
	}
	public void setCargo(Cargo cargo)
	{
		this.cargo = cargo;
	}
	public Colaborador getColaborador()
	{
		return colaborador;
	}
	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}
	public Turma getTurma()
	{
		return turma;
	}
	public void setTurma(Turma turma)
	{
		this.turma = turma;
	}


	public String getTempoSemCurso()
	{
		return tempoSemCurso;
	}
	public void setTempoSemCurso(String tempoSemCurso)
	{
		this.tempoSemCurso = tempoSemCurso;
	}
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("cargo", this.cargo).append("tempoSemTurma",
						this.tempoSemCurso).append("colaborador",
						this.colaborador).append("turma", this.turma)
				.toString();
	}
	public Curso getCurso()
	{
		return curso;
	}
	public void setCurso(Curso curso)
	{
		this.curso = curso;
	}
	public Estabelecimento getEstabelecimento()
	{
		return estabelecimento;
	}
	public void setEstabelecimento(Estabelecimento estabelecimento)
	{
		this.estabelecimento = estabelecimento;
	}
}

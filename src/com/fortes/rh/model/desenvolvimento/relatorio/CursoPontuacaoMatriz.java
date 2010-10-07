package com.fortes.rh.model.desenvolvimento.relatorio;

import java.io.Serializable;

import com.fortes.rh.model.desenvolvimento.Curso;

// Utilizado para gerar Matriz de Qualificaçao
public class CursoPontuacaoMatriz implements Serializable
{
	private Curso curso;
	private int pontuacao;
	private String sigla;

	public Curso getCurso()
	{
		return curso;
	}
	public void setCurso(Curso curso)
	{
		this.curso = curso;
	}
	public int getPontuacao()
	{
		return pontuacao;
	}
	public void setPontuacao(int pontuacao)
	{
		this.pontuacao = pontuacao;
	}
	public String getSigla()
	{
		return sigla;
	}
	public void setSigla(String sigla)
	{
		this.sigla = sigla;
	}


}

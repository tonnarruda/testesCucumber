package com.fortes.rh.model.desenvolvimento.relatorio;

import java.io.Serializable;
import java.util.Collection;

import com.fortes.rh.model.geral.Colaborador;

// Utilizado para gerar Matriz de Qualificaçao
public class ColaboradorCursoMatriz implements Serializable
{
	private Colaborador colaborador;
	private Collection<CursoPontuacaoMatriz> cursoPontuacaos;
	public Colaborador getColaborador()
	{
		return colaborador;
	}
	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}
	public Collection<CursoPontuacaoMatriz> getCursoPontuacaos()
	{
		return cursoPontuacaos;
	}
	public void setCursoPontuacaos(Collection<CursoPontuacaoMatriz> cursoPontuacaos)
	{
		this.cursoPontuacaos = cursoPontuacaos;
	}
}

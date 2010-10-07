package com.fortes.rh.model.desenvolvimento.relatorio;

import java.io.Serializable;

// Utilizado para gerar somatório da Matriz de Qualificação
public class SomatorioCursoMatriz implements Serializable
{
	private long cursoId;
	private int soma;

	public int getSoma()
	{
		return soma;
	}
	public void setSoma(int soma)
	{
		this.soma = soma;
	}
	public long getCursoId()
	{
		return cursoId;
	}
	public void setCursoId(long cursoId)
	{
		this.cursoId = cursoId;
	}

}

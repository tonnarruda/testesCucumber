package com.fortes.rh.model.geral.relatorio;

import java.util.Collection;

import com.fortes.rh.model.geral.Colaborador;

public class ColaboradorOcorrenciaRelatorio
{
    private Colaborador colaborador;
    private int qtdPontos;
    private Collection<OcorrenciaRelatorio> ocorrencias;

	public Colaborador getColaborador()
	{
		return colaborador;
	}
	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}
	public int getQtdPontos()
	{
		return qtdPontos;
	}
	public void setQtdPontos(int qtdPontos)
	{
		this.qtdPontos = qtdPontos;
	}
	public Collection<OcorrenciaRelatorio> getOcorrencias()
	{
		return ocorrencias;
	}
	public void setOcorrencias(Collection<OcorrenciaRelatorio> ocorrencias)
	{
		this.ocorrencias = ocorrencias;
	}
}
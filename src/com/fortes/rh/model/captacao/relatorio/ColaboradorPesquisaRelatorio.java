package com.fortes.rh.model.captacao.relatorio;

import java.io.Serializable;
import java.util.Collection;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.Pesquisa;

public class ColaboradorPesquisaRelatorio implements Serializable
{
	private Colaborador colaborador;
	private Pesquisa pesquisa;
    private Collection<PerguntaResposta> perguntaRespostas;

	public Colaborador getColaborador()
	{
		return colaborador;
	}
	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}
	public Pesquisa getPesquisa()
	{
		return pesquisa;
	}
	public void setPesquisa(Pesquisa pesquisa)
	{
		this.pesquisa = pesquisa;
	}
	public Collection<PerguntaResposta> getPerguntaRespostas()
	{
		return perguntaRespostas;
	}
	public void setPerguntaRespostas(Collection<PerguntaResposta> perguntaRespostas)
	{
		this.perguntaRespostas = perguntaRespostas;
	}
}
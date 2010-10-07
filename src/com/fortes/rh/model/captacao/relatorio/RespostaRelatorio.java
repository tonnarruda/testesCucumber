package com.fortes.rh.model.captacao.relatorio;

import com.fortes.rh.model.pesquisa.Resposta;

public class RespostaRelatorio
{
	private Resposta resposta;
	private boolean selecionada;
	private Integer tipo;

	public Integer getTipo()
	{
		return tipo;
	}
	public void setTipo(Integer tipo)
	{
		this.tipo = tipo;
	}
	public Resposta getResposta()
	{
		return resposta;
	}
	public void setResposta(Resposta resposta)
	{
		this.resposta = resposta;
	}
	public boolean isSelecionada()
	{
		return selecionada;
	}
	public void setSelecionada(boolean selecionada)
	{
		this.selecionada = selecionada;
	}
}

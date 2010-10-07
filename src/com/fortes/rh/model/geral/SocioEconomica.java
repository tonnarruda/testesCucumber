package com.fortes.rh.model.geral;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SocioEconomica implements Serializable
{
	private boolean pagaPensao = false;
	private int quantidade = 0;
	private Double valor = 0.0;
	private boolean possuiVeiculo = false;

	public boolean isPagaPensao()
	{
		return pagaPensao;
	}

	public void setPagaPensao(boolean pagaPensao)
	{
		this.pagaPensao = pagaPensao;
	}

	public int getQuantidade()
	{
		return quantidade;
	}

	public void setQuantidade(int quantidade)
	{
		this.quantidade = quantidade;
	}

	public Double getValor()
	{
		return valor;
	}

	public void setValor(Double valor)
	{
		this.valor = valor;
	}

	public boolean isPossuiVeiculo()
	{
		return possuiVeiculo;
	}

	public void setPossuiVeiculo(boolean possuiVeiculo)
	{
		this.possuiVeiculo = possuiVeiculo;
	}


	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("pagaPensao",this.pagaPensao)
				.append("quantidade", this.quantidade)
				.append("valor", this.valor)
			    .append("possuiVeiculo", this.possuiVeiculo).toString();

	}
}

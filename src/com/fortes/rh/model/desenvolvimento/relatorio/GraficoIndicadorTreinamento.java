package com.fortes.rh.model.desenvolvimento.relatorio;


public class GraficoIndicadorTreinamento 
{
	private String chave;
	private Integer valor;
	private String legenda;
	
	public GraficoIndicadorTreinamento(String chave, Integer valor, String legenda)
	{
		super();
		this.chave = chave;
		this.valor = valor;
		this.legenda = legenda;
	}

	public String getChave()
	{
		return chave;
	}
	public void setChave(String chave)
	{
		this.chave = chave;
	}
	public String getLegenda()
	{
		return legenda;
	}
	public void setLegenda(String legenda)
	{
		this.legenda = legenda;
	}
	public Integer getValor()
	{
		return valor;
	}
	public void setValor(Integer valor)
	{
		this.valor = valor;
	}	
}

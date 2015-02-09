package com.fortes.rh.model.geral.relatorio;


public class MotivoDemissaoQuantidade
{
	private String motivo;
	private Integer quantidade;
	private String descricaoTurnover;

	public MotivoDemissaoQuantidade()
	{
		
	}
	
	public MotivoDemissaoQuantidade(String motivo, Integer quantidade)
	{
		super();
		this.motivo = motivo;
		this.quantidade = quantidade;
	}
	
	public MotivoDemissaoQuantidade(String motivo, String descricaoTurnover, Integer quantidade){
		super();
		this.motivo = motivo;
		this.quantidade = quantidade;
		this.descricaoTurnover = descricaoTurnover;
	}

	public String getMotivo()
	{
		return motivo;
	}
	public void setMotivo(String motivo)
	{
		this.motivo = motivo;
	}
	public Integer getQuantidade()
	{
		return quantidade;
	}
	public void setQuantidade(Integer quantidade)
	{
		this.quantidade = quantidade;
	}

	public String getDescricaoTurnover() {
		return descricaoTurnover;
	}

	public void setDescricaoTurnover(String descricaoTurnover) {
		this.descricaoTurnover = descricaoTurnover;
	}

}

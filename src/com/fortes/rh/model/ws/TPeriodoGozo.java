package com.fortes.rh.model.ws;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TPeriodoGozo implements Serializable
{
	private String codigoAC;
	private String nome;
	private String dataAdmissao;
	private String previsaoFerias;
	private String dataLimite; // Limite do gozo das férias
	private String gozoInicial;
	private String gozoFinal;
	
	public TPeriodoGozo(String gozoInicial, String gozoFinal) {
		super();
		this.gozoInicial = gozoInicial;
		this.gozoFinal = gozoFinal;
	}

	public TPeriodoGozo(){
		super();
	}

	public String getCodigoAC()
	{
		return codigoAC;
	}

	public void setCodigoAC(String codigoAC)
	{
		this.codigoAC = codigoAC;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String getDataAdmissao()
	{
		return dataAdmissao;
	}

	public void setDataAdmissao(String dataAdmissao)
	{
		this.dataAdmissao = dataAdmissao;
	}

	public String getPrevisaoFerias()
	{
		return previsaoFerias;
	}

	public void setPrevisaoFerias(String previsaoFerias)
	{
		this.previsaoFerias = previsaoFerias;
	}

	public String getDataLimite()
	{
		return dataLimite;
	}

	public void setDataLimite(String dataLimite)
	{
		this.dataLimite = dataLimite;
	}

	public String getGozoInicial()
	{
		return gozoInicial;
	}

	public void setGozoInicial(String gozoInicial)
	{
		this.gozoInicial = gozoInicial;
	}

	public String getGozoFinal()
	{
		return gozoFinal;
	}

	public void setGozoFinal(String gozoFinal)
	{
		this.gozoFinal = gozoFinal;
	}
	

}
package com.fortes.rh.model.ws;

import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	
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
	
	public Long getQtdDias(){
		try {
			if(!gozoInicial.isEmpty() && !gozoInicial.isEmpty()){
				Date gInicial = new java.sql.Date(format.parse(gozoInicial).getTime());
				Date gFinal = new java.sql.Date(format.parse(gozoFinal).getTime());
				long diferencaEmMilisegundos = gFinal.getTime() - gInicial.getTime();
				return diferencaEmMilisegundos/1000/60/60/24 + 1;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
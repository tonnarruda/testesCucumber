package com.fortes.rh.model.ws;

import java.io.Serializable;
import java.util.Date;

import com.fortes.rh.util.DateUtil;

public class TIndiceHistorico implements Serializable
{
	private String data;
	private Double valor;
	private String indiceCodigo;
	private String grupoAC;

	public String getGrupoAC() {
		return grupoAC;
	}

	public void setGrupoAC(String grupoAC) {
		this.grupoAC = grupoAC;
	}

	public Date getDataFormatada()
	{
		return DateUtil.montaDataByString(this.data);
	}
	public Double getValor()
	{
		return valor;
	}
	public void setValor(Double valor)
	{
		this.valor = valor;
	}
	public String getIndiceCodigo()
	{
		return indiceCodigo;
	}
	public void setIndiceCodigo(String indiceCodigo)
	{
		this.indiceCodigo = indiceCodigo;
	}
	public String getData()
	{
		return data;
	}
	public void setData(String data)
	{
		this.data = data;
	}
}
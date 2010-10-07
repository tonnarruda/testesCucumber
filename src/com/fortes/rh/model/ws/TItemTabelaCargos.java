package com.fortes.rh.model.ws;

import java.io.Serializable;

public class TItemTabelaCargos implements Serializable
{
	private String codigo;
	private String empresa;
	private String data;
	private Double valor;
	private Integer rh_sca_id;

	public String getCodigo()
	{
		return codigo;
	}
	public void setCodigo(String codigo)
	{
		this.codigo = codigo;
	}
	public String getData()
	{
		return data;
	}
	public void setData(String data)
	{
		this.data = data;
	}
	public String getEmpresa()
	{
		return empresa;
	}
	public void setEmpresa(String empresa)
	{
		this.empresa = empresa;
	}
	public Integer getRh_sca_id()
	{
		return rh_sca_id;
	}
	public void setRh_sca_id(Integer rh_sca_id)
	{
		this.rh_sca_id = rh_sca_id;
	}
	public Double getValor()
	{
		return valor;
	}
	public void setValor(Double valor)
	{
		this.valor = valor;
	}
}

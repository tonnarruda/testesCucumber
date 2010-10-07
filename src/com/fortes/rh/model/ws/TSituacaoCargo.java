package com.fortes.rh.model.ws;

import java.io.Serializable;
import java.util.Date;

import com.fortes.rh.util.DateUtil;

public class TSituacaoCargo implements Serializable
{
	private Integer id;
	private String codigo;
	private String empresaCodigoAC;
	private String indiceCodigo;
	private String data;
	private String tipo;
	private Double valor;
	private Double quantidade;
	
	public Date getDataFormatada()
	{
		return DateUtil.montaDataByString(this.data);
	}
	
	public String getCodigo()
	{
		return codigo;
	}
	public void setCodigo(String codigo)
	{
		this.codigo = codigo;
	}
	public String getEmpresaCodigoAC()
	{
		return empresaCodigoAC;
	}
	public void setEmpresaCodigoAC(String empresaCodigoAC)
	{
		this.empresaCodigoAC = empresaCodigoAC;
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
	public Double getValor()
	{
		return valor;
	}
	public void setValor(Double valor)
	{
		this.valor = valor;
	}
	public Double getQuantidade()
	{
		return quantidade;
	}
	public void setQuantidade(Double quantidade)
	{
		this.quantidade = quantidade;
	}

	public String getTipo()
	{
		return tipo;
	}

	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}
	
}
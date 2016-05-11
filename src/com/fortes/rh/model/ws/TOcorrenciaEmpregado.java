package com.fortes.rh.model.ws;

import java.io.Serializable;
import java.util.Date;

import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
public class TOcorrenciaEmpregado implements Serializable
{
	private String codigo;
	private String empresa;
	private String codigoEmpregado;
	private String data;
	private String dataFim;
	private String obs;
	private String grupoAC;
	private int id;

	public String getGrupoAC() {
		return grupoAC;
	}

	public void setGrupoAC(String grupoAC) {
		this.grupoAC = grupoAC;
	}

	public String getCodigoEmpregado()
	{
		return codigoEmpregado;
	}
	public void setCodigoEmpregado(String codigoEmpregado)
	{
		this.codigoEmpregado = codigoEmpregado;
	}
	public String getEmpresa()
	{
		return empresa;
	}
	public void setEmpresa(String empresa)
	{
		this.empresa = empresa;
	}
	public String getCodigo()
	{
		return codigo;
	}
	public void setCodigo(String codigo)
	{
		this.codigo = codigo;
	}
	
	public String getObs()
	{
		return obs;
	}
	public void setObs(String obs)
	{
		this.obs = obs;
	}
	public String getData()
	{
		return data;
	}
	public void setData(String data)
	{
		this.data = data;
	}
	
	public Date getDataFormatada()
	{
		return DateUtil.montaDataByString(this.data);
	}

	public String getDataFim() {
		return dataFim;
	}

	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}
	
	public Date getDataFimFormatada()
	{
		return DateUtil.montaDataByString(this.dataFim);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

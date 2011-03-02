package com.fortes.rh.model.ws;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TOcorrencia implements Serializable
{
	private String codigo;
	private String empresa;
	private String descricao;
	private String grupoAC;

	public String getGrupoAC() {
		return grupoAC;
	}

	public void setGrupoAC(String grupoAC) {
		this.grupoAC = grupoAC;
	}

	public String getDescricao()
	{
		return descricao;
	}
	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
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
}

package com.fortes.rh.model.ws;

import java.io.Serializable;

public class TIndice implements Serializable
{
	private String codigo;
	private String nome;
	private String grupoAC;

	public String getGrupoAC() {
		return grupoAC;
	}

	public void setGrupoAC(String grupoAC) {
		this.grupoAC = grupoAC;
	}

	public String getCodigo()
	{
		return codigo;
	}
	public void setCodigo(String codigo)
	{
		this.codigo = codigo;
	}
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
}
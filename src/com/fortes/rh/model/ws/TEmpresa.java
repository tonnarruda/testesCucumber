package com.fortes.rh.model.ws;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TEmpresa implements Serializable
{
	private Long id;
	private String codigoAC;
	private String nome;
	private String razaoSocial;

	public TEmpresa()
	{
	}

	public TEmpresa(Long id, String nome, String razaoSocial, String codigoAC)
	{
		this.id = id;
		this.nome = nome;
		this.razaoSocial = razaoSocial;
		this.codigoAC = codigoAC;
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
	public String getRazaoSocial()
	{
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial)
	{
		this.razaoSocial = razaoSocial;
	}
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
}
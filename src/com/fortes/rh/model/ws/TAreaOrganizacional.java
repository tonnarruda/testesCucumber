package com.fortes.rh.model.ws;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TAreaOrganizacional implements Serializable
{
	private Long id;
	private String codigo;
	private String nome;
	private String areaMaeCodigo;
	private String empresaCodigo;
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
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
	public String getAreaMaeCodigo()
	{
		return areaMaeCodigo;
	}
	public void setAreaMaeCodigo(String areaMaeCodigo)
	{
		this.areaMaeCodigo = areaMaeCodigo;
	}
	public String getEmpresaCodigo()
	{
		return empresaCodigo;
	}
	public void setEmpresaCodigo(String empresaCodigo)
	{
		this.empresaCodigo = empresaCodigo;
	}
	
}

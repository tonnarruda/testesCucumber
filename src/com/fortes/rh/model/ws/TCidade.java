package com.fortes.rh.model.ws;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TCidade implements Serializable
{
	private Long id;
	private String nome;

	public TCidade()
	{
	}

	public TCidade(Long id, String nome)
	{
		this.id = id;
		this.nome = nome;
	}

	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
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
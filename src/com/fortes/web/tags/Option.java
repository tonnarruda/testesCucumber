package com.fortes.web.tags;

public class Option
{
	private Long id;
	private String nome;
	private String titulo;
	private boolean selecionado;
	private boolean desabilitado;

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
	
	public String getTitulo()
	{
		return titulo;
	}
	
	public void setTitulo(String titulo)
	{
		this.titulo = titulo;
	}
	
	public boolean isSelecionado()
	{
		return selecionado;
	}
	
	public void setSelecionado(boolean selecionado)
	{
		this.selecionado = selecionado;
	}

	public boolean isDesabilitado() 
	{
		return desabilitado;
	}

	public void setDesabilitado(boolean desabilitado) 
	{
		this.desabilitado = desabilitado;
	}
}

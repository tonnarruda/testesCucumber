package com.fortes.web.tags;

public class CheckBox
{
	private Long id;
	private String nome;
	private boolean selecionado;

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
	public boolean isSelecionado()
	{
		return selecionado;
	}
	public void setSelecionado(boolean selecionado)
	{
		this.selecionado = selecionado;
	}
	
}

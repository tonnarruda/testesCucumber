package com.fortes.web.tags;

import java.util.Map;

public class CheckBox
{
	private String id;
	private String nome;
	private String titulo;
	private boolean selecionado;
	private boolean desabilitado;
	private Map<String, String> parameters;

	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public void setId(Long id)
	{
		if (id != null)
			this.id = id.toString();
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

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
}

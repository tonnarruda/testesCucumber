package com.fortes.rh.test.util;

import java.util.Date;

public class BeanBasico
{
	private Long id;
	private Integer ordem;
	private String nome;
	private Date data;
	private Boolean ativo;

	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public Integer getOrdem()
	{
		return ordem;
	}
	public void setOrdem(Integer ordem)
	{
		this.ordem = ordem;
	}
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	public Date getData()
	{
		return data;
	}
	public void setData(Date data)
	{
		this.data = data;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
}
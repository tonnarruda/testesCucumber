package com.fortes.rh.model.captacao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fortes.rh.model.geral.Empresa;

@Entity
public class Competencia
{
	@Transient
	public static final Character CONHECIMENTO = 'C';
	@Transient
	public static final Character HABILIDADE = 'H';
	@Transient
	public static final Character ATITUDE = 'A';
	
	@Id
	private Long id;
	@Column(length=100)
	private String nome;
	@ManyToOne
	private Empresa empresa;
	@Lob
	private String observacao;
	private Character tipo;

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
	
	public Empresa getEmpresa() 
	{
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa) 
	{
		this.empresa = empresa;
	}
	
	public String getObservacao() 
	{
		return observacao;
	}
	
	public void setObservacao(String observacao) 
	{
		this.observacao = observacao;
	}
	
	public Character getTipo() 
	{
		return tipo;
	}
	
	public void setTipo(Character tipo) 
	{
		this.tipo = tipo;
	}
}
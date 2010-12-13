package com.fortes.rh.model.captacao;

import java.io.Serializable;

import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.geral.Empresa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="atitude_sequence", allocationSize=1)
public class Atitude extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String nome;
	@ManyToOne
	private Empresa empresa;

	public Atitude()	{
	}

	public Atitude(Long id, String nome)
	{
		this.setId(id);
		this.nome = nome;
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
}

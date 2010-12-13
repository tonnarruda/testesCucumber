package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="habilidade_sequence", allocationSize=1)
public class Habilidade extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String nome;
	@ManyToOne
	private Empresa empresa;

	public Habilidade()	{
	}

	public Habilidade(Long id, String nome)
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
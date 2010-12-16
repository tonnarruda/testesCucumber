package com.fortes.rh.model.captacao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="habilidade_sequence", allocationSize=1)
public class Habilidade extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String nome;
	@ManyToOne
	private Empresa empresa;
	@Lob
	private String observacao;

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

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}
package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@Entity
@SequenceGenerator(name="sequence", sequenceName="bairro_sequence", allocationSize=1)
public class Bairro extends AbstractModel implements Serializable
{
    @Column(length=60)
    private String nome;
	@ManyToOne
	private Cidade cidade;

	public void setProjectionCidadeNome(String projectionCidadeNome)
	{
		if (this.cidade == null)
			this.cidade = new Cidade();

		this.cidade.setNome(projectionCidadeNome);
	}

	public Cidade getCidade()
	{
		return cidade;
	}
	public void setCidade(Cidade cidade)
	{
		this.cidade = cidade;
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
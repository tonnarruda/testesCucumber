package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;

@Entity
@SequenceGenerator(name="sequence", sequenceName="prioridadetreinamento_sequence", allocationSize=1)
public class PrioridadeTreinamento extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String descricao;
	@Column(length=5)
	private String sigla;
	private int numero;

	public String getDescricao()
	{
		return descricao;
	}
	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
	public int getNumero()
	{
		return numero;
	}
	public void setNumero(int numero)
	{
		this.numero = numero;
	}
	public String getSigla()
	{
		return sigla;
	}
	public void setSigla(String sigla)
	{
		this.sigla = sigla;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("numero", this.numero).append("sigla", this.sigla)
				.append("id", this.getId()).append("descricao", this.descricao)
				.toString();
	}
}
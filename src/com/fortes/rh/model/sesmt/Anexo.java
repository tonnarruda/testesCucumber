package com.fortes.rh.model.sesmt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.dicionario.OrigemAnexo;

@Entity
@SequenceGenerator(name="sequence", sequenceName="anexo_sequence", allocationSize=1)
public class Anexo extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String nome;
	@Lob
	private String observacao;
	@Column(length=120)
	private String url;
	//Dicionario OrigemAnexo
	private char origem = OrigemAnexo.AnexoCandidato;
	private Long origemId;

	public String getUrlPath()
	{
		StringBuilder urlPath = new StringBuilder("");

		if (this.origem == OrigemAnexo.LTCAT || this.origem == OrigemAnexo.PPRA)
			urlPath.append("sesmt/");

		urlPath.append(this.url);

		return urlPath.toString();
	}

    public String getObservacao()
	{
		return observacao;
	}
	public void setObservacao(String observacao)
	{
		this.observacao = observacao;
	}
	public char getOrigem()
	{
		return origem;
	}
	public void setOrigem(char origem)
	{
		this.origem = origem;
	}
	public Long getOrigemId()
	{
		return origemId;
	}
	public void setOrigemId(Long origemId)
	{
		this.origemId = origemId;
	}
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
}
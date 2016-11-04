package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.avaliacao.Avaliacao;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="mensagem_sequence", allocationSize=1)
public class Mensagem extends AbstractModel implements Serializable
{
	@Column(length=100)
    private String remetente;
	
	@Column(length=200)
	private String link;

	@ManyToOne(fetch = FetchType.LAZY)
	private Colaborador colaborador;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Avaliacao avaliacao;
	
	private Character tipo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;

	@Lob
	private String texto;

	public String getRemetente()
	{
		return remetente;
	}

	public void setRemetente(String remetente)
	{
		this.remetente = remetente;
	}

	public Date getData()
	{
		return data;
	}

	public void setData(Date data)
	{
		this.data = data;
	}

	public String getTexto()
	{
		return texto;
	}

	public void setTexto(String texto)
	{
		this.texto = texto;
	}

	public String getTextoAbreviado()
	{
		
		if (this.texto == null)
			return "";
		
		if(this.texto.length() > 90)
			return this.texto.substring(0, 90) + "...";
		else
			return this.texto;
	}

	public String getLink()
	{
		return link;
	}

	public void setLink(String link)
	{
		this.link = link;
	}

	public Colaborador getColaborador() 
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) 
	{
		this.colaborador = colaborador;
	}

	public Character getTipo() 
	{
		return tipo;
	}

	public void setTipo(Character tipo) 
	{
		this.tipo = tipo;
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}
}
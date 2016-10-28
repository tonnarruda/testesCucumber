package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="engenheiroresponsavel_sequence", allocationSize=1)
public class EngenheiroResponsavel extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String nome;

	@Temporal(TemporalType.DATE)
	private Date inicio;
	@Temporal(TemporalType.DATE)
	private Date fim;
	
	@Column(length=15)
	private String nit;
	
	@Column(length=20)
	private String crea;

	@ManyToOne
	private Empresa empresa;


	public void setEmpresaIdProjection(Long empresaIdProjection)
	{
		if (this.empresa == null)
			this.empresa = new Empresa();
		this.empresa.setId(empresaIdProjection);
	}
	
	public String getPeriodoFormatado()
	{
		String periodo = "";
		if (inicio != null)
			periodo += DateUtil.formataDiaMesAno(inicio);
		if (fim != null)
			periodo += " - " + DateUtil.formataDiaMesAno(fim);

		return periodo;
	}
	
	public String getPeriodoRelatorio()
	{
		return DateUtil.formataDiaMesAno(this.inicio) + " a " + (this.fim != null ? DateUtil.formataDiaMesAno(this.fim) : "__/__/___");
	}

	public String getCrea()
	{
		return crea;
	}
	
	public void setCrea(String crea)
	{
		this.crea = crea;
	}
	
	public Empresa getEmpresa()
	{
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}
	
	public String getNome()
	{
		return nome;
	}
	
	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public Date getInicio() 
	{
		return inicio;
	}

	public void setInicio(Date inicio) 
	{
		this.inicio = inicio;
	}

	public Date getFim() 
	{
		return fim;
	}

	public void setFim(Date fim) 
	{
		this.fim = fim;
	}

	public String getNit() 
	{
		return nit;
	}

	public void setNit(String nit) 
	{
		this.nit = nit;
	}
}
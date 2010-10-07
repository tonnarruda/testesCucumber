package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;

@Entity
@SequenceGenerator(name="sequence", sequenceName="dnt_sequence", allocationSize=1)
public class DNT extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String nome;
	@Temporal(TemporalType.DATE)
	private Date data;
	@ManyToOne
	private Empresa empresa;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="dnt")
	private Collection<ColaboradorTurma> colaboradorTurmas;

	//Projection
	public void setProjectionEmpresaId(Long empresaId)
	{
		if(this.empresa == null)
			this.empresa = new Empresa();
		
		this.empresa.setId(empresaId);
	}
	
	
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	public Collection<ColaboradorTurma> getColaboradorTurmas()
	{
		return colaboradorTurmas;
	}
	public void setColaboradorTurmas(Collection<ColaboradorTurma> colaboradorTurmas)
	{
		this.colaboradorTurmas = colaboradorTurmas;
	}
	public Date getData()
	{
		return data;
	}
	public void setData(Date data)
	{
		this.data = data;
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
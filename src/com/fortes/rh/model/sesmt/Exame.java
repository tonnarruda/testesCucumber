package com.fortes.rh.model.sesmt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="exame_sequence", allocationSize=1)
public class Exame extends AbstractModel implements Serializable
{
	@Column(length=100)
    private String nome;

    @ManyToOne
    private Empresa empresa;

    private boolean periodico = true;

    private int periodicidade;

    public Exame()
    {
    }

    public Exame(Long id, String nome)
    {
    	this.setId(id);
    	this.setNome(nome);
    }

    //Projections
    public void setEmpresaIdProjection(Long empresaIdProjection)
	{
		if (this.empresa == null)
			this.empresa = new Empresa();
		this.empresa.setId(empresaIdProjection);
	}
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	public int getPeriodicidade()
	{
		return periodicidade;
	}
	public void setPeriodicidade(int periodicidade)
	{
		this.periodicidade = periodicidade;
	}
	public Empresa getEmpresa()
	{
		return empresa;
	}
	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}
	public boolean isPeriodico()
	{
		return periodico;
	}
	public void setPeriodico(boolean periodico)
	{
		this.periodico = periodico;
	}
}
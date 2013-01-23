package com.fortes.rh.model.cargosalario;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="indicehistorico_sequence", allocationSize=1)
public class IndiceHistorico extends AbstractModel implements Serializable, Cloneable
{
	@Temporal(TemporalType.DATE)
    private Date data;

	@ManyToOne
    private Indice indice;
	
	@ManyToOne
	private ReajusteIndice reajusteIndice;

	private Double valor;

	@Transient
	private Double valorAtual;

	public Double getValorAtual()
	{
		return valorAtual;
	}

	public void setValorAtual(Double valorAtual)
	{
		this.valorAtual = valorAtual;
	}

	//Projections
    public void setProjectionIndiceId(Long projectionIndiceId)
    {
    	if(this.indice == null)
    		this.indice = new Indice();

    	this.indice.setId(projectionIndiceId);
    }

    public void setProjectionIndiceNome(String projectionIndiceNome)
    {
    	if(this.indice == null)
    		this.indice = new Indice();

    	this.indice.setNome(projectionIndiceNome);
    }

    public Object clone()
	{
		try
		{
			IndiceHistorico clone = (IndiceHistorico) super.clone();

			return clone;
		}
		catch (CloneNotSupportedException e)
		{
			throw new Error("This should not occur since we implement Cloneable");
		}
	}

	public Date getData()
	{
		return data;
	}

	public void setData(Date data)
	{
		this.data = data;
	}

	public Indice getIndice()
	{
		return indice;
	}

	public void setIndice(Indice indice)
	{
		this.indice = indice;
	}

	public Double getValor()
	{
		return valor;
	}

	public void setValor(Double valor)
	{
		this.valor = valor;
	}

	public ReajusteIndice getReajusteIndice() 
	{
		return reajusteIndice;
	}

	public void setReajusteIndice(ReajusteIndice reajusteIndice) 
	{
		this.reajusteIndice = reajusteIndice;
	}
}
package com.fortes.rh.model.cargosalario;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name = "sequence", sequenceName = "reajusteindice_sequence", allocationSize = 1)
public class ReajusteIndice extends AbstractModel implements Serializable
{
	@ManyToOne
	private Indice indice;
	@ManyToOne
	private TabelaReajusteColaborador tabelaReajusteColaborador;
	private Double valorAtual;
	private Double valorProposto;
	
	public Indice getIndice() 
	{
		return indice;
	}
	
	public void setIndice(Indice indice) 
	{
		this.indice = indice;
	}
	
	public TabelaReajusteColaborador getTabelaReajusteColaborador() 
	{
		return tabelaReajusteColaborador;
	}
	
	public void setTabelaReajusteColaborador(TabelaReajusteColaborador tabelaReajusteColaborador) 
	{
		this.tabelaReajusteColaborador = tabelaReajusteColaborador;
	}
	
	public Double getValorAtual() 
	{
		return valorAtual;
	}
	
	public void setValorAtual(Double valorAtual) 
	{
		this.valorAtual = valorAtual;
	}
	
	public Double getValorProposto() 
	{
		return valorProposto;
	}
	
	public void setValorProposto(Double valorProposto) 
	{
		this.valorProposto = valorProposto;
	}
}
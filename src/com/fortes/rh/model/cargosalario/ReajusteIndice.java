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
	
	public void setProjectionTabelaReajusteColaboradorId(Long tabelaReajusteColaboradorId) 
	{
		if (this.tabelaReajusteColaborador == null)
			this.tabelaReajusteColaborador = new TabelaReajusteColaborador();
		
		this.tabelaReajusteColaborador.setId(tabelaReajusteColaboradorId);
	}
	
	public void setProjectionTabelaReajusteColaboradorNome(String tabelaReajusteColaboradorNome) 
	{
		if (this.tabelaReajusteColaborador == null)
			this.tabelaReajusteColaborador = new TabelaReajusteColaborador();
		
		this.tabelaReajusteColaborador.setNome(tabelaReajusteColaboradorNome);
	}

	public void setProjectionIndiceId(Long indiceId) 
	{
		if (this.indice == null)
			this.indice = new Indice();
		
		this.indice.setId(indiceId);
	}
	
	public void setProjectionIndiceNome(String indiceNome) 
	{
		if (this.indice == null)
			this.indice = new Indice();
		
		this.indice.setNome(indiceNome);
	}
	
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
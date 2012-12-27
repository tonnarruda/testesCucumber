package com.fortes.rh.model.cargosalario;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name = "sequence", sequenceName = "reajustefaixasalarial_sequence", allocationSize = 1)
public class ReajusteFaixaSalarial extends AbstractModel implements Serializable
{
	@ManyToOne
	private FaixaSalarial faixaSalarial;
	@ManyToOne
	private TabelaReajusteColaborador tabelaReajusteColaborador;
	private Integer tipoAtual;
	private Integer tipoProposto;
	@ManyToOne
	private Indice indiceAtual;
	@ManyToOne
	private Indice indiceProposto;
	private Double qtdIndiceAtual;
	private Double qtdIndiceProposta;
	private Double valorAtual;
	private Double valorProposto;
	
	public void setProjectionTabelaReajusteColaboradorId(Long tabelaReajusteColaboradorId) 
	{
		if (this.tabelaReajusteColaborador == null)
			this.tabelaReajusteColaborador = new TabelaReajusteColaborador();
		
		this.tabelaReajusteColaborador.setId(tabelaReajusteColaboradorId);
	}	
	
	public TabelaReajusteColaborador getTabelaReajusteColaborador() 
	{
		return tabelaReajusteColaborador;
	}
	
	public void setTabelaReajusteColaborador(TabelaReajusteColaborador tabelaReajusteColaborador) 
	{
		this.tabelaReajusteColaborador = tabelaReajusteColaborador;
	}
	
	public Integer getTipoAtual() 
	{
		return tipoAtual;
	}
	
	public void setTipoAtual(Integer tipoAtual) 
	{
		this.tipoAtual = tipoAtual;
	}
	
	public Integer getTipoProposto() 
	{
		return tipoProposto;
	}
	
	public void setTipoProposto(Integer tipoProposto) 
	{
		this.tipoProposto = tipoProposto;
	}
	
	public Indice getIndiceAtual() 
	{
		return indiceAtual;
	}
	
	public void setIndiceAtual(Indice indiceAtual) 
	{
		this.indiceAtual = indiceAtual;
	}
	
	public Indice getIndiceProposto() 
	{
		return indiceProposto;
	}
	
	public void setIndiceProposto(Indice indiceProposto) 
	{
		this.indiceProposto = indiceProposto;
	}
	
	public Double getQtdIndiceAtual() 
	{
		return qtdIndiceAtual;
	}
	
	public void setQtdIndiceAtual(Double qtdIndiceAtual) 
	{
		this.qtdIndiceAtual = qtdIndiceAtual;
	}
	
	public Double getQtdIndiceProposta() 
	{
		return qtdIndiceProposta;
	}
	
	public void setQtdIndiceProposta(Double qtdIndiceProposta) 
	{
		this.qtdIndiceProposta = qtdIndiceProposta;
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
	
	public FaixaSalarial getFaixaSalarial() 
	{
		return faixaSalarial;
	}
	
	public void setFaixaSalarial(FaixaSalarial faixaSalarial) 
	{
		this.faixaSalarial = faixaSalarial;
	}
}
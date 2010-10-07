package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;

@Entity
@SequenceGenerator(name="sequence", sequenceName="gastoEmpresaItem_sequence", allocationSize=1)
public class GastoEmpresaItem extends AbstractModel implements Serializable
{
    @ManyToOne
    private Gasto gasto;
    private Double valor;
    @ManyToOne
    private GastoEmpresa gastoEmpresa;

	public Gasto getGasto()
	{
		return gasto;
	}
	public void setGasto(Gasto gasto)
	{
		this.gasto = gasto;
	}
	public Double getValor()
	{
		return valor;
	}
	public void setValor(Double valor)
	{
		this.valor = valor;
	}
	public GastoEmpresa getGastoEmpresa()
	{
		return gastoEmpresa;
	}
	public void setGastoEmpresa(GastoEmpresa gastoEmpresa)
	{
		this.gastoEmpresa = gastoEmpresa;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", this.getId()).append("gasto", this.gasto).append(
						"gastoEmpresa", this.gastoEmpresa).append("valor",
						this.valor).toString();
	}
}
package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;

@Entity
@SequenceGenerator(name="sequence", sequenceName="gastoEmpresa_sequence", allocationSize=1)
public class GastoEmpresa extends AbstractModel implements Serializable
{
    @ManyToOne
    private Colaborador colaborador;
    @Temporal(TemporalType.DATE)
    private Date mesAno;

    @OneToMany(mappedBy = "gastoEmpresa")
    private Collection<GastoEmpresaItem> gastoEmpresaItems;

    @ManyToOne
    private Empresa empresa;

	public Colaborador getColaborador()
	{
		return colaborador;
	}
	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public void setColaboradorNomeComercial(String nomeComercial)
	{
		if(colaborador == null)
			colaborador = new Colaborador();

		colaborador.setNomeComercial(nomeComercial);
	}

	public Collection<GastoEmpresaItem> getGastoEmpresaItems()
	{
		return gastoEmpresaItems;
	}
	public void setGastoEmpresaItems(Collection<GastoEmpresaItem> gastoEmpresaItems)
	{
		this.gastoEmpresaItems = gastoEmpresaItems;
	}
	public Date getMesAno()
	{
		return mesAno;
	}
	public void setMesAno(Date mesAno)
	{
		this.mesAno = mesAno;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", this.getId()).append("colaborador", this.colaborador)
				.append("mesAno", this.mesAno).toString();
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
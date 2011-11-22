package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;

@Entity
@SequenceGenerator(name="sequence", sequenceName="gasto_sequence", allocationSize=1)
public class Gasto extends AbstractModel implements Serializable
{
    @Column(length=100)
    private String nome;
    private boolean naoImportar;
    @Column(length=12)
    private String codigoAc;

    @ManyToOne
    private GrupoGasto grupoGasto;

    @ManyToOne
    private Empresa empresa;

    public void setProjectionEmpresaId(Long projectionEmpresaId)
	{
		if (this.empresa == null)
			this.empresa = new Empresa();
		this.empresa.setId(projectionEmpresaId);
	}

    public void setProjectionEmpresaNome(String projectionEmpresaNome)
    {
    	if (this.empresa == null)
    		this.empresa = new Empresa();
    	this.empresa.setNome(projectionEmpresaNome);
    }
    
    public GrupoGasto getGrupoGasto()
	{
		return grupoGasto;
	}
	public void setGrupoGasto(GrupoGasto grupoGasto)
	{
		this.grupoGasto = grupoGasto;
	}
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	public boolean isNaoImportar()
	{
		return naoImportar;
	}
	public void setNaoImportar(boolean naoImportar)
	{
		this.naoImportar = naoImportar;
	}
	public String getCodigoAc()
	{
		return codigoAc;
	}
	public void setCodigoAc(String codigoAc)
	{
		this.codigoAc = codigoAc;
	}
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("nome", this.nome).append("id", this.getId()).append(
						"grupoGasto", this.grupoGasto).append("naoImportar",
						this.naoImportar).toString();
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
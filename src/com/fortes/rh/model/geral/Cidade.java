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
@SequenceGenerator(name="sequence", sequenceName="cidade_sequence", allocationSize=1)
public class Cidade extends AbstractModel implements Serializable
{
    @ManyToOne
    private Estado uf;
    @Column(length=80)
    private String nome;
    @Column(length=12)
    private String codigoAC;
    private Integer codigoIBGE;

    public void setProjectionUfId(Long ufId)
    {
    	if(this.uf == null)
    		this.uf = new Estado();
    	this.uf.setId(ufId);
    }

    public void setProjectionUfSigla(String ufSigla)
    {
    	if(this.uf == null)
    		this.uf = new Estado();
    	this.uf.setSigla(ufSigla);
    }

	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	public Estado getUf()
	{
		return uf;
	}
	public void setUf(Estado uf)
	{
		this.uf = uf;
	}
	public String getCodigoAC()
	{
		return codigoAC;
	}
	public void setCodigoAC(String codigoAC)
	{
		this.codigoAC = codigoAC;
	}

	public Integer getCodigoIBGE() {
		return codigoIBGE;
	}

	public void setCodigoIBGE(Integer codigoIBGE) {
		this.codigoIBGE = codigoIBGE;
	}
	
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("nome", this.nome).append("id", this.getId()).append("uf",
						this.uf).toString();
	}
}
package com.fortes.rh.model.portalcolaborador;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.dicionario.URLTransacaoPC;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="transacaopc_sequence", allocationSize=1)
public class TransacaoPC extends AbstractModel implements Serializable
{
	@ManyToOne
	private Empresa empresa;
	private Integer codigoUrl;
    private String json;
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    public String toString()
    {
    	return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
    	.append("codigoUrl", this.codigoUrl)
    	.append("url", URLTransacaoPC.getById(this.codigoUrl).getUrl())
    	.append("json", this.json)
    	.append("data", this.data)
    	.toString();
    }
    
	public Integer getCodigoUrl() {
		return codigoUrl;
	}

	public void setCodigoUrl(Integer codigoUrl) {
		this.codigoUrl = codigoUrl;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}
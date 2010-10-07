package com.fortes.rh.model.captacao;

import java.io.Serializable;

import javax.persistence.Column;

public class CertificadoMilitar implements Serializable
{
	@Column(length=12)
	private String certMilNumero;
	@Column(length=5)
	private String certMilTipo;
	@Column(length=12)
	private String certMilSerie;

	public String getCertMilNumero()
	{
		return certMilNumero;
	}
	public void setCertMilNumero(String certMilNumero)
	{
		this.certMilNumero = certMilNumero;
	}
	public String getCertMilSerie()
	{
		return certMilSerie;
	}
	public void setCertMilSerie(String certMilSerie)
	{
		this.certMilSerie = certMilSerie;
	}
	public String getCertMilTipo()
	{
		return certMilTipo;
	}
	public void setCertMilTipo(String certMilTipo)
	{
		this.certMilTipo = certMilTipo;
	}

}
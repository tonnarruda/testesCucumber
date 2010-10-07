package com.fortes.rh.model.captacao;

import java.io.Serializable;

import javax.persistence.Column;

public class TituloEleitoral implements Serializable
{
	@Column(length=13)
	private String titEleitNumero;
	@Column(length=13)
	private String titEleitZona;
	@Column(length=13)
	private String titEleitSecao;
	public String getTitEleitNumero()
	{
		return titEleitNumero;
	}
	public void setTitEleitNumero(String titEleitNumero)
	{
		this.titEleitNumero = titEleitNumero;
	}
	public String getTitEleitSecao()
	{
		return titEleitSecao;
	}
	public void setTitEleitSecao(String titEleitSecao)
	{
		this.titEleitSecao = titEleitSecao;
	}
	public String getTitEleitZona()
	{
		return titEleitZona;
	}
	public void setTitEleitZona(String titEleitZona)
	{
		this.titEleitZona = titEleitZona;
	}

}
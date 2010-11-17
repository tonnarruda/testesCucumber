package com.fortes.rh.model.ws;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TRemuneracaoVariavel implements Serializable
{
	private String CodigoEmpregado;
	private String AnoMes;
	private Double Valor;
	
	public String getCodigoEmpregado() {
		return CodigoEmpregado;
	}
	public void setCodigoEmpregado(String codigoEmpregado) {
		CodigoEmpregado = codigoEmpregado;
	}
	public String getAnoMes() {
		return AnoMes;
	}
	public void setAnoMes(String anoMes) {
		AnoMes = anoMes;
	}
	public Double getValor() {
		return Valor;
	}
	public void setValor(Double valor) {
		Valor = valor;
	}
}
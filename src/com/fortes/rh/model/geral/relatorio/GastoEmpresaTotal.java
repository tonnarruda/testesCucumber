package com.fortes.rh.model.geral.relatorio;

import java.io.Serializable;
import java.util.Collection;

public class GastoEmpresaTotal implements Serializable
{
	Collection<GastoRelatorio> gastoRelatorios;
	Collection<TotalGastoRelatorio> totais;

	public Collection<GastoRelatorio> getGastoRelatorios()
	{
		return gastoRelatorios;
	}
	public void setGastoRelatorios(Collection<GastoRelatorio> gastoRelatorios)
	{
		this.gastoRelatorios = gastoRelatorios;
	}
	public Collection<TotalGastoRelatorio> getTotais()
	{
		return totais;
	}
	public void setTotais(Collection<TotalGastoRelatorio> totais)
	{
		this.totais = totais;
	}
}
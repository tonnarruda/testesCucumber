package com.fortes.rh.model.geral.relatorio;

import java.io.Serializable;
import java.util.Collection;

import com.fortes.rh.model.geral.AreaOrganizacional;

// Utilizado para gerar relatório de gastos empresas.
public class GastoRelatorio implements Serializable
{
	private AreaOrganizacional areaOrganizacional;
	private Collection<GastoRelatorioItem> gastoRelatorioItems;

	public AreaOrganizacional getAreaOrganizacional()
	{
		return areaOrganizacional;
	}
	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		this.areaOrganizacional = areaOrganizacional;
	}

	public Collection<GastoRelatorioItem> getGastoRelatorioItems()
	{
		return gastoRelatorioItems;
	}
	public void setGastoRelatorioItems(Collection<GastoRelatorioItem> gastoRelatorioItems)
	{
		this.gastoRelatorioItems = gastoRelatorioItems;
	}

}

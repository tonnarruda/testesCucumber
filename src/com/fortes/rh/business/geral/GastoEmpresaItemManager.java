package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.GastoEmpresa;
import com.fortes.rh.model.geral.GastoEmpresaItem;

public interface GastoEmpresaItemManager extends GenericManager<GastoEmpresaItem>
{
	void removeGastos(GastoEmpresa gastoEmpresa);

	Collection<GastoEmpresaItem> getGastosImportaveis(GastoEmpresa gastoEmpresa);

}
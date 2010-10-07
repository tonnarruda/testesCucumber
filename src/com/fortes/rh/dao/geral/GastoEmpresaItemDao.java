package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.GastoEmpresa;
import com.fortes.rh.model.geral.GastoEmpresaItem;

public interface GastoEmpresaItemDao extends GenericDao<GastoEmpresaItem>
{
	void removeGastos(GastoEmpresa gastoEmpresa);

	Collection<GastoEmpresaItem> getGastosImportaveis(GastoEmpresa gastoEmpresa);
}
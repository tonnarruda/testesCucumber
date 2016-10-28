package com.fortes.rh.business.geral;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.GastoEmpresaItemDao;
import com.fortes.rh.model.geral.GastoEmpresa;
import com.fortes.rh.model.geral.GastoEmpresaItem;

@Component
public class GastoEmpresaItemManagerImpl extends GenericManagerImpl<GastoEmpresaItem, GastoEmpresaItemDao> implements GastoEmpresaItemManager
{
	@Autowired
	GastoEmpresaItemManagerImpl(GastoEmpresaItemDao dao) {
		setDao(dao);
	}

	public void removeGastos(GastoEmpresa gastoEmpresa)
	{
		getDao().removeGastos(gastoEmpresa);
	}

	public Collection<GastoEmpresaItem> getGastosImportaveis(GastoEmpresa gastoEmpresa)
	{
		return getDao().getGastosImportaveis(gastoEmpresa);
	}
}
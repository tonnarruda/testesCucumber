package com.fortes.rh.business.cargosalario;

import java.util.Collection;

import com.fortes.rh.model.cargosalario.FaturamentoMensal;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.cargosalario.FaturamentoMensalManager;
import com.fortes.rh.dao.cargosalario.FaturamentoMensalDao;

public class FaturamentoMensalManagerImpl extends GenericManagerImpl<FaturamentoMensal, FaturamentoMensalDao> implements FaturamentoMensalManager
{

	public Collection<FaturamentoMensal> findAllSelect(Long empresaId) {
		return getDao().findAllSelect(empresaId);
	}
}

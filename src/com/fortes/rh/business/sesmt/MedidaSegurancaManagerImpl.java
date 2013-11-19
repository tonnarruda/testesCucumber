package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.rh.model.sesmt.MedidaSeguranca;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.sesmt.MedidaSegurancaManager;
import com.fortes.rh.dao.sesmt.MedidaSegurancaDao;

public class MedidaSegurancaManagerImpl extends GenericManagerImpl<MedidaSeguranca, MedidaSegurancaDao> implements MedidaSegurancaManager
{
	public Collection<MedidaSeguranca> findAllSelect(String descricao, Long empresaId) 
	{
		return getDao().findAllSelect(descricao, empresaId);
	}
}

package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.MedidaSegurancaDao;
import com.fortes.rh.model.sesmt.MedidaSeguranca;

public class MedidaSegurancaManagerImpl extends GenericManagerImpl<MedidaSeguranca, MedidaSegurancaDao> implements MedidaSegurancaManager
{
	public Collection<MedidaSeguranca> findAllSelect(String descricao, Long empresaId) 
	{
		return getDao().findAllSelect(descricao, empresaId);
	}
}

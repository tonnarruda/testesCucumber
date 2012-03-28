package com.fortes.rh.business.sesmt;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.RiscoFuncaoDao;
import com.fortes.rh.model.sesmt.RiscoFuncao;

public class RiscoFuncaoManagerImpl extends GenericManagerImpl<RiscoFuncao, RiscoFuncaoDao> implements RiscoFuncaoManager 
{
	public boolean removeByHistoricoFuncao(Long historicoFuncaoId) 
	{
		return getDao().removeByHistoricoFuncao(historicoFuncaoId);
	}
}
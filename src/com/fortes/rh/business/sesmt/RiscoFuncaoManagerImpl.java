package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.RiscoFuncaoDao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoFuncao;

public class RiscoFuncaoManagerImpl extends GenericManagerImpl<RiscoFuncao, RiscoFuncaoDao> implements RiscoFuncaoManager 
{
	public boolean removeByHistoricoFuncao(Long historicoFuncaoId) 
	{
		return getDao().removeByHistoricoFuncao(historicoFuncaoId);
	}

	public Collection<Risco> findRiscosByFuncaoData(Long funcaoId, Date data) 
	{
		return getDao().findRiscosByFuncaoData(funcaoId, data);
	}

	public void removeByFuncao(Long funcaoId) 
	{
		getDao().removeByFuncao(funcaoId);
	}

	public List<RiscoFuncao> riscosByHistoricoFuncao( HistoricoFuncao historicoFuncao) {
		return getDao().riscosByHistoricoFuncao(historicoFuncao);
	}
}
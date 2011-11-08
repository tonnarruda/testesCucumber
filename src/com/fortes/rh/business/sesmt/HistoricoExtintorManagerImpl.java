package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.HistoricoExtintorDao;
import com.fortes.rh.model.sesmt.HistoricoExtintor;

public class HistoricoExtintorManagerImpl extends GenericManagerImpl<HistoricoExtintor, HistoricoExtintorDao> implements HistoricoExtintorManager
{
	public Collection<HistoricoExtintor> findByExtintor(Long extintorId) 
	{
		return getDao().findByExtintor(extintorId);
	}
}
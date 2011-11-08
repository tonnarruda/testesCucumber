package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.HistoricoExtintor;

public interface HistoricoExtintorDao extends GenericDao<HistoricoExtintor>
{
	Collection<HistoricoExtintor> findByExtintor(Long extintorId);
}
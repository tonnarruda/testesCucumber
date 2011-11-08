package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.HistoricoExtintor;

public interface HistoricoExtintorManager extends GenericManager<HistoricoExtintor>
{
	Collection<HistoricoExtintor> findByExtintor(Long extintorId);
}
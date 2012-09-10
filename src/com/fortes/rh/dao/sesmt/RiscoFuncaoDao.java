package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoFuncao;

public interface RiscoFuncaoDao extends GenericDao<RiscoFuncao> 
{
	boolean removeByHistoricoFuncao(Long historicoFuncaoId);
	Collection<Risco> findRiscosByFuncaoData(Long funcaoId, Date data);
	void removeByFuncao(Long funcaoId);
}
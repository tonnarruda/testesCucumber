package com.fortes.rh.dao.sesmt;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.RiscoFuncao;

public interface RiscoFuncaoDao extends GenericDao<RiscoFuncao> 
{
	boolean removeByHistoricoFuncao(Long historicoFuncaoId);
}

package com.fortes.rh.business.sesmt;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.RiscoFuncao;

public interface RiscoFuncaoManager extends GenericManager<RiscoFuncao>
{
	boolean removeByHistoricoFuncao(Long historicoFuncaoId);
}

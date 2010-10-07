package com.fortes.rh.business.desenvolvimento;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.desenvolvimento.DNT;

public interface DNTManager extends GenericManager<DNT>
{
	DNT getUltimaDNT(Long empresaId);
}
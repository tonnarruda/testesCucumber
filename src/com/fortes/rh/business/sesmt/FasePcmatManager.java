package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.FasePcmat;

public interface FasePcmatManager extends GenericManager<FasePcmat>
{
	Collection<FasePcmat> findAllSelect(String descricao, Long empresaId);
}

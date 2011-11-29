package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.NaturezaLesao;

public interface NaturezaLesaoManager extends GenericManager<NaturezaLesao>
{
	Collection<NaturezaLesao> findAllSelect(Long empresaId);
}

package com.fortes.rh.business.sesmt.eSocialTabelas;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.eSocialTabelas.DescricaoNaturezaLesao;

public interface DescricaoNaturezaLesaoManager extends GenericManager<DescricaoNaturezaLesao>
{
	Collection<DescricaoNaturezaLesao> findAllSelect();
}

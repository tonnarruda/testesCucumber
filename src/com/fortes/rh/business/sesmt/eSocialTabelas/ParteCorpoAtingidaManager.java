package com.fortes.rh.business.sesmt.eSocialTabelas;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.eSocialTabelas.ParteCorpoAtingida;

public interface ParteCorpoAtingidaManager extends GenericManager<ParteCorpoAtingida>
{
	Collection<ParteCorpoAtingida> findAllSelect();
}

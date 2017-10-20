package com.fortes.rh.business.sesmt.eSocialTabelas;

import java.util.Collection;

import com.fortes.rh.model.sesmt.eSocialTabelas.ParteCorpoAtingida;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.sesmt.eSocialTabelas.ParteCorpoAtingidaManager;
import com.fortes.rh.dao.sesmt.eSocialTabelas.ParteCorpoAtingidaDao;

public class ParteCorpoAtingidaManagerImpl extends GenericManagerImpl<ParteCorpoAtingida, ParteCorpoAtingidaDao> implements ParteCorpoAtingidaManager
{
	public Collection<ParteCorpoAtingida> findAllSelect() {
		return getDao().findAllSelect();
	}
}

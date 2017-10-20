package com.fortes.rh.business.sesmt.eSocialTabelas;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.eSocialTabelas.DescricaoNaturezaLesaoDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.DescricaoNaturezaLesao;

public class DescricaoNaturezaLesaoManagerImpl extends GenericManagerImpl<DescricaoNaturezaLesao, DescricaoNaturezaLesaoDao> implements DescricaoNaturezaLesaoManager
{
	public Collection<DescricaoNaturezaLesao> findAllSelect() {
		return getDao().findAllSelect();
	}
}

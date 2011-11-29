package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.rh.model.sesmt.NaturezaLesao;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.sesmt.NaturezaLesaoManager;
import com.fortes.rh.dao.sesmt.NaturezaLesaoDao;

public class NaturezaLesaoManagerImpl extends GenericManagerImpl<NaturezaLesao, NaturezaLesaoDao> implements NaturezaLesaoManager
{
	public Collection<NaturezaLesao> findAllSelect(Long empresaId) 
	{
		return getDao().findAllSelect(empresaId);
	}
}

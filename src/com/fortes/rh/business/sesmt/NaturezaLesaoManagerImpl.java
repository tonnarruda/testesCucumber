package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.NaturezaLesaoDao;
import com.fortes.rh.model.sesmt.NaturezaLesao;

public class NaturezaLesaoManagerImpl extends GenericManagerImpl<NaturezaLesao, NaturezaLesaoDao> implements NaturezaLesaoManager
{
	public Collection<NaturezaLesao> findAllSelect(Long empresaId) 
	{
		return getDao().findAllSelect(empresaId);
	}
}

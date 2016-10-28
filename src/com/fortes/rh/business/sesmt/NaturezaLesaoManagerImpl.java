package com.fortes.rh.business.sesmt;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.NaturezaLesaoDao;
import com.fortes.rh.model.sesmt.NaturezaLesao;

@Component
public class NaturezaLesaoManagerImpl extends GenericManagerImpl<NaturezaLesao, NaturezaLesaoDao> implements NaturezaLesaoManager
{
	@Autowired
	NaturezaLesaoManagerImpl(NaturezaLesaoDao naturezaLesaoDao) {
			setDao(naturezaLesaoDao);
	}
	
	public Collection<NaturezaLesao> findAllSelect(Long empresaId) 
	{
		return getDao().findAllSelect(empresaId);
	}
}

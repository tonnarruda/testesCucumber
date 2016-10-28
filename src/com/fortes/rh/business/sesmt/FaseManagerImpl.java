package com.fortes.rh.business.sesmt;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.FaseDao;
import com.fortes.rh.model.sesmt.Fase;

@Component
public class FaseManagerImpl extends GenericManagerImpl<Fase, FaseDao> implements FaseManager
{
	@Autowired
	FaseManagerImpl(FaseDao faseDao) {
			setDao(faseDao);
	}

	public Collection<Fase> findAllSelect(String descricao, Long empresaId) {
		return getDao().findAllSelect(descricao, empresaId);
	}
}

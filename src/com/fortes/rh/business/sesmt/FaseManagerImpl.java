package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.rh.model.sesmt.Fase;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.sesmt.FaseManager;
import com.fortes.rh.dao.sesmt.FaseDao;

public class FaseManagerImpl extends GenericManagerImpl<Fase, FaseDao> implements FaseManager
{

	public Collection<Fase> findAllSelect(String descricao, Long empresaId) {
		return getDao().findAllSelect(descricao, empresaId);
	}
}

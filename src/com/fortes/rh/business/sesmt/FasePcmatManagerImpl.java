package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.rh.model.sesmt.FasePcmat;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.sesmt.FasePcmatManager;
import com.fortes.rh.dao.sesmt.FasePcmatDao;

public class FasePcmatManagerImpl extends GenericManagerImpl<FasePcmat, FasePcmatDao> implements FasePcmatManager
{

	public Collection<FasePcmat> findAllSelect(String descricao, Long empresaId) {
		return getDao().findAllSelect(descricao, empresaId);
	}
}

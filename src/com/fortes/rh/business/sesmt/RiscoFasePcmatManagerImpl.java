package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.rh.model.sesmt.RiscoFasePcmat;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.sesmt.RiscoFasePcmatManager;
import com.fortes.rh.dao.sesmt.RiscoFasePcmatDao;

public class RiscoFasePcmatManagerImpl extends GenericManagerImpl<RiscoFasePcmat, RiscoFasePcmatDao> implements RiscoFasePcmatManager
{
	public Collection<RiscoFasePcmat> findByFasePcmat(Long fasePcmatId) 
	{
		return getDao().findByFasePcmat(fasePcmatId);
	}
}

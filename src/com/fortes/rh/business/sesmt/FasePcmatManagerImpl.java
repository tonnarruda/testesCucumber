package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.FasePcmatDao;
import com.fortes.rh.model.sesmt.FasePcmat;

public class FasePcmatManagerImpl extends GenericManagerImpl<FasePcmat, FasePcmatDao> implements FasePcmatManager
{
	public Collection<FasePcmat> findByPcmat(Long pcmatId) 
	{
		return getDao().findByPcmat(pcmatId);
	}

	public FasePcmat findByIdProjection(Long id) 
	{
		return getDao().findByIdProjection(id);
	}
}

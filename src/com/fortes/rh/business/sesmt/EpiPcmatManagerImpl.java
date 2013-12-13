package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.rh.model.sesmt.EpiPcmat;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.sesmt.EpiPcmatManager;
import com.fortes.rh.dao.sesmt.EpiPcmatDao;

public class EpiPcmatManagerImpl extends GenericManagerImpl<EpiPcmat, EpiPcmatDao> implements EpiPcmatManager
{

	public Collection<EpiPcmat> findByPcmat(Long pcmatId) 
	{
		return getDao().findByPcmat(pcmatId);
	}
}

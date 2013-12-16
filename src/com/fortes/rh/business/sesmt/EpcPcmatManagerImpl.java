package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.rh.model.sesmt.EpcPcmat;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.sesmt.EpcPcmatManager;
import com.fortes.rh.dao.sesmt.EpcPcmatDao;

public class EpcPcmatManagerImpl extends GenericManagerImpl<EpcPcmat, EpcPcmatDao> implements EpcPcmatManager
{

	public Collection<EpcPcmat> findByPcmat(Long pcmatId) {
		return getDao().findByPcmat(pcmatId);
	}
}

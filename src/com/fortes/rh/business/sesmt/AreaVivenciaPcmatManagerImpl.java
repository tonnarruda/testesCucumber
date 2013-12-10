package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.AreaVivenciaPcmatDao;
import com.fortes.rh.model.sesmt.AreaVivenciaPcmat;

public class AreaVivenciaPcmatManagerImpl extends GenericManagerImpl<AreaVivenciaPcmat, AreaVivenciaPcmatDao> implements AreaVivenciaPcmatManager
{
	public Collection<AreaVivenciaPcmat> findByPcmat(Long pcmatId) 
	{
		return getDao().findByPcmat(pcmatId);
	}
}

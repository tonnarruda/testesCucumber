package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.SinalizacaoPcmatDao;
import com.fortes.rh.model.sesmt.SinalizacaoPcmat;

public class SinalizacaoPcmatManagerImpl extends GenericManagerImpl<SinalizacaoPcmat, SinalizacaoPcmatDao> implements SinalizacaoPcmatManager
{
	public Collection<SinalizacaoPcmat> findByPcmat(Long pcmatId) 
	{
		return getDao().findByPcmat(pcmatId);
	}
}

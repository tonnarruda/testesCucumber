package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.EpcPcmatDao;
import com.fortes.rh.model.sesmt.EpcPcmat;

public class EpcPcmatManagerImpl extends GenericManagerImpl<EpcPcmat, EpcPcmatDao> implements EpcPcmatManager
{
	public Collection<EpcPcmat> findByPcmat(Long pcmatId) 
	{
		return getDao().findByPcmat(pcmatId);
	}

	public void clonar(Long pcmatOrigemId, Long pcmatDestinoId) 
	{
		Collection<EpcPcmat> epcsPcmat = getDao().findByPcmat(pcmatOrigemId);
		for (EpcPcmat epcPcmat : epcsPcmat) 
		{
			epcPcmat.setId(null);
			epcPcmat.setPcmatId(pcmatDestinoId);
			save(epcPcmat);
		}
	}
}

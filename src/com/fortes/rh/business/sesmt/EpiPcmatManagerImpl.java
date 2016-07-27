package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.EpiPcmatDao;
import com.fortes.rh.model.sesmt.EpiPcmat;

public class EpiPcmatManagerImpl extends GenericManagerImpl<EpiPcmat, EpiPcmatDao> implements EpiPcmatManager
{

	public Collection<EpiPcmat> findByPcmat(Long pcmatId) 
	{
		return getDao().findByPcmat(pcmatId);
	}

	public void clonar(Long pcmatOrigemId, Long pcmatDestinoId) 
	{
		Collection<EpiPcmat> episPcmat = getDao().findByPcmat(pcmatOrigemId);
		for (EpiPcmat epiPcmat : episPcmat) 
		{
			epiPcmat.setId(null);
			epiPcmat.setPcmatId(pcmatDestinoId);
			save(epiPcmat);
		}
	}
}

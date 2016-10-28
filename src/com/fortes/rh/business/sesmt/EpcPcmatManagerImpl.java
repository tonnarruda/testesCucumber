package com.fortes.rh.business.sesmt;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.EpcPcmatDao;
import com.fortes.rh.model.sesmt.EpcPcmat;

@Component
public class EpcPcmatManagerImpl extends GenericManagerImpl<EpcPcmat, EpcPcmatDao> implements EpcPcmatManager
{
	@Autowired
	EpcPcmatManagerImpl(EpcPcmatDao epcPcmatDao) {
		setDao(epcPcmatDao);
	}
	
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

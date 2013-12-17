package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.FasePcmatDao;
import com.fortes.rh.model.sesmt.FasePcmat;

public class FasePcmatManagerImpl extends GenericManagerImpl<FasePcmat, FasePcmatDao> implements FasePcmatManager
{
	private RiscoFasePcmatManager riscoFasePcmatManager;
	
	public Collection<FasePcmat> findByPcmat(Long pcmatId) 
	{
		return getDao().findByPcmat(pcmatId);
	}

	public FasePcmat findByIdProjection(Long id) 
	{
		return getDao().findByIdProjection(id);
	}

	public void clonar(Long pcamtOrigemId, Long pcmatDestinoId) 
	{
		Collection<FasePcmat> fasePcmats = getDao().findByPcmat(pcamtOrigemId);
		
		Long fasePcmatOrigemId;
		for (FasePcmat fasePcmatDestino : fasePcmats) 
		{
			fasePcmatOrigemId = fasePcmatDestino.getId();
			
			fasePcmatDestino.setId(null);
			fasePcmatDestino.setPcmatId(pcmatDestinoId);
			getDao().save(fasePcmatDestino);
			
			riscoFasePcmatManager.clonar(fasePcmatOrigemId, fasePcmatDestino.getId());
		}
	}

	public void setRiscoFasePcmatManager(RiscoFasePcmatManager riscoFasePcmatManager) {
		this.riscoFasePcmatManager = riscoFasePcmatManager;
	}
}

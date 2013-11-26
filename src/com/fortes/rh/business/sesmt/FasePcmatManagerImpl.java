package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.FasePcmatDao;
import com.fortes.rh.model.sesmt.FasePcmat;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoFasePcmat;

public class FasePcmatManagerImpl extends GenericManagerImpl<FasePcmat, FasePcmatDao> implements FasePcmatManager
{
	private RiscoFasePcmatManager riscoFasePcmatManager;
	
	public Collection<FasePcmat> findByPcmat(Long pcmatId) 
	{
		return getDao().findByPcmat(pcmatId);
	}

	public void saveFasePcmatRiscos(FasePcmat fasePcmat, Long[] riscosIds) 
	{
		save(fasePcmat);

		RiscoFasePcmat riscoFasePcmat;
		
		for (Long riscoId : riscosIds)
		{
			riscoFasePcmat = new RiscoFasePcmat();
			riscoFasePcmat.setRisco(new Risco(riscoId, null));
			riscoFasePcmat.setFasePcmat(fasePcmat);
			
			riscoFasePcmatManager.save(riscoFasePcmat);
		}
	}

	public void setRiscoFasePcmatManager(RiscoFasePcmatManager riscoFasePcmatManager) {
		this.riscoFasePcmatManager = riscoFasePcmatManager;
	}
}

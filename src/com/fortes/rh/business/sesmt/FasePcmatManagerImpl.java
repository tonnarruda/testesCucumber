package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.FasePcmatDao;
import com.fortes.rh.model.sesmt.FasePcmat;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoFasePcmat;
import com.fortes.rh.util.CollectionUtil;

public class FasePcmatManagerImpl extends GenericManagerImpl<FasePcmat, FasePcmatDao> implements FasePcmatManager
{
	private RiscoFasePcmatManager riscoFasePcmatManager;
	
	public Collection<FasePcmat> findByPcmat(Long pcmatId) 
	{
		return getDao().findByPcmat(pcmatId);
	}

	@SuppressWarnings("unchecked")
	public void saveFasePcmatRiscos(FasePcmat fasePcmat, Long[] riscosIds) 
	{
		Collection<Long> novosRiscosIds = new ArrayList<Long>(Arrays.asList(riscosIds));
		
		if (fasePcmat.getId() != null)
		{
			update(fasePcmat);
			
			Collection<RiscoFasePcmat> riscosFasePcmat = riscoFasePcmatManager.findByFasePcmat(fasePcmat.getId());
			Collection<Long> riscosExistentesIds = new ArrayList<Long>(Arrays.asList( new CollectionUtil<RiscoFasePcmat>().convertCollectionToArrayIds(riscosFasePcmat, "getRiscoId") ));
			
			Collection<Long> riscosExcluir = CollectionUtils.subtract(riscosExistentesIds, novosRiscosIds);
			if (!riscosExcluir.isEmpty())
				riscoFasePcmatManager.removeByFasePcmatRisco(fasePcmat.getId(), riscosExcluir);
			
			novosRiscosIds.removeAll(riscosExistentesIds);
		}
		else
		{
			save(fasePcmat);
		}

		RiscoFasePcmat riscoFasePcmat;
		
		for (Long riscoId : novosRiscosIds)
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

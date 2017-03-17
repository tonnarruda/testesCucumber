package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.FasePcmatDao;
import com.fortes.rh.model.sesmt.FasePcmat;
import com.fortes.rh.model.sesmt.RiscoFasePcmat;

@Component
public class FasePcmatManagerImpl extends GenericManagerImpl<FasePcmat, FasePcmatDao> implements FasePcmatManager
{
	@Autowired private RiscoFasePcmatManager riscoFasePcmatManager;
	
	@Autowired
	FasePcmatManagerImpl(FasePcmatDao fasePcmatDao) {
			setDao(fasePcmatDao);
	}
	
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
	
	public Map<FasePcmat, Collection<RiscoFasePcmat>> findByPcmatRiscos(Long pcmatId) 
	{
		Collection<RiscoFasePcmat> riscosFasePcmat = riscoFasePcmatManager.findByPcmat(pcmatId);
		Map<FasePcmat, Collection<RiscoFasePcmat>> fasesRiscos = new LinkedHashMap<FasePcmat, Collection<RiscoFasePcmat>>();
		
		for (RiscoFasePcmat riscoFasePcmat : riscosFasePcmat) 
		{
			if (!fasesRiscos.containsKey(riscoFasePcmat.getFasePcmat()))
				fasesRiscos.put(riscoFasePcmat.getFasePcmat(), new ArrayList<RiscoFasePcmat>());
			
			if (riscoFasePcmat.getId() != null)
				fasesRiscos.get(riscoFasePcmat.getFasePcmat()).add(riscoFasePcmat);
		}
		
		return fasesRiscos;
	}
}
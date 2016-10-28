package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.MedidaRiscoFasePcmatDao;
import com.fortes.rh.model.sesmt.MedidaRiscoFasePcmat;

@Component
public class MedidaRiscoFasePcmatManagerImpl extends GenericManagerImpl<MedidaRiscoFasePcmat, MedidaRiscoFasePcmatDao> implements MedidaRiscoFasePcmatManager
{
	@Autowired
	MedidaRiscoFasePcmatManagerImpl(MedidaRiscoFasePcmatDao medidaRiscoFasePcmatDao) {
			setDao(medidaRiscoFasePcmatDao);
	}
	
	public void deleteByRiscoFasePcmat(Long riscoFasePcmatId) 
	{
		getDao().deleteByRiscoFasePcmat(riscoFasePcmatId);
	}

	public Collection<MedidaRiscoFasePcmat> findByRiscoFasePcmat(Long riscoFasePcmatId) 
	{
		return getDao().findByRiscoFasePcmat(riscoFasePcmatId);
	}

	public void clonar(Long riscoFasePcmatOrigemId, Long riscoFasePcmatDestinoId) 
	{
		Collection<MedidaRiscoFasePcmat> medidaRiscoFasePcmats = getDao().findByRiscoFasePcmat(riscoFasePcmatOrigemId);
		
		for (MedidaRiscoFasePcmat medidaRiscoFasePcmatDestino : medidaRiscoFasePcmats) 
		{	
			medidaRiscoFasePcmatDestino.setId(null);
			medidaRiscoFasePcmatDestino.setRiscoFasePcmatId(riscoFasePcmatDestinoId);
			
			getDao().save(medidaRiscoFasePcmatDestino);
		}
	}

	public Map<Long, Collection<MedidaRiscoFasePcmat>> findByPcmatRiscos(Long pcmatId) 
	{
		Map<Long, Collection<MedidaRiscoFasePcmat>> riscosMedidas = new LinkedHashMap<Long, Collection<MedidaRiscoFasePcmat>>();
		Collection<MedidaRiscoFasePcmat> medidasRiscoFasePcmat = getDao().findByPcmat(pcmatId);
		
		for (MedidaRiscoFasePcmat medidaRiscoFasePcmat : medidasRiscoFasePcmat) 
		{
			if (!riscosMedidas.containsKey(medidaRiscoFasePcmat.getRiscoFasePcmat().getId()))
				riscosMedidas.put(medidaRiscoFasePcmat.getRiscoFasePcmat().getId(), new ArrayList<MedidaRiscoFasePcmat>());
			
			riscosMedidas.get(medidaRiscoFasePcmat.getRiscoFasePcmat().getId()).add(medidaRiscoFasePcmat);
		}
		
		return riscosMedidas;
	}
}

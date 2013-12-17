package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.rh.model.sesmt.MedidaRiscoFasePcmat;
import com.fortes.rh.model.sesmt.RiscoFasePcmat;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.sesmt.MedidaRiscoFasePcmatManager;
import com.fortes.rh.dao.sesmt.MedidaRiscoFasePcmatDao;

public class MedidaRiscoFasePcmatManagerImpl extends GenericManagerImpl<MedidaRiscoFasePcmat, MedidaRiscoFasePcmatDao> implements MedidaRiscoFasePcmatManager
{
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
}

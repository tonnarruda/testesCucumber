package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.RiscoFasePcmatDao;
import com.fortes.rh.model.sesmt.MedidaRiscoFasePcmat;
import com.fortes.rh.model.sesmt.RiscoFasePcmat;

public class RiscoFasePcmatManagerImpl extends GenericManagerImpl<RiscoFasePcmat, RiscoFasePcmatDao> implements RiscoFasePcmatManager
{
	private MedidaRiscoFasePcmatManager medidaRiscoFasePcmatManager;
	
	public Collection<RiscoFasePcmat> findByFasePcmat(Long fasePcmatId) 
	{
		return getDao().findByFasePcmat(fasePcmatId);
	}

	public void removeByFasePcmatRisco(Long fasePcmatId, Collection<Long> riscosIds) 
	{
		getDao().removeByFasePcmatRisco(fasePcmatId, riscosIds);
	}

	public void saveRiscosMedidas(RiscoFasePcmat riscoFasePcmat, Long[] medidasSegurancaIds) 
	{
		if (riscoFasePcmat.getId() == null)
			getDao().save(riscoFasePcmat);
		else
			getDao().update(riscoFasePcmat);
		
		medidaRiscoFasePcmatManager.deleteByRiscoFasePcmat(riscoFasePcmat.getId());
		
		MedidaRiscoFasePcmat medida;
		for (Long medidaSegurancaId : medidasSegurancaIds) 
		{
			medida = new MedidaRiscoFasePcmat();
			medida.setMedidaSegurancaId(medidaSegurancaId);
			medida.setRiscoFasePcmat(riscoFasePcmat);
			
			medidaRiscoFasePcmatManager.save(medida);
		}
	}

	public void setMedidaRiscoFasePcmatManager(MedidaRiscoFasePcmatManager medidaRiscoFasePcmatManager) {
		this.medidaRiscoFasePcmatManager = medidaRiscoFasePcmatManager;
	}

	public void clonar(Long fasePcmatOrigemId, Long fasePcmatDestinoId) 
	{
		Collection<RiscoFasePcmat> riscoFasePcmats = getDao().findByFasePcmat(fasePcmatOrigemId);
		
		Long riscoFasePcmatOrigemId;
		for (RiscoFasePcmat riscoFasePcmatDestino : riscoFasePcmats) {
			
			riscoFasePcmatOrigemId = riscoFasePcmatDestino.getId();
			
			riscoFasePcmatDestino.setId(null);
			riscoFasePcmatDestino.setFasePcmatId(fasePcmatDestinoId);
			save(riscoFasePcmatDestino);
			
			medidaRiscoFasePcmatManager.clonar(riscoFasePcmatOrigemId, riscoFasePcmatDestino.getId());
		}
	}

	public Collection<RiscoFasePcmat> findByPcmat(Long pcmatId) 
	{
		return getDao().findByPcmat(pcmatId);
	}
}

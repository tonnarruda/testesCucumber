package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.AtividadeSegurancaPcmatDao;
import com.fortes.rh.model.sesmt.AtividadeSegurancaPcmat;

public class AtividadeSegurancaPcmatManagerImpl extends GenericManagerImpl<AtividadeSegurancaPcmat, AtividadeSegurancaPcmatDao> implements AtividadeSegurancaPcmatManager
{
	public Collection<AtividadeSegurancaPcmat> findByPcmat(Long pcmatId) 
	{
		return getDao().findByPcmat(pcmatId);
	}

	public void clonar(Long pcmatOrigemId, Long pcmatDestinoId) 
	{
		Collection<AtividadeSegurancaPcmat> atividadesSegurancaPcmat = getDao().findByPcmat(pcmatOrigemId);
		for (AtividadeSegurancaPcmat atividadeSegurancaPcmat : atividadesSegurancaPcmat) 
		{
			atividadeSegurancaPcmat.setId(null);
			atividadeSegurancaPcmat.setPcmatId(pcmatDestinoId);
			save(atividadeSegurancaPcmat);
		}
	}
}

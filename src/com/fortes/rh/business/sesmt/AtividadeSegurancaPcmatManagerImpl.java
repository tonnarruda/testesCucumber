package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.rh.model.sesmt.AtividadeSegurancaPcmat;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.sesmt.AtividadeSegurancaPcmatManager;
import com.fortes.rh.dao.sesmt.AtividadeSegurancaPcmatDao;

public class AtividadeSegurancaPcmatManagerImpl extends GenericManagerImpl<AtividadeSegurancaPcmat, AtividadeSegurancaPcmatDao> implements AtividadeSegurancaPcmatManager
{
	public Collection<AtividadeSegurancaPcmat> findByPcmat(Long pcmatId) 
	{
		return getDao().findByPcmat(pcmatId);
	}
}

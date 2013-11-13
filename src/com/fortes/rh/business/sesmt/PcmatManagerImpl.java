package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.sesmt.PcmatManager;
import com.fortes.rh.dao.sesmt.PcmatDao;

public class PcmatManagerImpl extends GenericManagerImpl<Pcmat, PcmatDao> implements PcmatManager
{
	public Collection<Pcmat> findAllSelect(String nomeObra, Long empresaId) 
	{
		return getDao().findAllSelect(nomeObra, empresaId);
	}
}

package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.rh.model.sesmt.AreaVivencia;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.sesmt.AreaVivenciaManager;
import com.fortes.rh.dao.sesmt.AreaVivenciaDao;

public class AreaVivenciaManagerImpl extends GenericManagerImpl<AreaVivencia, AreaVivenciaDao> implements AreaVivenciaManager
{

	public Collection<AreaVivencia> findAllSelect(String nome, Long empresaId)
	{
		return getDao().findAllSelect(nome, empresaId);
	}

	public AreaVivencia findByIdProjection(Long id)
	{
		return getDao().findByIdProjection(id);
	}
}

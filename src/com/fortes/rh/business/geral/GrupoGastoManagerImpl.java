package com.fortes.rh.business.geral;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.GrupoGastoDao;
import com.fortes.rh.model.geral.GrupoGasto;

public class GrupoGastoManagerImpl extends GenericManagerImpl<GrupoGasto, GrupoGastoDao> implements GrupoGastoManager
{
	public GrupoGasto findByIdProjection(Long grupoGastoId)
	{
		return getDao().findByIdProjection(grupoGastoId);
	}
}
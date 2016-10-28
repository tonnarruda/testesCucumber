package com.fortes.rh.business.geral;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.GrupoGastoDao;
import com.fortes.rh.model.geral.GrupoGasto;

@Component
public class GrupoGastoManagerImpl extends GenericManagerImpl<GrupoGasto, GrupoGastoDao> implements GrupoGastoManager
{
	@Autowired
	GrupoGastoManagerImpl(GrupoGastoDao dao) {
		setDao(dao);
	}
	
	public GrupoGasto findByIdProjection(Long grupoGastoId)
	{
		return getDao().findByIdProjection(grupoGastoId);
	}
}
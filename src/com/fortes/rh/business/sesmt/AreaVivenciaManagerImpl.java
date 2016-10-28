package com.fortes.rh.business.sesmt;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.AreaVivenciaDao;
import com.fortes.rh.model.sesmt.AreaVivencia;

@Component
public class AreaVivenciaManagerImpl extends GenericManagerImpl<AreaVivencia, AreaVivenciaDao> implements AreaVivenciaManager
{
	@Autowired
	AreaVivenciaManagerImpl(AreaVivenciaDao fooDao) {
		setDao(fooDao);
	}

	public Collection<AreaVivencia> findAllSelect(String nome, Long empresaId)
	{
		return getDao().findAllSelect(nome, empresaId);
	}

	public AreaVivencia findByIdProjection(Long id)
	{
		return getDao().findByIdProjection(id);
	}
}

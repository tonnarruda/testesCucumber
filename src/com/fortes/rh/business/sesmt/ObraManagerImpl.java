package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.ObraDao;
import com.fortes.rh.model.sesmt.Obra;

public class ObraManagerImpl extends GenericManagerImpl<Obra, ObraDao> implements ObraManager
{
	public Collection<Obra> findAllSelect(String nome, Long empresaId) 
	{
		return getDao().findAllSelect(nome, empresaId);
	}

	public Obra findByIdProjection(Long id) 
	{
		return getDao().findByIdProjecion(id);
	}
}

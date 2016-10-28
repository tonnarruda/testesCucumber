package com.fortes.rh.business.sesmt;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.ObraDao;
import com.fortes.rh.model.sesmt.Obra;

@Component
public class ObraManagerImpl extends GenericManagerImpl<Obra, ObraDao> implements ObraManager
{
	@Autowired
	ObraManagerImpl(ObraDao obraDao) {
			setDao(obraDao);
	}
	
	public Collection<Obra> findAllSelect(String nome, Long empresaId) 
	{
		return getDao().findAllSelect(nome, empresaId);
	}

	public Obra findByIdProjection(Long id) 
	{
		return getDao().findByIdProjecion(id);
	}
}

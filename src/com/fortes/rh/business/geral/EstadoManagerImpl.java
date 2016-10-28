package com.fortes.rh.business.geral;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.EstadoDao;
import com.fortes.rh.model.geral.Estado;

@Component
public class EstadoManagerImpl extends GenericManagerImpl<Estado, EstadoDao> implements EstadoManager
{
	@Autowired
	EstadoManagerImpl(EstadoDao dao) {
		setDao(dao);
	}
	
	public Estado findBySigla(String sigla)
	{
		return getDao().findBySigla(sigla);
	}
}
package com.fortes.rh.business.geral;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.EstadoDao;
import com.fortes.rh.model.geral.Estado;

public class EstadoManagerImpl extends GenericManagerImpl<Estado, EstadoDao> implements EstadoManager
{

	public Estado findBySigla(String sigla)
	{
		return getDao().findBySigla(sigla);
	}
}
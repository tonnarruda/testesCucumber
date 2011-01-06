package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.rh.model.geral.CodigoCBO;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.CodigoCBOManager;
import com.fortes.rh.dao.geral.CodigoCBODao;

public class CodigoCBOManagerImpl extends GenericManagerImpl<CodigoCBO, CodigoCBODao> implements CodigoCBOManager
{
	public Collection<String> buscaCodigosCBO(String codigo)
	{
		return getDao().buscaCodigosCBO(codigo);
	}
	
}

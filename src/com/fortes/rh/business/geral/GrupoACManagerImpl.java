package com.fortes.rh.business.geral;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.GrupoACDao;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.ws.TGrupo;

public class GrupoACManagerImpl extends GenericManagerImpl<GrupoAC, GrupoACDao> implements GrupoACManager
{
	public TGrupo[] findTGrupos() 
	{
		return getDao().findTGrupos();
	}

	public GrupoAC findByCodigo(String codigo) 
	{
		return getDao().findByCodigo(codigo);
	}
}

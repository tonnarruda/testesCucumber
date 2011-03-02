package com.fortes.rh.business.geral;

import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.ws.TGrupo;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.GrupoACManager;
import com.fortes.rh.dao.geral.GrupoACDao;

public class GrupoACManagerImpl extends GenericManagerImpl<GrupoAC, GrupoACDao> implements GrupoACManager
{
	public TGrupo[] findTGrupos() 
	{
		return getDao().findTGrupos();
	}
}

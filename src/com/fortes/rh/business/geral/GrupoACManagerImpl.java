package com.fortes.rh.business.geral;

import org.apache.commons.lang.StringUtils;

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

	public GrupoAC updateGrupo(GrupoAC grupoAC) 
	{
		GrupoAC grupoACTemp = findById(grupoAC.getId());
		grupoAC.setCodigo(grupoACTemp.getCodigo());

		if(StringUtils.isBlank(grupoAC.getAcSenha()))
			grupoAC.setAcSenha(grupoACTemp.getAcSenha());

		update(grupoAC);
		return grupoAC;
	}
}

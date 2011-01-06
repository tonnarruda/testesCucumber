package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.rh.model.geral.CodigoCBO;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.CodigoCBOManager;
import com.fortes.rh.dao.geral.CodigoCBODao;

public class CodigoCBOManagerImpl extends GenericManagerImpl<CodigoCBO, CodigoCBODao> implements CodigoCBOManager
{
	public Collection<CodigoCBO> buscaCodigosCBO(String codigo, String descricao)
	{
		return getDao().buscaCodigosCBO(codigo, descricao);
	}
	public String findDescricaoByCodigo(String cboCodigo)
	{
		if (cboCodigo != null && !cboCodigo.equals(""))
		{
			String desc = getDao().findDescricaoByCodigo(cboCodigo);
			return desc != null ? desc : "";
		}
		
		return "";
	}
}

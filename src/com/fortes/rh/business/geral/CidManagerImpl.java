package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.CidDao;
import com.fortes.rh.model.geral.Cid;

public class CidManagerImpl extends GenericManagerImpl<Cid, CidDao> implements CidManager
{
	public Collection<Cid> buscaCids(String codigo, String descricao)
	{
		return getDao().buscaCids(codigo, descricao);
	}
	public String findDescricaoByCodigo(String codigo)
	{
		if (codigo != null && !codigo.equals(""))
		{
			String desc = getDao().findDescricaoByCodigo(codigo);
			return desc != null ? desc : "";
		}
		
		return "";
	}
}

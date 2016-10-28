package com.fortes.rh.business.geral;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.CidDao;
import com.fortes.rh.model.geral.Cid;

@Component
public class CidManagerImpl extends GenericManagerImpl<Cid, CidDao> implements CidManager
{
	@Autowired
	CidManagerImpl(CidDao dao) {
		setDao(dao);
	}
	
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

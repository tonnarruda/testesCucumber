package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.CodigoCBODao;
import com.fortes.rh.model.geral.AutoCompleteVO;
import com.fortes.rh.model.geral.CodigoCBO;

public class CodigoCBOManagerImpl extends GenericManagerImpl<CodigoCBO, CodigoCBODao> implements CodigoCBOManager
{
	public Collection<AutoCompleteVO> buscaCodigosCBO(String descricao)
	{
		return getDao().buscaCodigosCBO(descricao);
	}
	
	public String findDescricaoByCodigo(String cboCodigo)
	{
		if (cboCodigo != null && !cboCodigo.equals(""))
		{
			Collection<AutoCompleteVO> vos = getDao().buscaCodigosCBO(cboCodigo);
			if(!vos.isEmpty())
			{
				String desc = ((AutoCompleteVO)vos.toArray()[0]).getValue();
				return desc != null ? desc : "";
			}
		}
		
		return "";
	}
}

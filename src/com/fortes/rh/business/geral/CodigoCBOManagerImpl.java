package com.fortes.rh.business.geral;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.CodigoCBODao;
import com.fortes.rh.model.geral.AutoCompleteVO;
import com.fortes.rh.model.geral.CodigoCBO;

@Component
public class CodigoCBOManagerImpl extends GenericManagerImpl<CodigoCBO, CodigoCBODao> implements CodigoCBOManager
{
	@Autowired
	CodigoCBOManagerImpl(CodigoCBODao dao) {
		setDao(dao);
	}
	
	public Collection<AutoCompleteVO> buscaCodigosCBO(String descricao)
	{
		return getDao().buscaCodigosCBO(descricao);
	}
	
	public String findDescricaoByCodigo(String cboCodigo)
	{
		if (cboCodigo != null && !cboCodigo.equals(""))
		{
			return getDao().findDescricaoByCodigo(cboCodigo);
		}
		
		return "";
	}
}

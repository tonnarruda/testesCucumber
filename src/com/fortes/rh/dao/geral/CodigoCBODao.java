package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.AutoCompleteVO;
import com.fortes.rh.model.geral.CodigoCBO;

public interface CodigoCBODao extends GenericDao<CodigoCBO> 
{
	public Collection<AutoCompleteVO> buscaCodigosCBO(String descricao);
}

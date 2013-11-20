package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.Fase;

public interface FaseDao extends GenericDao<Fase> 
{
	Collection<Fase> findAllSelect(String descricao, Long empresaId);
}

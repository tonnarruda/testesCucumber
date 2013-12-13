package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.Obra;

public interface ObraDao extends GenericDao<Obra> 
{
	Collection<Obra> findAllSelect(String nome, Long empresaId);
	Obra findByIdProjecion(Long id);
}

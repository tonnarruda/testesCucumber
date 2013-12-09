package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.AreaVivencia;

public interface AreaVivenciaDao extends GenericDao<AreaVivencia> 
{
	Collection<AreaVivencia> findAllSelect(String nome, Long empresaId);
	AreaVivencia findByIdProjection(Long id);
}

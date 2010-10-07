package com.fortes.rh.dao.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.GrupoGasto;

public interface GrupoGastoDao extends GenericDao<GrupoGasto>
{
	GrupoGasto findByIdProjection(Long grupoGastoId);
}
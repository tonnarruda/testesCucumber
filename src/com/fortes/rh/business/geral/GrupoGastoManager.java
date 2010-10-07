package com.fortes.rh.business.geral;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.GrupoGasto;

public interface GrupoGastoManager extends GenericManager<GrupoGasto>
{

	GrupoGasto findByIdProjection(Long grupoGastoId);
}
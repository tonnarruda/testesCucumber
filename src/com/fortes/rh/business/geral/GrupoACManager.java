package com.fortes.rh.business.geral;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.ws.TGrupo;

public interface GrupoACManager extends GenericManager<GrupoAC>
{
	TGrupo[] findTGrupos();

	GrupoAC findByCodigo(String codigo);

	GrupoAC updateGrupo(GrupoAC grupoAC);
}

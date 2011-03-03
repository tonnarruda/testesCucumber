package com.fortes.rh.dao.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.ws.TGrupo;

public interface GrupoACDao extends GenericDao<GrupoAC> 
{
	TGrupo[] findTGrupos();

	GrupoAC findByCodigo(String codigo);
}

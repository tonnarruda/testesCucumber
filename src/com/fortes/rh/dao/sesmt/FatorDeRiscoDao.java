package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.FatorDeRisco;

public interface FatorDeRiscoDao extends GenericDao<FatorDeRisco>{
	
	public Collection<FatorDeRisco> findByGrupoRiscoESocial(String codigoGrupoRiscoESocial);

}

package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.FatorDeRisco;

public interface FatorDeRiscoManager extends GenericManager<FatorDeRisco> {

	public Collection<FatorDeRisco> findByGrupoRiscoESocial(String codigoGrupoRiscoESocial);
}

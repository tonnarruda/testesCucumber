package com.fortes.rh.web.dwr;

import java.util.Collection;

import com.fortes.rh.business.sesmt.FatorDeRiscoManager;
import com.fortes.rh.model.sesmt.FatorDeRisco;

public class FatorDeRiscoDWR {

	private FatorDeRiscoManager fatorDeRiscoManager;

	public Collection<FatorDeRisco> findByGrupoRiscoESocial(String codigoGrupoRiscoESocial){
		return fatorDeRiscoManager.findByGrupoRiscoESocial(codigoGrupoRiscoESocial);
	}
	
	public void setFatorDeRiscoManager(FatorDeRiscoManager fatorDeRiscoManager) {
		this.fatorDeRiscoManager = fatorDeRiscoManager;
	}
}

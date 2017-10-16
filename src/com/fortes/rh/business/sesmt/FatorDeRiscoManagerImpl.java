package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.FatorDeRiscoDao;
import com.fortes.rh.model.sesmt.FatorDeRisco;

public class FatorDeRiscoManagerImpl extends GenericManagerImpl<FatorDeRisco, FatorDeRiscoDao> implements FatorDeRiscoManager{

	public Collection<FatorDeRisco> findByGrupoRiscoESocial(String codigoGrupoRiscoESocial){
		return getDao().findByGrupoRiscoESocial(codigoGrupoRiscoESocial);
	}
}

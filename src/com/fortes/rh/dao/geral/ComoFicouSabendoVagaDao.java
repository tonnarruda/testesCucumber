package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;

public interface ComoFicouSabendoVagaDao extends GenericDao<ComoFicouSabendoVaga> 
{

	Collection<ComoFicouSabendoVaga> findAllSemOutros();

}

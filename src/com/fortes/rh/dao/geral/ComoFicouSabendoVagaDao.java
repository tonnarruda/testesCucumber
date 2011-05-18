package com.fortes.rh.dao.geral;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;

public interface ComoFicouSabendoVagaDao extends GenericDao<ComoFicouSabendoVaga> 
{
	Collection<ComoFicouSabendoVaga> findAllSemOutros();

	Collection<ComoFicouSabendoVaga> findCandidatosComoFicouSabendoVaga(Date dataIni, Date dataFim, Long empresaId);
}

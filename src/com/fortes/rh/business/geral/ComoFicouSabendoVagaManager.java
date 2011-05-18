package com.fortes.rh.business.geral;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;

public interface ComoFicouSabendoVagaManager extends GenericManager<ComoFicouSabendoVaga>
{

	Collection<ComoFicouSabendoVaga> findAllSemOutros();

	Collection<ComoFicouSabendoVaga> findCandidatosComoFicouSabendoVaga(Date dataIni, Date dataFim);
}

package com.fortes.rh.business.geral;

import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.geral.ComoFicouSabendoVaga;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.ComoFicouSabendoVagaManager;
import com.fortes.rh.dao.geral.ComoFicouSabendoVagaDao;

public class ComoFicouSabendoVagaManagerImpl extends GenericManagerImpl<ComoFicouSabendoVaga, ComoFicouSabendoVagaDao> implements ComoFicouSabendoVagaManager
{

	public Collection<ComoFicouSabendoVaga> findAllSemOutros() {
		
		return getDao().findAllSemOutros();
	}

	public Collection<ComoFicouSabendoVaga> findCandidatosComoFicouSabendoVaga(Date dataIni, Date dataFim, Long empresaId) {
		return getDao().findCandidatosComoFicouSabendoVaga(dataIni, dataFim, empresaId);
	}
}

package com.fortes.rh.business.geral;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.annotations.TesteAutomatico;
import com.fortes.rh.dao.geral.ComoFicouSabendoVagaDao;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;

public class ComoFicouSabendoVagaManagerImpl extends GenericManagerImpl<ComoFicouSabendoVaga, ComoFicouSabendoVagaDao> implements ComoFicouSabendoVagaManager
{

	@TesteAutomatico
	public Collection<ComoFicouSabendoVaga> findAllSemOutros() {
		
		return getDao().findAllSemOutros();
	}

	@TesteAutomatico
	public Collection<ComoFicouSabendoVaga> findCandidatosComoFicouSabendoVaga(Date dataIni, Date dataFim, Long empresaId) {
		return getDao().findCandidatosComoFicouSabendoVaga(dataIni, dataFim, empresaId);
	}
}

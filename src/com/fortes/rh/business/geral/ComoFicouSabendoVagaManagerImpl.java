package com.fortes.rh.business.geral;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.annotations.TesteAutomatico;
import com.fortes.rh.dao.geral.ComoFicouSabendoVagaDao;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;

@Component
public class ComoFicouSabendoVagaManagerImpl extends GenericManagerImpl<ComoFicouSabendoVaga, ComoFicouSabendoVagaDao> implements ComoFicouSabendoVagaManager
{
	@Autowired
	ComoFicouSabendoVagaManagerImpl(ComoFicouSabendoVagaDao dao) {
		setDao(dao);
	}

	@TesteAutomatico
	public Collection<ComoFicouSabendoVaga> findAllSemOutros() {
		
		return getDao().findAllSemOutros();
	}

	@TesteAutomatico
	public Collection<ComoFicouSabendoVaga> findCandidatosComoFicouSabendoVaga(Date dataIni, Date dataFim, Long empresaId) {
		return getDao().findCandidatosComoFicouSabendoVaga(dataIni, dataFim, empresaId);
	}
}

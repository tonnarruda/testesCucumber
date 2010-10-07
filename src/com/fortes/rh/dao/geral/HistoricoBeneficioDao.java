package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.HistoricoBeneficio;

public interface HistoricoBeneficioDao extends GenericDao<HistoricoBeneficio>
{

	HistoricoBeneficio findByHistoricoId(Long id);

	Collection<HistoricoBeneficio> getHistoricos();
}
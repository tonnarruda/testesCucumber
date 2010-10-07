package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.HistoricoBeneficio;

public interface HistoricoBeneficioManager extends GenericManager<HistoricoBeneficio>
{
	public HistoricoBeneficio findByHistoricoId(Long id);
	public Collection<HistoricoBeneficio> getHistoricos();
}
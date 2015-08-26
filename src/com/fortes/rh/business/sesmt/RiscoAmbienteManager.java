package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoAmbiente;

public interface RiscoAmbienteManager extends GenericManager<RiscoAmbiente>
{
	boolean removeByHistoricoAmbiente(Long historicoAmbienteId);
	Collection<Risco> findRiscosByAmbienteData(Long ambienteId, Date data);
	Collection<String> findColaboradoresSemAmbiente(Date data, Long estabelecimentoId);
	Collection<Long> findAmbienteAtualDosColaboradores(Date data, Long estabelecimentoId);
	Collection<RiscoAmbiente> findByAmbiente(Long ambienteId);
}

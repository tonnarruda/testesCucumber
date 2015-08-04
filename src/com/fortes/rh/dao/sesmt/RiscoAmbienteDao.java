package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoAmbiente;

public interface RiscoAmbienteDao extends GenericDao<RiscoAmbiente> 
{
	boolean removeByHistoricoAmbiente(Long historicoAmbienteId);
	Collection<Risco> findRiscosByAmbienteData(Long ambienteId, Date data);
	Collection<String> findColaboradoresSemAmbiente(Date data, Long estabelecimentoId);
	Collection<Long> findAmbienteAtualDosColaboradores(Date data, Long estabelecimentoId);
	Collection<RiscoAmbiente> findRiscoAmbienteByAmbiente(Long ambienteId);
}

package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
import com.fortes.rh.model.sesmt.RiscoFuncao;

public interface RiscoManager extends GenericManager<Risco>
{
	Collection<Risco> findAllSelect(Long empresaId);
	Collection<Epi> findEpisByRisco(Long riscoId);
	Collection<RiscoFuncao> findRiscosFuncoesByEmpresa(Long empresaId);
	Collection<RiscoAmbiente> findRiscosAmbientesByEmpresa(Long empresaId);
	void sincronizar(Long empresaOrigemId, Long empresaDestinoId, Map<Long,Long> epiIds);
	Collection<Risco> listRiscos(int page, int pagingSize, Risco risco) throws ColecaoVaziaException;
	Integer getCount(Risco risco);
}
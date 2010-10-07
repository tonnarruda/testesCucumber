package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.relatorio.PppFatorRisco;
import com.fortes.rh.model.sesmt.EpiHistorico;

public interface EpiHistoricoManager extends GenericManager<EpiHistorico>
{
	Collection<EpiHistorico> findByData(Date data, Long empresaId);

	Collection<EpiHistorico> getHistoricosEpi(PppFatorRisco fatorRisco);

	Collection<EpiHistorico> findByEpi(Long epiOrigemId);
	
	public void clonar(EpiHistorico epiHistorico, Long epiId);
}
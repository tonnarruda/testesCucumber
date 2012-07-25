package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.EpiHistorico;

public interface EpiHistoricoDao extends GenericDao<EpiHistorico>
{
	Collection<EpiHistorico> findByData(Date data, Long empresaId);

	Collection<EpiHistorico> getHistoricosEpi(Long id, Date dataInicio, Date dataFim);

	Collection<EpiHistorico> findByEpi(Long epiId);

	EpiHistorico findUltimoByEpiId(Long epiId);

	void removeByEpi(Long epiId);
}
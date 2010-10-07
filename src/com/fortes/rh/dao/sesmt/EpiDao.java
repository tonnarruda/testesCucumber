package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.Epi;

public interface EpiDao extends GenericDao<Epi>
{
	Epi findByIdProjection(Long epiId);
	Collection<Epi> findByVencimentoCa(Date data, Long empresaId);
	Collection<Epi> findEpisDoAmbiente(Long ambienteId, Date data);
	Collection<Epi> findByRiscoAmbiente(Long riscoId, Long ambienteId, Date data);
	Collection<Epi> findByRisco(Long riscoId);
	Collection<Epi> findByHistoricoFuncao(Long historicoFuncaoId);
	public Collection<Epi> findSincronizarEpiInteresse(Long empresaOrigemId);
}
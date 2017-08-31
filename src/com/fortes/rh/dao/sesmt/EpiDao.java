package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.Epi;

public interface EpiDao extends GenericDao<Epi>
{
	Integer getCount(Long empresaId, String epiNome, Boolean ativo);
	Collection<Epi> findEpis(int page, int pagingSize, Long empresaId, String epiNome, Boolean ativo);
	Epi findByIdProjection(Long epiId);
	Collection<Epi> findAllSelect(Long empresaId);
	Collection<Epi> findByVencimentoCa(Date data, Long empresaId, Long[] tipoEPIIds);
	Collection<Epi> findEpisDoAmbiente(Long ambienteId, Date data);
	Collection<Epi> findByRiscoAmbienteOuFuncao(Long riscoId, Long ambienteOuFuncaoId, Date data, boolean controlaRiscoPorAmbiente);
	Collection<Epi> findByRisco(Long riscoId);
	Collection<Epi> findByHistoricoFuncao(Long historicoFuncaoId);
	Collection<Epi> findSincronizarEpiInteresse(Long empresaOrigemId);
	Collection<String> findFabricantesDistinctByEmpresa(Long empresaId);
	Collection<Epi> findPriorizandoEpiRelacionado(Long empresaId, Long colaboradorId, boolean somenteAtivos);
	Epi findByCodigo(String codigo);
}
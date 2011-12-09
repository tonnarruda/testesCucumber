/* Autor: Robertson Freitas
 * Data: 16/06/2006
 * Requisito: RFA33 */
package com.fortes.rh.dao.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.Conhecimento;

public interface ConhecimentoDao extends GenericDao<Conhecimento>
{
	Collection<Conhecimento> findByAreaOrganizacionalIds(Long[] areasOrganizacionais, Long empresasId);
	Collection<Conhecimento> findByAreaInteresse(Long[] longs,Long empresaId);
	Collection<Conhecimento> findAllSelect(Long empresaId);
	Collection<Conhecimento> findByCargo(Long cargoId);
	Conhecimento findByIdProjection(Long conhecimentoId);
	Collection<Conhecimento> findAllSelectDistinctNome();
	Collection<Conhecimento> findByCandidato(Long candidatoId);
	Collection<Conhecimento> findSincronizarConhecimentos(Long empresaOrigemId);
	void deleteByAreaOrganizacional(Long[] areaIds) throws Exception;
}
/* Autor: Robertson Freitas
 * Data: 16/06/2006
 * Requisito: RFA33 */
package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.web.tags.CheckBox;

public interface ConhecimentoManager extends GenericManager<Conhecimento>
{
	public Collection<Conhecimento> findByAreaInteresse(Long[] longs,Long empresaId);
	public Collection<Conhecimento> findByAreasOrganizacionalIds(Long[] areasOrganizacionais, Long empresaId);
	public Collection<Conhecimento> findAllSelect(Long empresaId);
	public Collection<Conhecimento> findByCargo(Long cargoId);
	Collection<CheckBox> populaCheckOrderNome(long empresaId);
	Collection<CheckBox> populaCheckOrderNomeByAreaOrganizacionals(Long[] areasId, long empresaId);
	Collection<Conhecimento> populaConhecimentos(String[] conhecimentosCheck);
	public Conhecimento findByIdProjection(Long conhecimentoId);
	public Collection<Conhecimento> findAllSelectDistinctNome();
	public Collection<Conhecimento> findByCandidato(Long candidatoId);
	public void sincronizar(Long empresaOrigemId, Long empresaDestinoId, Map<Long, Long> areaIds, Map<Long, Long> conhecimentoIds);
}
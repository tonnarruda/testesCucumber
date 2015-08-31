/* Autor: Robertson Freitas
 * Data: 19/06/2006
 * Requisito: RFA0023 */
package com.fortes.rh.dao.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.EtapaSeletiva;

public interface EtapaSeletivaDao extends GenericDao<EtapaSeletiva>
{
	EtapaSeletiva findPrimeiraEtapa(Long empresaId);

	void ordeneCrescentementeEntre(int ordemOriginal, int novaOrdem, EtapaSeletiva etapaSeletiva);

	void ordeneDecrescentementeEntre(int ordemOriginal, int novaOrdem, EtapaSeletiva etapaSeletiva);

	void ordeneDecrescentementeApartirDe(int ordem, EtapaSeletiva etapaSeletiva);

	void ordeneCrescentementeAPartirDe(int ordem, EtapaSeletiva etapaSeletiva);

	Collection<EtapaSeletiva> findAllSelect(int page, int pagingSize, Long empresaId);

	Collection<EtapaSeletiva> findByIdProjection(Long empresaId, Long[] etapaIds);

	EtapaSeletiva findByEtapaSeletivaId(Long etapaSeletivaId, Long empresaId);

	Integer getCount(Long empresaId);

	EtapaSeletiva findByIdProjection(Long etapaSeletivaId);
	
	public Collection<EtapaSeletiva> findByCargo(Long cargoId);
	
	public void deleteVinculoComCargo(Long etapaSeletivaId);
}
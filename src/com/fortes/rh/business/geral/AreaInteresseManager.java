/* autor: Moesio Medeiros
 * Data: 06/06/2006
 * Requisito: RFA014 - Cadastro de Área de Interesse
 */

package com.fortes.rh.business.geral;

import java.util.Collection;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.AreaOrganizacional;

public interface AreaInteresseManager extends GenericManager<AreaInteresse>
{
	public Collection<AreaInteresse> findAreasInteresseByAreaOrganizacional(AreaOrganizacional areaOrganizacional);
	public Collection<AreaInteresse> findAllSelect(Long... empresaIds);
	public AreaInteresse findByIdProjection(Long areaInteresseId);
	public void sincronizar(Long empresaOrigemId, Long empresaDestinoId, Map<Long, Long> areaIds, Map<Long, Long> areaInteresseIds);
	public void deleteByAreaOrganizacional(Long[] areaIds) throws Exception;
}
/*
 * autor: Moesio Medeiros
 * Data: 06/06/2006
 * Requisito: RFA014
 */

package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.AreaOrganizacional;

public interface AreaInteresseDao extends GenericDao<AreaInteresse>
{
	public Collection<AreaInteresse> findAreasInteresseByAreaOrganizacional(AreaOrganizacional areaOrganizacional);
	public Collection<AreaInteresse> findAllSelect(Long[] empresaIds);
	public AreaInteresse findByIdProjection(Long areaInteresseId);
	public Collection<AreaInteresse> findSincronizarAreasInteresse(Long empresaOrigemId);
	public void deleteByAreaOrganizacional(Long[] areaIds) throws Exception;
}
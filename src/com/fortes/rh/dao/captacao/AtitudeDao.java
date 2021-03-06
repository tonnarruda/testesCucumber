package com.fortes.rh.dao.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.Atitude;

public interface AtitudeDao extends GenericDao<Atitude> 
{
	public Collection<Atitude> findAllSelect(Long empresaId);
	public Collection<Atitude> findByCargo(Long cargoId);
	public Collection<Atitude> findByAreasOrganizacionalIds(Long[] areaOrganizacionalIds, Long empresasId);
	public Collection<Atitude> findSincronizarAtitudes(Long empresaOrigemId);
	public void deleteByAreaOrganizacional(Long[] areaIds) throws Exception;
	public Atitude findByIdProjection(Long atitudeId);
}

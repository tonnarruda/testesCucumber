package com.fortes.rh.dao.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.Habilidade;

public interface HabilidadeDao extends GenericDao<Habilidade> 
{
	public Collection<Habilidade> findAllSelect(Long empresaId);
	public Collection<Habilidade> findByCargo(Long cargoId);
	public Collection<Habilidade> findByAreasOrganizacionalIds(Long[] areaOrganizacionalIds, Long empresasId);
	public Collection<Habilidade> findSincronizarHabilidades(Long empresaOrigemId);
}

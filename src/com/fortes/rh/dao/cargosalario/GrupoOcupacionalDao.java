package com.fortes.rh.dao.cargosalario;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;

/**
 * @author Francisco Barroso
 */
public interface GrupoOcupacionalDao extends GenericDao<GrupoOcupacional>
{
	Integer getCount(Long empresaId);

	Collection<GrupoOcupacional> findAllSelect(int page, int pagingSize, Long empresaId);

	GrupoOcupacional findByIdProjection(Long grupoOcupacionalId);

	Collection<GrupoOcupacional> findByEmpresasIds(Long... empresaIds);
}
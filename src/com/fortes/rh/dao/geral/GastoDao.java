package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.Gasto;

public interface GastoDao extends GenericDao<Gasto>
{

	Collection<Gasto> getGastosSemGrupo(Long empresaId);

	Collection<Gasto> findGastosDoGrupo(Long id);

	Collection<Gasto> findByEmpresa(Long empresaId);

	void updateGrupoGastoByGastos(Long grupoGastoId, Long[] gastosIds);

	Gasto findByIdProjection(Long gastoId);

	Collection<Gasto> findSemCodigoAC(Long empresaId);
}
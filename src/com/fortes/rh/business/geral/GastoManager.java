package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Gasto;
import com.fortes.rh.model.geral.GrupoGasto;

public interface GastoManager extends GenericManager<Gasto>
{

	Collection<Gasto> getGastosSemGrupo(Long empresaId);

	void agrupar(GrupoGasto grupoGastoAgrupar, String[] gastosCheck) throws Exception;

	Collection<Gasto> findGastosDoGrupo(Long id);

	public Collection<Gasto> findByEmpresa(Long empresaId);

	Gasto findByIdProjection(Long gastoId);

	Collection<Gasto> findSemCodigoAC(Long empresaId);
}
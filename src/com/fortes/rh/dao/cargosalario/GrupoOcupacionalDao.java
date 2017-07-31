package com.fortes.rh.dao.cargosalario;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;

public interface GrupoOcupacionalDao extends GenericDao<GrupoOcupacional>
{
	Integer getCount(Long empresaId);
	Collection<GrupoOcupacional> findAllSelect(int page, int pagingSize, Long empresaId);
	GrupoOcupacional findByIdProjection(Long grupoOcupacionalId);
	Collection<GrupoOcupacional> findByEmpresasIds(Long... empresaIds);
	Collection<GrupoOcupacional> findAllSelectByAreasResponsavelCoresponsavel(Long empresaId, Long[] areasIds);
    Collection<GrupoOcupacional> findGruposUsadosPorCargosByEmpresaId(Long empresaId);
    void deletarGruposInseridosENaoUtilizadosAposImportarCadastroEntreEmpresas(Long[] gruposOcupacionaisIds, Long empresaId);
}
package com.fortes.rh.business.cargosalario;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.web.tags.CheckBox;

public interface GrupoOcupacionalManager extends GenericManager<GrupoOcupacional>
{
	Integer getCount(Long empresaId);
	Collection<GrupoOcupacional> findAllSelect(Long empresaId);
	Collection<GrupoOcupacional> findAllSelect(int page, int pagingSize, Long empresaId);
	Collection<CheckBox> populaCheckOrderNome(Long empresaId);
	GrupoOcupacional findByIdProjection(Long grupoOcupacionalId);
	Collection<GrupoOcupacional> findByEmpresasIds(Long... empresaIds);
	Collection<CheckBox> populaCheckByAreasResponsavelCoresponsavel(Long empresaId, Long[] areasIds);
	Collection<GrupoOcupacional> findAllSelectByAreasResponsavelCoresponsavel(Long empresaId, Long[] areasIds);
}
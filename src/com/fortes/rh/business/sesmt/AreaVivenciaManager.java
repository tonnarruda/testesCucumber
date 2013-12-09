package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.AreaVivencia;

public interface AreaVivenciaManager extends GenericManager<AreaVivencia>
{
	public Collection<AreaVivencia> findAllSelect(String nome, Long empresaId);
	public AreaVivencia findByIdProjection(Long id); 
}

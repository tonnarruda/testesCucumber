package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.Fase;

public interface FaseManager extends GenericManager<Fase>
{
	Collection<Fase> findAllSelect(String descricao, Long empresaId);
}

package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;

public interface ConfiguracaoLimiteColaboradorManager extends GenericManager<ConfiguracaoLimiteColaborador>
{
	Collection<ConfiguracaoLimiteColaborador> findAllSelect(Long empresaId);
	Collection<Long> findIdAreas(Long empresaId);
}

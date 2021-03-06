package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;

public interface ConfiguracaoLimiteColaboradorManager extends GenericManager<ConfiguracaoLimiteColaborador>
{
	Collection<ConfiguracaoLimiteColaborador> findAllSelect(Long empresaId);
	Collection<Long> findIdAreas(Long empresaId);
	void enviaEmail(ConfiguracaoLimiteColaborador configuracaoLimiteColaborador, Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos, Empresa empresaSistema);
	void deleteByAreaOrganizacional(Long[] areaIds) throws Exception;
}

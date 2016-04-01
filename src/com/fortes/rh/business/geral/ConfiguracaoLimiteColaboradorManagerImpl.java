package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.annotations.TesteAutomatico;
import com.fortes.rh.dao.geral.ConfiguracaoLimiteColaboradorDao;
import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;

public class ConfiguracaoLimiteColaboradorManagerImpl extends GenericManagerImpl<ConfiguracaoLimiteColaborador, ConfiguracaoLimiteColaboradorDao> implements ConfiguracaoLimiteColaboradorManager
{
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	
	@TesteAutomatico
	public Collection<ConfiguracaoLimiteColaborador> findAllSelect(Long empresaId) 
	{
		return getDao().findAllSelect(empresaId);
	}

	@TesteAutomatico
	public Collection<Long> findIdAreas(Long empresaId) {
		return getDao().findIdAreas(empresaId);
	}

	public void enviaEmail(ConfiguracaoLimiteColaborador configuracaoLimiteColaborador, Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos, Empresa empresa) 
	{
		gerenciadorComunicacaoManager.enviaEmailConfiguracaoLimiteColaborador(configuracaoLimiteColaborador, quantidadeLimiteColaboradoresPorCargos, empresa);
	}

	@TesteAutomatico
	public void deleteByAreaOrganizacional(Long[] areaIds) throws Exception {
		getDao().deleteByAreaOrganizacional(areaIds);
	}

	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}
}

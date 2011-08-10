package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaColaboradorDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;

public class ConfiguracaoNivelCompetenciaColaboradorManagerImpl extends GenericManagerImpl<ConfiguracaoNivelCompetenciaColaborador, ConfiguracaoNivelCompetenciaColaboradorDao> implements ConfiguracaoNivelCompetenciaColaboradorManager
{
	public ConfiguracaoNivelCompetenciaColaborador findByIdProjection(Long configuracaoNivelCompetenciaColaboradorId) 
	{
		return getDao().findByIdProjection(configuracaoNivelCompetenciaColaboradorId);
	}

	public Collection<ConfiguracaoNivelCompetenciaColaborador> findByColaborador(Long colaboradorId) 
	{
		return getDao().findByColaborador(colaboradorId);
	}

	public void checarHistoricoMesmaData(ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador) throws Exception 
	{
		ConfiguracaoNivelCompetenciaColaborador configMesmaData = getDao().checarHistoricoMesmaData(configuracaoNivelCompetenciaColaborador);
		if (configMesmaData != null && configMesmaData.getId() != null)
			throw new Exception("Já existe uma configuração para este Colaborador na data informada.");
	}
}

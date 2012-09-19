package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaColaboradorDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.geral.Colaborador;

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
			throw new Exception("Já existe uma configuração de competências para este Colaborador na data informada.");
	}

	public void removeColaborador(Colaborador colaborador) {
		getDao().removeColaborador(colaborador);
	}

	public void deleteByFaixaSalarial(Long[] faixaIds) throws Exception {
		getDao().deleteByFaixaSalarial(faixaIds);
	}
}

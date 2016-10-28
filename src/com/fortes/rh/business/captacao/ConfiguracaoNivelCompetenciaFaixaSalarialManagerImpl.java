package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.util.SpringUtil;

@Component
public class ConfiguracaoNivelCompetenciaFaixaSalarialManagerImpl extends GenericManagerImpl<ConfiguracaoNivelCompetenciaFaixaSalarial, ConfiguracaoNivelCompetenciaFaixaSalarialDao> implements ConfiguracaoNivelCompetenciaFaixaSalarialManager
{
	@Autowired
	ConfiguracaoNivelCompetenciaFaixaSalarialManagerImpl(ConfiguracaoNivelCompetenciaFaixaSalarialDao configuracaoNivelCompetenciaFaixaSalarialDao) {
		setDao(configuracaoNivelCompetenciaFaixaSalarialDao);
	}
	
	public void deleteByFaixaSalarial(Long[] faixaIds)
	{
		deleteDependenciasByFaixaSalarial(faixaIds);
		getDao().deleteByFaixaSalarial(faixaIds);
	}
	
	public void deleteDependenciasByFaixaSalarial(Long[] faixaIds)
	{
		ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager = (ConfiguracaoNivelCompetenciaManager) SpringUtil.getBean("configuracaoNivelCompetenciaManager");
		configuracaoNivelCompetenciaManager.removeDependenciasComConfiguracaoNivelCompetenciaFaixaSalarialByFaixaSalarial(faixaIds);
	}

	public Collection<ConfiguracaoNivelCompetenciaFaixaSalarial> findProximasConfiguracoesAposData(Long faixaSalarialId, Date data)
	{
		return getDao().findProximasConfiguracoesAposData(faixaSalarialId, data);
	}

	public boolean existByNivelCompetenciaHistoricoId(Long nivelCompetenciaHistoricoId) 
	{
		return getDao().existByNivelCompetenciaHistoricoId(nivelCompetenciaHistoricoId);
	}

	public ConfiguracaoNivelCompetenciaFaixaSalarial findByFaixaSalarialIdAndData(Long faixaSalarialId, Date data) {
		return getDao().findByFaixaSalarialIdAndData(faixaSalarialId, data);
	}

	public ConfiguracaoNivelCompetenciaFaixaSalarial findByProjection( Long configuracaoNivelCompetenciaFaixaSalarialId) {
		return getDao().findByProjection(configuracaoNivelCompetenciaFaixaSalarialId);
	}
}

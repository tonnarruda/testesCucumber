package com.fortes.rh.business.captacao;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.util.SpringUtil;

public class ConfiguracaoNivelCompetenciaFaixaSalarialManagerImpl extends GenericManagerImpl<ConfiguracaoNivelCompetenciaFaixaSalarial, ConfiguracaoNivelCompetenciaFaixaSalarialDao> implements ConfiguracaoNivelCompetenciaFaixaSalarialManager
{
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
}

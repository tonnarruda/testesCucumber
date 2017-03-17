/* Autor: Robertson Freitas
 * Data: 16/06/2006
 * Requisito: RFA33 */
package com.fortes.rh.business.captacao;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaCriterioDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCriterio;

@Component
public class ConfiguracaoNivelCompetenciaCriterioManagerImpl extends GenericManagerImpl<ConfiguracaoNivelCompetenciaCriterio, ConfiguracaoNivelCompetenciaCriterioDao> implements ConfiguracaoNivelCompetenciaCriterioManager
{
	@Autowired
	public ConfiguracaoNivelCompetenciaCriterioManagerImpl(ConfiguracaoNivelCompetenciaCriterioDao configuracaoNivelCompetenciaCriterioDao) {
		setDao(configuracaoNivelCompetenciaCriterioDao);
	}
	
	public Collection<ConfiguracaoNivelCompetenciaCriterio> findByConfiguracaoNivelCompetencia(Long configuracaoNivelCompetenciaId, Long configuracaoNivelCompetenciaFaixaSalarialId)
	{
		return getDao().findByConfiguracaoNivelCompetencia(configuracaoNivelCompetenciaId, configuracaoNivelCompetenciaFaixaSalarialId);
	}
	
	public void removeByConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelCompetenciaColaboradorId) {
		getDao().removeByConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaboradorId);
	}
}
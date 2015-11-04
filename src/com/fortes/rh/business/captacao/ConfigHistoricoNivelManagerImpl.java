package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.ConfigHistoricoNivelDao;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;

public class ConfigHistoricoNivelManagerImpl extends GenericManagerImpl<ConfigHistoricoNivel, ConfigHistoricoNivelDao> implements ConfigHistoricoNivelManager
{
	public Collection<ConfigHistoricoNivel> findByNivelCompetenciaHistoricoId(Long nivelCompetenciaHistoricoId) {
		return getDao().findByNivelCompetenciaHistoricoId(nivelCompetenciaHistoricoId);
	}
}

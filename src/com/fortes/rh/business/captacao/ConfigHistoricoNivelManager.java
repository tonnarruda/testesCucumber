package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;

public interface ConfigHistoricoNivelManager extends GenericManager<ConfigHistoricoNivel>
{
	Collection<ConfigHistoricoNivel> findByNivelCompetenciaHistoricoId(Long nivelCompetenciaHistoricoId);
	Collection<ConfigHistoricoNivel> findNiveisCompetenciaByEmpresa(Long empresaId);
}

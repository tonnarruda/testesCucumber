package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;

public interface ConfigHistoricoNivelManager extends GenericManager<ConfigHistoricoNivel>
{
	Collection<ConfigHistoricoNivel> findByNivelCompetenciaHistoricoId(Long nivelCompetenciaHistoricoId);
	Collection<ConfigHistoricoNivel> findNiveisCompetenciaByEmpresa(Long empresaId);
	public void removeByNivelConfiguracaoHistorico(Long nivelConfiguracaoHIstoricoId);
	public void removeNotIds(Long[] configHistoricoNiveis, Long nivelConfiguracaoHIstoricoId);
	public Collection<ConfigHistoricoNivel> findByEmpresaAndDataNivelCompetenciaHistorico(Long empresaId, Date dataNivelCompetenciaHistorico);
	
}

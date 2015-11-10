package com.fortes.rh.business.captacao;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;

public interface NivelCompetenciaHistoricoManager extends GenericManager<NivelCompetenciaHistorico>
{
	public void removeNivelConfiguracaoHistorico(Long id) throws Exception;
		
}

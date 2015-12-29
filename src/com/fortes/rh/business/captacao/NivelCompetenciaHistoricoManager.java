package com.fortes.rh.business.captacao;

import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;

public interface NivelCompetenciaHistoricoManager extends GenericManager<NivelCompetenciaHistorico>
{
	public void removeNivelConfiguracaoHistorico(Long id) throws Exception;
	public Long findByData(Date date, Long empresaId);
	public void updateNivelConfiguracaoHistorico(NivelCompetenciaHistorico nivelCompetenciaHistorico);
	public NivelCompetenciaHistorico save(NivelCompetenciaHistorico nivelCompetenciaHistorico);
}

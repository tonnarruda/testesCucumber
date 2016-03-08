package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetencia;

public interface NivelCompetenciaManager extends GenericManager<NivelCompetencia>
{
	Collection<NivelCompetencia> findAllSelect(Long empresaId, Long nivelCompetenciaHistoricoId, Date data);
	
	Collection<NivelCompetencia> findAllSelect(Long empresaId);

	void validaLimite(Long empresaId) throws Exception;

	Collection<ConfiguracaoNivelCompetencia> findByCargoOrEmpresa(Long id, Long empresaId);

	Double getPontuacaoObtidaByConfiguracoesNiveisCompetencia(Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaMarcados);

	int getOrdemMaxima(Long empresaId, Date data);

	boolean existePercentual(Long nivelCompetenciaId, Long empresaId, Double percentual);

	boolean existeNivelCompetenciaSemPercentual(Long empresaId);
	
	Double getOrdemMaximaByNivelCompetenciaHistoricoId(Long nivelCompetenciaHistoricoId);
}

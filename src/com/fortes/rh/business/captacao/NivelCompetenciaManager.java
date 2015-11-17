package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetencia;

public interface NivelCompetenciaManager extends GenericManager<NivelCompetencia>
{
	Collection<NivelCompetencia> findAllSelect(Long empresaId, Long nivelCompetenciaHistoricoId, Date data);

	void validaLimite(Long empresaId) throws Exception;

	Collection<ConfiguracaoNivelCompetencia> findByCargoOrEmpresa(Long id, Long empresaId);

	Integer getPontuacaoObtidaByConfiguracoesNiveisCompetencia(Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaMarcados);

	int getOrdemMaxima(Long empresaId);

	boolean existePercentual(Long nivelCompetenciaId, Long empresaId, Double percentual);

	boolean existeNivelCompetenciaSemPercentual(Long empresaId);

	void gerarPercentualIgualmente(Long empresaId);
}

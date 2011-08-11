package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;

public interface ConfiguracaoNivelCompetenciaManager extends GenericManager<ConfiguracaoNivelCompetencia>
{
	Collection<ConfiguracaoNivelCompetencia> findByFaixa(Long faixaSalarialId);
	
	void saveCompetencias(Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais, Long faixaSalarialId, Long candidatoId);

	Collection<ConfiguracaoNivelCompetencia> findByCandidato(Long candidato);

	Collection<ConfiguracaoNivelCompetencia> getCompetenciasCandidato(Long candidatoId, Long empresaId);

	void saveCompetenciasColaborador(Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais, ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador);

	Collection<ConfiguracaoNivelCompetencia> findByColaborador(Long configuracaoNivelCompetenciaColaboradorId);

	Collection<ConfiguracaoNivelCompetencia> findCompetenciaByFaixaSalarial(Long faixaId);

	Collection<ConfiguracaoNivelCompetencia> findCompetenciaColaborador(Long[] competenciasIds);

	Collection<ConfiguracaoNivelCompetencia> findColaboradorAbaixoNivel(Long[] competenciasIds);
}

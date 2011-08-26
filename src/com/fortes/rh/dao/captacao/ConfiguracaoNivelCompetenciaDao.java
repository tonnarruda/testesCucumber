package com.fortes.rh.dao.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;

public interface ConfiguracaoNivelCompetenciaDao extends GenericDao<ConfiguracaoNivelCompetencia> 
{
	void deleteConfiguracaoByFaixa(Long faixaSalarialId);
	void deleteConfiguracaoByCandidatoFaixa(Long candidatoId, Long faixaSalarialId);

	Collection<ConfiguracaoNivelCompetencia> findByFaixa(Long faixaSalarialId);

	Collection<ConfiguracaoNivelCompetencia> findByCandidato(Long candidatoId);
	Collection<ConfiguracaoNivelCompetencia> findByColaborador(Long configuracaoNivelCompetenciaColaboradorId);
	void deleteByConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelCompetenciaColaboradorId);
	Collection<ConfiguracaoNivelCompetencia> findCompetenciaByFaixaSalarial(Long faixaId);
	Collection<ConfiguracaoNivelCompetencia> findCompetenciaColaborador(Long[] competenciasIds, Long faixaSalarialColaboradorId, boolean ordenarPorNivel);
	Collection<ConfiguracaoNivelCompetencia> findCompetenciaCandidato(Long faixaSalarialId);
	void removeByFaixas(Long[] faixaSalarialIds);
}

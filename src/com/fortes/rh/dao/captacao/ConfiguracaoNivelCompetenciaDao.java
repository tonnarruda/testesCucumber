package com.fortes.rh.dao.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.geral.Colaborador;

public interface ConfiguracaoNivelCompetenciaDao extends GenericDao<ConfiguracaoNivelCompetencia> 
{
	void deleteConfiguracaoByFaixa(Long faixaSalarialId);
	void deleteConfiguracaoByCandidatoFaixa(Long candidatoId, Long faixaSalarialId);

	Collection<ConfiguracaoNivelCompetencia> findByFaixa(Long faixaSalarialId);

	Collection<ConfiguracaoNivelCompetencia> findByCandidato(Long candidatoId);
	Collection<ConfiguracaoNivelCompetencia> findByConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelCompetenciaColaboradorId);
	void deleteByConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelCompetenciaColaboradorId);
	Collection<ConfiguracaoNivelCompetencia> findCompetenciaByFaixaSalarial(Long faixaId);
	Collection<ConfiguracaoNivelCompetencia> findCompetenciaColaborador(Long[] competenciasIds, Long faixaSalarialColaboradorId, boolean ordenarPorNivel);
	Collection<ConfiguracaoNivelCompetencia> findCompetenciaCandidato(Long faixaSalarialId, Collection<Long> candidatosIds);
	void removeByFaixas(Long[] faixaSalarialIds);
	void removeColaborador(Colaborador colaborador);
	void removeByConfiguracaoNivelColaborador(Long configuracaoNivelColaboradorId);
	void removeByCandidato(Long candidatoId);
	Long[] findCompetenciasIdsConfiguradasByFaixaSolicitacao(Long faixaSalarialId);
	Integer somaConfiguracoesByFaixa(Long faixaSalarialId);
	Collection<ConfiguracaoNivelCompetencia> findByColaborador(Long colaboradorId);
	Collection<ConfiguracaoNivelCompetencia> findColaboradoresCompetenciasAbaixoDoNivel(Long empresaId, Long[] areaIds, Long[] estabelecimentoIds);
}

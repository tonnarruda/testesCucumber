package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaVO;
import com.fortes.rh.model.geral.Colaborador;

public interface ConfiguracaoNivelCompetenciaManager extends GenericManager<ConfiguracaoNivelCompetencia>
{
	Collection<ConfiguracaoNivelCompetencia> findByFaixa(Long faixaSalarialId);
	
	void saveCompetencias(Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais, Long faixaSalarialId, Long candidatoId);

	Collection<ConfiguracaoNivelCompetencia> findByCandidato(Long candidato);

	Collection<ConfiguracaoNivelCompetencia> getCompetenciasCandidato(Long candidatoId, Long empresaId);

	void saveCompetenciasColaborador(Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais, ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador);

	Collection<ConfiguracaoNivelCompetencia> findByConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelCompetenciaColaboradorId);

	Collection<ConfiguracaoNivelCompetencia> findCompetenciaByFaixaSalarial(Long faixaId);

	Collection<ConfiguracaoNivelCompetencia> findColaboradorAbaixoNivel(Long[] competenciasIds, Long faixaSalarialId);

	Collection<ConfiguracaoNivelCompetenciaVO> montaRelatorioConfiguracaoNivelCompetencia(Long empresaId, Long faixaSalarialId, Long[] competenciasIds);

	Collection<ConfiguracaoNivelCompetenciaVO> montaMatrizCompetenciaCandidato(Long empresaId, Long faixaSalarialId, Long solicitacaoId);

	void removeByFaixas(Long[] faixaSalarialIds);

	void removeColaborador(Colaborador colaborador);

	void removeConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelColaboradorId);

	void removeByCandidato(Long candidatoId);
}

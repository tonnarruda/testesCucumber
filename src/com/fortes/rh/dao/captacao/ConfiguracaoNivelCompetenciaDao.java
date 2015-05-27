package com.fortes.rh.dao.captacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.geral.Colaborador;

public interface ConfiguracaoNivelCompetenciaDao extends GenericDao<ConfiguracaoNivelCompetencia> 
{
	void deleteConfiguracaoByFaixa(Long faixaSalarialId);
	void deleteConfiguracaoByCandidatoFaixa(Long candidatoId, Long faixaSalarialId);

	Collection<ConfiguracaoNivelCompetencia> findByFaixa(Long faixaSalarialId, Date data);

	Collection<ConfiguracaoNivelCompetencia> findByCandidato(Long candidatoId);
	Collection<ConfiguracaoNivelCompetencia> findByConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelCompetenciaColaboradorId);
	void deleteConfiguracaoNivelCompetenciaByConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelCompetenciaColaboradorId);
	void deleteConfiguracaoNivelCompetenciaByConfiguracaoNivelCompetenciaFaixaSalarial(Long configuracaoNivelCompetenciaFaixaSalarialId);
	Collection<ConfiguracaoNivelCompetencia> findCompetenciaByFaixaSalarial(Long faixaId, Date data);
	Collection<ConfiguracaoNivelCompetencia> findCompetenciaColaborador(Date dataIni, Date dataFim, Long[] competenciasIds, Long faixaSalarialColaboradorId, boolean ordenarPorNivel);
	Collection<ConfiguracaoNivelCompetencia> findCompetenciaCandidato(Long faixaSalarialId, Collection<Long> candidatosIds);
	void removeByFaixas(Long[] faixaSalarialIds);
	void removeColaborador(Colaborador colaborador);
	void removeByConfiguracaoNivelColaborador(Long configuracaoNivelColaboradorId);
	void removeByConfiguracaoNivelFaixaSalarial(Long configuracaoNivelFaixaSalarialId);
	void removeByCandidato(Long candidatoId);
	Long[] findCompetenciasIdsConfiguradasByFaixaSolicitacao(Long faixaSalarialId);
	Integer somaConfiguracoesByFaixa(Long faixaSalarialId);
	Collection<ConfiguracaoNivelCompetencia> findByColaborador(Long colaboradorId, Long avaliadorId, Long colaboradorQuestionarioId);
	Collection<ConfiguracaoNivelCompetencia> findColaboradoresCompetenciasAbaixoDoNivel(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Boolean colaboradoresAvaliados, char agruparPor);
}

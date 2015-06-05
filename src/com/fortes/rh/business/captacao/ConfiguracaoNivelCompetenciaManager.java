package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaVO;
import com.fortes.rh.model.captacao.MatrizCompetenciaNivelConfiguracao;
import com.fortes.rh.model.geral.Colaborador;

public interface ConfiguracaoNivelCompetenciaManager extends GenericManager<ConfiguracaoNivelCompetencia>
{
	Collection<ConfiguracaoNivelCompetencia> findByFaixa(Long faixaSalarialId, Date data);
	void saveCompetenciasCandidato(Collection<ConfiguracaoNivelCompetencia> configuracaoNiveisCompetencias, Long faixaSalarialId, Long candidatoId);
	Collection<ConfiguracaoNivelCompetencia> findByCandidato(Long candidato);
	Collection<ConfiguracaoNivelCompetencia> getCompetenciasCandidato(Long candidatoId, Long empresaId);
	void saveCompetenciasColaborador(Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais, ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador);
	void saveCompetenciasFaixaSalarial(Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais, ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial) throws Exception;
	Collection<ConfiguracaoNivelCompetencia> findByConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelCompetenciaColaboradorId);
	Collection<ConfiguracaoNivelCompetencia> findByConfiguracaoNivelCompetenciaFaixaSalarial(Long configuracaoNivelCompetenciaFaixaSalarialId);
	Collection<ConfiguracaoNivelCompetencia> findCompetenciaByFaixaSalarial(Long faixaId, Date data);
	Collection<ConfiguracaoNivelCompetencia> findColaboradorAbaixoNivel(Long[] competenciasIds, Long faixaSalarialId, Date data);
	Collection<ConfiguracaoNivelCompetenciaVO> montaRelatorioConfiguracaoNivelCompetencia(Date dataIni, Date dataFim, Long empresaId, Long faixaSalarialId, Long[] competenciasIds);
	Collection<MatrizCompetenciaNivelConfiguracao> montaConfiguracaoNivelCompetenciaByFaixa(Long empresaId, Long faixaSalarialId, Date data);
	Collection<ConfiguracaoNivelCompetenciaVO> montaMatrizCompetenciaCandidato(Long empresaId, Long faixaSalarialId, Long solicitacaoId);
	void removeByFaixas(Long[] faixaSalarialIds);
	void removeColaborador(Colaborador colaborador);
	void removeConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelColaboradorId);
	void removeConfiguracaoNivelCompetenciaFaixaSalarial(Long configuracaoNivelFaixaSalarialId) throws Exception;
	void removeByCandidato(Long candidatoId);
	Long[] findCompetenciasIdsConfiguradasByFaixaSolicitacao(Long faixaSalarialId);
	Integer somaConfiguracoesByFaixa(Long faixaSalarialId);
	Collection<ConfiguracaoNivelCompetencia> findByColaborador(Long colaboradorId, Long avaliadorId, Long colaboradorQuestionarioId);
	Collection<ConfiguracaoNivelCompetencia> findColaboradoresCompetenciasAbaixoDoNivel(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Boolean colaboradoresAvaliados, char agruparPor);
	void removeDependenciasComConfiguracaoNivelCompetenciaColaboradorByFaixaSalarial(Long[] faixaIds);
	void removeDependenciasComConfiguracaoNivelCompetenciaFaixaSalarialByFaixaSalarial(Long[] faixaIds);
	Collection<Competencia> findCompetenciasColaboradorByFaixaSalarialAndPeriodo(Long faixaId, Date dataIni, Date dataFim);
}

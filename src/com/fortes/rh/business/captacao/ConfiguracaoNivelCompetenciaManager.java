package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaVO;
import com.fortes.rh.model.captacao.MatrizCompetenciaNivelConfiguracao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;

public interface ConfiguracaoNivelCompetenciaManager extends GenericManager<ConfiguracaoNivelCompetencia>
{
	Collection<ConfiguracaoNivelCompetencia> findByFaixa(Long faixaSalarialId, Date data);
	void saveCompetenciasCandidato(Collection<ConfiguracaoNivelCompetencia> configuracaoNiveisCompetencias, Long faixaSalarialId, Long candidatoId, Long solicitacaoId);
	Collection<ConfiguracaoNivelCompetencia> findByCandidatoAndSolicitacao(Long candidatoId, Long solicitacaoId);
	Collection<Solicitacao> getCompetenciasCandidato(Long empresaId, Long candidatoId);
	void saveCompetenciasColaborador(Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais, ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador);
	void saveCompetenciasFaixaSalarial(Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais, ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial) throws Exception;
	Collection<ConfiguracaoNivelCompetencia> findByConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelCompetenciaColaboradorId, Date data);
	Collection<ConfiguracaoNivelCompetencia> findByConfiguracaoNivelCompetenciaFaixaSalarial(Long configuracaoNivelCompetenciaFaixaSalarialId, Date configuracaoNivelCompetenciaFaixaSalarialData);
	Collection<ConfiguracaoNivelCompetencia> findCompetenciaByFaixaSalarial(Long faixaId, Date data);
	Collection<ConfiguracaoNivelCompetencia> findColaboradorAbaixoNivel(Long[] competenciasIds, Long faixaSalarialId, Date data);
	Collection<ConfiguracaoNivelCompetenciaVO> montaRelatorioConfiguracaoNivelCompetencia(Date dataIni, Date dataFim, Long empresaId, Long faixaSalarialId, Long[] competenciasIds);
	Collection<MatrizCompetenciaNivelConfiguracao> montaConfiguracaoNivelCompetenciaByFaixa(Long empresaId, Long faixaSalarialId, Date data);
	public Collection<MatrizCompetenciaNivelConfiguracao> montaMatrizCNCByQuestionario(ColaboradorQuestionario colaboradorQuestionario, Long empresaId);
	Collection<ConfiguracaoNivelCompetenciaVO> montaMatrizCompetenciaCandidato(Long empresaId, Long faixaSalarialId, Solicitacao solicitacao);
	void removeByFaixas(Long[] faixaSalarialIds);
	void removeColaborador(Colaborador colaborador);
	void removeConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelColaboradorId) throws Exception, FortesException;
	void removeConfiguracaoNivelCompetenciaFaixaSalarial(Long configuracaoNivelFaixaSalarialId) throws Exception;
	void removeByCandidato(Long candidatoId);
	Long[] findCompetenciasIdsConfiguradasByFaixaSolicitacao(Long faixaSalarialId);
	Integer somaConfiguracoesByFaixa(Long faixaSalarialId);
	Collection<ConfiguracaoNivelCompetencia> findByColaborador(Long colaboradorId, Long avaliadorId, Long colaboradorQuestionarioId);
	Collection<ConfiguracaoNivelCompetencia> findColaboradoresCompetenciasAbaixoDoNivel(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Boolean colaboradoresAvaliados, char agruparPor);
	void removeDependenciasComConfiguracaoNivelCompetenciaColaboradorByFaixaSalarial(Long[] faixaIds);
	void removeDependenciasComConfiguracaoNivelCompetenciaFaixaSalarialByFaixaSalarial(Long[] faixaIds);
	Collection<Competencia> findCompetenciasColaboradorByFaixaSalarialAndPeriodo(Long faixaId, Date dataIni, Date dataFim);
	void saveCompetenciasColaboradorAndRecalculaPerformance(Long empresaId, Collection<ConfiguracaoNivelCompetencia> configuracaoNiveisCompetencias, ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador);
	Collection<Colaborador> findDependenciaComColaborador(Long faixaSalarialId,	Date data);
	Collection<Candidato> findDependenciaComCandidato(Long faixaSalarialId, Date data);
	void removeByCandidatoAndSolicitacao(Long candidatoId, Long solicitacaoId);
	void criaCNCColaboradorByCNCCnadidato(Colaborador colaborador,Long idCandidato, Solicitacao solicitacao,	HistoricoColaborador historico);
	public boolean existeConfiguracaoNivelCompetencia(Long competenciaId, char tipoCompetencia);
	void removeBySolicitacaoId(Long solicitacaoId);
}

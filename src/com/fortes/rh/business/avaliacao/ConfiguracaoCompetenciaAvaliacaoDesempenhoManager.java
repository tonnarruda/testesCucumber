package com.fortes.rh.business.avaliacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.security.spring.aop.callback.ConfiguracaoCompetenciaAvaliacaoDesempenhoAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;

public interface ConfiguracaoCompetenciaAvaliacaoDesempenhoManager extends GenericManager<ConfiguracaoCompetenciaAvaliacaoDesempenho> 
{
	@Audita(operacao="Inserção/Atualização", auditor=ConfiguracaoCompetenciaAvaliacaoDesempenhoAuditorCallbackImpl.class)
	public void save(Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> configuracaoCompetenciaAvaliacaoDesempenhos, AvaliacaoDesempenho avaliacaoDesempenho);
	public void removeNotIn(Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> configuracaoCompetenciaAvaliacaoDesempenhos, Long avaliacaoDesempenhoId) throws Exception;
	public Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> findByAvaliador(Long avaliadorId, Long faixaSalarialId, Long avaliacaoDesempenhoId);
	public Collection<FaixaSalarial> findFaixasSalariaisByCompetenciasConfiguradasParaAvaliacaoDesempenho(Long avaliacaoDesempenhoId);
	public boolean existeNovoHistoricoDeCompetenciaParaFaixaSalarialDeAlgumAvaliado(Long avaliacaoDesempenhoId);
	public void removeByAvaliacaoDesempenho(Long avaliacaoDesempenhoId);
	public boolean existe(Long configuracaoNivelCompetenciaFaixaSalarialId,Long avaliacaoDesempenhoId);
	public Collection<Colaborador> findColabSemCompetenciaConfiguradaByAvalDesempenhoId(Long avaliacaoDesempenhoId);
	public Collection<AvaliacaoDesempenho> findAvaliacoesComColabSemCompetenciaConfiguradaByAvalDesempenhoIds(Long[] avaliacaoDesempenhoIds);
	public ConfiguracaoNivelCompetenciaFaixaSalarial getConfiguracaoNivelCompetenciaFaixaSalarial(Long avaliadorId, Long faixaSalarialId, Long avaliacaoDesempenhoId);
}

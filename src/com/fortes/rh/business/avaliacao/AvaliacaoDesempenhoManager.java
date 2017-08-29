package com.fortes.rh.business.avaliacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.AnaliseDesempenhoOrganizacao;
import com.fortes.rh.model.avaliacao.ResultadoAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.web.tags.CheckBox;

public interface AvaliacaoDesempenhoManager extends GenericManager<AvaliacaoDesempenho>
{
	AvaliacaoDesempenho findByIdProjection(Long id);
	Collection<AvaliacaoDesempenho> findAllSelect(Long empresaId, Boolean ativa, Character tipoModeloAvaliacao);
	void clonar(Long avaliacaoDesempenhoId, boolean clonarParticipantes, Long... empresasIds) throws Exception;
	void liberarOrBloquear(AvaliacaoDesempenho avaliacaoDesempenho, boolean liberar) throws Exception;
	void remover(Long avaliacaoDesempenhoId) throws Exception;
	Collection<AvaliacaoDesempenho> findByAvaliador(Long avaliadorId, Boolean liberada, Long... empresasIds);
	Collection<ResultadoAvaliacaoDesempenho> montaResultado(Collection<Long> avaliadosIds, AvaliacaoDesempenho avaliacaoDesempenho, boolean agruparPorAspectos, boolean desconsiderarAutoAvaliacao) throws ColecaoVaziaException;
	void enviarLembrete(Long avaliacaoDesempenhoId, Empresa empresa);
	void enviarLembreteAoLiberar(Long avaliacaoDesempenhoId, Empresa empresa);
	Integer findCountTituloModeloAvaliacao(Integer page, Integer pagingSize, Date periodoInicial, Date periodoFinal, Long empresaId, String nomeBusca, Long avaliacaoId, Boolean liberada);
	Collection<AvaliacaoDesempenho> findTituloModeloAvaliacao(Integer page, Integer pagingSize, Date periodoInicial, Date periodoFinal, Long empresaId, String nomeBusca, Long avaliacaoId, Boolean liberada);
	void gerarAutoAvaliacoes(AvaliacaoDesempenho avaliacaoDesempenho, Collection<Colaborador> participantes);
	Collection<AvaliacaoDesempenho> findIdsAvaliacaoDesempenho(Long avaliacaoId);
	Collection<CheckBox> populaCheckBox(Long empresaId, boolean ativa);
	void liberarEmLote(String[] avaliacoesCheck) throws Exception;
	ResultadoAvaliacaoDesempenho getResultadoAvaliacaoDesempenho(AvaliacaoDesempenho avaliacaoDesempenho,Long avaliadoId);
	void saveOrUpdateRespostaAvDesempenho(Usuario usuario, Empresa empresa, Colaborador colaborador, ColaboradorQuestionario colaboradorQuestionario, AvaliacaoDesempenho avaliacaoDesempenho, ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial, Collection<Pergunta> perguntas, Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais) throws FortesException;
	Collection<AvaliacaoDesempenho> findComCompetencia(Long empresaId);
	boolean isExibiNivelCompetenciaExigido(Long avaliacaoDesempenhoId);
	Collection<AvaliacaoDesempenho> findByCncfId(Long configuracaoNivelCompetenciaFaixaSalarialId);
	Collection<AvaliacaoDesempenho> findByModelo(Long modeloId);
	Collection<Estabelecimento> findEstabelecimentosDosParticipantes(Long[] avaliacoesDesempenhoIds);
	Collection<AreaOrganizacional> findAreasOrganizacionaisDosParticipantes(Long[] avaliacoesDesempenhoIds);
	Collection<Cargo> findCargosDosParticipantes(Long[] avaliacoesDesempenhoIds);
	Collection<AnaliseDesempenhoOrganizacao> findAnaliseDesempenhoOrganizacao(Long[] avaliacoesIds, Long[] estabelecimentosIds, Long[] cargosIds, Long[] areasIds, Long[] competenciasIds, String agrupamentoDasCompetencias, Long empresaId);
}

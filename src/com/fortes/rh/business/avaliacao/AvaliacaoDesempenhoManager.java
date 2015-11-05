package com.fortes.rh.business.avaliacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ResultadoAvaliacaoDesempenho;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.web.tags.CheckBox;

public interface AvaliacaoDesempenhoManager extends GenericManager<AvaliacaoDesempenho>
{
	AvaliacaoDesempenho findByIdProjection(Long id);
	Collection<AvaliacaoDesempenho> findAllSelect(Long empresaId, Boolean ativa, Character tipoModeloAvaliacao);
	void clonar(Long avaliacaoDesempenhoId, boolean clonarParticipantes, Long... empresasIds) throws Exception;
	void liberar(AvaliacaoDesempenho avaliacaoDesempenho, Collection<Colaborador> avaliados, Collection<Colaborador> avaliadores) throws Exception;
	void bloquear(AvaliacaoDesempenho avaliacaoDesempenho) throws Exception;
	void remover(Long avaliacaoDesempenhoId) throws Exception;
	Collection<AvaliacaoDesempenho> findByAvaliador(Long avaliadorId, Boolean liberada, Long... empresasIds);
	Collection<ResultadoAvaliacaoDesempenho> montaResultado(Collection<Long> avaliadosIds, AvaliacaoDesempenho avaliacaoDesempenho, boolean agruparPorAspectos, boolean desconsiderarAutoAvaliacao) throws ColecaoVaziaException;
	void enviarLembrete(Long avaliacaoDesempenhoId, Empresa empresa);
	void enviarLembreteAoLiberar(Long avaliacaoDesempenhoId, Empresa empresa);
	Integer findCountTituloModeloAvaliacao(Integer page, Integer pagingSize, Date periodoInicial, Date periodoFinal, Long empresaId, String nomeBusca, Long avaliacaoId, Boolean liberada);
	Collection<AvaliacaoDesempenho> findTituloModeloAvaliacao(Integer page, Integer pagingSize, Date periodoInicial, Date periodoFinal, Long empresaId, String nomeBusca, Long avaliacaoId, Boolean liberada);
	void gerarAutoAvaliacoes(AvaliacaoDesempenho avaliacaoDesempenho, Collection<Colaborador> participantes);
	Collection<AvaliacaoDesempenho> findIdsAvaliacaoDesempenho(Long avaliacaoId);
	Collection<CheckBox> populaCheckBox(Long empresaId, boolean ativa, char tipoModeloAvaliacao);
	void liberarEmLote(String[] avaliacoesCheck, Empresa empresa) throws Exception;
}

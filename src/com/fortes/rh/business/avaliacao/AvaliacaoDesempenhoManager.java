package com.fortes.rh.business.avaliacao;

import java.util.Collection;

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
	void clonar(Long avaliacaoDesempenhoId, Long... empresasIds) throws Exception;
	void liberar(AvaliacaoDesempenho avaliacaoDesempenho) throws Exception;
	void bloquear(AvaliacaoDesempenho avaliacaoDesempenho) throws Exception;
	Collection<AvaliacaoDesempenho> findByAvaliador(Long avaliadorId, Boolean liberada, Long empresaId);
	Collection<ResultadoAvaliacaoDesempenho> montaResultado(Collection<Long> avaliadosIds, AvaliacaoDesempenho avaliacaoDesempenho, boolean agruparPorAspectos) throws ColecaoVaziaException;
	void enviarLembrete(Long id, Empresa empresa);
	Collection<AvaliacaoDesempenho> findTituloModeloAvaliacao(Long empresaId, String nomeBusca, Long avaliacaoId);
	void gerarAutoAvaliacoes(AvaliacaoDesempenho avaliacaoDesempenho, Collection<Colaborador> participantes);
	Collection<AvaliacaoDesempenho> findIdsAvaliacaoDesempenho(Long avaliacaoId);
	Collection<CheckBox> populaCheckBox(Long empresaId, boolean ativa, char tipoModeloAvaliacao);
}

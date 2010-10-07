package com.fortes.rh.business.avaliacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ResultadoAvaliacaoDesempenho;
import com.fortes.rh.model.geral.Empresa;

public interface AvaliacaoDesempenhoManager extends GenericManager<AvaliacaoDesempenho>
{
	AvaliacaoDesempenho findByIdProjection(Long id);
	Collection<AvaliacaoDesempenho> findAllSelect(Long empresaId, Boolean ativa);
	void clonar(Long avaliacaoDesempenhoId) throws Exception;
	void liberar(AvaliacaoDesempenho avaliacaoDesempenho) throws Exception;
	void bloquear(AvaliacaoDesempenho avaliacaoDesempenho) throws Exception;
	Collection<AvaliacaoDesempenho> findByAvaliador(Long avaliadorId, Boolean liberada);
	Collection<ResultadoAvaliacaoDesempenho> montaResultado(Collection<Long> avaliadosIds, AvaliacaoDesempenho avaliacaoDesempenho, boolean agruparPorAspectos) throws ColecaoVaziaException;
	void enviarLembrete(Long id, Empresa empresa);
}

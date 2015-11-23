package com.fortes.rh.business.avaliacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ParticipanteAvaliacaoDesempenho;
import com.fortes.rh.model.geral.Colaborador;

public interface ParticipanteAvaliacaoDesempenhoManager extends GenericManager<ParticipanteAvaliacaoDesempenho> 
{
	public void save(AvaliacaoDesempenho avaliacaoDesempenho, Long[] colaboradorIds, char tipo);
	public Collection<Colaborador> findParticipantes(Long avaliacaoDesempenhoId, Character tipo);
	public void removeNotIn(Long[] participantes, Long avaliacaoDesempenhoId, Character tipo) throws Exception;
}

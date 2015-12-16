package com.fortes.rh.business.avaliacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ParticipanteAvaliacaoDesempenho;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;

public interface ParticipanteAvaliacaoDesempenhoManager extends GenericManager<ParticipanteAvaliacaoDesempenho> 
{
	public void save(AvaliacaoDesempenho avaliacaoDesempenho, Long[] colaboradorIds, String[] produtividade, char tipo);
	public Collection<Colaborador> findColaboradoresParticipantes(Long avaliacaoDesempenhoId, Character tipo);
	public void removeNotIn(Long[] participantes, Long avaliacaoDesempenhoId, Character tipo) throws Exception;
	Collection<FaixaSalarial> findFaixasSalariaisDosAvaliadosComCompetenciasByAvaliacaoDesempenho(Long avaliacaoDesempenhoId);
	public Collection<FaixaSalarial> findFaixasSalariaisDosAvaliadosByAvaliador(Long avaliacaoDesempenhoId, Long avaliadorId);
	public Collection<ParticipanteAvaliacaoDesempenho> findParticipantes(Long avaliacaoDesempenhoId, Character tipo);
}

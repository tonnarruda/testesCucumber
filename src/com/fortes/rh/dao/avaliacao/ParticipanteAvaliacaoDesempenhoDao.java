package com.fortes.rh.dao.avaliacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.avaliacao.ParticipanteAvaliacaoDesempenho;
import com.fortes.rh.model.geral.Colaborador;

public interface ParticipanteAvaliacaoDesempenhoDao extends GenericDao<ParticipanteAvaliacaoDesempenho> 
{
	public Collection<Colaborador> findParticipantes(Long avaliacaoDesempenhoId, Character tipo);
}
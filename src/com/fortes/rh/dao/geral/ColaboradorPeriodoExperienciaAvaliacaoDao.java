package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;

public interface ColaboradorPeriodoExperienciaAvaliacaoDao extends GenericDao<ColaboradorPeriodoExperienciaAvaliacao>
{
	void removeByColaborador(Long... colaboradorIds);
	Collection<ColaboradorPeriodoExperienciaAvaliacao> findByColaborador(Long colaboradorId);
	Collection<ColaboradorPeriodoExperienciaAvaliacao> findColaboradoresComAvaliacaoNaoRespondida();
	void removeByAvaliacao(Long avaliacaoId);
}
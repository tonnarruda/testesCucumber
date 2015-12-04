package com.fortes.rh.dao.avaliacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenho;

public interface ConfiguracaoCompetenciaAvaliacaoDesempenhoDao extends GenericDao<ConfiguracaoCompetenciaAvaliacaoDesempenho> 
{
	public Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> findByAvaliacaoDesempenho(Long avaliacaoDesempenhoId);
	public Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> findByAvaliadorId(Long avaliadorId);
}
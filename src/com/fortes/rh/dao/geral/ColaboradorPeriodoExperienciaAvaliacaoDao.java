package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;

public interface ColaboradorPeriodoExperienciaAvaliacaoDao extends GenericDao<ColaboradorPeriodoExperienciaAvaliacao>
{
	void removeByColaborador(Long colaboradorId);

	Collection<ColaboradorPeriodoExperienciaAvaliacao> findByColaborador(Long colaboradorId);
	public Collection<ColaboradorPeriodoExperienciaAvaliacao> getColaboradoresComAvaliacaoVencidaHoje();
}
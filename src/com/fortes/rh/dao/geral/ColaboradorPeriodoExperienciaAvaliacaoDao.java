package com.fortes.rh.dao.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;

public interface ColaboradorPeriodoExperienciaAvaliacaoDao extends GenericDao<ColaboradorPeriodoExperienciaAvaliacao>
{

	void removeByColaborador(Long colaboradorId);

}
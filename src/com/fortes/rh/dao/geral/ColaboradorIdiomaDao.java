package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorIdioma;

public interface ColaboradorIdiomaDao extends GenericDao<ColaboradorIdioma>
{
	void removeColaborador(Colaborador colaborador);

	Collection<ColaboradorIdioma> findByColaborador(Long colaboradorId);
}
package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorIdioma;

public interface ColaboradorIdiomaManager extends GenericManager<ColaboradorIdioma>
{
	void removeColaborador(Colaborador colaborador);

	public Collection<ColaboradorIdioma> findByColaborador(Long colaboradorId);
}
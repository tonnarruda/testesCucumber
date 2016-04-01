package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.annotations.TesteAutomatico;
import com.fortes.rh.dao.geral.ColaboradorIdiomaDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorIdioma;

public class ColaboradorIdiomaManagerImpl extends GenericManagerImpl<ColaboradorIdioma, ColaboradorIdiomaDao> implements ColaboradorIdiomaManager
{
	@TesteAutomatico
	public void removeColaborador(Colaborador colaborador)
	{
		getDao().removeColaborador(colaborador);
	}

	public Collection<ColaboradorIdioma> findByColaborador(Long colaboradorId)
	{
		return getDao().findByColaborador(colaboradorId);
	}
}
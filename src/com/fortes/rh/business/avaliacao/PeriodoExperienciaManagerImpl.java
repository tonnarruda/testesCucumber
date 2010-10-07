package com.fortes.rh.business.avaliacao;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.avaliacao.PeriodoExperienciaDao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;

public class PeriodoExperienciaManagerImpl extends GenericManagerImpl<PeriodoExperiencia, PeriodoExperienciaDao> implements PeriodoExperienciaManager
{
	public Collection<PeriodoExperiencia> findAllSelect(Long empresaId)
	{
		return getDao().findAllSelect(empresaId);
	}
}

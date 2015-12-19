package com.fortes.rh.business.captacao;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.CompetenciaDao;
import com.fortes.rh.model.captacao.Competencia;

public class CompetenciaManagerImpl extends GenericManagerImpl<Competencia, CompetenciaDao> implements CompetenciaManager
{
	public boolean existeNome(String nome, Long competenciaId, Character tipo, Long empresaId) 
	{
		return getDao().existeNome(nome, competenciaId, tipo, empresaId);
	}

	public Competencia findCompetencia(Long competenciaId, Character tipoCompetencia) {
		return getDao().findCompetencia(competenciaId, tipoCompetencia);
	}
}
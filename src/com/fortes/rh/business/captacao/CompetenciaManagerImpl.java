package com.fortes.rh.business.captacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.CompetenciaDao;
import com.fortes.rh.model.captacao.Competencia;

@Component
public class CompetenciaManagerImpl extends GenericManagerImpl<Competencia, CompetenciaDao> implements CompetenciaManager
{
	@Autowired
	CompetenciaManagerImpl(CompetenciaDao competenciaDao) {
		setDao(competenciaDao);
	}
	
	public boolean existeNome(String nome, Long competenciaId, Character tipo, Long empresaId) 
	{
		return getDao().existeNome(nome, competenciaId, tipo, empresaId);
	}
}
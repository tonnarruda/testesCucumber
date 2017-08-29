package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.CompetenciaDao;
import com.fortes.rh.model.captacao.Competencia;

public class CompetenciaManagerImpl extends GenericManagerImpl<Competencia, CompetenciaDao> implements CompetenciaManager
{
	public boolean existeNome(String nome, Long competenciaId, Character tipo, Long empresaId) 
	{
		return getDao().existeNome(nome, competenciaId, tipo, empresaId);
	}

	public Collection<Competencia> findByAvaliacoesDesempenho(Long empresaId, Long[] avaliacoesDesempenhoIds, String competenciasConsideradas) {
		return getDao().findByAvaliacoesDesempenho(empresaId, avaliacoesDesempenhoIds, competenciasConsideradas);
	}

}
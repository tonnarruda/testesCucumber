package com.fortes.rh.dao.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.Competencia;

public interface CompetenciaDao extends GenericDao<Competencia>
{
	boolean existeNome(String nome, Long competenciaId, Character tipo, Long empresaId);
	Collection<Competencia> findByAvaliacoesDesempenho(Long empresaId, Long[] avaliacoesDesempenhoIds, String competenciasConsideradas);
}
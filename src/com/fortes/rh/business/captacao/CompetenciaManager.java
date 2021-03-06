package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.Competencia;

public interface CompetenciaManager extends GenericManager<Competencia>
{
	boolean existeNome(String nome, Long competenciaId, Character tipo, Long empresaId);
	Collection<Competencia> findByAvaliacoesDesempenho(Long empresaId, Long[] avaliacoesDesempenhoIds, String competenciasConsideradas);
}
package com.fortes.rh.dao.desenvolvimento;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;

public interface AvaliacaoCursoDao extends GenericDao<AvaliacaoCurso> 
{

	Collection<AvaliacaoCurso> findByCurso(Long cursoId);

	Integer countAvaliacoes(Long turmaId, String wherePor);

	Integer countAvaliacoes(Long[] cursoIds);
	
	public Collection<AvaliacaoCurso> buscaFiltro(String titulo);
	
}
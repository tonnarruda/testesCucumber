package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;

public interface AvaliacaoCursoManager extends GenericManager<AvaliacaoCurso>
{

	Collection<AvaliacaoCurso> findByCurso(Long cursoId);

	Integer countAvaliacoes(Long turmaId, String wherePor);

	Integer countAvaliacoes(Long[] cursoIds);
	
	public Collection<AvaliacaoCurso> buscaFiltro(String titulo);

	Collection<AvaliacaoCurso> findByCursos(Long[] cursosIds);
	
	public boolean existeAvaliacaoCursoDeTipoNotaOuPorcentagemRespondida(Long avaliacaoCursoId);

	public boolean existeAvaliacaoCursoDeTipoAvaliacaoRespondida(Long avaliacaoCursoId);
	
}
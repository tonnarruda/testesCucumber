package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.desenvolvimento.AvaliacaoCursoDao;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;

public class AvaliacaoCursoManagerImpl extends GenericManagerImpl<AvaliacaoCurso, AvaliacaoCursoDao> implements AvaliacaoCursoManager
{

	public Collection<AvaliacaoCurso> findByCurso(Long cursoId)
	{
		return getDao().findByCursos(new Long[]{cursoId});
	}

	public Collection<AvaliacaoCurso> findByCursos(Long[] cursosIds)
	{
		return getDao().findByCursos(cursosIds);
	}

	public Integer countAvaliacoes(Long turmaId, String wherePor)
	{
		return getDao().countAvaliacoes(turmaId, wherePor);
	}

	public Integer countAvaliacoes(Long[] cursoIds)
	{
		return getDao().countAvaliacoes(cursoIds);
	}

	public Collection<AvaliacaoCurso> buscaFiltro(String titulo)
	{
		return getDao().buscaFiltro(titulo);
	}
	
	public boolean existeAvaliacaoCursoRespondida(Long avaliacaoCursoId, char tipoAvaliacaoCurso)
	{
		return getDao().existeAvaliacaoCursoRespondida(avaliacaoCursoId, tipoAvaliacaoCurso);
	}
}
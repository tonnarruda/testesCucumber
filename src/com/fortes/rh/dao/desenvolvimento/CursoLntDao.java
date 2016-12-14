package com.fortes.rh.dao.desenvolvimento;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.desenvolvimento.CursoLnt;

public interface CursoLntDao extends GenericDao<CursoLnt> 
{
	Collection<CursoLnt> findByLntId(Long lntId);
	Boolean existePerticipanteASerRelacionado(Long cursoLntId);
	void updateNomeNovoCurso(Long cursoId, String nomeNovoCurso);
	void removeCursoId(Long cursoId);
}

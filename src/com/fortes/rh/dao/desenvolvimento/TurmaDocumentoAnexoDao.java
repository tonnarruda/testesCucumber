package com.fortes.rh.dao.desenvolvimento;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.desenvolvimento.TurmaDocumentoAnexo;

public interface TurmaDocumentoAnexoDao extends GenericDao<TurmaDocumentoAnexo>
{
	void removeByTurma(Long turmaId);
	Collection<TurmaDocumentoAnexo> findByColaborador(Long colaboradorId);
}
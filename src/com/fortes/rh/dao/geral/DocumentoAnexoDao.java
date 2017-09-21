package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.DocumentoAnexo;

public interface DocumentoAnexoDao extends GenericDao<DocumentoAnexo>
{
	Collection<DocumentoAnexo> getDocumentoAnexoByOrigemId(char origem, Long origemId, Long origemCandidatoId);

	DocumentoAnexo findByIdProjection(Long documentoAnexoId);
	
	Collection<DocumentoAnexo> findByTurma(Long turmaId);
}
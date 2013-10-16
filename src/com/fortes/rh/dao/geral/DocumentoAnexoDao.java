package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.DocumentoAnexo;

public interface DocumentoAnexoDao extends GenericDao<DocumentoAnexo>
{
	Collection<DocumentoAnexo> getDocumentoAnexoByOrigemId(Boolean moduloExterno, char origem, Long... origemId);

	DocumentoAnexo findByIdProjection(Long documentoAnexoId);
}
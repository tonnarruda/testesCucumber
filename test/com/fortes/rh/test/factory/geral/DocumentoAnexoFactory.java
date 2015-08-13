package com.fortes.rh.test.factory.geral;

import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.geral.DocumentoAnexo;

public class DocumentoAnexoFactory
{
	public static DocumentoAnexo getEntity()
	{
		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		return documentoAnexo;
	}

	public static DocumentoAnexo getEntity(long id)
	{
		DocumentoAnexo documentoAnexo = getEntity();
		documentoAnexo.setId(id);
		return documentoAnexo;
	}
}

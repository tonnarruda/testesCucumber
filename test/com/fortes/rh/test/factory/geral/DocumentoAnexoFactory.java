package com.fortes.rh.test.factory.geral;

import java.util.Date;

import com.fortes.rh.model.geral.DocumentoAnexo;

public class DocumentoAnexoFactory
{
	public static DocumentoAnexo getEntity()
	{
		DocumentoAnexo documentoAnexo = new DocumentoAnexo();
		documentoAnexo.setId(null);
		documentoAnexo.setDescricao("documentoAnexo");
		documentoAnexo.setEtapaSeletiva(null);
		documentoAnexo.setData(new Date());
		documentoAnexo.setUrl("url");
		documentoAnexo.setOrigem('Z');
		documentoAnexo.setOrigemId(1L);

		return documentoAnexo;
	}

	public static DocumentoAnexo getEntity(long id)
	{
		DocumentoAnexo documentoAnexo = getEntity();
		documentoAnexo.setId(id);
		return documentoAnexo;
	}
	
	public static DocumentoAnexo getEntity(Long id, Long origemId, char origem, String descricao, Date data )
	{
		DocumentoAnexo documentoAnexo = getEntity();
		documentoAnexo.setId(id);
		documentoAnexo.setOrigemId(origemId);
		documentoAnexo.setOrigem(origem);
		documentoAnexo.setDescricao(descricao);
		documentoAnexo.setData(data);
		return documentoAnexo;
	}
}
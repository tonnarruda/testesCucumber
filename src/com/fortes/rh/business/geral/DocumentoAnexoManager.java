package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.DocumentoAnexo;

public interface DocumentoAnexoManager extends GenericManager<DocumentoAnexo>
{
	public Collection<DocumentoAnexo> getDocumentoAnexoByOrigemId(char origem, Long origemId);
	public void atualizarDocumentoAnexo(String diretorio, DocumentoAnexo documentoAnexo, com.fortes.model.type.File documento) throws Exception;
	public void inserirDocumentoAnexo(String diretorio, DocumentoAnexo documentoAnexo, com.fortes.model.type.File documento) throws Exception;
	public void deletarDocumentoAnexo(String diretorio, DocumentoAnexo documentoAnexo) throws Exception;
	public DocumentoAnexo findByIdProjection(Long documentoAnexoId);
	public String getTituloEdit(char origem, Long id, boolean isEdicao);
	public String getTituloList(char origem, Long id);
	public Collection<DocumentoAnexo> findByTurma(Long turmaId);
}
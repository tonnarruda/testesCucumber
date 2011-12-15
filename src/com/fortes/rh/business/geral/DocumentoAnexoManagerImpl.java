package com.fortes.rh.business.geral;

import java.io.File;
import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.dao.geral.DocumentoAnexoDao;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.util.ArquivoUtil;

public class DocumentoAnexoManagerImpl extends GenericManagerImpl<DocumentoAnexo, DocumentoAnexoDao> implements DocumentoAnexoManager
{
	private ColaboradorManager colaboradorManager;
	private CandidatoManager candidatoManager;
	
	public Collection<DocumentoAnexo> getDocumentoAnexoByOrigemId(char origem, long origemId)
	{
		return getDao().getDocumentoAnexoByOrigemId(origem, origemId);
	}

	public void atualizarDocumentoAnexo(String diretorio,DocumentoAnexo documentoAnexo, com.fortes.model.type.File documento) throws Exception
	{
		try
		{
			setEtapaSeletivaETipoDocumento(documentoAnexo);
			
			if(documento != null)
			{
				ArquivoUtil.deletaArquivos(diretorio, new String[]{documentoAnexo.getUrl()});
				File file = ArquivoUtil.salvaArquivo(diretorio, documento, true);
				documentoAnexo.setUrl(file.getName());
			}

			getDao().update(documentoAnexo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	public void inserirDocumentoAnexo(String diretorio,DocumentoAnexo documentoAnexo, com.fortes.model.type.File documento) throws Exception
	{
		try
		{
			setEtapaSeletivaETipoDocumento(documentoAnexo);

			File file = ArquivoUtil.salvaArquivo(diretorio, documento, true);
			documentoAnexo.setUrl(file.getName());

			getDao().save(documentoAnexo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	public void deletarDocumentoAnexo(String diretorio,DocumentoAnexo documentoAnexo) throws Exception
	{
		try
		{
			String documentoAnexoUrl = documentoAnexo.getUrl();
			
			setEtapaSeletivaETipoDocumento(documentoAnexo);
			
			getDao().remove(documentoAnexo);
			
			ArquivoUtil.deletaArquivos(diretorio, new String[]{documentoAnexoUrl});
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

	}

	private void setEtapaSeletivaETipoDocumento(DocumentoAnexo documentoAnexo) {
		if(documentoAnexo.getEtapaSeletiva() != null && documentoAnexo.getEtapaSeletiva().getId() == null)
				documentoAnexo.setEtapaSeletiva(null);

		if(documentoAnexo.getTipoDocumento() != null && documentoAnexo.getTipoDocumento().getId() == null)
				documentoAnexo.setTipoDocumento(null);
	}

	public DocumentoAnexo findByIdProjection(Long documentoAnexoId)
	{
		return getDao().findByIdProjection(documentoAnexoId);
	}
	
	public String getNome(char origem, Long id){
		String nome = null;
		//Candidato = C
		//Colaborador = D
		if(origem == 'C')
			nome = candidatoManager.findByCandidatoId(id).getNome();
		else if(origem == 'D')
			nome = colaboradorManager.findColaboradorByIdProjection(id).getNome();
			
		return nome;
	}


	public void setCandidatoManager(CandidatoManager candidatoManager) {
		this.candidatoManager = candidatoManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}
}
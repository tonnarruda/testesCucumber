package com.fortes.rh.business.geral;

import java.io.File;
import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.dao.geral.DocumentoAnexoDao;
import com.fortes.rh.model.dicionario.OrigemAnexo;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.util.ArquivoUtil;

public class DocumentoAnexoManagerImpl extends GenericManagerImpl<DocumentoAnexo, DocumentoAnexoDao> implements DocumentoAnexoManager
{
	private ColaboradorManager colaboradorManager;
	private CandidatoManager candidatoManager;
	private CursoManager cursoManager;
	
	public Collection<DocumentoAnexo> getDocumentoAnexoByOrigemId(Boolean moduloExterno, char origem, Long origemId)
	{
		if (origem == OrigemAnexo.AnexoColaborador)
		{
			Colaborador colab = (Colaborador) colaboradorManager.findByIdProjectionEmpresa(origemId);
			return getDao().getDocumentoAnexoByOrigemId(moduloExterno, origem, origemId, colab.getCandidato().getId());
		}
		
		return getDao().getDocumentoAnexoByOrigemId(moduloExterno, origem, origemId, null);
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
	
	public Collection<DocumentoAnexo> findByTurma(Long turmaId) {
		return getDao().findByTurma(turmaId);
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
		else if(origem == 'U')
			nome = cursoManager.findById(id).getNome();
			
		return nome;
	}

	public void setCandidatoManager(CandidatoManager candidatoManager) {
		this.candidatoManager = candidatoManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public void setCursoManager(CursoManager cursoManager) {
		this.cursoManager = cursoManager;
	}
}
package com.fortes.rh.business.geral;

import java.io.File;
import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.dao.geral.DocumentoAnexoDao;
import com.fortes.rh.model.dicionario.OrigemAnexo;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.ModelUtil;

public class DocumentoAnexoManagerImpl extends GenericManagerImpl<DocumentoAnexo, DocumentoAnexoDao> implements DocumentoAnexoManager
{
	private ColaboradorManager colaboradorManager;
	private CandidatoManager candidatoManager;
	private CursoManager cursoManager;
	private SolicitacaoManager solicitacaoManager;
	
	public Collection<DocumentoAnexo> getDocumentoAnexoByOrigemId(char origem, Long origemId)
	{
		if (origem == OrigemAnexo.COLABORADOR){
			Colaborador colab = (Colaborador) colaboradorManager.findByIdProjectionEmpresa(origemId);
			return getDao().getDocumentoAnexoByOrigemId(origem, origemId, colab.getCandidato().getId());
		}
		
		return getDao().getDocumentoAnexoByOrigemId(origem, origemId, null);
	}

	public void atualizarDocumentoAnexo(String diretorio,DocumentoAnexo documentoAnexo, com.fortes.model.type.File documento) throws Exception
	{
		try
		{
			removeEntidadesComIdNulo(documentoAnexo);
			
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
			removeEntidadesComIdNulo(documentoAnexo);

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
	
	private void removeEntidadesComIdNulo(DocumentoAnexo documentoAnexo){
		if(ModelUtil.hasNull("getTipoDocumento().getId()", documentoAnexo))
			documentoAnexo.setTipoDocumento(null);
		
		if(ModelUtil.hasNull("getEtapaSeletiva().getId()", documentoAnexo))
			documentoAnexo.setEtapaSeletiva(null);
	}

	public void deletarDocumentoAnexo(String diretorio,DocumentoAnexo documentoAnexo) throws Exception
	{
		try{
			String documentoAnexoUrl = documentoAnexo.getUrl();
			removeEntidadesComIdNulo(documentoAnexo);

			getDao().remove(documentoAnexo);
			ArquivoUtil.deletaArquivos(diretorio, new String[]{documentoAnexoUrl});
		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	public Collection<DocumentoAnexo> findByTurma(Long turmaId) {
		return getDao().findByTurma(turmaId);
	}

	public DocumentoAnexo findByIdProjection(Long documentoAnexoId)
	{
		return getDao().findByIdProjection(documentoAnexoId);
	}
	
	public String getTituloEdit(char origem, Long id, boolean isEdicao){
		return (isEdicao ? "Editar " : "Novo ") + "Documento " + getTitulo(origem, id, "");
	}
	
	public String getTituloList(char origem, Long id){
		return "Documentos " + getTitulo(origem, id, "");
	}

	private String getTitulo(char origem, Long id, String nome) {
		if(origem == OrigemAnexo.CANDIDATO || origem == OrigemAnexo.CANDIDATOEXTERNO)
			nome += "do Candidato: " + candidatoManager.findByCandidatoId(id).getNome();
		else if(origem == OrigemAnexo.COLABORADOR)
			nome += "do Colaborador: " + colaboradorManager.findColaboradorByIdProjection(id).getNome();
		else if(origem == OrigemAnexo.CURSO)
			nome += "do Curso: " + cursoManager.findEntidadeComAtributosSimplesById(id).getNome();
		else if(origem == OrigemAnexo.SOLICITACAOPESSOAL)
			nome += "da Solicitação de Pessoal: " + solicitacaoManager.findEntidadeComAtributosSimplesById(id).getCodigoMaisDescricaoFormatada();
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

	public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager) {
		this.solicitacaoManager = solicitacaoManager;
	}
}
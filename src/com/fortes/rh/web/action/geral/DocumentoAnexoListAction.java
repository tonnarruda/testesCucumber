package com.fortes.rh.web.action.geral;

import java.util.Collection;

import com.fortes.rh.business.geral.DocumentoAnexoManager;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class DocumentoAnexoListAction extends MyActionSupportList
{
	private DocumentoAnexoManager documentoAnexoManager;

	private Collection<DocumentoAnexo> documentoAnexos;
	
	private DocumentoAnexo documentoAnexo;

	private String diretorio;
	private String nome;
	//Usados pela listagem de Candidatos para manter dados do filtro
	private Long etapaSeletivaId;
	private char visualizar;

	//Usada apenas para armazenar quando os documentos são acessador através da tela de solicitação de pessoal.
	private Long solicitacaoId;

	public String list() throws Exception
	{
		setVideoAjuda(764L);
		
		documentoAnexos = documentoAnexoManager.getDocumentoAnexoByOrigemId(documentoAnexo.getOrigem(), documentoAnexo.getOrigemId(), null);
		nome = documentoAnexoManager.getNome(documentoAnexo.getOrigem(),documentoAnexo.getOrigemId());
		return Action.SUCCESS;
	}

	private String delete() throws Exception
	{
		try
		{
			documentoAnexo = documentoAnexoManager.findByIdProjection(documentoAnexo.getId());
			documentoAnexoManager.deletarDocumentoAnexo(diretorio, documentoAnexo);
			addActionMessage("Documento excluído com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir o documento.");
		}

		return list();
	}

	public String deleteCandidato() throws Exception
	{
		diretorio = "documentosCandidatos";
		return delete();
	}

	public String deleteColaborador() throws Exception
	{
		diretorio = "documentosColaboradores";
		return delete();
	}

	public Collection<DocumentoAnexo> getDocumentoAnexos()
	{
		return documentoAnexos;
	}

	public DocumentoAnexo getDocumentoAnexo()
	{
		if(documentoAnexo == null)
		{
			documentoAnexo = new DocumentoAnexo();
		}
		return documentoAnexo;
	}

	public void setDocumentoAnexo(DocumentoAnexo documentoAnexo)
	{
		this.documentoAnexo = documentoAnexo;
	}

	public void setDocumentoAnexoManager(DocumentoAnexoManager documentoAnexoManager)
	{
		this.documentoAnexoManager = documentoAnexoManager;
	}

	public void setDocumentoAnexos(Collection<DocumentoAnexo> documentoAnexos)
	{
		this.documentoAnexos = documentoAnexos;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public Long getSolicitacaoId()
	{
		return solicitacaoId;
	}

	public void setSolicitacaoId(Long solicitacaoId)
	{
		this.solicitacaoId = solicitacaoId;
	}

	public Long getEtapaSeletivaId()
	{
		return etapaSeletivaId;
	}

	public void setEtapaSeletivaId(Long etapaSeletivaId)
	{
		this.etapaSeletivaId = etapaSeletivaId;
	}

	public char getVisualizar()
	{
		return visualizar;
	}

	public void setVisualizar(char visualizar)
	{
		this.visualizar = visualizar;
	}
}
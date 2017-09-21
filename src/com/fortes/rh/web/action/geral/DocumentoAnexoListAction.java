package com.fortes.rh.web.action.geral;

import java.util.Collection;

import com.fortes.rh.business.geral.DocumentoAnexoManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.dicionario.OrigemAnexo;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings("serial")
public class DocumentoAnexoListAction extends MyActionSupportList
{
	private DocumentoAnexoManager documentoAnexoManager;
	
	private OrigemAnexo origemAnexo = new OrigemAnexo();
	private Collection<DocumentoAnexo> documentoAnexos;
	private DocumentoAnexo documentoAnexo;
	private String titulo = "";
	private String voltar = "";
	private Long etapaSeletivaId;
	private char visualizar;
	private Long solicitacaoId;
	private Long colaboradorId;
	private Character origem; 

	public String list() throws Exception
	{
		setVideoAjuda(764L);
		
		if(documentoAnexo.getOrigemId() == null || !origemAnexo.possuiPermissao(documentoAnexo.getOrigem(), solicitacaoId, ActionContext.getContext().getSession())){
			addActionMessage("Usuário sem permissão de acesso.");
			return Action.SUCCESS;
		}
		
		if(colaboradorId != null){
			documentoAnexo.setOrigem(OrigemAnexo.AnexoColaborador);
			documentoAnexo.setOrigemId(colaboradorId);
		}
		
		if(origem == null)
			origem = documentoAnexo.getOrigem();
		else
			documentoAnexo.setOrigem(origem);
		
		documentoAnexos = documentoAnexoManager.getDocumentoAnexoByOrigemId(documentoAnexo.getOrigem(), documentoAnexo.getOrigemId());
		titulo = documentoAnexoManager.getTituloList(documentoAnexo.getOrigem(), documentoAnexo.getOrigemId());

		voltar = origemAnexo.getVoltarListAnexoByOrigem(documentoAnexo.getOrigem(), solicitacaoId);
		
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try{
			if(documentoAnexo.getOrigemId() == null && !origemAnexo.possuiPermissao(documentoAnexo.getOrigem(), solicitacaoId, ActionContext.getContext().getSession()))
				throw new Exception();
			
			checkCandidatoExterno();
			documentoAnexo = documentoAnexoManager.findByIdProjection(documentoAnexo.getId());
			documentoAnexoManager.deletarDocumentoAnexo(origemAnexo.diretorioOrigemAnexo(documentoAnexo.getOrigem()), documentoAnexo);
			addActionSuccess("Documento excluído com sucesso.");
		}catch (FortesException e){
			e.printStackTrace();
			addActionWarning(e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			addActionError("Não foi possível excluir o documento.");
		}

		return list();
	}
	
	private void checkCandidatoExterno() throws FortesException{
		if(origem == null)
			origem = documentoAnexo.getOrigem();
		
		if(origem == OrigemAnexo.AnexoCandidatoExterno){
			DocumentoAnexo documentoAnexoTmp = documentoAnexoManager.findByIdProjection(documentoAnexo.getId());
			if (!ActionContext.getContext().getSession().get("SESSION_CANDIDATO_ID").equals(documentoAnexoTmp.getOrigemId()))
				throw new FortesException("O documento selecionado não consta na sua lista.");
		}
	}
	
	public Collection<DocumentoAnexo> getDocumentoAnexos()
	{
		return documentoAnexos;
	}

	public DocumentoAnexo getDocumentoAnexo()
	{
		if(documentoAnexo == null)
			documentoAnexo = new DocumentoAnexo();
		
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

	public String getVoltar() {
		return voltar;
	}

	public String getTitulo() {
		return titulo;
	}

	public Long getColaboradorId() {
		return colaboradorId;
	}

	public void setColaboradorId(Long colaboradorId) {
		this.colaboradorId = colaboradorId;
	}

	public Character getOrigem() {
		return origem;
	}

	public void setOrigem(Character origem) {
		this.origem = origem;
	}
}
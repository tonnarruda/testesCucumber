package com.fortes.rh.web.action.geral;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import com.fortes.model.type.FileUtil;
import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.business.geral.DocumentoAnexoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.geral.TipoDocumentoManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.dicionario.OrigemAnexo;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.model.geral.TipoDocumento;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings({"serial"})
public class DocumentoAnexoEditAction extends MyActionSupportEdit
{
	private DocumentoAnexoManager documentoAnexoManager;
	private EtapaSeletivaManager etapaSeletivaManager;
	private TipoDocumentoManager tipoDocumentoManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;

	private OrigemAnexo origemAnexo = new OrigemAnexo();
	private DocumentoAnexo documentoAnexo;
	private Collection<EtapaSeletiva> etapaSeletivas;
	private com.fortes.model.type.File documento;

	private String titulo;
	private Long solicitacaoId;
	private Long colaboradorId;

	private int page;
	private Long etapaSeletivaId;
	private char visualizar;
	private Collection<TipoDocumento> tipoDocumentos;
	private Integer max_file_size;
	private Character origem; 

	public String prepare() throws Exception
	{
		if(documentoAnexo.getOrigemId() == null || !origemAnexo.possuiPermissao(documentoAnexo.getOrigem(), solicitacaoId, ActionContext.getContext().getSession())){
			addActionMessage("Usuário sem permissão de acesso.");
			return Action.SUCCESS;
		}

		if(documentoAnexo.getOrigem() == OrigemAnexo.CANDIDATOEXTERNO){
			max_file_size  = parametrosDoSistemaManager.findByIdProjection(1L).getTamanhoMaximoUpload();
			documentoAnexo.setData(new Date());
		}else{
			etapaSeletivas = etapaSeletivaManager.findAllSelect(getEmpresaSistema().getId());
			tipoDocumentos = tipoDocumentoManager.findAll();
		}

		boolean isEdicao = documentoAnexo != null && documentoAnexo.getId() != null;
		
		if(isEdicao)
			documentoAnexo = documentoAnexoManager.findById(documentoAnexo.getId());
		
		titulo = documentoAnexoManager.getTituloEdit(documentoAnexo.getOrigem(), documentoAnexo.getOrigemId(), isEdicao);

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		try{
			if(!origemAnexo.possuiPermissao(documentoAnexo.getOrigem(), solicitacaoId, ActionContext.getContext().getSession()))
				throw new Exception();
			
			documentoAnexoManager.inserirDocumentoAnexo(origemAnexo.diretorioOrigemAnexo(documentoAnexo.getOrigem()), documentoAnexo, documento);
			return Action.SUCCESS;
		}catch (Exception e){
			addActionError("Não foi possível inserir o documento/anexo.");
			return Action.INPUT;
		}
	}

	public String update()
	{
		try{
			if(!origemAnexo.possuiPermissao(documentoAnexo.getOrigem(), solicitacaoId, ActionContext.getContext().getSession()))
				throw new Exception();
			
			checkCandidatoExterno();
			documentoAnexoManager.atualizarDocumentoAnexo(origemAnexo.diretorioOrigemAnexo(documentoAnexo.getOrigem()), documentoAnexo, documento);
			return Action.SUCCESS;
		}catch (FortesException e){
			e.printStackTrace();
			addActionWarning(e.getMessage());
			return Action.INPUT;
		}catch (Exception e){
			addActionError("Não foi possível atualizar o documento/anexo.");
			return Action.INPUT;
		}
	}

	public String showDocumento()
	{
		try {
			if(!origemAnexo.possuiPermissao(documentoAnexo.getOrigem(), solicitacaoId, ActionContext.getContext().getSession())){
				addActionMessage("Sem permissão de acesso.");
				return Action.SUCCESS;
			}
			
			checkCandidatoExterno();
			documentoAnexo = documentoAnexoManager.findById(documentoAnexo.getId());
			java.io.File file = null;
			if (documentoAnexo.getUrl() != null && !documentoAnexo.getUrl().equals("") && origemAnexo.containsKey(documentoAnexo.getOrigem())){
				file = ArquivoUtil.getArquivo(documentoAnexo.getUrl(),origemAnexo.diretorioOrigemAnexo(documentoAnexo.getOrigem()));
	
				com.fortes.model.type.File arquivo = new com.fortes.model.type.File();
				arquivo.setBytes(FileUtil.getFileBytes(file));
				arquivo.setName(file.getName());
				arquivo.setSize(file.length());
				int pos = arquivo.getName().indexOf(".");
				if(pos > 0)
					arquivo.setContentType(arquivo.getName().substring(pos));
	
				if (arquivo != null && arquivo.getBytes() != null){
					HttpServletResponse response = ServletActionContext.getResponse();
	
					response.addHeader("Expires", "0");
					response.addHeader("Pragma", "no-cache");
					response.addHeader("Content-type", arquivo.getContentType());
					response.addHeader("Content-Disposition", "attachment; filename=" + documentoAnexo.getDescricao() + arquivo.getContentType());
					response.addHeader("Content-Transfer-Encoding", "binary");
	
					response.getOutputStream().write(arquivo.getBytes());
				}
			}
		}catch (FortesException e){
			e.printStackTrace();
			addActionWarning(e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			addActionError("Ocorreu uma inconsistência ao exibir documento.");
		}

		return Action.SUCCESS;
	}
	
	private void checkCandidatoExterno() throws FortesException{
		if(origem == null)
			origem = documentoAnexo.getOrigem();
		
		if(origem == OrigemAnexo.CANDIDATOEXTERNO){
			DocumentoAnexo documentoAnexoTmp = documentoAnexoManager.findByIdProjection(documentoAnexo.getId());
			if (!ActionContext.getContext().getSession().get("SESSION_CANDIDATO_ID").equals(documentoAnexoTmp.getOrigemId()))
				throw new FortesException("O documento selecionado não consta na sua lista.");
		}
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

	public Collection<EtapaSeletiva> getEtapaSeletivas()
	{
		return etapaSeletivas;
	}

	public void setEtapaSeletivas(Collection<EtapaSeletiva> etapaSeletivas)
	{
		this.etapaSeletivas = etapaSeletivas;
	}

	public void setEtapaSeletivaManager(EtapaSeletivaManager etapaSeletivaManager)
	{
		this.etapaSeletivaManager = etapaSeletivaManager;
	}

	public com.fortes.model.type.File getDocumento()
	{
		return documento;
	}

	public void setDocumento(com.fortes.model.type.File documento)
	{
		this.documento = documento;
	}

	public Long getSolicitacaoId()
	{
		return solicitacaoId;
	}

	public void setSolicitacaoId(Long solicitacaoId)
	{
		this.solicitacaoId = solicitacaoId;
	}

	public int getPage()
	{
		return page;
	}

	public void setPage(int page)
	{
		this.page = page;
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

	public Collection<TipoDocumento> getTipoDocumentos() {
		return tipoDocumentos;
	}

	public void setTipoDocumentos(Collection<TipoDocumento> tipoDocumentos) {
		this.tipoDocumentos = tipoDocumentos;
	}

	public void setTipoDocumentoManager(TipoDocumentoManager tipoDocumentoManager) {
		this.tipoDocumentoManager = tipoDocumentoManager;
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

	public Integer getMax_file_size() {
		if (max_file_size == null)
			return 0;
		else
			return max_file_size;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public Character getOrigem() {
		return origem;
	}

	public void setOrigem(Character origem) {
		this.origem = origem;
	}
}
package com.fortes.rh.web.action.geral;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import com.fortes.model.type.FileUtil;
import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.business.geral.DocumentoAnexoManager;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

@SuppressWarnings({"serial"})
public class DocumentoAnexoEditAction extends MyActionSupportEdit
{
	private DocumentoAnexoManager documentoAnexoManager;
	private EtapaSeletivaManager etapaSeletivaManager;

	private DocumentoAnexo documentoAnexo;
	private Collection<EtapaSeletiva> etapaSeletivas;
	private com.fortes.model.type.File documento;

	private String diretorio;

	private char origemTmp;
	private Long origemIdTmp;
	private String nome;
	private Long solicitacaoId;


	//	Usados pela listagem de Candidatos para manter dados do filtro
	private int page;
	private Long etapaSeletivaId;
	private char visualizar;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(documentoAnexo != null && documentoAnexo.getId() != null)
		{	
			documentoAnexo = (DocumentoAnexo) documentoAnexoManager.findById(documentoAnexo.getId());
			origemIdTmp = documentoAnexo.getOrigemId();
			origemTmp = documentoAnexo.getOrigem();
		}
		else
		{
			documentoAnexo = new DocumentoAnexo();
			documentoAnexo.setOrigem(origemTmp);
			documentoAnexo.setOrigemId(origemIdTmp);
		}
		nome = documentoAnexoManager.getNome(documentoAnexo.getOrigem(), documentoAnexo.getOrigemId());
	}

	public String prepareInsertCandidato() throws Exception
	{
		prepare();
		etapaSeletivas = etapaSeletivaManager.findAllSelect(getEmpresaSistema().getId());
		return Action.SUCCESS;
	}

	public String prepareInsertColaborador() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdateCandidato() throws Exception
	{
		prepare();
		etapaSeletivas = etapaSeletivaManager.findAllSelect(getEmpresaSistema().getId());
		return Action.SUCCESS;
	}

	public String prepareUpdateColaborador() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	private String insert() throws Exception
	{
		try
		{
			documentoAnexoManager.inserirDocumentoAnexo(diretorio, documentoAnexo, documento);
			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			addActionError("Não foi possível inserir o documento.");
			return Action.INPUT;
		}
	}

	public String insertCandidato() throws Exception
	{
		diretorio = "documentosCandidatos";
		return insert();
	}

	public String insertColaborador() throws Exception
	{
		diretorio = "documentosColaboradores";
		return insert();
	}

	public String update() throws Exception
	{
		try
		{
			documentoAnexoManager.atualizarDocumentoAnexo(diretorio, documentoAnexo, documento);
			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			addActionError("Não foi possível editar o documento.");
			return Action.INPUT;
		}
	}

	public String updateCandidato() throws Exception
	{
		diretorio = "documentosCandidatos";
		return update();
	}

	public String updateColaborador() throws Exception
	{
		diretorio = "documentosColaboradores";
		return update();
	}

	public String showDocumento() throws Exception
	{
		documentoAnexo = documentoAnexoManager.findById(documentoAnexo.getId());
		java.io.File file = null;
		if (documentoAnexo.getUrl() != null && !documentoAnexo.getUrl().equals(""))
		{
			if(documentoAnexo.getOrigem() == 'D'){
				file = ArquivoUtil.getArquivo(documentoAnexo.getUrl(),"documentosColaboradores");
			}
			else if(documentoAnexo.getOrigem() == 'C'){
				file = ArquivoUtil.getArquivo(documentoAnexo.getUrl(),"documentosCandidatos");
			}
			
			if(file != null)
			{
				com.fortes.model.type.File arquivo = new com.fortes.model.type.File();
				arquivo.setBytes(FileUtil.getFileBytes(file));
				arquivo.setName(file.getName());
				arquivo.setSize(file.length());
				int pos = arquivo.getName().indexOf(".");
				if(pos > 0)
					arquivo.setContentType(arquivo.getName().substring(pos));
				
				if (arquivo != null && arquivo.getBytes() != null)
				{
					HttpServletResponse response = ServletActionContext.getResponse();

					response.addHeader("Expires", "0");
					response.addHeader("Pragma", "no-cache");
					response.addHeader("Content-type", arquivo.getContentType());
					response.addHeader("Content-Disposition", "filename=" + documentoAnexo.getDescricao() + arquivo.getContentType());
					response.addHeader("Content-Transfer-Encoding", "binary");

					response.getOutputStream().write(arquivo.getBytes());
				}
			}
		}

		return Action.SUCCESS;
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

	public Long getOrigemIdTmp()
	{
		return origemIdTmp;
	}

	public void setOrigemIdTmp(Long origemIdTmp)
	{
		this.origemIdTmp = origemIdTmp;
	}

	public char getOrigemTmp()
	{
		return origemTmp;
	}

	public void setOrigemTmp(char origemTmp)
	{
		this.origemTmp = origemTmp;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	}
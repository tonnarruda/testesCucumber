package com.fortes.rh.web.action.sesmt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.model.type.File;
import com.fortes.model.type.FileUtil;
import com.fortes.rh.business.sesmt.AnexoManager;
import com.fortes.rh.model.dicionario.OrigemAnexo;
import com.fortes.rh.model.sesmt.Anexo;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"serial"})
public class AnexoEditAction extends MyActionSupportEdit implements ModelDriven
{
	@Autowired private AnexoManager anexoManager;

	private File arquivo;
	private Anexo anexo;
	private String anexoDestino;

	private Collection<Anexo> anexos = new ArrayList<Anexo>();

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String showArquivo() throws Exception
	{
		anexo = anexoManager.findById(anexo.getId());
		java.io.File file = ArquivoUtil.getArquivo(anexo.getUrl(),"sesmt");
		File arquivo = new File();
		arquivo.setBytes(FileUtil.getFileBytes(file));
		arquivo.setName(file.getName());
		int pos = arquivo.getName().indexOf(".");
		if(pos > 0){
			arquivo.setContentType(arquivo.getName().substring(pos));
		}
		arquivo.setSize(file.length());


		if (arquivo != null && arquivo.getBytes() != null)
		{
			try
			{
				HttpServletResponse response = ServletActionContext.getResponse();
		        response.addHeader("Content-Disposition", "attachment; filename= " + arquivo.getName());
		        response.addHeader("Content-Length", arquivo.getSize().toString());
				response.addHeader("Content-type", arquivo.getContentType());
				response.addHeader("Content-Transfer-Encoding", "binary");
				response.getOutputStream().write(arquivo.getBytes());

			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(anexo != null && anexo.getId() != null)
			anexo = (Anexo) anexoManager.findById(anexo.getId());
		anexoDestino = (String) new OrigemAnexo().get(anexo.getOrigem());
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		anexos = anexoManager.findByOrigem(anexo.getOrigemId(), anexo.getOrigem());
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String voltarList() throws Exception
	{
		return anexoManager.getStringRetorno(anexo.getOrigem());
	}

	public String insert() throws Exception
	{
		if(arquivo != null && !arquivo.getName().trim().equals(""))
		{
			anexo = anexoManager.gravaAnexo(arquivo, anexo);

			if(anexo != null)
			{
				anexoManager.save(anexo);
				return anexoManager.getStringRetorno(anexo.getOrigem());
			}
		}

		addActionError("Não foi possível salvar este Anexo.");
		return Action.INPUT;
	}

	public String update() throws Exception
	{
		anexo = anexoManager.populaAnexo(anexo);
		anexoManager.update(anexo);

		return anexoManager.getStringRetorno(anexo.getOrigem());
	}


	public Object getModel()
	{
		return getAnexo();
	}

	public Anexo getAnexo()
	{
		if(anexo == null)
			anexo = new Anexo();
		return anexo;
	}

	public void setAnexo(Anexo anexo)
	{
		this.anexo = anexo;
	}

	public File getArquivo()
	{
		return arquivo;
	}

	public void setArquivo(File arquivo)
	{
		this.arquivo = arquivo;
	}

	public Collection<Anexo> getAnexos()
	{
		return anexos;
	}

	public void setAnexos(Collection<Anexo> anexos)
	{
		this.anexos = anexos;
	}

	public String getAnexoDestino()
	{
		return anexoDestino;
	}
}
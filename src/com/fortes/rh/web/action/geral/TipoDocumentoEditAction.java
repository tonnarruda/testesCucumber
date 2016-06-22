package com.fortes.rh.web.action.geral;


import java.util.Collection;

import com.fortes.rh.business.geral.TipoDocumentoManager;
import com.fortes.rh.model.geral.TipoDocumento;
import com.fortes.rh.util.ExceptionUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class TipoDocumentoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private TipoDocumentoManager tipoDocumentoManager;
	private TipoDocumento tipoDocumento;
	private Collection<TipoDocumento> tipoDocumentos;

	private void prepare() throws Exception
	{
		if(tipoDocumento != null && tipoDocumento.getId() != null)
			tipoDocumento = (TipoDocumento) tipoDocumentoManager.findById(tipoDocumento.getId());

	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		tipoDocumentoManager.save(tipoDocumento);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		tipoDocumentoManager.update(tipoDocumento);
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		tipoDocumentos = tipoDocumentoManager.findAll();
		
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			tipoDocumentoManager.remove(tipoDocumento.getId());
			addActionSuccess("Tipo do Documento excluído com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			ExceptionUtil.traduzirMensagem(this, e, "Não foi possível excluir este Tipo do Documento.");
		}

		return list();
	}
	
	public TipoDocumento getTipoDocumento()
	{
		if(tipoDocumento == null)
			tipoDocumento = new TipoDocumento();
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento)
	{
		this.tipoDocumento = tipoDocumento;
	}

	public void setTipoDocumentoManager(TipoDocumentoManager tipoDocumentoManager)
	{
		this.tipoDocumentoManager = tipoDocumentoManager;
	}
	
	public Collection<TipoDocumento> getTipoDocumentos()
	{
		return tipoDocumentos;
	}
}

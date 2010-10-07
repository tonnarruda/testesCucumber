package com.fortes.rh.web.action.sesmt;

import com.fortes.rh.business.sesmt.TipoEPIManager;
import com.fortes.rh.model.sesmt.TipoEPI;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;

@SuppressWarnings({"serial"})
public class TipoEPIEditAction extends MyActionSupportEdit
{
	private TipoEPIManager tipoEPIManager;

	private TipoEPI tipoEPI;

	private String msgAlert;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(tipoEPI != null && tipoEPI.getId() != null)
			tipoEPI = (TipoEPI) tipoEPIManager.findById(tipoEPI.getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		if(tipoEPI == null || tipoEPI.getId() == null || !tipoEPIManager.verifyExists(new String[]{"id","empresa.id"}, new Object[]{tipoEPI.getId(),getEmpresaSistema().getId()}))
		{
			msgAlert = "O Tipo de EPI solicitado não existe na empresa " + getEmpresaSistema().getNome() +".";
			return Action.ERROR;
		}
		prepare();

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		tipoEPI.setEmpresa(getEmpresaSistema());
		tipoEPIManager.save(tipoEPI);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		if(tipoEPI == null || tipoEPI.getId() == null || !tipoEPIManager.verifyExists(new String[]{"id","empresa.id"}, new Object[]{tipoEPI.getId(),getEmpresaSistema().getId()}))
		{
			addActionError("O Tipo de EPI solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
			return Action.ERROR;
		}
		tipoEPI.setEmpresa(getEmpresaSistema());

		tipoEPIManager.update(tipoEPI);
		return Action.SUCCESS;
	}

	public TipoEPI getTipoEPI()
	{
		if(tipoEPI == null)
			tipoEPI = new TipoEPI();
		return tipoEPI;
	}

	public void setTipoEPI(TipoEPI tipoEPI)
	{
		this.tipoEPI = tipoEPI;
	}

	public void setTipoEPIManager(TipoEPIManager tipoEPIManager)
	{
		this.tipoEPIManager = tipoEPIManager;
	}

	public String getMsgAlert()
	{
		return msgAlert;
	}

	public void setMsgAlert(String msgAlert)
	{
		this.msgAlert = msgAlert;
	}

}
package com.fortes.rh.web.action.sesmt;

import com.fortes.rh.business.sesmt.TamanhoEPIManager;
import com.fortes.rh.model.sesmt.TamanhoEPI;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;

@SuppressWarnings({"serial"})
public class TamanhoEPIEditAction extends MyActionSupportEdit
{
	private TamanhoEPIManager tamanhoEPIManager;

	private TamanhoEPI tamanhoEPI;

	private String msgAlert;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	private void prepare() throws Exception {
		if(tamanhoEPI != null && tamanhoEPI.getId() != null)
			tamanhoEPI = (TamanhoEPI) tamanhoEPIManager.findById(tamanhoEPI.getId());
	}

	public String prepareInsert() throws Exception {
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception {
		prepare();
		return Action.SUCCESS;
	}

	public String insert() throws Exception	{
		tamanhoEPIManager.save(tamanhoEPI);
		return Action.SUCCESS;
	}

	public String update() throws Exception {
		tamanhoEPIManager.update(tamanhoEPI);
		return Action.SUCCESS;
	}

	public TamanhoEPI getTamanhoEPI()
	{
		if(tamanhoEPI == null)
			tamanhoEPI = new TamanhoEPI();
		return tamanhoEPI;
	}

	public String getMsgAlert()
	{
		return msgAlert;
	}

	public void setMsgAlert(String msgAlert)
	{
		this.msgAlert = msgAlert;
	}

	public TamanhoEPIManager getTamanhoEPIManager() {
		return tamanhoEPIManager;
	}

	public void setTamanhoEPIManager(TamanhoEPIManager tamanhoEPIManager) {
		this.tamanhoEPIManager = tamanhoEPIManager;
	}

	public void setTamanhoEPI(TamanhoEPI tamanhoEPI) {
		this.tamanhoEPI = tamanhoEPI;
	}

}
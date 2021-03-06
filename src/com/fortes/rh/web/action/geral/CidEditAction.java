package com.fortes.rh.web.action.geral;

import java.util.Collection;

import com.fortes.rh.business.geral.CidManager;
import com.fortes.rh.model.geral.Cid;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;

@SuppressWarnings({"serial"})
public class CidEditAction extends MyActionSupportEdit
{
	private String json;
	private String codigo;
	private String descricao;
	private CidManager cidManager;
	private Collection<Cid> cbos;

	public String find() throws Exception
	{
		cbos = cidManager.buscaCids(codigo, descricao);
		
		json = StringUtil.toJSON(cbos, null);
		
		return Action.SUCCESS;
	}

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}
	
	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public void setCidManager(CidManager cidManager) {
		this.cidManager = cidManager;
	}

	public CidManager getCidManager() {
		return cidManager;
	}

	public Collection<Cid> getCbos() {
		return cbos;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
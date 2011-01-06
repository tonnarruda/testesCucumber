package com.fortes.rh.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.geral.CodigoCBOManager;
import com.fortes.rh.model.geral.CodigoCBO;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;

@SuppressWarnings({"serial"})
public class CodigoCBOEditAction extends MyActionSupportEdit
{
	private String json;
	private String codigo;
	private String descricao;
	private CodigoCBOManager codigoCBOManager;
	private Collection<CodigoCBO> cbos;

	public String find() throws Exception
	{
		cbos = codigoCBOManager.buscaCodigosCBO(codigo, descricao);
		
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

	public void setCodigoCBOManager(CodigoCBOManager codigoCBOManager) {
		this.codigoCBOManager = codigoCBOManager;
	}

	public CodigoCBOManager getCodigoCBOManager() {
		return codigoCBOManager;
	}

	public Collection<CodigoCBO> getCbos() {
		return cbos;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
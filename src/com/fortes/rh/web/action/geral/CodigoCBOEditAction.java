package com.fortes.rh.web.action.geral;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.CodigoCBOManager;
import com.fortes.rh.model.geral.AutoCompleteVO;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;

@SuppressWarnings({"serial"})
public class CodigoCBOEditAction extends MyActionSupportEdit
{
	private String json;
	private String codigo;
	private String descricao;
	@Autowired private CodigoCBOManager codigoCBOManager;

	public String find() throws Exception
	{
		Collection<AutoCompleteVO> cbos = codigoCBOManager.buscaCodigosCBO(descricao);
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

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
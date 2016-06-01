package com.fortes.rh.web.action.geral;

import java.util.Collection;

import org.apache.commons.io.FileUtils;

import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.ExceptionUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings("serial")
public class EmpresaListAction extends MyActionSupportList
{
	private EmpresaManager empresaManager;

	private Collection<Empresa> empresas;
	private Empresa empresa;
	private boolean usuarioFortes;
	private String json;
	private Long empresaId;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		usuarioFortes = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession()).getId().equals(1L); 
		empresas = empresaManager.findToList(new String[]{"id","nome","razaoSocial"}, new String[]{"id","nome","razaoSocial"}, new String[]{"nome"});

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		if(empresa.getId().equals(getEmpresaSistema().getId()))
		{
			addActionWarning("Não é possível excluir a empresa cujo você esta logado.");
			return Action.SUCCESS;			
		}
		
		try {
			if(SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession()).getId().equals(1L) || empresa.getId().equals(1L))
				empresaManager.removeEmpresa(empresaManager.findByIdProjection(empresa.getId()));
			else
				empresaManager.remove(new Long[]{empresa.getId()});
			
			addActionSuccess("Empresa excluída com sucesso.");
			
			java.io.File pastaExterna = new java.io.File(ArquivoUtil.getPathExternoEmpresa(empresa.getId()));
			if (pastaExterna.exists())
				FileUtils.deleteDirectory(pastaExterna);
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionUtil.traduzirMensagem(this, e, "Não foi possível excluir esta empresa.");
		}

		return Action.SUCCESS;
	}

	public Collection<Empresa> getEmpresas() {
		return empresas;
	}
	
	public Empresa getEmpresa(){
		if(empresa == null){
			empresa = new Empresa();
		}
		return empresa;
	}

	public void setEmpresa(Empresa empresa){
		this.empresa=empresa;
	}

	public void setEmpresaManager(EmpresaManager empresaManager){
		this.empresaManager=empresaManager;
	}

	public boolean isUsuarioFortes() {
		return usuarioFortes;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}
}
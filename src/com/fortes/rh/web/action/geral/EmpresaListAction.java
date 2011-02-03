package com.fortes.rh.web.action.geral;

import java.util.Collection;

import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class EmpresaListAction extends MyActionSupportList
{
	private EmpresaManager empresaManager;

	private Collection<Empresa> empresas;
	private Empresa empresa;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		empresas = empresaManager.findToList(new String[]{"id","nome","razaoSocial"}, new String[]{"id","nome","razaoSocial"}, new String[]{"nome"});

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		if(empresa.getId().equals(1L) && !empresa.getId().equals(getEmpresaSistema().getId()))
			empresaManager.removeEmpresaPadrao(1L);//só vai apagar se for a padrão, id=1 e caso o usuario não esteja logada nela
		else
			empresaManager.remove(new Long[]{empresa.getId()});
		
		addActionMessage("Empresa excluída com sucesso.");

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
}
package com.fortes.rh.web.action.cargosalario;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings("serial")
public class GrupoOcupacionalEditAction extends MyActionSupportEdit implements ModelDriven
{
	@Autowired private GrupoOcupacionalManager grupoOcupacionalManager;

	private GrupoOcupacional grupoOcupacional;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(grupoOcupacional != null && grupoOcupacional.getId() != null)
			grupoOcupacional = grupoOcupacionalManager.findByIdProjection(grupoOcupacional.getId());

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

	public String insert() throws Exception {

 		grupoOcupacional.setEmpresa(SecurityUtil.getEmpresaSession(ActionContext.getContext().getSession()));
		grupoOcupacionalManager.save(grupoOcupacional);

		return Action.SUCCESS;
	}

	public String update() throws Exception {

		grupoOcupacionalManager.update(grupoOcupacional);

		return Action.SUCCESS;
	}

	public GrupoOcupacional getGrupoOcupacional(){
		if(grupoOcupacional == null){
			grupoOcupacional = new GrupoOcupacional();
		}
		return grupoOcupacional;
	}

	public void setGrupoOcupacional(GrupoOcupacional grupoOcupacional){
		this.grupoOcupacional=grupoOcupacional;
	}

	public Object getModel()
	{
		return getGrupoOcupacional();
	}
}
package com.fortes.rh.web.action.cargosalario;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class GrupoOcupacionalListAction extends MyActionSupportList
{
	@Autowired private GrupoOcupacionalManager grupoOcupacionalManager;
	private Collection<GrupoOcupacional> grupoOcupacionals;
	private GrupoOcupacional grupoOcupacional;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		setTotalSize(grupoOcupacionalManager.getCount(getEmpresaSistema().getId()));
		grupoOcupacionals = grupoOcupacionalManager.findAllSelect(getPage(), getPagingSize(), getEmpresaSistema().getId());

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		grupoOcupacionalManager.remove(grupoOcupacional.getId());
		addActionMessage("Grupo Ocupacional excluído com sucesso.");

		return Action.SUCCESS;
	}

	public Collection<GrupoOcupacional> getGrupoOcupacionals() {
		return grupoOcupacionals;
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
}
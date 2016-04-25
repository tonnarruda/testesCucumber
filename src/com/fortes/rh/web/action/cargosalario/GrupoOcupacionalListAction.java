package com.fortes.rh.web.action.cargosalario;

import java.util.Collection;

import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.util.ExceptionUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class GrupoOcupacionalListAction extends MyActionSupportList
{
	private GrupoOcupacionalManager grupoOcupacionalManager;
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
		try {
			grupoOcupacionalManager.remove(grupoOcupacional.getId());
			addActionSuccess("Grupo Ocupacional excluído com sucesso.");
		} catch (Exception e) {
			ExceptionUtil.traduzirMensagem(this, e, "Não foi possível excluir este Grupo Ocupacional.");
			e.printStackTrace();
		}

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

	public void setGrupoOcupacionalManager(GrupoOcupacionalManager grupoOcupacionalManager){
		this.grupoOcupacionalManager=grupoOcupacionalManager;
	}
}
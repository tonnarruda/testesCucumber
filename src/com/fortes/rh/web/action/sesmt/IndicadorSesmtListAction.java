package com.fortes.rh.web.action.sesmt;


import com.fortes.rh.business.sesmt.CatManager;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class IndicadorSesmtListAction extends MyActionSupportList
{
	private CatManager catManager;
	
	private int qtdDiasSemAcidentes;
	
	public String painel()
	{
		Long empresaId = getEmpresaSistema().getId();
		
		qtdDiasSemAcidentes = catManager.findQtdDiasSemAcidentes(empresaId);
		
		return Action.SUCCESS;
	}

	public void setCatManager(CatManager catManager) {
		this.catManager = catManager;
	}

	public int getQtdDiasSemAcidentes() {
		return qtdDiasSemAcidentes;
	}
}
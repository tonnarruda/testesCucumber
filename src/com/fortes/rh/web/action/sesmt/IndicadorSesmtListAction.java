package com.fortes.rh.web.action.sesmt;


import java.util.Collection;
import java.util.Date;

import com.fortes.rh.business.sesmt.CatManager;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class IndicadorSesmtListAction extends MyActionSupportList
{
	private CatManager catManager;
	
	private Date dataDe;
	private Date dataAte;
	
	private int qtdDiasSemAcidentes;
	
	private String grfQtdCatsPorDiaSemana = "";
	
	public String painel()
	{
		Date hoje = new Date();
		if (dataAte == null)
			dataAte = hoje;
		if (dataDe == null)
			dataDe = DateUtil.retornaDataAnteriorQtdMeses(hoje, 12, true);
		
		Long empresaId = getEmpresaSistema().getId();
		
		qtdDiasSemAcidentes = catManager.findQtdDiasSemAcidentes(empresaId);
		
		Collection<DataGrafico> graficoQtdCatsPorDiaSemana = catManager.findQtdCatsPorDiaSemana(empresaId, dataDe, dataAte);
		grfQtdCatsPorDiaSemana = StringUtil.toJSON(graficoQtdCatsPorDiaSemana, null);
		
		return Action.SUCCESS;
	}

	public void setCatManager(CatManager catManager) {
		this.catManager = catManager;
	}

	public int getQtdDiasSemAcidentes() {
		return qtdDiasSemAcidentes;
	}

	public String getGrfQtdCatsPorDiaSemana() {
		return grfQtdCatsPorDiaSemana;
	}

	public Date getDataDe() {
		return dataDe;
	}

	public void setDataDe(Date dataDe) {
		this.dataDe = dataDe;
	}

	public Date getDataAte() {
		return dataAte;
	}

	public void setDataAte(Date dataAte) {
		this.dataAte = dataAte;
	}
}
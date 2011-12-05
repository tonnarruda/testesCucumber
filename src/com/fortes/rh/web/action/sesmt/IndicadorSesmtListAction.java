package com.fortes.rh.web.action.sesmt;


import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.rh.business.sesmt.CatManager;
import com.fortes.rh.business.sesmt.ColaboradorAfastamentoManager;
import com.fortes.rh.business.sesmt.ProntuarioManager;
import com.fortes.rh.business.sesmt.RealizacaoExameManager;
import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class IndicadorSesmtListAction extends MyActionSupportList
{
	private CatManager catManager;
	private ColaboradorAfastamentoManager colaboradorAfastamentoManager;
	private RealizacaoExameManager realizacaoExameManager;
	private ProntuarioManager prontuarioManager;
	
	private Date dataDe;
	private Date dataAte;
	
	private int qtdDiasSemAcidentes;
	private int qtdExamesRealizados;
	private int qtdProntuarios;
	private int qtdAfastamentosInss;
	private int qtdAfastamentosNaoInss;
	
	private String grfQtdCatsPorDiaSemana = "";
	private String grfQtdCatsPorHorario = "";
	private String grfQtdAfastamentosPorMotivo = "";
	private Collection<Exame> exames;
	
	public String painel()
	{
		Date hoje = new Date();
		if (dataAte == null)
			dataAte = hoje;
		if (dataDe == null)
			dataDe = DateUtil.retornaDataAnteriorQtdMeses(hoje, 12, true);
		
		Long empresaId = getEmpresaSistema().getId();
		
		qtdDiasSemAcidentes = catManager.findQtdDiasSemAcidentes(empresaId);
		qtdExamesRealizados = realizacaoExameManager.findQtdRealizados(empresaId, dataDe, dataAte);
		qtdProntuarios = prontuarioManager.findQtdByEmpresa(empresaId, dataDe, dataAte);
		qtdAfastamentosInss = colaboradorAfastamentoManager.findQtdAfastamentosInss(empresaId, dataDe, dataAte, true);
		qtdAfastamentosNaoInss = colaboradorAfastamentoManager.findQtdAfastamentosInss(empresaId, dataDe, dataAte, false);
		
		Collection<DataGrafico> graficoQtdCatsPorDiaSemana = catManager.findQtdCatsPorDiaSemana(empresaId, dataDe, dataAte);
		grfQtdCatsPorDiaSemana = StringUtil.toJSON(graficoQtdCatsPorDiaSemana, null);

		Collection<DataGrafico> graficoQtdCatsPorHorario = catManager.findQtdCatsPorHorario(empresaId, dataDe, dataAte);
		grfQtdCatsPorHorario = StringUtil.toJSON(graficoQtdCatsPorHorario, null);

		Collection<DataGrafico> graficoQtdAfastamentosPorMotivo = colaboradorAfastamentoManager.findQtdCatsPorDiaSemana(empresaId, dataDe, dataAte);
		grfQtdAfastamentosPorMotivo = StringUtil.toJSON(graficoQtdAfastamentosPorMotivo, null);
		
		exames = realizacaoExameManager.findQtdPorExame(empresaId, dataDe, dataAte);
		
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

	public String getGrfQtdAfastamentosPorMotivo() {
		return grfQtdAfastamentosPorMotivo;
	}

	public void setGrfQtdAfastamentosPorMotivo(String grfQtdAfastamentosPorMotivo) {
		this.grfQtdAfastamentosPorMotivo = grfQtdAfastamentosPorMotivo;
	}

	public void setColaboradorAfastamentoManager(ColaboradorAfastamentoManager colaboradorAfastamentoManager) {
		this.colaboradorAfastamentoManager = colaboradorAfastamentoManager;
	}

	public String getGrfQtdCatsPorHorario() {
		return grfQtdCatsPorHorario;
	}

	public void setRealizacaoExameManager(RealizacaoExameManager realizacaoExameManager) {
		this.realizacaoExameManager = realizacaoExameManager;
	}

	public int getQtdExamesRealizados() {
		return qtdExamesRealizados;
	}

	public void setProntuarioManager(ProntuarioManager prontuarioManager) {
		this.prontuarioManager = prontuarioManager;
	}

	public int getQtdProntuarios() {
		return qtdProntuarios;
	}

	public int getQtdAfastamentosInss() {
		return qtdAfastamentosInss;
	}

	public int getQtdAfastamentosNaoInss() {
		return qtdAfastamentosNaoInss;
	}

	public Collection<Exame> getExames() {
		return exames;
	}
}
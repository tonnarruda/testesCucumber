package com.fortes.rh.web.action.sesmt;


import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.sesmt.CatManager;
import com.fortes.rh.business.sesmt.ColaboradorAfastamentoManager;
import com.fortes.rh.business.sesmt.ProntuarioManager;
import com.fortes.rh.business.sesmt.RealizacaoExameManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings("serial")
public class IndicadorSesmtListAction extends MyActionSupportList
{
	@Autowired private CatManager catManager;
	@Autowired private ColaboradorAfastamentoManager colaboradorAfastamentoManager;
	@Autowired private RealizacaoExameManager realizacaoExameManager;
	@Autowired private ProntuarioManager prontuarioManager;
	@Autowired private EmpresaManager empresaManager;
	@Autowired private ParametrosDoSistemaManager parametrosDoSistemaManager;
	
	private Empresa empresa;
	
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
	
	private Collection<Empresa> empresas;
	private Collection<Exame> exames;
	
	private boolean compartilharColaboradores;
	
	public String painel()
	{
		Date hoje = new Date();
		if (dataAte == null)
			dataAte = hoje;
		if (dataDe == null)
			dataDe = DateUtil.retornaDataAnteriorQtdMeses(hoje, 12, true);
		
		if(empresa == null || empresa.getId() == null)
			empresa = getEmpresaSistema();
		
		compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
		empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores, getEmpresaSistema().getId(),SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_SESMT_PAINEL_IND");
		
		qtdDiasSemAcidentes = catManager.findQtdDiasSemAcidentes(empresa.getId());
		qtdExamesRealizados = realizacaoExameManager.findQtdRealizados(empresa.getId(), dataDe, dataAte);
		qtdProntuarios = prontuarioManager.findQtdByEmpresa(empresa.getId(), dataDe, dataAte);
		qtdAfastamentosInss = colaboradorAfastamentoManager.findQtdAfastamentosInss(empresa.getId(), dataDe, dataAte, true);
		qtdAfastamentosNaoInss = colaboradorAfastamentoManager.findQtdAfastamentosInss(empresa.getId(), dataDe, dataAte, false);
		
		Collection<DataGrafico> graficoQtdCatsPorDiaSemana = catManager.findQtdCatsPorDiaSemana(empresa.getId(), dataDe, dataAte);
		grfQtdCatsPorDiaSemana = StringUtil.toJSON(graficoQtdCatsPorDiaSemana, null);

		Collection<DataGrafico> graficoQtdCatsPorHorario = catManager.findQtdCatsPorHorario(empresa.getId(), dataDe, dataAte);
		grfQtdCatsPorHorario = StringUtil.toJSON(graficoQtdCatsPorHorario, null);

		Collection<DataGrafico> graficoQtdAfastamentosPorMotivo = colaboradorAfastamentoManager.findQtdCatsPorDiaSemana(empresa.getId(), dataDe, dataAte);
		grfQtdAfastamentosPorMotivo = StringUtil.toJSON(graficoQtdAfastamentosPorMotivo, null);
		
		exames = realizacaoExameManager.findQtdPorExame(empresa.getId(), dataDe, dataAte);
		
		return Action.SUCCESS;
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

	public String getGrfQtdCatsPorHorario() {
		return grfQtdCatsPorHorario;
	}

	public int getQtdExamesRealizados() {
		return qtdExamesRealizados;
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

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Collection<Empresa> getEmpresas() {
		return empresas;
	}
}
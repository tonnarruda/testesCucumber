package com.fortes.rh.web.action.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.captacao.HistoricoCandidatoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.captacao.relatorio.ProdutividadeRelatorio;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupport;
import com.opensymphony.xwork.Action;

public class ProdutividadeReportAction extends MyActionSupport
{
	private Collection<ProdutividadeRelatorio> dataSource = new ArrayList<ProdutividadeRelatorio>();

	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private HistoricoCandidatoManager historicoCandidatoManager;

	private ParametrosDoSistema parametrosDoSistema;
	private Map parametros = new HashMap();

	private String  ano;

	public String prepareProdutividade() throws Exception
	{
		return Action.SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public String imprimirRelatorioProdutividade() throws Exception
	{
		if (ano == null || ano.trim().equals(""))
		{
			addActionError("Informe o ano.");
			return Action.INPUT;
		}
		setDataSource(historicoCandidatoManager.getProdutividade(ano, getEmpresaSistema().getId()));

		try
		{
			parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Produtividade Anual - " + ano, getEmpresaSistema(), "");

		}
		catch (Exception e)
		{
			e.printStackTrace();
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}

	public String getAno()
	{
		return ano;
	}

	public void setAno(String ano)
	{
		this.ano = ano;
	}

	public Collection<ProdutividadeRelatorio> getDataSource()
	{
		return dataSource;
	}

	public void setDataSource(Collection<ProdutividadeRelatorio> dataSource)
	{
		this.dataSource = dataSource;
	}

	public Map getParametros()
	{
		return parametros;
	}

	public void setParametros(Map parametros)
	{
		this.parametros = parametros;
	}

	public ParametrosDoSistema getParametrosDoSistema()
	{
		return parametrosDoSistema;
	}

	public void setParametrosDoSistema(ParametrosDoSistema parametrosDoSistema)
	{
		this.parametrosDoSistema = parametrosDoSistema;
	}

	public void setHistoricoCandidatoManager(HistoricoCandidatoManager historicoCandidatoManager)
	{
		this.historicoCandidatoManager = historicoCandidatoManager;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public HistoricoCandidatoManager getHistoricoCandidatoManager()
	{
		return historicoCandidatoManager;
	}

	public ParametrosDoSistemaManager getParametrosDoSistemaManager()
	{
		return parametrosDoSistemaManager;
	}
}
package com.fortes.rh.web.action.sesmt;


import java.util.Collection;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiItemManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;

@SuppressWarnings({"serial"})
public class SolicitacaoEpiEditAction extends MyActionSupportEdit
{
	private SolicitacaoEpiManager solicitacaoEpiManager;
	private ColaboradorManager colaboradorManager;
	private EpiManager epiManager;
	private SolicitacaoEpiItemManager solicitacaoEpiItemManager;

	private Colaborador colaborador;
	private SolicitacaoEpi solicitacaoEpi;

	private Collection<Colaborador> colaboradors;
	private Collection<SolicitacaoEpiItem> solicitacaoEpiItems;

	private Object[][] listaEpis;
	private String[] selectQtdSolicitado;
	private String[] selectDataSolicitado;
	private String[] selectQtdEntregue;
	private String[] epiIds;


	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(solicitacaoEpi != null && solicitacaoEpi.getId() != null)
			solicitacaoEpi = (SolicitacaoEpi) solicitacaoEpiManager.findById(solicitacaoEpi.getId());

		Collection<Epi> epis = epiManager.find(new String[]{"empresa.id"}, new Object[]{getEmpresaSistema().getId()}, new String[]{"nome"});

		listaEpis = new Object[epis.size()][2];
		int i=0;

		for (Epi epi : epis)
		{
			listaEpis[i][0] = epi;
			listaEpis[i][1] = new SolicitacaoEpiItem();
			i++;
		}
	}

	public String filtroColaboradores() throws Exception
	{
		prepare();
		colaborador.setPessoalCpf(StringUtil.removeMascara(colaborador.getPessoal().getCpf()));
		colaboradors = colaboradorManager.findByNomeCpfMatricula(colaborador, getEmpresaSistema().getId(), false);
		if (colaboradors == null || colaboradors.isEmpty())
			addActionMessage("Nenhum colaborador para o filtro informado.");

		return SUCCESS;
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		solicitacaoEpiItems = solicitacaoEpiItemManager.findBySolicitacaoEpi(solicitacaoEpi.getId());
		epiIds = new String[solicitacaoEpiItems.size()];

		// Preparando os EPIs marcados e as quantidades solicitadas
		int j=0;
		for (int i=0; i<listaEpis.length; i++)
		{
			Epi epi = (Epi)listaEpis[i][0];
			for (SolicitacaoEpiItem item : solicitacaoEpiItems)
			{
				if (item.getEpi().getId().equals(epi.getId()))
				{
					epiIds[j] = epi.getId().toString();
					j++;
					listaEpis[i][1] = item;
				}
			}
		}

		return Action.SUCCESS;
	}

	public String prepareEntrega() throws Exception
	{
		if(solicitacaoEpi != null && solicitacaoEpi.getId() != null)
			solicitacaoEpi = (SolicitacaoEpi) solicitacaoEpiManager.findById(solicitacaoEpi.getId());

		solicitacaoEpiItems = solicitacaoEpiItemManager.findBySolicitacaoEpi(solicitacaoEpi.getId());

		listaEpis = new Object[solicitacaoEpiItems.size()][2];
		int k=0;

		for (SolicitacaoEpiItem item : solicitacaoEpiItems)
		{
			listaEpis[k][0] = item.getEpi();
			listaEpis[k][1] = item;
			k++;
		}

		return Action.SUCCESS;
	}

	public String entrega() throws Exception
	{
		try
		{
			solicitacaoEpiManager.entrega(solicitacaoEpi, epiIds, selectQtdSolicitado, selectDataSolicitado);
		}
		catch (Exception e)
		{
			addActionError("Erro ao gravar entrega.");
			e.printStackTrace();
			prepareEntrega();
			return INPUT;
		}

		addActionMessage("Entrega gravada com sucesso.");
		return SUCCESS;
	}

	public String insert() throws Exception
	{
		try
		{
			this.colaborador = colaboradorManager.findByIdDadosBasicos(solicitacaoEpi.getColaborador().getId());
			solicitacaoEpi.setCargo(colaborador.getFaixaSalarial().getCargo());
			solicitacaoEpi.setEmpresa(getEmpresaSistema());

			solicitacaoEpiManager.save(solicitacaoEpi, epiIds, selectQtdSolicitado);
		}
		catch (Exception e)
		{
			addActionError("Erro ao gravar solicitação.");
			e.printStackTrace();
			prepare();
			return INPUT;
		}

		addActionMessage("Solicitação gravada com sucesso.");
		return SUCCESS;
	}

	public String update() throws Exception
	{
		solicitacaoEpiManager.update(solicitacaoEpi, epiIds, selectQtdSolicitado);
		return SUCCESS;
	}

	public SolicitacaoEpi getSolicitacaoEpi()
	{
		if(solicitacaoEpi == null)
			solicitacaoEpi = new SolicitacaoEpi();
		return solicitacaoEpi;
	}

	public void setSolicitacaoEpi(SolicitacaoEpi solicitacaoEpi)
	{
		this.solicitacaoEpi = solicitacaoEpi;
	}

	public void setSolicitacaoEpiManager(SolicitacaoEpiManager solicitacaoEpiManager)
	{
		this.solicitacaoEpiManager = solicitacaoEpiManager;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public Collection<Colaborador> getColaboradors()
	{
		return colaboradors;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setEpiManager(EpiManager epiManager)
	{
		this.epiManager = epiManager;
	}

	public Object[][] getListaEpis()
	{
		return listaEpis;
	}

	public String[] getSelectQtdSolicitado()
	{
		return selectQtdSolicitado;
	}

	public void setSelectQtdSolicitado(String[] selectQtdSolicitado)
	{
		this.selectQtdSolicitado = selectQtdSolicitado;
	}

	public String[] getEpiIds()
	{
		return epiIds;
	}

	public void setEpiIds(String[] epiIds)
	{
		this.epiIds = epiIds;
	}

	public String[] getSelectQtdEntregue()
	{
		return selectQtdEntregue;
	}

	public void setSelectQtdEntregue(String[] selectQtdEntregue)
	{
		this.selectQtdEntregue = selectQtdEntregue;
	}

	public Collection<SolicitacaoEpiItem> getSolicitacaoEpiItems()
	{
		return solicitacaoEpiItems;
	}

	public void setSolicitacaoEpiItemManager(SolicitacaoEpiItemManager solicitacaoEpiItemManager)
	{
		this.solicitacaoEpiItemManager = solicitacaoEpiItemManager;
	}

	public String[] getSelectDataSolicitado() {
		return selectDataSolicitado;
	}

	public void setSelectDataSolicitado(String[] selectDataSolicitado) {
		this.selectDataSolicitado = selectDataSolicitado;
	}
}
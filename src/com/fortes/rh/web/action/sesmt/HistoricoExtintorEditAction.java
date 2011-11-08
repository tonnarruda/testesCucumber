package com.fortes.rh.web.action.sesmt;

import java.util.Collection;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.ExtintorManager;
import com.fortes.rh.business.sesmt.HistoricoExtintorManager;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.HistoricoExtintor;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class HistoricoExtintorEditAction extends MyActionSupportEdit
{
	private HistoricoExtintorManager historicoExtintorManager;
	private ExtintorManager extintorManager;
	private EstabelecimentoManager estabelecimentoManager;
	
	private HistoricoExtintor historicoExtintor;
	private Extintor extintor;
	
	private Collection<HistoricoExtintor> historicoExtintores;
	private Collection<Estabelecimento> estabelecimentos;
	private String localizacoes = "";

	public String prepare() throws Exception
	{
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		localizacoes = extintorManager.getLocalizacoes(getEmpresaSistema().getId());

		if (historicoExtintor != null && historicoExtintor.getId() != null)
			historicoExtintor = (HistoricoExtintor) historicoExtintorManager.findById(historicoExtintor.getId());
		else
			historicoExtintor = new HistoricoExtintor();
		
		extintor = extintorManager.findById(extintor.getId());

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		historicoExtintor.setExtintor(extintor);
		historicoExtintorManager.save(historicoExtintor);
		
		return Action.SUCCESS;
	}
	
	public String update() throws Exception
	{
		historicoExtintorManager.update(historicoExtintor);
		return Action.SUCCESS;
	}
	
	public String delete() throws Exception
	{
		historicoExtintorManager.remove(historicoExtintor.getId());
		return Action.SUCCESS;
	}

	public void setHistoricoExtintorManager(HistoricoExtintorManager historicoExtintorManager) {
		this.historicoExtintorManager = historicoExtintorManager;
	}

	public void setExtintorManager(ExtintorManager extintorManager) {
		this.extintorManager = extintorManager;
	}

	public Extintor getExtintor() {
		return extintor;
	}

	public void setExtintor(Extintor extintor) {
		this.extintor = extintor;
	}

	public String getLocalizacoes() {
		return localizacoes;
	}

	public Collection<Estabelecimento> getEstabelecimentos() {
		return estabelecimentos;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public HistoricoExtintor getHistoricoExtintor() {
		return historicoExtintor;
	}

	public void setHistoricoExtintor(HistoricoExtintor historicoExtintor) {
		this.historicoExtintor = historicoExtintor;
	}

	public Collection<HistoricoExtintor> getHistoricoExtintores() {
		return historicoExtintores;
	}
}
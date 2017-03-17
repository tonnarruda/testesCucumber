package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.ExtintorManager;
import com.fortes.rh.business.sesmt.HistoricoExtintorManager;
import com.fortes.rh.model.dicionario.TipoExtintor;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.HistoricoExtintor;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class HistoricoExtintorEditAction extends MyActionSupportList
{
	@Autowired private HistoricoExtintorManager historicoExtintorManager;
	@Autowired private ExtintorManager extintorManager;
	@Autowired private EstabelecimentoManager estabelecimentoManager;
	
	private HistoricoExtintor historicoExtintor;
	private Extintor extintor;
	private Map<String,String> tipos = new TipoExtintor();
	
	private Collection<HistoricoExtintor> historicoExtintores;
	private Collection<Estabelecimento> estabelecimentos;
	private Long[] extintorsCheck;
	private Collection<CheckBox> extintorsCheckList = new ArrayList<CheckBox>();
	private String localizacoes = "";
	private boolean troca;
	
	private Date data;
	private String tipo;
	private String localizacao;
	private Long estabelecimentoId;
	
	public String list()
	{
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		setTotalSize(historicoExtintorManager.countAllHistoricosAtuais(data, localizacao, tipo, estabelecimentoId, getEmpresaSistema().getId()));
		historicoExtintores = historicoExtintorManager.findAllHistoricosAtuais(getPage(), getPagingSize(), data, localizacao, tipo, estabelecimentoId, getEmpresaSistema().getId());
		return SUCCESS;
	}

	public String prepareInsertTroca() throws Exception
	{
		Collection<Extintor> extintors = extintorManager.findAllComHistAtual(null, null, getEmpresaSistema().getId());
		extintorsCheckList = CheckListBoxUtil.populaCheckListBox(extintors, "getId", "getDescricaoMaisLocalizacao", null);

		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		localizacoes = extintorManager.getLocalizacoes(getEmpresaSistema().getId());

		if (historicoExtintor != null && historicoExtintor.getId() != null)
			historicoExtintor = (HistoricoExtintor) historicoExtintorManager.findById(historicoExtintor.getId());
		else
			historicoExtintor = new HistoricoExtintor();
		
		return Action.SUCCESS;
	}

	public String troca() throws Exception
	{
		historicoExtintorManager.insertNewHistoricos(extintorsCheck, historicoExtintor);
		return Action.SUCCESS;
	}
	
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
		
		if(troca)
			return "successTroca";
		
		return Action.SUCCESS;
	}
	
	public String delete() throws Exception
	{
		if(extintor != null && extintor.getId()!=null  && (historicoExtintorManager.findByExtintor(extintor.getId()).size() <= 1))
			addActionError("Historico do extintor não pode ser excluido devido o extintor possui apenas um único histórico.");
		else
			historicoExtintorManager.remove(historicoExtintor.getId());
		
		if(troca)
			return "successTroca";
		
		return Action.SUCCESS;
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

	public HistoricoExtintor getHistoricoExtintor() {
		return historicoExtintor;
	}

	public void setHistoricoExtintor(HistoricoExtintor historicoExtintor) {
		this.historicoExtintor = historicoExtintor;
	}

	public Collection<HistoricoExtintor> getHistoricoExtintores() {
		return historicoExtintores;
	}


	public void setData(Date data) {
		this.data = data;
	}


	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}


	public void setEstabelecimentoId(Long estabelecimentoId) {
		this.estabelecimentoId = estabelecimentoId;
	}

	public Date getData() {
		return data;
	}


	public String getLocalizacao() {
		return localizacao;
	}


	public Long getEstabelecimentoId() {
		return estabelecimentoId;
	}

	public void setExtintorsCheck(Long[] extintorsCheck) {
		this.extintorsCheck = extintorsCheck;
	}

	public Collection<CheckBox> getExtintorsCheckList() {
		return extintorsCheckList;
	}

	public Map<String, String> getTipos() {
		return tipos;
	}

	public void setTipos(Map<String, String> tipos) {
		this.tipos = tipos;
	}

	public boolean isTroca() {
		return troca;
	}

	public void setTroca(boolean troca) {
		this.troca = troca;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
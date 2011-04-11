package com.fortes.rh.web.action.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.business.captacao.HistoricoCandidatoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.dicionario.Apto;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings("serial")
public class HistoricoCandidatoEditAction extends MyActionSupportEdit implements ModelDriven
{
	private HistoricoCandidatoManager historicoCandidatoManager;
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	private EtapaSeletivaManager etapaSeletivaManager;
	private CandidatoManager candidatoManager;
	private SolicitacaoManager solicitacaoManager;

	private CandidatoSolicitacao candidatoSol;
	private HistoricoCandidato historicoCandidato;

	private Collection<HistoricoCandidato> historicoCandidatos;
	private Collection<EtapaSeletiva> etapas = new ArrayList<EtapaSeletiva>();
	private Collection<EtapaSeletiva> etapaCargos = new ArrayList<EtapaSeletiva>();

	private boolean blacklist = false;

	private Solicitacao solicitacao;
    private String[] candidatosCheck;
    private Collection<CheckBox> candidatosCheckList = new ArrayList<CheckBox>();

	//dados propagados para manter no ftl (candidatos da solicitação)
    private Long etapaSeletivaId;
	private char visualizar;
	private int page;
	private String indicadoPor;
	private String responsaveis = "";
	private Map aptos;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(historicoCandidato != null && historicoCandidato.getId() != null)
			historicoCandidato = historicoCandidatoManager.findByIdProjection(historicoCandidato.getId());

		if(candidatoSol != null && candidatoSol.getId() != null)
		{
			candidatoSol = candidatoSolicitacaoManager.findByCandidatoSolicitacao(candidatoSol);
			solicitacao = candidatoSol.getSolicitacao();
		}

		etapas = etapaSeletivaManager.findAllSelect(getEmpresaSistema().getId());
		etapaCargos = etapaSeletivaManager.findByCargo(solicitacao.getFaixaSalarial().getCargo().getId());
		
		aptos = new Apto();
		
		String[] resps = historicoCandidatoManager.findResponsaveis();
		if(resps != null)
			responsaveis = StringUtil.toJSON(resps, null);
	}

	public String prepareInsert() throws Exception
	{
		if(solicitacao != null && solicitacao.getId() != null)
		{
			solicitacao = solicitacaoManager.getValor(solicitacao.getId());
			Collection<CandidatoSolicitacao> candidatoSolicitacaos = candidatoSolicitacaoManager.getCandidatoSolicitacaoList(null, null, solicitacao.getId(), null, null, true, true, true, null, null);

			candidatosCheckList = CheckListBoxUtil.populaCheckListBox(candidatoSolicitacaos, "getId", "getCandidatoNome");
		}

		prepare();
		return Action.SUCCESS;
	}

	public String insertGrupoCandidatos()
	{
		try
		{
			historicoCandidatoManager.saveHistoricos(historicoCandidato, candidatosCheck, blacklist);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			setActionMsg("Não foi possível inserir os Históricos");
		}

		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();

		blacklist = candidatoSol.getCandidato().isBlackList();

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		historicoCandidato.setCandidatoSolicitacao(candidatoSol);
		historicoCandidatoManager.save(historicoCandidato);

		candidatoManager.setBlackList(historicoCandidato, candidatoSol.getId(), blacklist);

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		historicoCandidato.setCandidatoSolicitacao(candidatoSol);
		historicoCandidatoManager.update(historicoCandidato);

		candidatoSol = candidatoSolicitacaoManager.findCandidatoSolicitacaoById(candidatoSol.getId());

		if (blacklist)
			candidatoManager.updateBlackList(historicoCandidato.getObservacao(), blacklist, candidatoSol.getCandidato().getId());
		else if(candidatoSol.getCandidato().isBlackList())
			candidatoManager.updateBlackList("", blacklist, candidatoSol.getCandidato().getId());

		return Action.SUCCESS;
	}

	public HistoricoCandidato getHistoricoCandidato()
	{
		if (historicoCandidato == null)
			historicoCandidato = new HistoricoCandidato();
		return historicoCandidato;
	}

	public void setHistoricoCandidato(HistoricoCandidato historicoCandidato)
	{
		this.historicoCandidato = historicoCandidato;
	}

	public Object getModel()
	{
		return getHistoricoCandidato();
	}

	public Collection<HistoricoCandidato> getHistoricoCandidatos()
	{
		return historicoCandidatos;
	}

	public void setHistoricoCandidatos(Collection<HistoricoCandidato> historicoCandidatos)
	{
		this.historicoCandidatos = historicoCandidatos;
	}

	public void setCandidatoSolicitacaoManager(CandidatoSolicitacaoManager candidatoSolicitacaoManager)
	{
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
	}

	public CandidatoSolicitacao getCandidatoSol()
	{
		return candidatoSol;
	}

	public void setCandidatoSol(CandidatoSolicitacao candidatoSol)
	{
		this.candidatoSol = candidatoSol;
	}

	public Collection<EtapaSeletiva> getEtapas()
	{
		return etapas;
	}

	public void setEtapas(Collection<EtapaSeletiva> etapas)
	{
		this.etapas = etapas;
	}

	public void setEtapaSeletivaManager(EtapaSeletivaManager etapaSeletivaManager)
	{
		this.etapaSeletivaManager = etapaSeletivaManager;
	}

	public void setHistoricoCandidatoManager(HistoricoCandidatoManager historicoCandidatoManager)
	{
		this.historicoCandidatoManager = historicoCandidatoManager;
	}

	public boolean isBlacklist()
	{
		return blacklist;
	}

	public void setBlacklist(boolean blacklist)
	{
		this.blacklist = blacklist;
	}

	public void setCandidatoManager(CandidatoManager candidatoManager)
	{
		this.candidatoManager = candidatoManager;
	}

	public Solicitacao getSolicitacao()
	{
		return solicitacao;
	}

	public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager)
	{
		this.solicitacaoManager = solicitacaoManager;
	}

	public void setSolicitacao(Solicitacao solicitacao)
	{
		this.solicitacao = solicitacao;
	}

	public String[] getCandidatosCheck()
	{
		return candidatosCheck;
	}

	public void setCandidatosCheck(String[] candidatosCheck)
	{
		this.candidatosCheck = candidatosCheck;
	}

	public Collection<CheckBox> getCandidatosCheckList()
	{
		return candidatosCheckList;
	}

	public void setCandidatosCheckList(Collection<CheckBox> candidatosCheckList)
	{
		this.candidatosCheckList = candidatosCheckList;
	}

	public Long getEtapaSeletivaId()
	{
		return etapaSeletivaId;
	}

	public void setEtapaSeletivaId(Long etapaSeletivaId)
	{
		this.etapaSeletivaId = etapaSeletivaId;
	}

	public int getPage()
	{
		return page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public char getVisualizar()
	{
		return visualizar;
	}

	public void setVisualizar(char visualizar)
	{
		this.visualizar = visualizar;
	}

	public String getIndicadoPor()
	{
		return indicadoPor;
	}

	public void setIndicadoPor(String indicadoPor)
	{
		this.indicadoPor = indicadoPor;
	}

	public Collection<EtapaSeletiva> getEtapaCargos() 
	{
		return etapaCargos;
	}
	public void setEtapaCargos(Collection<EtapaSeletiva> etapaCargos) 
	{
		this.etapaCargos = etapaCargos;
	}
	public String getResponsaveis() 
	{
		return responsaveis;
	}

	public Map getAptos() {
		return aptos;
	}
	
}
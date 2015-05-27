package com.fortes.rh.web.action.captacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.business.captacao.HistoricoCandidatoManager;
import com.fortes.rh.business.captacao.SolicitacaoAvaliacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.SolicitacaoAvaliacao;
import com.fortes.rh.model.dicionario.SolicitacaoHistoricoColaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class CandidatoSolicitacaoListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;

	private SolicitacaoManager solicitacaoManager;
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	private HistoricoCandidatoManager historicoCandidatoManager;
	private EtapaSeletivaManager etapaSeletivaManager;
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private SolicitacaoAvaliacaoManager solicitacaoAvaliacaoManager;
	
	private Collection<CandidatoSolicitacao> candidatoSolicitacaos;
	private Collection<Solicitacao> solicitacaos;
	private Collection<HistoricoCandidato> historicoCandidatos;
	private Collection<SolicitacaoHistoricoColaborador> historicos;
	private Collection<EtapaSeletiva> etapas;
	private Collection<SolicitacaoAvaliacao> solicitacaoAvaliacaos;
	private Collection<ColaboradorQuestionario> colaboradorQuestionarios;

	private CandidatoSolicitacao candidatoSolicitacao;
	private Long[] candidatoSolicitacaoIdsSelecionados;
	private Solicitacao solicitacao;
	private Solicitacao solicitacaoDestino;
	private Candidato candidato;
	private Long[] candidatosId;
	private String indicadoPor;
	private String observacaoRH;
	private String nomeBusca;
	private boolean existeCompetenciaParaFaixa;
	private RelatorioCandidatoSolicitacaoList relatorioCandidatoSolicitacaoList;

	private Long etapaSeletivaId;//usado no ftl para manter dados do filtro
	private char visualizar;

	// Utilizado no ftl para escapar nomes de candidatos contendo apóstrofos
	private StringUtil stringUtil = new StringUtil();

	public String list() throws Exception
	{
		setVideoAjuda(841L);
		
		etapas = etapaSeletivaManager.findAllSelect(getEmpresaSistema().getId());
		solicitacao = solicitacaoManager.getValor(solicitacao.getId());
		
		setTotalSize(candidatoSolicitacaoManager.getCount(solicitacao.getId(), etapaSeletivaId, indicadoPor, getValueApto(visualizar), false, observacaoRH, nomeBusca));
		candidatoSolicitacaos = candidatoSolicitacaoManager.getCandidatoSolicitacaoList(getPage(), getPagingSize(), solicitacao.getId(), etapaSeletivaId, indicadoPor, getValueApto(visualizar), false, true, observacaoRH, nomeBusca, visualizar);

		solicitacaoAvaliacaos = solicitacaoAvaliacaoManager.findBySolicitacaoId(solicitacao.getId(), null);
		
		if(candidatoSolicitacaos == null || candidatoSolicitacaos.size() == 0)
		{
			if (getPage() > 1)
			{
				setPage(getPage()-1);
				candidatoSolicitacaos = candidatoSolicitacaoManager.getCandidatoSolicitacaoList(getPage(), getPagingSize(), solicitacao.getId(), etapaSeletivaId, indicadoPor, getValueApto(visualizar), false, false, observacaoRH, null, null);
			}
			else
				addActionMessage("Não existem candidatos para o filtro informado.");
		}


		if(getActionMsg() != null && !getActionMsg().equals("") && (getActionMessages() != null && getActionMessages().toArray()[0].equals("")))
		{
			addActionError(getActionMsg());
			getActionMessages().clear();
		}

		//Não sei pq essa ActionMessages vem vazia "" (ajuste tecnico) Fco Barroso
		if(getActionMessages() == null || getActionMessages().isEmpty() || getActionMessages().toArray()[0].equals(""))
		{
			setActionMessages(null);
		}

		existeCompetenciaParaFaixa = !configuracaoNivelCompetenciaManager.findByFaixa(solicitacao.getFaixaSalarial().getId(), new Date()).isEmpty();
		
		return Action.SUCCESS;
	}

	private Boolean getValueApto(char visual)
	{
		if(visual == 'A')
			return true;
		else if(visual == 'N')
			return false;

		return null;
	}

	public String listTriagem() throws Exception
	{
		solicitacao = solicitacaoManager.getValor(solicitacao.getId());
		candidatoSolicitacaos = candidatoSolicitacaoManager.findBySolicitacaoTriagem(solicitacao.getId());

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		candidatoSolicitacao = candidatoSolicitacaoManager.findCandidatoSolicitacaoById(candidatoSolicitacao.getId());
		
		historicoColaboradorManager.removeCandidatoSolicitacao(candidatoSolicitacao.getId());
		historicoCandidatoManager.removeByCandidatoSolicitacao(candidatoSolicitacao.getId());
		
		candidatoSolicitacaoManager.remove(new Long[]{candidatoSolicitacao.getId()});
		solicitacao = candidatoSolicitacao.getSolicitacao();

		return Action.SUCCESS;
	}

	public String removerTriagem() throws Exception
	{
		if (candidatoSolicitacaoIdsSelecionados != null && candidatoSolicitacaoIdsSelecionados.length > 0) {
			candidatoSolicitacaoManager.updateTriagem(candidatoSolicitacaoIdsSelecionados, false);
		}

		return Action.SUCCESS;
	}

	public String verHistoricoCandidato() throws Exception
	{
		//candidatoSolicitacaos = candidatoSolicitacaoManager.find(new String[]{"candidato.id"}, new Object[]{candidato.getId()}, new String[]{"solicitacao.data asc"});
		historicoCandidatos = historicoCandidatoManager.findByCandidato(candidato);
		historicos = historicoCandidatoManager.montaMapaHistorico(historicoCandidatos);

		return Action.SUCCESS;
	}

	public String prepareMover() throws Exception
	{
		solicitacao = solicitacaoManager.getValor(solicitacao.getId());

		candidatoSolicitacaos = candidatoSolicitacaoManager.getCandidatoSolicitacaoList(null, null, solicitacao.getId(), null, null, null, false, true, observacaoRH, null, null);
		
		if(candidatoSolicitacaos.size() == 0)
			setActionMsg("Não existem candidatos a serem transferidos.");

		solicitacaos = solicitacaoManager.findSolicitacaoList(null, false, null, false);
		solicitacaos.remove(solicitacao);

		return Action.SUCCESS;
	}

	public String mover() throws Exception
	{
		try
		{
			candidatoSolicitacaoManager.moverCandidatos(candidatosId, solicitacaoDestino);			
		} catch (ColecaoVaziaException e)
		{
			//faz nada
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Erro ao mover candidatos.");
		}
		
		return Action.SUCCESS;
	}
	
	public String popupAvaliacoesCandidatoSolicitacao()
	{
		colaboradorQuestionarios = candidatoSolicitacaoManager.findAvaliacoesCandidatoSolicitacao(solicitacao.getId(), candidato.getId());
		
		return Action.SUCCESS;
	}

	public String removerCandidatosDaSolicitacao()
	{
		try {
			candidatoSolicitacaoManager.remove(candidatoSolicitacaoIdsSelecionados);
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Erro ao remover candidatos.");
		}
		
		return Action.SUCCESS;
	}
	
	public Solicitacao getSolicitacao()
	{
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao)
	{
		this.solicitacao = solicitacao;
	}

	public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager)
	{
		this.solicitacaoManager = solicitacaoManager;
	}

	public Collection<CandidatoSolicitacao> getCandidatoSolicitacaos()
	{
		return candidatoSolicitacaos;
	}

	public void setCandidatoSolicitacaos(Collection<CandidatoSolicitacao> candidatoSolicitacaos)
	{
		this.candidatoSolicitacaos = candidatoSolicitacaos;
	}

	public void setCandidatoSolicitacaoManager(CandidatoSolicitacaoManager candidatoSolicitacaoManager)
	{
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
	}

	public char getVisualizar()
	{
		return visualizar;
	}

	public void setVisualizar(char visualizar)
	{
		this.visualizar = visualizar;
	}

	public CandidatoSolicitacao getCandidatoSolicitacao()
	{
		return candidatoSolicitacao;
	}

	public void setCandidatoSolicitacao(CandidatoSolicitacao candidatoSolicitacao)
	{
		this.candidatoSolicitacao = candidatoSolicitacao;
	}

	public Candidato getCandidato()
	{
		return candidato;
	}

	public void setCandidato(Candidato candidato)
	{
		this.candidato = candidato;
	}

	public Collection<HistoricoCandidato> getHistoricoCandidatos()
	{
		return historicoCandidatos;
	}

	public void setHistoricoCandidatos(Collection<HistoricoCandidato> historicoCandidatos)
	{
		this.historicoCandidatos = historicoCandidatos;
	}

	public void setHistoricoCandidatoManager(HistoricoCandidatoManager historicoCandidatoManager)
	{
		this.historicoCandidatoManager = historicoCandidatoManager;
	}

	public Collection<SolicitacaoHistoricoColaborador> getHistoricos()
	{
		return historicos;
	}

	public void setHistoricos(Collection<SolicitacaoHistoricoColaborador> historicos)
	{
		this.historicos = historicos;
	}

	public Collection<Solicitacao> getSolicitacaos()
	{
		return solicitacaos;
	}

	public void setSolicitacaos(Collection<Solicitacao> solicitacaos)
	{
		this.solicitacaos = solicitacaos;
	}

	public Long[] getCandidatosId()
	{
		return candidatosId;
	}

	public void setCandidatosId(Long[] candidatosId)
	{
		this.candidatosId = candidatosId;
	}

	public Collection<EtapaSeletiva> getEtapas() {
		return etapas;
	}

	public void setEtapas(Collection<EtapaSeletiva> etapas) {
		this.etapas = etapas;
	}

	public void setEtapaSeletivaManager(EtapaSeletivaManager etapaSeletivaManager) {
		this.etapaSeletivaManager = etapaSeletivaManager;
	}

	public Long getEtapaSeletivaId()
	{
		return etapaSeletivaId;
	}

	public void setEtapaSeletivaId(Long etapaSeletivaId)
	{
		this.etapaSeletivaId = etapaSeletivaId;
	}

	public StringUtil getStringUtil()
	{
		return stringUtil;
	}

	public String getIndicadoPor()
	{
		return indicadoPor;
	}

	public void setIndicadoPor(String indicadoPor)
	{
		this.indicadoPor = indicadoPor;
	}

	public String getObservacaoRH()
	{
		return observacaoRH;
	}

	public void setObservacaoRH(String observacaoRH)
	{
		this.observacaoRH = observacaoRH;
	}

	public String getNomeBusca() {
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca) {
		this.nomeBusca = nomeBusca;
	}

	public boolean isExisteCompetenciaParaFaixa() {
		return existeCompetenciaParaFaixa;
	}

	public void setConfiguracaoNivelCompetenciaManager(ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) {
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}

	public Solicitacao getSolicitacaoDestino() {
		return solicitacaoDestino;
	}

	public void setSolicitacaoDestino(Solicitacao solicitacaoDestino) {
		this.solicitacaoDestino = solicitacaoDestino;
	}

	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager) {
		this.historicoColaboradorManager = historicoColaboradorManager;
	}

	
	public Long[] getCandidatoSolicitacaoIdsSelecionados()
	{
		return candidatoSolicitacaoIdsSelecionados;
	}
	
	public void setCandidatoSolicitacaoIdsSelecionados(Long[] candidatoSolicitacaoIdsSelecionados)
	{
		this.candidatoSolicitacaoIdsSelecionados = candidatoSolicitacaoIdsSelecionados;
	}

	public Collection<SolicitacaoAvaliacao> getSolicitacaoAvaliacaos() {
		return solicitacaoAvaliacaos;
	}

	public void setSolicitacaoAvaliacaoManager(SolicitacaoAvaliacaoManager solicitacaoAvaliacaoManager) {
		this.solicitacaoAvaliacaoManager = solicitacaoAvaliacaoManager;
	}

	public Collection<ColaboradorQuestionario> getColaboradorQuestionarios() {
		return colaboradorQuestionarios;
	}

	public RelatorioCandidatoSolicitacaoList getRelatorioCandidatoSolicitacaoList() {
		return relatorioCandidatoSolicitacaoList;
	}

	public void setRelatorioCandidatoSolicitacaoList(
			RelatorioCandidatoSolicitacaoList relatorioCandidatoSolicitacaoList) {
		this.relatorioCandidatoSolicitacaoList = relatorioCandidatoSolicitacaoList;
	}
}
package com.fortes.rh.web.action.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.business.captacao.HistoricoCandidatoManager;
import com.fortes.rh.business.captacao.SolicitacaoAvaliacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.SolicitacaoAvaliacao;
import com.fortes.rh.model.dicionario.SolicitacaoHistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.dicionario.StatusAutorizacaoGestor;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class CandidatoSolicitacaoListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;

	@Autowired private SolicitacaoManager solicitacaoManager;
	@Autowired private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	@Autowired private HistoricoCandidatoManager historicoCandidatoManager;
	@Autowired private EtapaSeletivaManager etapaSeletivaManager;
	@Autowired private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	@Autowired private HistoricoColaboradorManager historicoColaboradorManager;
	@Autowired private SolicitacaoAvaliacaoManager solicitacaoAvaliacaoManager;
	@Autowired private AreaOrganizacionalManager areaOrganizacionalManager;
	@Autowired private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	
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
	private HashMap status = new StatusAprovacaoSolicitacao();
	private HashMap statusAutorizacao = new StatusAutorizacaoGestor();
	private char statusBusca = 'T';
	private String solicitacaoDescricaoBusca;
	private String colaboradorNomeBusca;

	// Utilizado no ftl para escapar nomes de candidatos contendo apóstrofos
	private StringUtil stringUtil = new StringUtil();

	public String list() throws Exception
	{
		setVideoAjuda(841L);
		
		etapas = etapaSeletivaManager.findAllSelect(getEmpresaSistema().getId());
		solicitacao = solicitacaoManager.getValor(solicitacao.getId());
		
		setTotalSize(candidatoSolicitacaoManager.getCount(solicitacao.getId(), etapaSeletivaId, indicadoPor, getValueApto(visualizar), false, true, observacaoRH, nomeBusca, visualizar));
		candidatoSolicitacaos = candidatoSolicitacaoManager.getCandidatoSolicitacaoList(getPage(), getPagingSize(), solicitacao.getId(), etapaSeletivaId, indicadoPor, getValueApto(visualizar), false, true, observacaoRH, nomeBusca, visualizar);
		solicitacaoAvaliacaos = solicitacaoAvaliacaoManager.findBySolicitacaoId(solicitacao.getId(), null);
		
		if(candidatoSolicitacaos == null || candidatoSolicitacaos.size() == 0){
			if (getPage() > 1){
				setPage(getPage()-1);
				candidatoSolicitacaos = candidatoSolicitacaoManager.getCandidatoSolicitacaoList(getPage(), getPagingSize(), solicitacao.getId(), etapaSeletivaId, indicadoPor, getValueApto(visualizar), false, false, observacaoRH, null, null);
			}else
				addActionMessage("Não existem candidatos para o filtro informado.");
		}


		if(getActionMsg() != null && !getActionMsg().equals("") && (getActionMessages() != null && getActionMessages().toArray()[0].equals(""))){
			addActionError(getActionMsg());
			getActionMessages().clear();
		}

		//Não sei pq essa ActionMessages vem vazia "" (ajuste tecnico) Fco Barroso
		if(getActionMessages() == null || getActionMessages().isEmpty() || getActionMessages().toArray()[0].equals("")){
			setActionMessages(null);
		}

		existeCompetenciaParaFaixa = !configuracaoNivelCompetenciaManager.findByFaixa(solicitacao.getFaixaSalarial().getId(), solicitacao.getData()).isEmpty();
		
		return Action.SUCCESS;
	}

	private Boolean getValueApto(Character opcaoVisualizar)
	{
		if(opcaoVisualizar != null && opcaoVisualizar == 'A')
			return true;
		else if(opcaoVisualizar != null && opcaoVisualizar == 'N')
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
		historicoColaboradorManager.removeVinculoCandidatoSolicitacao(candidatoSolicitacao.getId());
		historicoCandidatoManager.removeByCandidatoSolicitacao(candidatoSolicitacao.getId());
		configuracaoNivelCompetenciaManager.removeByCandidatoAndSolicitacao(candidatoSolicitacao.getCandidato().getId(), candidatoSolicitacao.getSolicitacao().getId());
		
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
	
    public String prepareAutorizarColabSolicitacaoPessoal(){
    	try {
    		Collection<AreaOrganizacional> areasOrganizacionais = new ArrayList<AreaOrganizacional>();
    		if(getUsuarioLogado().getId() == 1L || SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VER_AREAS"}))
    			areasOrganizacionais = areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, getEmpresaSistema().getId());
    		else
    			areasOrganizacionais = areaOrganizacionalManager.findAreasByUsuarioResponsavel(getUsuarioLogado(), getEmpresaSistema().getId());
    		
    		setTotalSize(candidatoSolicitacaoManager.findColaboradorParticipantesDaSolicitacaoByAreas(areasOrganizacionais, solicitacaoDescricaoBusca, colaboradorNomeBusca, statusBusca, null, null).size());
    		candidatoSolicitacaos = candidatoSolicitacaoManager.findColaboradorParticipantesDaSolicitacaoByAreas(areasOrganizacionais, solicitacaoDescricaoBusca, colaboradorNomeBusca, statusBusca, getPage(), getPagingSize());

    		if(candidatoSolicitacaos.size() == 0)
    			addActionMessage("Não existem colaboradores a serem listados.");
    		
    	} catch (Exception e) {
			addActionError("Ocorreu uma inconsistência ao carregar a tela.");
			e.printStackTrace();
		}
    	
    	return Action.SUCCESS;
    }
    
    public String autorizarColabSolicitacaoPessoal(){
    	try {
    		CandidatoSolicitacao candidatoSolicitacaoAnetrior = (CandidatoSolicitacao) candidatoSolicitacaoManager.findById(candidatoSolicitacao.getId()).clone();
    		candidatoSolicitacao.setDataAutorizacaoGestor(new Date());
    		candidatoSolicitacaoManager.updateStatusAutorizacaoGestor(candidatoSolicitacao);
    		
    		if(!candidatoSolicitacaoAnetrior.getStatusAutorizacaoGestor().equals(candidatoSolicitacao.getStatusAutorizacaoGestor()))
    			gerenciadorComunicacaoManager.enviarAvisoAoAlterarStatusColaboradorSolPessoal(getUsuarioLogado(), candidatoSolicitacaoAnetrior, candidatoSolicitacao, getEmpresaSistema());
    		
    		addActionMessage("Status gravado com sucesso.");
		} catch (Exception e) {
			addActionError("Ocorreu uma inconsistência ao tentar gravar o status.");
			e.printStackTrace();
		}
    	
    	prepareAutorizarColabSolicitacaoPessoal();
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

	public Collection<CandidatoSolicitacao> getCandidatoSolicitacaos()
	{
		return candidatoSolicitacaos;
	}

	public void setCandidatoSolicitacaos(Collection<CandidatoSolicitacao> candidatoSolicitacaos)
	{
		this.candidatoSolicitacaos = candidatoSolicitacaos;
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

	public Solicitacao getSolicitacaoDestino() {
		return solicitacaoDestino;
	}

	public void setSolicitacaoDestino(Solicitacao solicitacaoDestino) {
		this.solicitacaoDestino = solicitacaoDestino;
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

	public HashMap getStatus() {
		return status;
	}

	public void setStatus(HashMap status) {
		this.status = status;
	}

	public char getStatusBusca() {
		return statusBusca;
	}

	public void setStatusBusca(char statusBusca) {
		this.statusBusca = statusBusca;
	}

	public String getSolicitacaoDescricaoBusca() {
		return solicitacaoDescricaoBusca;
	}

	public void setSolicitacaoDescricaoBusca(String solicitacaoDescricaoBusca) {
		this.solicitacaoDescricaoBusca = solicitacaoDescricaoBusca;
	}

	public String getColaboradorNomeBusca() {
		return colaboradorNomeBusca;
	}

	public void setColaboradorNomeBusca(String colaboradorNomeBusca) {
		this.colaboradorNomeBusca = colaboradorNomeBusca;
	}

	public HashMap getStatusAutorizacao() {
		return statusAutorizacao;
	}

	public char getVisualizar() {
		return visualizar;
	}

	public void setVisualizar(char visualizar) {
		this.visualizar = visualizar;
	}
}
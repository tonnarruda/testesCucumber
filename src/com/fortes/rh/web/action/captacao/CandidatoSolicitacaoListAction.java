package com.fortes.rh.web.action.captacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.collections4.CollectionUtils;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.business.captacao.HistoricoCandidatoManager;
import com.fortes.rh.business.captacao.SolicitacaoAvaliacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.QuantidadeLimiteColaboradoresPorCargoManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.LimiteColaboradorExcedidoException;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.SolicitacaoAvaliacao;
import com.fortes.rh.model.dicionario.OrigemAnexo;
import com.fortes.rh.model.dicionario.SolicitacaoHistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.dicionario.StatusAutorizacaoGestor;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.ModelUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

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
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private ColaboradorManager colaboradorManager;
	private QuantidadeLimiteColaboradoresPorCargoManager quantidadeLimiteColaboradoresPorCargoManager;
	
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
	private Boolean atualizarModelo;
	private boolean contratacoesExcederam;
	private boolean qtdLimiteColaboradorPorCargo;

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
		
		if(CollectionUtils.isEmpty(candidatoSolicitacaos)){
			if (getPage() > 1){
				setPage(getPage()-1);
				candidatoSolicitacaos = candidatoSolicitacaoManager.getCandidatoSolicitacaoList(getPage(), getPagingSize(), solicitacao.getId(), etapaSeletivaId, indicadoPor, getValueApto(visualizar), false, false, observacaoRH, null, null);
			}else
				addActionMessage("Não existem candidatos para o filtro informado.");
		}
		contratacoesExcederam = colaboradorManager.excedeuContratacao(getEmpresaSistema().getId());

		if(ModelUtil.getValor(solicitacao, "getMotivoSolicitacao().isConsiderarQtdColaboradoresPorCargo()", Boolean.FALSE).equals(Boolean.TRUE)){
			try {
				quantidadeLimiteColaboradoresPorCargoManager.validaLimite(solicitacao.getAreaOrganizacional().getId(), solicitacao.getFaixaSalarial().getId(), getEmpresaSistema().getId(), null);
			} catch (LimiteColaboradorExcedidoException e) {
				qtdLimiteColaboradorPorCargo = true;
				if(CollectionUtils.isNotEmpty(candidatoSolicitacaos) && ModelUtil.hasNotNull("getFaixaSalarial().getCargo()", solicitacao) )
					addActionMessage("O limite de colaboradores cadastrados para o cargo \"" + solicitacao.getFaixaSalarial().getCargo().getNome() + "\" foi atingido de acordo com a configuração existente.");
			}
		}

		if(getActionMsg() != null && !getActionMsg().equals("") && (getActionMessages() != null && getActionMessages().toArray()[0].equals(""))){
			addActionError(getActionMsg());
			getActionMessages().clear();
		}

		if(getActionMessages() == null || getActionMessages().isEmpty() || getActionMessages().toArray()[0].equals("")){
			setActionMessages(null);
		}

		existeCompetenciaParaFaixa = !configuracaoNivelCompetenciaManager.findByFaixa(solicitacao.getFaixaSalarial().getId(), solicitacao.getData()).isEmpty();
		
		return Action.SUCCESS;
	}

	public Boolean getValueApto(Character opcaoVisualizar)
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
		try {
			candidatoSolicitacao = candidatoSolicitacaoManager.findCandidatoSolicitacaoById(candidatoSolicitacao.getId());
			historicoColaboradorManager.removeVinculoCandidatoSolicitacao(candidatoSolicitacao.getId());
			historicoCandidatoManager.removeByCandidatoSolicitacao(candidatoSolicitacao.getId());
			configuracaoNivelCompetenciaManager.removeByCandidatoAndSolicitacao(candidatoSolicitacao.getCandidato().getId(), candidatoSolicitacao.getSolicitacao().getId());
			colaboradorQuestionarioManager.removeByCandidatoSolicitacaoIdsAndSolicitacaoId(Arrays.asList(candidatoSolicitacao.getId()), candidatoSolicitacao.getSolicitacao().getId());
			
			candidatoSolicitacaoManager.remove(new Long[]{candidatoSolicitacao.getId()});
			solicitacao = candidatoSolicitacao.getSolicitacao();
			addActionSuccess("Candidato excluído do processo seletivo.");
		} catch (Exception e) {
			addActionError("Erro ao excluír o candidato do processo seletivo: " + e.getMessage());
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	public String removerCandidatoDaSolicitacao(){
		try {
			candidatoSolicitacaoManager.remove(new Long[]{candidatoSolicitacao.getId()});
			addActionSuccess("Candidato excluído do processo seletivo.");
		} catch (Exception e) {
			addActionError("Erro ao excluír o candidato do processo seletivo: " + e.getMessage());
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}

	public String removerTriagem() throws Exception
	{
		try {
			if (candidatoSolicitacaoIdsSelecionados != null && candidatoSolicitacaoIdsSelecionados.length > 0) {
				candidatoSolicitacaoManager.updateTriagem(candidatoSolicitacaoIdsSelecionados, false);
			}
			addActionSuccess("Candidato(s) inserido(s) no processo selectivo com sucesso.");
		} catch (Exception e) {
			addActionError("Erro ao inserir o(s) candidato(s) no processo seletivo: " + e.getMessage());
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}

	public String verHistoricoCandidato() throws Exception
	{
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
		try{
			candidatoSolicitacaoManager.moverCandidatos(candidatosId, solicitacao, solicitacaoDestino, atualizarModelo);			
		} catch (ColecaoVaziaException e){
			e.printStackTrace();
			setActionMsg(e.getMessage());
		} catch (Exception e){
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
			addActionSuccess("Candidato(s) excluído(s) do processo seletivo.");
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Erro ao remover candidatos do processo seletivo: " + e.getMessage());
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

	public void setRelatorioCandidatoSolicitacaoList(RelatorioCandidatoSolicitacaoList relatorioCandidatoSolicitacaoList) {
		this.relatorioCandidatoSolicitacaoList = relatorioCandidatoSolicitacaoList;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
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

	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}

	public Boolean getAtualizarModelo() {
		return atualizarModelo;
	}

	public void setAtualizarModelo(Boolean atualizarModelo) {
		this.atualizarModelo = atualizarModelo;
	}
	
	public boolean isContratacoesExcederam() {
		return contratacoesExcederam;
	}
	
	public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager) {
		this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
	}

	public ColaboradorManager getColaboradorManager() {
		return colaboradorManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public void setQuantidadeLimiteColaboradoresPorCargoManager(QuantidadeLimiteColaboradoresPorCargoManager quantidadeLimiteColaboradoresPorCargoManager) {
		this.quantidadeLimiteColaboradoresPorCargoManager = quantidadeLimiteColaboradoresPorCargoManager;
	}

	public boolean isQtdLimiteColaboradorPorCargo() {
		return qtdLimiteColaboradorPorCargo;
	}
	
	public char getOrigemAnexo() {
		return OrigemAnexo.CANDIDATO;
	}
}
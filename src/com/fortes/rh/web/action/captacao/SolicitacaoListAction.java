/* Autor: Robertson Freitas
 * Data: 23/06/2006
 * Requisito: RFA015 */
package com.fortes.rh.web.action.captacao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.HistoricoCandidatoManager;
import com.fortes.rh.business.captacao.MotivoSolicitacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Anuncio;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.EventoAgenda;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.thread.EnviaEmailSolicitanteThread;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.webwork.dispatcher.SessionMap;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class SolicitacaoListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;

    private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
    private ParametrosDoSistemaManager parametrosDoSistemaManager;
    private HistoricoCandidatoManager historicoCandidatoManager;
    private AreaOrganizacionalManager areaOrganizacionalManager;
    private MotivoSolicitacaoManager motivoSolicitacaoManager;
    private EstabelecimentoManager estabelecimentoManager;
    private PlatformTransactionManager transactionManager;
    private SolicitacaoManager solicitacaoManager;
    private CandidatoManager candidatoManager;
    private CargoManager cargoManager;
    private ColaboradorManager colaboradorManager;
    private Anuncio anuncio;
    
    private Map<String,Object> parametros = new HashMap<String, Object>();
    private HashMap<Character, String> status;

	private Collection<Solicitacao> solicitacaos;
	private Collection<HistoricoCandidato> historicoCandidatos;	
    private Collection<CheckBox> solicitacaosCheck;
    private Solicitacao solicitacao;
    private char visualizar = 'A';
    private Candidato candidato;
    private String[] solicitacaosCheckIds;
    private String dataEncerramento;
    private Empresa empresa;
    private Collection<Empresa> empresas;
    private EmpresaManager empresaManager;
    private boolean pgInicial = false;//usado pelo decorator

    private Collection<MotivoSolicitacao> motivosSolicitacoes = new ArrayList<MotivoSolicitacao>();
    private Collection<AreaOrganizacional> areasOrganizacionais = new ArrayList<AreaOrganizacional>();
    private Collection<Estabelecimento> estabelecimentos;
    private Collection<Cargo> cargos;

    private MotivoSolicitacao motivoSolicitacao = new MotivoSolicitacao();
    private AreaOrganizacional areaOrganizacional = new AreaOrganizacional();
    private Estabelecimento estabelecimento = new Estabelecimento();
    private Cargo cargo = new Cargo();
    private String descricaoBusca;
    private char statusBusca = 'T';
    private String codigoBusca;
    
	private String json;
    private char statusSolicitacaoAnterior;
    private Date dataStatusSolicitacaoAnterior;
    private char statusCandSol;
	private Boolean compartilharCandidatos;
	
	private Date dataIni;
	private Date dataFim;

	private String voltarPara;


	public String list() throws Exception
    {
		setVideoAjuda(678L);
		
        Map session = ActionContext.getContext().getSession();

		boolean roleMovSolicitacaoSelecao = SecurityUtil.verifyRole(session, new String[]{"ROLE_MOV_SOLICITACAO_SELECAO"});
		
		if(roleMovSolicitacaoSelecao)
		{
			setTotalSize(solicitacaoManager.getCount(visualizar, getEmpresaSistema().getId(), estabelecimento.getId(), areaOrganizacional.getId(), cargo.getId(), motivoSolicitacao.getId(), descricaoBusca, statusBusca, codigoBusca, dataIni, dataFim));
			solicitacaos = solicitacaoManager.findAllByVisualizacao(getPage(), getPagingSize(), visualizar, getEmpresaSistema().getId(), estabelecimento.getId(), areaOrganizacional.getId(), cargo.getId(), motivoSolicitacao.getId(), descricaoBusca, statusBusca, codigoBusca, dataIni, dataFim);
		}
		else
		{
			Usuario usuario = getUsuarioLogado();
			Long[] areasIdsComFilhas = areaOrganizacionalManager.findIdsAreasDoResponsavelCoResponsavel(usuario, getEmpresaSistema().getId());
			
			setTotalSize(solicitacaoManager.getCount(visualizar, getEmpresaSistema().getId(), usuario.getId(), estabelecimento.getId(), areaOrganizacional.getId(), cargo.getId(), motivoSolicitacao.getId(), descricaoBusca, statusBusca, areasIdsComFilhas, codigoBusca, dataIni, dataFim));
			solicitacaos = solicitacaoManager.findAllByVisualizacao(getPage(), getPagingSize(), visualizar, getEmpresaSistema().getId(), usuario.getId(), estabelecimento.getId(), areaOrganizacional.getId(), cargo.getId(), motivoSolicitacao.getId(), descricaoBusca, statusBusca, areasIdsComFilhas, codigoBusca, dataIni, dataFim);
		}

		if(solicitacaos == null || solicitacaos.size() == 0)
			addActionMessage("Não existem solicitações a serem visualizadas!");

		cargos = cargoManager.findAllSelect(getEmpresaSistema().getId(), "nome", null, Cargo.TODOS);
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		
		if(roleMovSolicitacaoSelecao)
			areasOrganizacionais = areaOrganizacionalManager.findAllSelectOrderDescricao(getEmpresaSistema().getId(), AreaOrganizacional.ATIVA, null, false);
		else
			areasOrganizacionais = areaOrganizacionalManager.findAllSelectOrderDescricaoByUsuarioId(getEmpresaSistema().getId(), getUsuarioLogado().getId(), AreaOrganizacional.ATIVA, null);
		
		motivosSolicitacoes = motivoSolicitacaoManager.findAll();
		
		status = new StatusAprovacaoSolicitacao();
		
        return Action.SUCCESS;
    }
	
	public String getNomeLiberador(Long idLiberador){
		
		Colaborador colaboradorLiberador = colaboradorManager.findByUsuarioProjection(solicitacao.getLiberador().getId(), null);
		String nomeLiberador = colaboradorLiberador!=null?colaboradorLiberador.getNomeMaisNomeComercial():solicitacao.getLiberador().getNome();
		
		return nomeLiberador;
	}

	public String listRecebidas() throws Exception
    {
        return Action.SUCCESS;
    }
	
	public String agenda() throws Exception
	{
		setVideoAjuda(771L);
		
		return Action.SUCCESS;
	}
	
	public String findEventos() throws Exception
	{
		Collection<EventoAgenda> eventos = historicoCandidatoManager.getEventos("", null);
		
		json = StringUtil.toJSON(eventos, null);
		
		return Action.SUCCESS;
	}
	
	public String imprimirAgenda()
	{
		historicoCandidatos = historicoCandidatoManager.getEventos(getEmpresaSistema().getId(), dataIni, dataFim);
		parametros = RelatorioUtil.getParametrosRelatorio("Agenda dos Candidatos", getEmpresaSistema(), "Período: " + DateUtil.formataDiaMesAno(dataIni) + " a " + DateUtil.formataDiaMesAno(dataFim));
		return Action.SUCCESS;
	}

	public String verSolicitacoes() throws Exception
    {
		if (empresa == null || empresa.getId() == null)
			empresa = getEmpresaSistema();
		
		compartilharCandidatos = parametrosDoSistemaManager.findById(1L).getCompartilharCandidatos();
		empresas = empresaManager.findEmpresasPermitidas(compartilharCandidatos, getEmpresaSistema().getId(), getUsuarioLogado().getId(),new String[]{"ROLE_MOV_SOLICITACAO","ROLE_COLAB_LIST_SOLICITACAO"});
		
    	candidato = candidatoManager.findByCandidatoId(candidato.getId());

    	solicitacaos = solicitacaoManager.findSolicitacaoList(empresa.getId(), false, StatusAprovacaoSolicitacao.APROVADO, false);

    	solicitacaosCheck = CheckListBoxUtil.populaCheckListBox(solicitacaos, "getId", "getDescricaoFormatada");
    	return Action.SUCCESS;
    }

    public String gravarSolicitacoesCandidato() throws Exception
    {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try {

			if(solicitacaosCheckIds != null && solicitacaosCheckIds.length > 0)
				candidatoManager.updateDisponivel(true, candidato.getId());

			for (String id : solicitacaosCheckIds) {
				
				solicitacao = new Solicitacao();
				solicitacao.setId(Long.parseLong(id));

				candidatoSolicitacaoManager.insertCandidatos(new String[]{Long.toString(candidato.getId())}, solicitacao,statusCandSol);
			}

			if(voltarPara != null && voltarPara.equals("../../geral/colaborador/list.action") && !SecurityUtil.verifyRole((SessionMap) ActionContext.getContext().getSession(), new String[]{"ROLE_MOV_SOLICITACAO"})) {
				setActionMsg("Colaborador inserido na solicitação com sucesso.");
				return "successColaboradorList";
			}

			transactionManager.commit(status);
			if (solicitacaosCheckIds != null && solicitacaosCheckIds.length == 1 && empresa.getId().equals(getEmpresaSistema().getId()))
				return "successSolicitacao";

			verSolicitacoes();

			CheckListBoxUtil.marcaCheckListBox(solicitacaosCheck, solicitacaosCheckIds);
			
			addActionSuccess("Candidato incluído com sucesso");
			
		}catch(Exception e){

			transactionManager.rollback(status);
			e.printStackTrace();
			addActionError("Não foi possível concluir esta operação.");

			return Action.INPUT;
		}

		return Action.SUCCESS;
    }

    public String delete() throws Exception
    {
        if(!solicitacaoManager.removeCascade(solicitacao.getId()))
        	addActionWarning("Não é possível excluir a Solicitação, pois existem candidatos para esta.");
        else
        	addActionSuccess("Solicitação excluída com sucesso.");
        
        return list();
    }

    public String encerrarSolicitacao() throws Exception
    {
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	dateFormat.setLenient(false);
    	Date dataEncerra = null;
    	try
		{
    		dataEncerra = dateFormat.parse(dataEncerramento);
		}
		catch (Exception e)
		{
			addActionWarning("Data de Encerramento Inválida.");
			list();
			return Action.INPUT;
		}

		solicitacao.setDataEncerramento(dataEncerra);
    	solicitacaoManager.encerraSolicitacao(solicitacao, getEmpresaSistema());

        return Action.SUCCESS;
    }

    public String reabrirSolicitacao() throws Exception
    {
        solicitacaoManager.updateEncerraSolicitacao(false, null, solicitacao.getId());

        return Action.SUCCESS;
    }

    public String suspenderSolicitacao() throws Exception
    {
    	solicitacaoManager.updateSuspendeSolicitacao(true, solicitacao.getObsSuspensao(), solicitacao.getId());

    	return Action.SUCCESS;
    }

    public String liberarSolicitacao() throws Exception
    {
    	solicitacaoManager.updateSuspendeSolicitacao(false, "", solicitacao.getId());

    	return Action.SUCCESS;
    }

    public String updateStatusSolicitacao() throws Exception {
		if((solicitacao.getStatus() != statusSolicitacaoAnterior || (solicitacao.getDataStatus() != null && !solicitacao.getDataStatus().equals(dataStatusSolicitacaoAnterior))) && SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_LIBERA_SOLICITACAO"})) {
    		solicitacao.setLiberador(SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession()));
    		if (solicitacao.getDataStatus() == null) {
				solicitacao.setDataStatus(new Date());
			}
        	solicitacaoManager.updateStatusSolicitacao(solicitacao);
    		
        	new EnviaEmailSolicitanteThread(solicitacao, getEmpresaSistema(), getUsuarioLogado()).start();
        } else {
        	Solicitacao solicitacaoSomenteObs = solicitacaoManager.findById(solicitacao.getId());
        	solicitacaoSomenteObs.setObservacaoLiberador(solicitacao.getObservacaoLiberador());
        	solicitacaoManager.update(solicitacaoSomenteObs);
        }
		
    	return Action.SUCCESS;
    }

    public Collection<Solicitacao> getSolicitacaos()
    {
        return solicitacaos;
    }

    public Solicitacao getSolicitacao()
    {
        if(solicitacao == null)
            solicitacao = new Solicitacao();
        return solicitacao;
    }

    public void setSolicitacao(Solicitacao solicitacao){
        this.solicitacao = solicitacao;
    }

    public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager){
        this.solicitacaoManager = solicitacaoManager;
    }

    public char getVisualizar()
    {
        return visualizar;
    }

    public void setVisualizar(char visualizar)
    {
        this.visualizar = visualizar;
    }

	public Candidato getCandidato()
	{
		return candidato;
	}

	public void setCandidato(Candidato candidato)
	{
		this.candidato = candidato;
	}

	public Collection<CheckBox> getSolicitacaosCheck()
	{
		return solicitacaosCheck;
	}

	public void setSolicitacaosCheck(Collection<CheckBox> solicitacaosCheck)
	{
		this.solicitacaosCheck = solicitacaosCheck;
	}

	public String[] getSolicitacaosCheckIds()
	{
		return solicitacaosCheckIds;
	}

	public void setSolicitacaosCheckIds(String[] solicitacaosCheckIds)
	{
		this.solicitacaosCheckIds = solicitacaosCheckIds;
	}

	public void setCandidatoSolicitacaoManager(CandidatoSolicitacaoManager candidatoSolicitacaoManager)
	{
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
	}

	public void setCandidatoManager(CandidatoManager candidatoManager)
	{
		this.candidatoManager = candidatoManager;
	}

	public String getDataEncerramento()
	{
		return dataEncerramento;
	}

	public void setDataEncerramento(String dataEncerramento)
	{
		this.dataEncerramento = dataEncerramento;
	}

	public void setCargoManager(CargoManager cargoManager)
	{
		this.cargoManager = cargoManager;
	}

	public MotivoSolicitacao getMotivoSolicitacao() {
		return motivoSolicitacao;
	}

	public void setMotivoSolicitacao(MotivoSolicitacao motivoSolicitacao) {
		this.motivoSolicitacao = motivoSolicitacao;
	}

	public AreaOrganizacional getAreaOrganizacional() {
		return areaOrganizacional;
	}

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional) {
		this.areaOrganizacional = areaOrganizacional;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}
	
	public Collection<MotivoSolicitacao> getMotivosSolicitacoes() {
		return motivosSolicitacoes;
	}
	public Collection<AreaOrganizacional> getAreasOrganizacionais() {
		return areasOrganizacionais;
	}

	public Collection<Estabelecimento> getEstabelecimentos() {
		return estabelecimentos;
	}

	public Collection<Cargo> getCargos()
	{
		return cargos;
	}

	public void setCargos(Collection<Cargo> cargos)
	{
		this.cargos = cargos;
	}

	public Cargo getCargo()
	{
		return cargo;
	}

	public void setCargo(Cargo cargo)
	{
		this.cargo = cargo;
	}
	
    public void setAnuncio(Anuncio anuncio) 
    {
		this.anuncio = anuncio;
	}
    
    public Anuncio getAnuncio() 
    {
		return anuncio;
	}

	public Collection<Empresa> getEmpresas() {
		return empresas;
	}

	public EmpresaManager getEmpresaManager() {
		return empresaManager;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public boolean isPgInicial() {
		return pgInicial;
	}

	public void setHistoricoCandidatoManager(HistoricoCandidatoManager historicoCandidatoManager) {
		this.historicoCandidatoManager = historicoCandidatoManager;
	}

	public String getJson() {
		return json;
	}

	public char getStatusCandSol() {
		return statusCandSol;
	}

	public void setStatusCandSol(char statusCandSol) {
		this.statusCandSol = statusCandSol;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public Boolean getCompartilharCandidatos() {
		return compartilharCandidatos;
	}

	public Collection<HistoricoCandidato> getHistoricoCandidatos() {
		return historicoCandidatos;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}
	
	public Date getDataIni() {
		return dataIni;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public HashMap getStatus() {
		return status;
	}

	public char getStatusSolicitacaoAnterior() {
		return statusSolicitacaoAnterior;
	}

	public void setStatusSolicitacaoAnterior(char statusSolicitacaoAnterior) {
		this.statusSolicitacaoAnterior = statusSolicitacaoAnterior;
	}

	public String getVoltarPara() {
		return voltarPara;
	}

	public void setVoltarPara(String voltarPara) {
		this.voltarPara = voltarPara;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getDescricaoBusca() {
		return descricaoBusca;
	}

	public void setDescricaoBusca(String descricaoBusca) {
		this.descricaoBusca = descricaoBusca;
	}

	public char getStatusBusca() {
		return statusBusca;
	}

	public void setStatusBusca(char statusBusca) {
		this.statusBusca = statusBusca;
	}
	
	public String getCodigoBusca() {
		return codigoBusca;
	}

	public void setCodigoBusca(String codigoBusca) {
		this.codigoBusca = codigoBusca;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void setEstabelecimentoManager(
			EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public void setAreaOrganizacionalManager(
			AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}
	
	public void setMotivoSolicitacaoManager(
			MotivoSolicitacaoManager motivoSolicitacaoManager) {
		this.motivoSolicitacaoManager = motivoSolicitacaoManager;
	}

	public void setDataStatusSolicitacaoAnterior(Date dataStatusSolicitacaoAnterior) {
		this.dataStatusSolicitacaoAnterior = dataStatusSolicitacaoAnterior;
	}

	public ColaboradorManager getColaboradorManager() {
		return colaboradorManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}
}
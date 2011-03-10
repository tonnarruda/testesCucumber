/* Autor: Robertson Freitas
 * Data: 23/06/2006
 * Requisito: RFA015 */
package com.fortes.rh.web.action.captacao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.HistoricoCandidatoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Anuncio;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.EventoAgenda;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.dicionario.StatusCandidatoSolicitacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class SolicitacaoListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;

	private SolicitacaoManager solicitacaoManager;
    private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
    private CandidatoManager candidatoManager;
    private CargoManager cargoManager;
    private Anuncio anuncio;
    private HistoricoCandidatoManager historicoCandidatoManager;

	private Collection<Solicitacao> solicitacaos;
    private Collection<CheckBox> solicitacaosCheck;
    private Solicitacao solicitacao;
    private char visualizar = 'A';
    private Candidato candidato;
    private Long[] solicitacaosCheckIds;
    private String dataEncerramento;
    private Empresa empresa;
    private Collection<Empresa> empresas;
    private EmpresaManager empresaManager;
    private boolean pgInicial = false;//usado pelo decorator

    private Collection<Cargo> cargos;
    private Cargo cargo = new Cargo();
    
    private String json;

    @SuppressWarnings("unchecked")
	public String list() throws Exception
    {
        Map session = ActionContext.getContext().getSession();

		boolean roleMovSolicitacaoSelecao = SecurityUtil.verifyRole(session, new String[]{"ROLE_MOV_SOLICITACAO_SELECAO"});
		boolean roleLiberaSolicitacao = SecurityUtil.verifyRole(session, new String[]{"ROLE_LIBERA_SOLICITACAO"});


		if(roleMovSolicitacaoSelecao)
		{
			setTotalSize(solicitacaoManager.getCount(visualizar, roleLiberaSolicitacao, getEmpresaSistema().getId(), cargo.getId()));
			solicitacaos = solicitacaoManager.findAllByVisualizacao(getPage(), getPagingSize(), visualizar, roleLiberaSolicitacao, getEmpresaSistema().getId(), cargo.getId());
		}
		else
		{
			Usuario usuario = SecurityUtil.getUsuarioLoged(session);
			setTotalSize(solicitacaoManager.getCount(visualizar, roleLiberaSolicitacao, getEmpresaSistema().getId(), usuario.getId(), cargo.getId()));
			solicitacaos = solicitacaoManager.findAllByVisualizacao(getPage(), getPagingSize(), visualizar, roleLiberaSolicitacao, getEmpresaSistema().getId(), usuario.getId(), cargo.getId());
		}

		if(solicitacaos == null || solicitacaos.size() == 0)
			addActionMessage("Não existem solicitações a serem visualizadas!");

		cargos = cargoManager.findAllSelect(getEmpresaSistema().getId(), "nome");
		
        return Action.SUCCESS;
    }

	public String listRecebidas() throws Exception
    {
        return Action.SUCCESS;
    }
	
	public String agenda() throws Exception
	{
		pgInicial = true;//decorator
		
		return Action.SUCCESS;
	}
	
	public String findEventos() throws Exception
	{
		Collection<EventoAgenda> eventos = historicoCandidatoManager.getEventos("", null);
		
		json = StringUtil.toJSON(eventos, null);
		
		return Action.SUCCESS;
	}

	public String verSolicitacoes() throws Exception
    {
		empresas = empresaManager.findByUsuarioPermissao(SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_MOV_SOLICITACAO");
		
    	candidato = candidatoManager.findByCandidatoId(candidato.getId());

    	solicitacaos = solicitacaoManager.findSolicitacaoList(getEmpresaSistema().getId(), false, true, false);

    	solicitacaosCheck = CheckListBoxUtil.populaCheckListBox(solicitacaos, "getId", "getDescricaoFormatada");
    	return Action.SUCCESS;
    }

    public String gravarSolicitacoesCandidato() throws Exception
    {
    	for(Long id : solicitacaosCheckIds)
    	{

    		Solicitacao solicitacao = new Solicitacao();
	    	solicitacao.setId(id);

    		candidatoSolicitacaoManager.insertCandidatos(new String[]{Long.toString(candidato.getId())}, solicitacao, StatusCandidatoSolicitacao.APROMOVER);

    	}
    	return Action.SUCCESS;
    }

    public String delete() throws Exception
    {
        if(!solicitacaoManager.removeCascade(solicitacao.getId()))
        	addActionError("Não é possível excluir a Solicitação, pois existem candidatos para esta.");
        else
        	addActionMessage("Solicitação excluída com sucesso.");
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
			addActionError("Data de Encerramento Inválida.");
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

	public Long[] getSolicitacaosCheckIds()
	{
		return solicitacaosCheckIds;
	}

	public void setSolicitacaosCheckIds(Long[] solicitacaosCheckIds)
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
}
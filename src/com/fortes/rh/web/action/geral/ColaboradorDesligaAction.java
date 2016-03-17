/* Autor: Robertson Freitas
 * Data: 22/06/2006
 * Requisito: RFA0012 */
package com.fortes.rh.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.MotivoDemissaoManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.MotivoDemissao;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupport;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ModelDriven;

public class ColaboradorDesligaAction extends MyActionSupport implements ModelDriven
{
	private static final long serialVersionUID = 1L;
	
	private ColaboradorManager colaboradorManager;
	private MotivoDemissaoManager motivoDemissaoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private PlatformTransactionManager transactionManager;
	
	private Colaborador colaborador;
	private Comissao comissao;
	private MotivoDemissao motDemissao;
	private Character gerouSubstituicao;

	private Collection<MotivoDemissao> motivoDemissaos;
	private Collection<Colaborador> colaboradores;
	private Map<String,Object> parametros = new HashMap<String, Object>();

	private boolean desligado;
	private Date dataDesligamento;
	private String observacaoDemissao;
	private String nomeBusca;
	private String cpfBusca;
	private boolean integraAc;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		integraAc = getEmpresaSistema().isAcIntegra();
		motivoDemissaos = motivoDemissaoManager.findAllSelect(getEmpresaSistema().getId());
		colaborador = colaboradorManager.findColaboradorById(colaborador.getId());
		motDemissao = colaborador.getMotivoDemissao();
		gerouSubstituicao = colaborador.getDemissaoGerouSubstituicao();
		
		return Action.SUCCESS;
	}

	// TODO: SEM TESTE
	public String prepareDesliga() throws Exception
	{
		integraAc = getEmpresaSistema().isAcIntegra();
		motivoDemissaos = motivoDemissaoManager.find(new String[]{"empresa.id"}, new Object[]{getEmpresaSistema().getId()}, new String[]{"motivo asc"});

		return Action.SUCCESS;
	}
	
	// TODO: SEM TESTE
	public String desliga() throws Exception
	{
		try {
			if (dataDesligamento.before(colaborador.getDataAdmissao()))
				throw new Exception("Data de desligamento anterior à data de admissão");
			
			if (getEmpresaSistema().isSolicitarConfirmacaoDesligamento())
			{
				Colaborador solicitante = SecurityUtil.getColaboradorSession(ActionContext.getContext().getSession());
				Long solicitanteId = solicitante != null ? solicitante.getId() : null;
				
				colaboradorManager.solicitacaoDesligamento(dataDesligamento, observacaoDemissao, motDemissao.getId(), gerouSubstituicao, solicitanteId, colaborador.getId());
				addActionSuccess("Solicitação de desligamento cadastrada com sucesso.");
		
				gerenciadorComunicacaoManager.enviaAvisoSolicitacaoDesligamento(colaborador.getId(), getEmpresaSistema().getId());
			} 
			else 
			{
				boolean integraAC = colaborador.isNaoIntegraAc() ? false : getEmpresaSistema().isAcIntegra();
				colaboradorManager.desligaColaborador(desligado, dataDesligamento, observacaoDemissao, motDemissao.getId(), gerouSubstituicao, false, integraAC, colaborador.getId());
				addActionSuccess("Colaborador desligado com sucesso.");
			}
		
		} catch (Exception e) {
			addActionError(e.getMessage());
			e.printStackTrace();
			prepareUpdate();
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}
	
	// TODO: SEM TESTE
	public String solicitacaoDesligamento() throws Exception
	{
		try {
			if (dataDesligamento.before(colaborador.getDataAdmissao()))
				throw new Exception("Data de desligamento anterior à data de admissão");
			
			observacaoDemissao = (getEmpresaSistema().isSolicitarConfirmacaoDesligamento() ? "Aprovado por: " : "Solicitado por: ") + getUsuarioLogado().getNome() + ".  Obs: " +  observacaoDemissao;
			
			if(colaboradorManager.findByIdProjectionEmpresa(colaborador.getId()).isNaoIntegraAc()){
				addActionSuccess("Colaboardor não integrado com o Fortes Pessoal.");
			}else{
				colaboradorManager.solicitacaoDesligamentoAc(dataDesligamento, observacaoDemissao, motDemissao.getId(), gerouSubstituicao, colaborador.getId(), getEmpresaSistema());
				addActionSuccess("Solicitação de desligamento enviada com sucesso.");
			}
			
		} catch (Exception e) {
			if(e.getCause() != null && e.getCause().getMessage() != null)
				addActionError(e.getCause().getMessage());
			else
				addActionError("Erro ao tentar desligar colaborador no Fortes Pessoal " + e.getMessage());
			
			prepareUpdate();
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}
	
	// TODO: SEM TESTE
	public String reLiga() throws Exception
	{
		try
		{
			colaboradorManager.validaQtdCadastros();			
			colaboradorManager.religaColaborador(colaborador.getId());
			
			addActionSuccess("Colaborador religado com sucesso.");
		} 
		catch (FortesException e) 
		{
			addActionMessage(e.getMessage());
		} 
		catch (Exception e) 
		{
			addActionError(e.getMessage());
		}
				
		return Action.SUCCESS;
	}

	// TODO: SEM TESTE
	public String imprimiSolicitacaoDesligamento() throws Exception
	{
		try {
			String titulo = "Desligar Colaborador";
			
			if(getEmpresaSistema().isAcIntegra())
				titulo = "Solicitação de Desligamento de Colaborador no Fortes Pessoal";
			
			parametros = RelatorioUtil.getParametrosRelatorio(titulo, getEmpresaSistema(), "");
			MotivoDemissao motivo = motivoDemissaoManager.findById(motDemissao.getId());
			colaborador.setDemissaoGerouSubstituicao(gerouSubstituicao);
			
			//migue para funcionar reports	
			motivoDemissaos = new ArrayList<MotivoDemissao>();
			motivoDemissaos.add(motivo);

			parametros.put("EMPRESANOME", getEmpresaSistema().getNome());
			parametros.put("COLABORADORNOME", colaborador.getNome());
			parametros.put("DATADESLIGAMENTO", DateUtil.formataDiaMesAno(dataDesligamento));
			parametros.put("MOTIVODEMISSAO", motivo.getMotivo());
			parametros.put("GEROUSUBSTITUICAO", colaborador.getDescricaoDemissaoGerouSubstituicao());
			parametros.put("OBSDEMISSAO", observacaoDemissao);

			return Action.SUCCESS;
		} catch (Exception e) {
			addActionError("Erro ao gerar relatório");
			e.printStackTrace();
			
			return Action.INPUT;
		}
	}
	
	// TODO: SEM TESTE
	public String prepareAprovarReprovarSolicitacaoDesligamento() throws Exception
	{
		Long[] areasIdsPorResponsavel = null;
		
		if(getUsuarioLogado().getId() != 1L && !SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_COLAB_VER_TODOS"}))
		{
			areasIdsPorResponsavel = areaOrganizacionalManager.findIdsAreasDoResponsavelCoResponsavel(getUsuarioLogado(), getEmpresaSistema().getId());
			
			if(areasIdsPorResponsavel.length == 0){
				addActionWarning("Você não é responsável por uma Área Organizacional");
				return Action.SUCCESS;
			}
		}
		
		colaboradores = colaboradorManager.findAguardandoDesligamento(getEmpresaSistema().getId(), areasIdsPorResponsavel, colaboradorManager.findByUsuario(getUsuarioLogado().getId())); 
		return Action.SUCCESS;
	}

	// TODO: SEM TESTE
	public String visualizarSolicitacaoDesligamento() throws Exception
	{
		colaborador = colaboradorManager.findColaboradorByIdProjection(colaborador.getId());
		motivoDemissaos = motivoDemissaoManager.findAllSelect(getEmpresaSistema().getId());
		
		return Action.SUCCESS;
	}
	
	// TODO: SEM TESTE
	public String aprovarSolicitacaoDesligamento() throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try {
			dataDesligamento = colaborador.getDataSolicitacaoDesligamento();
			motDemissao = colaborador.getMotivoDemissao();
			observacaoDemissao = colaborador.getObservacaoDemissao();
			gerouSubstituicao = colaborador.getDemissaoGerouSubstituicao();
			
			if (getEmpresaSistema().isAcIntegra() && !colaborador.isNaoIntegraAc())
				return solicitacaoDesligamento();

			boolean integraAC = colaborador.isNaoIntegraAc() ? false : getEmpresaSistema().isAcIntegra();
			colaboradorManager.desligaColaborador(true, dataDesligamento, observacaoDemissao, motDemissao.getId(), gerouSubstituicao, false, integraAC, colaborador.getId());
			gerenciadorComunicacaoManager.enviaAvisoAprovacaoSolicitacaoDesligamento(colaborador.getId(), colaborador.getNome(), colaborador.getSolicitanteDemissao().getId(), getEmpresaSistema(), true);
			
			transactionManager.commit(status);
			
			addActionSuccess("Colaborador desligado com sucesso.");
		
		} catch (Exception e) {
			transactionManager.rollback(status);
			e.printStackTrace();
			addActionError("Não foi possível gravar a aprovação dessa solicitação de desligamento");
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}
	
	// TODO: SEM TESTE
	public String reprovarSolicitacaoDesligamento() throws Exception
	{
		try {
			colaboradorManager.reprovaSolicitacaoDesligamento(colaborador.getId());
			gerenciadorComunicacaoManager.enviaAvisoAprovacaoSolicitacaoDesligamento(colaborador.getId(), colaborador.getNome(), colaborador.getSolicitanteDemissao().getId(), getEmpresaSistema(), false);
			
			addActionSuccess("Solicitação de desligamento reprovada com sucesso.");
		
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Não foi possível gravar a reprovação dessa solicitação de desligamento");
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}

	public Colaborador getColaborador()
	{
		if (colaborador == null)
		{
			colaborador = new Colaborador();
		}
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public Object getModel()
	{
		return getColaborador();
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public Date getDataDesligamento()
	{
		return dataDesligamento;
	}

	public void setDataDesligamento(Date dataDesligamento)
	{
		this.dataDesligamento = dataDesligamento;
	}

	public boolean isDesligado()
	{
		return desligado;
	}

	public void setDesligado(boolean desligado)
	{
		this.desligado = desligado;
	}

	public String getCpfBusca() {
		return cpfBusca;
	}

	public void setCpfBusca(String cpfBusca) {
		this.cpfBusca = cpfBusca;
	}

	public String getNomeBusca() {
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca) {
		 this.nomeBusca = StringUtil.retiraAcento(nomeBusca);
	}

	public Collection<MotivoDemissao> getMotivoDemissaos()
	{
		return motivoDemissaos;
	}

	public void setMotivoDemissaos(Collection<MotivoDemissao> motivoDemissaos)
	{
		this.motivoDemissaos = motivoDemissaos;
	}

	public void setMotivoDemissaoManager(MotivoDemissaoManager motivoDemissaoManager)
	{
		this.motivoDemissaoManager = motivoDemissaoManager;
	}

	public MotivoDemissao getMotDemissao()
	{
		return motDemissao;
	}

	public void setMotDemissao(MotivoDemissao motivoDemissao)
	{
		this.motDemissao = motivoDemissao;
	}

	public boolean isIntegraAc() {
		return integraAc;
	}

	public String getObservacaoDemissao() {
		return observacaoDemissao;
	}

	public void setObservacaoDemissao(String observacaoDemissao) {
		this.observacaoDemissao = observacaoDemissao;
	}
	
	public Date getDataAtual() {
		return new Date();
	}

	public Comissao getComissao() {
		return comissao;
	}

	public void setComissao(Comissao comissao) {
		this.comissao = comissao;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public Collection<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}

	public Character getGerouSubstituicao() {
		return gerouSubstituicao;
	}

	public void setGerouSubstituicao(Character gerouSubstituicao) {
		this.gerouSubstituicao = gerouSubstituicao;
	}
	
	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
}
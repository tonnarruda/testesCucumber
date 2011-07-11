package com.fortes.rh.web.action.avaliacao;


import static com.fortes.rh.util.CheckListBoxUtil.populaCheckListBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.exception.AvaliacaoRespondidaException;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ResultadoAvaliacaoDesempenho;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class AvaliacaoDesempenhoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private AvaliacaoDesempenhoManager avaliacaoDesempenhoManager;
	private AvaliacaoManager avaliacaoManager;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EmpresaManager empresaManager;
	private ColaboradorManager colaboradorManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	
	private AvaliacaoDesempenho avaliacaoDesempenho;
	private Collection<AvaliacaoDesempenho> avaliacaoDesempenhos;
	private Collection<Avaliacao> avaliacaos;
	
	private Collection<Colaborador> participantes;
	private Collection<Colaborador> avaliadors;
	private Colaborador avaliador;
	
	private Collection<Empresa> empresas;
	
	//pesquisa colaborador
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] colaboradorsCheck;
	private Collection<CheckBox> colaboradorsCheckList = new ArrayList<CheckBox>();
	private String nomeBusca;
	private Long empresaId;
	
	private boolean isAvaliados;
	private boolean temParticipantesAssociados;
	
	private String msgDelete;
	private char respondida = '2';
	
	private String opcaoResultado; // criterio ou avaliador
	private Map<String, Object> parametros;
	private Collection<Pergunta> perguntas;
	private boolean exibirRespostas;
	private boolean exibirComentarios;
	private boolean agruparPorAspectos;
	private Long[] participanteIds;
	
	
	//questionario list
	private Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
	private Collection<ResultadoAvaliacaoDesempenho> resultados;
	private Boolean compartilharColaboradores;
	private Long avaliacaoId;
	
	private void prepare() throws Exception
	{
		if(avaliacaoDesempenho != null && avaliacaoDesempenho.getId() != null)
			avaliacaoDesempenho = avaliacaoDesempenhoManager.findById(avaliacaoDesempenho.getId());
		
		avaliacaos = avaliacaoManager.findAllSelect(getEmpresaSistema().getId(), true, TipoModeloAvaliacao.DESEMPENHO); 
	}
	
	public String enviarLembrete() throws Exception
	{
		try
		{
			avaliacaoDesempenhoManager.enviarLembrete(avaliacaoDesempenho.getId(), getEmpresaSistema());
			addActionMessage("Email(s) enviado(s) com sucesso.");
		} catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Erro ao enviar email(s).");
		}
		
		return Action.SUCCESS;
	}
	
	public String prepareAvaliados() throws Exception
	{
		isAvaliados = true;
		prepareParticipantes();
		avaliadors = colaboradorManager.findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), false);
		
		return Action.SUCCESS;
	}

	public String prepareAvaliadores() throws Exception
	{
		isAvaliados = false;
		prepareParticipantes();
		
		return Action.SUCCESS;
	}
	
	private void prepareParticipantes() 
	{
		empresaId = getEmpresaSistema().getId();
		compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
		empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores, empresaId, SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), null);
		
		avaliacaoDesempenho = avaliacaoDesempenhoManager.findById(avaliacaoDesempenho.getId());
		participantes = colaboradorManager.findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), isAvaliados);
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
	}
	
	public String deleteAvaliado()
	{
		msgDelete = "Avaliado(s)";
		isAvaliados = true;
		deleteParticipante();
		return Action.SUCCESS;
	}
	
	public String deleteAvaliador() throws Exception
	{
		msgDelete = "Avaliador(es)";
		isAvaliados = false;
		deleteParticipante();
		return Action.SUCCESS;
	}
	
	private void deleteParticipante() 
	{
		try 
		{
			if(participanteIds == null)
				addActionMessage("Nenhum participante selecionado.");
			else
			{
				colaboradorQuestionarioManager.remove(participanteIds, avaliacaoDesempenho.getId(), isAvaliados);
				addActionMessage(msgDelete + " excluído(s) com sucesso.");				
			}
		} 
		catch (AvaliacaoRespondidaException e) 
		{
			addActionMessage(e.getMessage());
		} 
		catch (Exception e) {
			addActionError("Não foi possível excluir o(s) " + msgDelete + ".");
			e.printStackTrace();
		}		
	}
	
	public String prepareResultado()
	{
		try {
			avaliacaoDesempenho = avaliacaoDesempenhoManager.findById(avaliacaoDesempenho.getId());
			participantes = colaboradorManager.findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), true);
			colaboradorsCheckList = populaCheckListBox(participantes, "getId", "getNome");
			colaboradorsCheckList = CheckListBoxUtil.marcaCheckListBox(colaboradorsCheckList, colaboradorsCheck);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Action.SUCCESS;
	}
	
	public String resultado() throws Exception
    {
		if (opcaoResultado.equals("avaliador"))
			return resultadoPorAvaliador();
		else
			return resultadoPorCriterio();
    }

	private String resultadoPorAvaliador()
	{
		avaliacaoDesempenho = avaliacaoDesempenhoManager.findById(avaliacaoDesempenho.getId());
		colaboradorQuestionarios = colaboradorQuestionarioManager.getPerformance(LongUtil.arrayStringToCollectionLong(colaboradorsCheck), avaliacaoDesempenho.getId());
		parametros = RelatorioUtil.getParametrosRelatorio("Resultado Avaliação Desempenho (" + avaliacaoDesempenho.getTitulo() + ")", getEmpresaSistema(), "Período: " + avaliacaoDesempenho.getPeriodoFormatado());
		
		if(colaboradorQuestionarios.isEmpty())
		{
			addActionMessage("Não existem respostas para o filtro informado.");
			prepareResultado();
			return Action.INPUT;
		}
		
		if(avaliacaoDesempenho.isAnonima())
			return "SUCCESS_ANONIMA";
		else
			return Action.SUCCESS;
	}

	private String resultadoPorCriterio() throws Exception
	{
		try 
		{
			avaliacaoDesempenho = avaliacaoDesempenhoManager.findById(avaliacaoDesempenho.getId());
			
			resultados = avaliacaoDesempenhoManager.montaResultado(LongUtil.arrayStringToCollectionLong(colaboradorsCheck), avaliacaoDesempenho, agruparPorAspectos);
		
			parametros = RelatorioUtil.getParametrosRelatorio("Resultado Avaliação Desempenho (" + avaliacaoDesempenho.getTitulo() + ")", getEmpresaSistema(), "Resultado por Critérios\nPeríodo: " + avaliacaoDesempenho.getPeriodoFormatado());
			
	    	parametros.put("AGRUPAR_ASPECTO", agruparPorAspectos);
			parametros.put("QUESTIONARIO_ANONIMO", avaliacaoDesempenho.isAnonima());
	    	parametros.put("EXIBIR_RESPOSTAS_SUBJETIVAS", exibirRespostas);
	    	parametros.put("EXIBIR_COMENTARIOS", exibirComentarios);
			parametros.put("QUESTIONARIO_CABECALHO", avaliacaoDesempenho.getAvaliacao().getCabecalho());
		
		} catch (ColecaoVaziaException e) 
		{
			addActionMessage("Não existem respostas para o filtro informado.");
			e.printStackTrace();
			prepareResultado();
			return INPUT;
		}
		
		return "SUCCESS_CRITERIO";
	}
	
	public String insertAvaliados() throws Exception
	{
		avaliacaoDesempenho = avaliacaoDesempenhoManager.findById(avaliacaoDesempenho.getId());
		colaboradorQuestionarioManager.save(avaliacaoDesempenho, LongUtil.arrayStringToArrayLong(colaboradorsCheck), isAvaliados);
		prepareAvaliados();
		return Action.SUCCESS;
	}
	
	public String insertAvaliadores() throws Exception
	{
		avaliacaoDesempenho = avaliacaoDesempenhoManager.findById(avaliacaoDesempenho.getId());
		colaboradorQuestionarioManager.save(avaliacaoDesempenho, LongUtil.arrayStringToArrayLong(colaboradorsCheck), isAvaliados);
		prepareAvaliadores();
		return Action.SUCCESS;
	}
	
	public String gerarAutoAvaliacoesEmLote() throws Exception
	{
		prepareAvaliados();
		avaliacaoDesempenhoManager.gerarAutoAvaliacoes(avaliacaoDesempenho, participantes);
		colaboradorQuestionarioManager.excluirColaboradorQuestionarioByAvaliacaoDesempenho(avaliacaoDesempenho.getId());
		avaliacaoDesempenhoManager.remove(avaliacaoDesempenho);
		
		return Action.SUCCESS;
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		temParticipantesAssociados = colaboradorQuestionarioManager.verifyTemParticipantesAssociados(avaliacaoDesempenho.getId());
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		avaliacaoDesempenhoManager.save(avaliacaoDesempenho);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		avaliacaoDesempenhoManager.update(avaliacaoDesempenho);
		return Action.SUCCESS;
	}
	
	public String clonar()
	{
		try
		{
			avaliacaoDesempenhoManager.clonar(avaliacaoDesempenho.getId());
			addActionMessage("Avaliação de Desempenho clonada com sucesso.");
		} 
		catch (Exception e) {
			addActionError("Não foi possível clonar a Avaliação de Desempenho.");
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		avaliacaos = avaliacaoManager.findAllSelect(getEmpresaSistema().getId(), true, TipoModeloAvaliacao.DESEMPENHO);
		avaliacaoDesempenhos = avaliacaoDesempenhoManager.findTituloModeloAvaliacao(getEmpresaSistema().getId(), nomeBusca, avaliacaoId);
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			avaliacaoDesempenhoManager.remove(avaliacaoDesempenho.getId());
			addActionMessage("Avaliação de Desempenho excluída com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir esta Avaliação de Desempenho.");
		}

		return SUCCESS;
	}
	
	public String liberar() 
	{
		try 
		{
			avaliacaoDesempenhoManager.liberar(avaliacaoDesempenho);
			addActionMessage("Avaliação liberada com sucesso.");
		}
		catch (FortesException e)
		{
			addActionError(e.getMessage());
		}
		catch (Exception e) 
		{
			addActionError("Não foi possível liberar esta Avaliação.");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String bloquear() 
	{
		try 
		{
			avaliacaoDesempenhoManager.bloquear(avaliacaoDesempenho);
			addActionMessage("Avaliação bloqueada com sucesso.");
		}
		catch (Exception e) 
		{
			addActionError("Não foi possível bloquear esta Avaliação.");
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String avaliacaoDesempenhoQuestionarioList()
	{
		if(avaliador == null)
			avaliador = SecurityUtil.getColaboradorSession(ActionContext.getContext().getSession());
		
		if(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_RESPONDER_AVALIACAO_POR_OUTRO_USUARIO"}) )
			avaliacaoDesempenhos = avaliacaoDesempenhoManager.findByAvaliador(null, true, getEmpresaSistema().getId());//pega todas liberadas
		else
			avaliacaoDesempenhos = avaliacaoDesempenhoManager.findByAvaliador(avaliador.getId(), true, getEmpresaSistema().getId());
		
		if(avaliacaoDesempenho == null && ! avaliacaoDesempenhos.isEmpty())
			avaliacaoDesempenho = (AvaliacaoDesempenho) avaliacaoDesempenhos.toArray()[0];
		
		if(avaliacaoDesempenho != null && SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_RESPONDER_AVALIACAO_POR_OUTRO_USUARIO"}) )
			avaliadors = colaboradorManager.findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), false);
		
		if(avaliacaoDesempenho != null)
			colaboradorQuestionarios = colaboradorQuestionarioManager.findAvaliadosByAvaliador(avaliacaoDesempenho.getId(), avaliador.getId(), filtroRespondida());
		
		return Action.SUCCESS;
	}
	
	private Boolean filtroRespondida()
	{
		if(this.respondida == '1')
			return true;
		else if(this.respondida == '2') 
			return false;
		else
			return null;
	}

	public AvaliacaoDesempenho getAvaliacaoDesempenho()
	{
		return avaliacaoDesempenho;
	}

	public void setAvaliacaoDesempenho(AvaliacaoDesempenho avaliacaoDesempenho)
	{
		this.avaliacaoDesempenho = avaliacaoDesempenho;
	}

	public void setAvaliacaoDesempenhoManager(AvaliacaoDesempenhoManager avaliacaoDesempenhoManager)
	{
		this.avaliacaoDesempenhoManager = avaliacaoDesempenhoManager;
	}
	
	public Collection<AvaliacaoDesempenho> getAvaliacaoDesempenhos()
	{
		return avaliacaoDesempenhos;
	}

	public Collection<Avaliacao> getAvaliacaos() {
		return avaliacaos;
	}

	public void setAvaliacaoManager(AvaliacaoManager avaliacaoManager) {
		this.avaliacaoManager = avaliacaoManager;
	}

	public Collection<Colaborador> getParticipantes() {
		return participantes;
	}

	public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager) {
		this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
	}

	public String[] getColaboradorsCheck() {
		return colaboradorsCheck;
	}

	public void setColaboradorsCheck(String[] colaboradorsCheck) {
		this.colaboradorsCheck = colaboradorsCheck;
	}

	public String getNomeBusca() {
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca) {
		this.nomeBusca = nomeBusca;
	}

	public Collection<CheckBox> getAreasCheckList() {
		return areasCheckList;
	}

	public Collection<CheckBox> getColaboradorsCheckList() {
		return colaboradorsCheckList;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public boolean getIsAvaliados() {
		return isAvaliados;
	}

	public void setIsAvaliados(boolean isAvaliados) {
		this.isAvaliados = isAvaliados;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public Collection<Empresa> getEmpresas() {
		return empresas;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public boolean getTemParticipantesAssociados() {
		return temParticipantesAssociados;
	}

	public void setTemParticipantesAssociados(boolean temParticipantesAssociados) {
		this.temParticipantesAssociados = temParticipantesAssociados;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public Collection<ColaboradorQuestionario> getColaboradorQuestionarios() {
		return colaboradorQuestionarios;
	}

	public char getRespondida()
	{
		return respondida;
	}

	public void setRespondida(char respondida)
	{
		this.respondida = respondida;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public String getOpcaoResultado() {
		return opcaoResultado;
	}

	public void setOpcaoResultado(String opcaoResultado) {
		this.opcaoResultado = opcaoResultado;
	}

	public Collection<Pergunta> getPerguntas() {
		return perguntas;
	}

	public boolean isAgruparPorAspectos() {
		return agruparPorAspectos;
	}

	public void setAgruparPorAspectos(boolean agruparPorAspectos) {
		this.agruparPorAspectos = agruparPorAspectos;
	}

	public Collection<ResultadoAvaliacaoDesempenho> getResultados() {
		return resultados;
	}

	public boolean isExibirRespostas() {
		return exibirRespostas;
	}

	public void setExibirRespostas(boolean exibirRespostas) {
		this.exibirRespostas = exibirRespostas;
	}

	public boolean isExibirComentarios() {
		return exibirComentarios;
	}

	public void setExibirComentarios(boolean exibirComentarios) {
		this.exibirComentarios = exibirComentarios;
	}

	public void setAvaliacaoDesempenhos(Collection<AvaliacaoDesempenho> avaliacaoDesempenhos) {
		this.avaliacaoDesempenhos = avaliacaoDesempenhos;
	}

	public Collection<Colaborador> getAvaliadors()
	{
		return avaliadors;
	}

	public Colaborador getAvaliador()
	{
		return avaliador;
	}

	public void setAvaliador(Colaborador avaliador)
	{
		this.avaliador = avaliador;
	}

	public void setParticipanteIds(Long[] participanteIds)
	{
		this.participanteIds = participanteIds;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public Boolean getCompartilharColaboradores() {
		return compartilharColaboradores;
	}

	public Long getAvaliacaoId() {
		return avaliacaoId;
	}

	public void setAvaliacaoId(Long avaliacaoId) {
		this.avaliacaoId = avaliacaoId;
	}
}
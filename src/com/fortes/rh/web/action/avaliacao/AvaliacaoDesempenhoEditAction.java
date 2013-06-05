package com.fortes.rh.web.action.avaliacao;


import static com.fortes.rh.util.CheckListBoxUtil.populaCheckListBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
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
import com.opensymphony.webwork.dispatcher.SessionMap;
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
	private CargoManager cargoManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private AvaliacaoDesempenho avaliacaoDesempenho;
	private Collection<AvaliacaoDesempenho> avaliacaoDesempenhos;
	private Collection<Avaliacao> avaliacaos;
	
	private Collection<Colaborador> participantes;
	private Collection<Colaborador> avaliadors;
	private Colaborador avaliador;
	private ColaboradorQuestionario colaboradorQuestionario; 
	
	private Empresa empresa;
	private Collection<Empresa> empresas;
	
	private String[] empresasCheck;
	private Collection<CheckBox> empresasCheckList = new ArrayList<CheckBox>();
	private String[] cargosCheck;
	private Collection<CheckBox> cargosCheckList = new ArrayList<CheckBox>();
	
	private String[] areasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] colaboradorsCheck;
	private Collection<CheckBox> colaboradorsCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> avaliacoesCheckList = new ArrayList<CheckBox>();
	private String[] avaliacoesCheck;
	
	private String nomeBusca;
	private Long empresaId;
	private Long colaboradorQuestionarioId;
	
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
	private boolean desconsiderarAutoAvaliacao;
	private boolean exibirObsAvaliadores;
	private Long[] participanteIds;
	
	
	//questionario list
	private Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
	private Collection<ResultadoAvaliacaoDesempenho> resultados;
	private Boolean compartilharColaboradores;
	private boolean exibeResultadoAutoavaliacao;
	private String msgResultadoAvaliacao;
	private Long avaliacaoId;
	
	private void prepare() throws Exception
	{
		if(avaliacaoDesempenho != null && avaliacaoDesempenho.getId() != null)
			avaliacaoDesempenho = avaliacaoDesempenhoManager.findById(avaliacaoDesempenho.getId());
		
		avaliacaos = avaliacaoManager.findAllSelect(null, null, getEmpresaSistema().getId(), true, TipoModeloAvaliacao.DESEMPENHO, null); 
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
		avaliadors = colaboradorManager.findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), false, null, null, null);
		
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
		participantes = colaboradorManager.findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), isAvaliados, null, null, null);
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
			compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
			empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores, empresaId, SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), null);
			
			avaliacaoDesempenho = avaliacaoDesempenhoManager.findById(avaliacaoDesempenho.getId());
			participantes = colaboradorManager.findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), true, null, null, null);
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
			resultados = avaliacaoDesempenhoManager.montaResultado(LongUtil.arrayStringToCollectionLong(colaboradorsCheck), avaliacaoDesempenho, agruparPorAspectos, desconsiderarAutoAvaliacao);
			parametros = RelatorioUtil.getParametrosRelatorio("Resultado Avaliação Desempenho (" + avaliacaoDesempenho.getTitulo() + ")", getEmpresaSistema(), "Resultado por Perguntas\nPeríodo: " + avaliacaoDesempenho.getPeriodoFormatado());
			
	    	parametros.put("AGRUPAR_ASPECTO", agruparPorAspectos);
			parametros.put("QUESTIONARIO_ANONIMO", avaliacaoDesempenho.isAnonima());
	    	parametros.put("EXIBIR_RESPOSTAS_SUBJETIVAS", exibirRespostas);
	    	parametros.put("EXIBIR_COMENTARIOS", exibirComentarios);
			parametros.put("QUESTIONARIO_CABECALHO", avaliacaoDesempenho.getAvaliacao().getCabecalho());
    		parametros.put("EXIBIR_OBS_AVALIADOS", exibirObsAvaliadores);
		
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
			Long[] empresasIds = LongUtil.arrayStringToArrayLong(empresasCheck);
			if (empresasIds != null && empresasIds.length > 0)
				avaliacaoDesempenhoManager.clonar(avaliacaoDesempenho.getId(), empresasIds);
			else
				avaliacaoDesempenhoManager.clonar(avaliacaoDesempenho.getId(), getEmpresaSistema().getId());
				
			addActionMessage("Avaliação de desempenho clonada com sucesso.");
		} 
		catch (Exception e) {
			addActionError("Não foi possível clonar a avaliação de desempenho.");
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		avaliacaos = avaliacaoManager.findAllSelect(null, null, getEmpresaSistema().getId(), true, TipoModeloAvaliacao.DESEMPENHO, null);
		
		setTotalSize(avaliacaoDesempenhoManager.findCountTituloModeloAvaliacao(null, null, getEmpresaSistema().getId(), nomeBusca, avaliacaoId, null));
		avaliacaoDesempenhos = avaliacaoDesempenhoManager.findTituloModeloAvaliacao(getPage(), getPagingSize(), getEmpresaSistema().getId(), nomeBusca, avaliacaoId, null);
		
		Collection<Empresa> empresas = empresaManager.findEmpresasPermitidas(true , null, getUsuarioLogado().getId(), "ROLE_MOV_QUESTIONARIO");
   		empresasCheckList =  CheckListBoxUtil.populaCheckListBox(empresas, "getId", "getNome");
		
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			avaliacaoDesempenhoManager.remove(avaliacaoDesempenho.getId());
			addActionMessage("Avaliação de desempenho excluída com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir esta avaliação de desempenho.");
		}

		return SUCCESS;
	}
	
	public String deleteAvaliacao() throws Exception
	{
		try
		{
			colaboradorQuestionarioManager.deleteRespostaAvaliacaoDesempenho(colaboradorQuestionarioId);
			addActionMessage("Respostas da avaliação de desempenho excluídas com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir as respostas da avaliação de desempenho.");
		}

		avaliacaoDesempenhoQuestionarioList();
		return SUCCESS;
	}
	
	public String liberarEmLote() throws Exception
	{
		try 
		{
			avaliacaoDesempenhoManager.liberarEmLote(avaliacoesCheck, getEmpresaSistema());
			addActionMessage("Avaliações liberada com sucesso.");
		}
		catch (Exception e) 
		{
			addActionError("Não foi possível as avaliações.");
			e.printStackTrace();
		}
		
		return Action.SUCCESS;
	}
	
	public String liberar() 
	{
		try 
		{
			avaliacaoDesempenhoManager.liberar(avaliacaoDesempenho);
			addActionSuccess("Avaliação liberada com sucesso.");
		}
		catch (FortesException e)
		{
			addActionWarning(e.getMessage());
		}
		catch (Exception e) 
		{
			addActionError("Não foi possível liberar esta avaliação.");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String bloquear() 
	{
		try 
		{
			avaliacaoDesempenhoManager.bloquear(avaliacaoDesempenho);
			addActionSuccess("Avaliação bloqueada com sucesso.");
		}
		catch (Exception e) 
		{
			addActionError("Não foi possível bloquear esta avaliação.");
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String avaliacaoDesempenhoQuestionarioList()
	{
		empresaId = getEmpresaSistema().getId();
		empresas = empresaManager.findEmpresasPermitidas(true, empresaId, SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), null);

		if(avaliador == null)
			avaliador = SecurityUtil.getColaboradorSession(ActionContext.getContext().getSession());
		
		if(SecurityUtil.verifyRole((SessionMap) ActionContext.getContext().getSession(), new String[]{"ROLE_RESPONDER_AVALIACAO_DESEMP_POR_OUTRO_USUARIO"}) )
			avaliacaoDesempenhos = avaliacaoDesempenhoManager.findByAvaliador(null, true, empresaId );//pega todas liberadas
		else
			avaliacaoDesempenhos = avaliacaoDesempenhoManager.findByAvaliador(avaliador.getId(), true, empresaId);
		
		if(avaliacaoDesempenho == null && ! avaliacaoDesempenhos.isEmpty())
			avaliacaoDesempenho = (AvaliacaoDesempenho) avaliacaoDesempenhos.toArray()[0];
		
		if(avaliacaoDesempenho != null && SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_RESPONDER_AVALIACAO_DESEMP_POR_OUTRO_USUARIO"}) )
			avaliadors = colaboradorManager.findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), false, null, null, null);
		
		if(avaliacaoDesempenho != null)
			colaboradorQuestionarios = colaboradorQuestionarioManager.findAvaliadosByAvaliador(avaliacaoDesempenho.getId(), avaliador.getId(), filtroRespondida(), false);
		
		if(exibeResultadoAutoavaliacao)
		{
			colaboradorQuestionario = colaboradorQuestionarioManager.findByIdProjection(colaboradorQuestionarioId);
			this.msgResultadoAvaliacao = colaboradorQuestionario.getAvaliacao().getCabecalho().replace("\r\n","<br>") + "<br><br><h4>Pontuação Obtida: " + colaboradorQuestionario.getPerformanceFormatada() + "<h4>";
		}
		
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

	public void setColaboradorQuestionarioId(Long colaboradorQuestionarioId) {
		this.colaboradorQuestionarioId = colaboradorQuestionarioId;
	}

	public String getMsgResultadoAvaliacao() {
		return msgResultadoAvaliacao;
	}

	public Long getColaboradorQuestionarioId() {
		return colaboradorQuestionarioId;
	}

	public void setMsgResultadoAvaliacao(String msgResultadoAvaliacao) {
		this.msgResultadoAvaliacao = msgResultadoAvaliacao;
	}

	public boolean isExibeResultadoAutoavaliacao() {
		return exibeResultadoAutoavaliacao;
	}

	public void setExibeResultadoAutoavaliacao(boolean exibeResultadoAutoavaliacao) {
		this.exibeResultadoAutoavaliacao = exibeResultadoAutoavaliacao;
	}

	public ColaboradorQuestionario getColaboradorQuestionario() {
		return colaboradorQuestionario;
	}

	public void setColaboradorQuestionario(
			ColaboradorQuestionario colaboradorQuestionario) {
		this.colaboradorQuestionario = colaboradorQuestionario;
	}

	public String[] getEmpresasCheck() {
		return empresasCheck;
	}

	public void setEmpresasCheck(String[] empresasCheck) {
		this.empresasCheck = empresasCheck;
	}

	public Collection<CheckBox> getEmpresasCheckList() {
		return empresasCheckList;
	}

	public void setEmpresasCheckList(Collection<CheckBox> empresasCheckList) {
		this.empresasCheckList = empresasCheckList;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void setAvaliacoesCheck(String[] avaliacoesCheck) {
		this.avaliacoesCheck = avaliacoesCheck;
	}

	public Collection<CheckBox> getAvaliacoesCheckList() {
		return avaliacoesCheckList;
	}

	
	public void setDesconsiderarAutoAvaliacao(boolean desconsiderarAutoAvaliacao)
	{
		this.desconsiderarAutoAvaliacao = desconsiderarAutoAvaliacao;
	}

	public Collection<CheckBox> getCargosCheckList() {
		return cargosCheckList;
	}

	public void setCargosCheck(String[] cargosCheck) {
		this.cargosCheck = cargosCheck;
	}

	public void setAreasCheck(String[] areasCheck) {
		this.areasCheck = areasCheck;
	}

	public void setCargoManager(CargoManager cargoManager) {
		this.cargoManager = cargoManager;
	}

	public void setExibirObsAvaliadores(boolean exibirObsAvaliadores) {
		this.exibirObsAvaliadores = exibirObsAvaliadores;
	}
}
package com.fortes.rh.web.action.pesquisa;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.relatorio.PerguntaFichaMedica;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.BooleanUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class ColaboradorQuestionarioListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private ColaboradorRespostaManager colaboradorRespostaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private EstabelecimentoManager estabelecimentoManager;
	private QuestionarioManager questionarioManager;
	private ColaboradorManager colaboradorManager;
	private AvaliacaoManager avaliacaoManager;

	private Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
	private Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();
	private Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
	private String[] estabelecimentosCheck;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();

	private ColaboradorQuestionario colaboradorQuestionario;
	private Colaborador colaborador;

	private Questionario questionario;

	private Long avaliacaoId;
	private Long pesquisaId;
	private Long colaboradorId;
	private Long questionarioId;

	private String urlVoltar;
	private String nomeComercialEntreParentese = "";
	private boolean exibirBotaoConcluir;
	private boolean ocultarBotaoVoltar;

	private TipoPergunta tipoPergunta = new TipoPergunta();
	private TipoQuestionario tipoQuestionario = new TipoQuestionario();
	private String colaboradorNome;
	
	private String[] colaboradorCheck;
	private Collection<CheckBox> colaboradorsCheckList = new ArrayList<CheckBox>();
	
	private Map<String, Object> parametros;
	private boolean modoEconomico;
	
	private AvaliacaoDesempenho avaliacaoDesempenho;
	private Collection<AvaliacaoDesempenho> avaliacaoDesempenhos;
	private Collection<PerguntaFichaMedica> perguntasRespondidas = new ArrayList<PerguntaFichaMedica>();
	
	private EmpresaManager empresaManager;
	private Collection<Empresa> empresas;
	private Long empresaId = -1L;
	private char respondida;
	private Long[] colaboradorQuestionarioIds;

	private String totalRespondidas;
	private String totalNaoRespondidas;
	private Boolean compartilharColaboradores;
	
	private String reportFilter;
	private String reportTitle;
	private String msgFinalRelatorioXls;
	
	private boolean agruparPorAspecto;

	public String list() throws Exception
	{
		compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
		
		if(empresaId == null)
			empresaId = getEmpresaSistema().getId();

		empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores ,empresaId,SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_PESQUISA");
		
		questionario = questionarioManager.findByIdProjection(questionario.getId());
		
		colaboradorQuestionarios = colaboradorQuestionarioManager.findByQuestionarioEmpresaRespondida(questionario.getId(), BooleanUtil.getValueCombo(respondida), LongUtil.arrayStringToCollectionLong(estabelecimentosCheck), empresaId);
		
		urlVoltar = TipoQuestionario.getUrlVoltarList(questionario.getTipo(), null);
		
		calcularTotalRespondidas();
		populaEstabelecimentos();
		
		return Action.SUCCESS;
	}

	private void populaEstabelecimentos() 
	{
		Long[] empresaIds;
		if(empresaId != null && empresaId.equals(-1L))
			empresaIds = new CollectionUtil<Empresa>().convertCollectionToArrayIds(empresas);
		else
			empresaIds = new Long[]{empresaId};
		
		estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(empresaIds);
		estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);
	}

	public String imprimirColaboradores() throws Exception
	{
		questionario = questionarioManager.findByIdProjection(questionario.getId());
		colaboradorQuestionarios = colaboradorQuestionarioManager.findColaboradorHistoricoByQuestionario(questionario.getId(), BooleanUtil.getValueCombo(respondida), empresaId);

		if(colaboradorQuestionarios.isEmpty())
			return Action.INPUT;

		calcularTotalRespondidas();
		msgFinalRelatorioXls = colaboradorQuestionarios.size() + " colaboradores/registros. Respondeu Pesquisa: " +  totalRespondidas + ". Não Respondeu: " + totalNaoRespondidas;

		reportFilter = questionario.getTitulo();
		reportTitle = "Colaboradores da " + TipoQuestionario.getDescricao(questionario.getTipo()); 
		
		parametros = RelatorioUtil.getParametrosRelatorio(reportTitle, getEmpresaSistema(), reportFilter);
		
		return Action.SUCCESS;
	}

	public String periodoExperienciaQuestionarioList() throws Exception
	{
		Long empresaId = getEmpresaSistema().getId();
		Collection<AreaOrganizacional> areas = null;
		CollectionUtil<AreaOrganizacional> cUtil = new CollectionUtil<AreaOrganizacional>();
		
		if(colaborador != null)
		{
			Colaborador colaboradorLogado = SecurityUtil.getColaboradorSession(ActionContext.getContext().getSession());
			
			if (SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VER_AREAS"}))
			{
				colaboradors = colaboradorManager.findByNomeCpfMatriculaComHistoricoComfirmado(colaborador, empresaId, null);
			}
			else if (colaboradorLogado != null && colaboradorLogado.getId() != null)
			{
				areas = areaOrganizacionalManager.findAreasByUsuarioResponsavel(getUsuarioLogado(), empresaId);
				Long[] areasIds = cUtil.convertCollectionToArrayIds(areas);
				if (areasIds.length == 0)
					areasIds = new Long[]{-1L};
				colaboradors = colaboradorManager.findByNomeCpfMatriculaComHistoricoComfirmado(colaborador, empresaId, areasIds);
			}
			
			if(colaborador.getId() != null)
			{
				colaborador = colaboradorManager.findByIdHistoricoAtual(colaborador.getId(), Boolean.TRUE);
				colaboradorManager.setFamiliaAreas(Arrays.asList(colaborador), empresaId);
				
				if(!colaborador.getNome().equals(colaborador.getNomeComercial()))
					nomeComercialEntreParentese = " (" + colaborador.getNomeComercial() + ")";
				
				colaboradorQuestionarios = colaboradorQuestionarioManager.findAvaliacaoByColaborador(colaborador.getId(),false);				
			}
		}
		
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		//TODO: Este metodo tambem remove o "colaboradorQuestionario" relacionado. O ideal seria
		//      que o "colaboradorQuestionarioManager" removesse as respostas e nao o contrario.
		try {
			ColaboradorQuestionario colaboradorQuestionarioTemp = colaboradorQuestionarioManager.findById(colaboradorQuestionario.getId());
			
			colaboradorRespostaManager.removeFicha(colaboradorQuestionario.getId());
			
			if(colaboradorQuestionarioTemp.getAvaliacao() != null  && TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA == colaboradorQuestionarioTemp.getAvaliacao().getTipoModeloAvaliacao()){
				gerenciadorComunicacaoManager.enviarMensagemAoExluirRespostasAvaliacaoPeriodoDeExperiencia(colaboradorQuestionarioTemp, getUsuarioLogado(), colaboradorQuestionarioTemp.getColaborador().getEmpresa());
			}
			addActionSuccess("Exclusão realizada com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Ocorreu um erro ao realizar a exclusão");
		}
		return Action.SUCCESS;
	}
	
	public String deleteColaboradores() throws Exception
	{
		colaboradorRespostaManager.removeFichas(colaboradorQuestionarioIds);
		
		return Action.SUCCESS;
	}

	public Collection<ColaboradorQuestionario> getColaboradorQuestionarios()
	{
		return colaboradorQuestionarios;
	}

	public String visualizarRespostasPorColaborador()
	{
		colaborador = colaboradorManager.findByIdProjectionUsuario(colaboradorId);
		questionario = questionarioManager.findByIdProjection(questionario.getId());
		colaboradorQuestionario = colaboradorQuestionarioManager.findByQuestionario(questionario.getId(), colaborador.getId(), null);
		colaboradorRespostas = colaboradorRespostaManager.findRespostasColaborador(colaboradorQuestionario.getId(), questionario.isAplicarPorAspecto());
			
		return Action.SUCCESS;
	}
	
	public String imprimirAvaliacaoRespondida()
	{
		try
		{
			if (modoEconomico)
			{
				parametros = new HashMap<String, Object>();//tem que ser aqui
				perguntasRespondidas = questionarioManager.montaImpressaoAvaliacaoRespondida(colaboradorQuestionario.getId(), parametros);
				return "success_economico";
			}
			else
			{
				colaboradorQuestionario = colaboradorQuestionarioManager.findByIdProjection(colaboradorQuestionario.getId());
				
				String filtro = colaboradorQuestionario.getAvaliacao().getTitulo();
				filtro += "\nNome: " + colaboradorQuestionario.getColaborador().getNome();
				filtro += "\nPerformance: " + colaboradorQuestionario.getPerformanceFormatada();
				
				parametros = RelatorioUtil.getParametrosRelatorio("Acompanhamento do Período de Experiência", getEmpresaSistema(), filtro);
				parametros.put("Observacao", colaboradorQuestionario.getObservacao());
				colaboradorRespostas = colaboradorRespostaManager.findPerguntasRespostasByColaboradorQuestionario(colaboradorQuestionario.getId(), agruparPorAspecto);
				
				return Action.SUCCESS;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return Action.INPUT;
		}
	}

	public ColaboradorQuestionario getColaboradorQuestionario()
	{
		if (colaboradorQuestionario == null)
		{
			colaboradorQuestionario = new ColaboradorQuestionario();
		}
		return colaboradorQuestionario;
	}
	
	private void calcularTotalRespondidas()
	{
		double total = colaboradorQuestionarios.size();
		double totalResp=0.0;		
		for (ColaboradorQuestionario colaboradorQuestionario : colaboradorQuestionarios)
			if (colaboradorQuestionario.getRespondida()) 
				totalResp += 1.0;
		double totalNaoResp=total - totalResp;
		DecimalFormat formata = (DecimalFormat) DecimalFormat.getInstance(new Locale("pt", "BR"));
		formata.applyPattern("#0.00 %");
		totalRespondidas = totalResp + " (" + formata.format(totalResp/total) + ")";
		totalNaoRespondidas = totalNaoResp + " (" + formata.format(totalNaoResp/total) + ")";
	}

	public void setColaboradorQuestionario(ColaboradorQuestionario colaboradorQuestionario)
	{
		this.colaboradorQuestionario = colaboradorQuestionario;
	}

	public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager)
	{
		this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
	}

	public Questionario getQuestionario()
	{
		return questionario;
	}

	public void setQuestionario(Questionario questionario)
	{
		this.questionario = questionario;
	}

	public Long getPesquisaId()
	{
		return pesquisaId;
	}

	public void setPesquisaId(Long pesquisaId)
	{
		this.pesquisaId = pesquisaId;
	}

	public void setColaboradorRespostaManager(ColaboradorRespostaManager colaboradorRespostaManager)
	{
		this.colaboradorRespostaManager = colaboradorRespostaManager;
	}

	public Collection<ColaboradorResposta> getColaboradorRespostas()
	{
		return colaboradorRespostas;
	}

	public Long getColaboradorId()
	{
		return colaboradorId;
	}

	public void setColaboradorId(Long colaboradorId)
	{
		this.colaboradorId = colaboradorId;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public TipoPergunta getTipoPergunta()
	{
		return tipoPergunta;
	}

	public ColaboradorManager getColaboradorManager()
	{
		return colaboradorManager;
	}
	
	public void setQuestionarioManager(QuestionarioManager questionarioManager)
	{
		this.questionarioManager = questionarioManager;
	}

	public String getUrlVoltar()
	{
		return urlVoltar;
	}

	public void setUrlVoltar(String urlVoltar)
	{
		this.urlVoltar = urlVoltar;
	}

	public Long getQuestionarioId()
	{
		return questionarioId;
	}

	public void setQuestionarioId(Long questionarioId)
	{
		this.questionarioId = questionarioId;
	}

	public TipoQuestionario getTipoQuestionario()
	{
		return tipoQuestionario;
	}

	public boolean isExibirBotaoConcluir()
	{
		return exibirBotaoConcluir;
	}

	public void setExibirBotaoConcluir(boolean exibirBotaoConcluir)
	{
		this.exibirBotaoConcluir = exibirBotaoConcluir;
	}

	public Collection<Colaborador> getColaboradors()
	{
		return colaboradors;
	}

	public String getColaboradorNome()
	{
		return colaboradorNome;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}
	public String getNomeComercialEntreParentese() {
		return nomeComercialEntreParentese;
	}

	public void setNomeComercialEntreParentese(String nomeComercialEntreParentese) {
		this.nomeComercialEntreParentese = nomeComercialEntreParentese;
	}

	public AvaliacaoDesempenho getAvaliacaoDesempenho() {
		return avaliacaoDesempenho;
	}

	public void setAvaliacaoDesempenho(AvaliacaoDesempenho avaliacaoDesempenho) {
		this.avaliacaoDesempenho = avaliacaoDesempenho;
	}

	public Collection<AvaliacaoDesempenho> getAvaliacaoDesempenhos() {
		return avaliacaoDesempenhos;
	}

	public Collection<PerguntaFichaMedica> getPerguntasRespondidas()
	{
		return perguntasRespondidas;
	}

	public String getDataDoDia() {
		return DateUtil.formataDiaMesAno(new Date());
	}

	public Collection<CheckBox> getColaboradorsCheckList() {
		return colaboradorsCheckList;
	}

	public String[] getColaboradorCheck() {
		return colaboradorCheck;
	}

	public void setColaboradorCheck(String[] colaboradorCheck) {
		this.colaboradorCheck = colaboradorCheck;
	}
	
//	public Empresa getEmpresa() {
//		return empresa;
//	}
//
//	public void setEmpresa(Empresa empresa) {
//		this.empresa = empresa;
//	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public Collection<Empresa> getEmpresas() {
		return empresas;
	}

	public char getRespondida() {
		return respondida;
	}

	public void setRespondida(char respondida) {
		this.respondida = respondida;
	}

	public void setColaboradorQuestionarioIds(Long[] colaboradorQuestionarioIds) {
		this.colaboradorQuestionarioIds = colaboradorQuestionarioIds;
	}

	public String getTotalRespondidas() {
		return totalRespondidas;
	}

	public String getTotalNaoRespondidas() {
		return totalNaoRespondidas;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public Boolean getCompartilharColaboradores() {
		return compartilharColaboradores;
	}

	public boolean isOcultarBotaoVoltar() {
		return ocultarBotaoVoltar;
	}

	public void setOcultarBotaoVoltar(boolean ocultarBotaoVoltar) {
		this.ocultarBotaoVoltar = ocultarBotaoVoltar;
	}

	public String[] getEstabelecimentosCheck() {
		return estabelecimentosCheck;
	}

	public void setEstabelecimentosCheck(String[] estabelecimentosCheck) {
		this.estabelecimentosCheck = estabelecimentosCheck;
	}

	public Collection<CheckBox> getEstabelecimentosCheckList() {
		return estabelecimentosCheckList;
	}

	public void setEstabelecimentosCheckList(
			Collection<CheckBox> estabelecimentosCheckList) {
		this.estabelecimentosCheckList = estabelecimentosCheckList;
	}

	public void setEstabelecimentoManager(
			EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public void setAreaOrganizacionalManager(
			AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public String getReportFilter() {
		return reportFilter;
	}

	public String getReportTitle() {
		return reportTitle;
	}

	public String getMsgFinalRelatorioXls() {
		return msgFinalRelatorioXls;
	}

	public boolean isModoEconomico() {
		return modoEconomico;
	}

	public void setModoEconomico(boolean modoEconomico) {
		this.modoEconomico = modoEconomico;
	}

	public void setAvaliacaoManager(AvaliacaoManager avaliacaoManager) {
		this.avaliacaoManager = avaliacaoManager;
	}

	public void setAvaliacaoId(Long avaliacaoId) {
		this.avaliacaoId = avaliacaoId;
	}

	public boolean isAgruparPorAspecto() {
		return agruparPorAspecto;
	}

	public void setAgruparPorAspecto(boolean agruparPorAspecto) {
		this.agruparPorAspecto = agruparPorAspecto;
	}

	public ParametrosDoSistemaManager getParametrosDoSistemaManager() {
		return parametrosDoSistemaManager;
	}

	public GerenciadorComunicacaoManager getGerenciadorComunicacaoManager() {
		return gerenciadorComunicacaoManager;
	}

	public void setGerenciadorComunicacaoManager(
			GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}
}
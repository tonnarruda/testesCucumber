package com.fortes.rh.web.action.avaliacao;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoManager;
import com.fortes.rh.business.avaliacao.ParticipanteAvaliacaoDesempenhoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.exception.AvaliacaoRespondidaException;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.avaliacao.AgrupadorAnaliseDesempenhoOrganizacao;
import com.fortes.rh.model.avaliacao.AnaliseDesempenhoOrganizacao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ParticipanteAvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.RelatorioAnaliseDesempenhoColaborador;
import com.fortes.rh.model.avaliacao.ResultadoAvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ResultadoCompetenciaColaborador;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.CompetenciasConsideradas;
import com.fortes.rh.model.dicionario.FiltroSituacaoAvaliacao;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.dicionario.TipoParticipanteAvaliacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.webwork.dispatcher.SessionMap;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
	private ParticipanteAvaliacaoDesempenhoManager participanteAvaliacaoDesempenhoManager;
	private ConfiguracaoCompetenciaAvaliacaoDesempenhoManager configuracaoCompetenciaAvaliacaoDesempenhoManager;
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	
	private AvaliacaoDesempenho avaliacaoDesempenho;
	private Collection<AvaliacaoDesempenho> avaliacaoDesempenhos;
	private Collection<Avaliacao> avaliacaos;
	private Collection<FaixaSalarial> faixaSalariais;
	private Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> configuracaoCompetenciaAvaliacaoDesempenhos = new ArrayList<ConfiguracaoCompetenciaAvaliacaoDesempenho>();
	
	private Collection<Colaborador> participantes;
	private Collection<Colaborador> avaliadors;
	
	private Collection<ParticipanteAvaliacaoDesempenho> participantesAvaliados = new ArrayList<ParticipanteAvaliacaoDesempenho>();
	private Collection<ParticipanteAvaliacaoDesempenho> participantesAvaliadores = new ArrayList<ParticipanteAvaliacaoDesempenho>();
	
	private Colaborador avaliador;
	private Colaborador avaliado;
	private ColaboradorQuestionario colaboradorQuestionario; 
	
	private Empresa empresa;
	private Collection<Empresa> empresas;
	private Long[] empresasPermitidasIds;
	
	private Collection<CheckBox> empresasCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> cargosCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> colaboradorsCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> avaliacoesCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> cargosAvaliadoCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> avaliadoresCheckList = new ArrayList<CheckBox>();
	
	private String[] empresasCheck;
	private String[] avaliadosCheck;
	private String[] colaboradorsCheck;
	private String[] avaliados;
	private String[] avaliadores;
	private String[] avaliacoesCheck;
	private Long[] estabelecimentosCheck;
	private Long[] cargosCheck;
	private Long[] areasCheck;
	private Long[] competenciasCheck;
	private Long[] colaboradorQuestionariosRemovidos;
	private Long[] participantesAvaliadosRemovidos;
	private Long[] participantesAvaliadoresRemovidos;
	
	// Variáveis utilizadas para receber os ids dos registros não selecionados na tela.(CheckListBox sem opção selecionada.)
	private Long[] estabelecimentosCheckAux;
	private Long[] cargosCheckAux;
	private Long[] areasCheckAux;
	private Long[] competenciasCheckAux;
	
	private String agrupamentoDasCompetencias;
	private Collection<ResultadoCompetenciaColaborador> resultadosCompetenciaColaborador = new ArrayList<ResultadoCompetenciaColaborador>();
	private Integer notaMinimaMediaGeralCompetencia;
	private boolean agruparPorCargo;
	
	private String nomeBusca;
	private Long empresaId;
	private Long colaboradorQuestionarioId;
	
	private boolean isAvaliados;
	private boolean temAvaliacoesRespondidas;
	private boolean relatorioDetalhado = true;
	
	private Date periodoInicial;
	private Date periodoFinal;
	
	private char respondida = 'N';
	
	private String opcaoResultado; // criterio ou avaliador
	private Map<String, Object> parametros;
	private Collection<Pergunta> perguntas;
	private boolean exibirRespostas;
	private boolean exibirComentarios;
	private boolean agruparPorAspectos;
	private boolean desconsiderarAutoAvaliacao;
	private boolean exibirObsAvaliadores;
	private boolean clonarParticipantes;
	private boolean exibeResultadoAutoAvaliacao;
	private boolean exibeMenuRespondidoParcialmente;
	
	private Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
	private Collection<ResultadoAvaliacaoDesempenho> resultados = new ArrayList<ResultadoAvaliacaoDesempenho>();
	private Collection<AgrupadorAnaliseDesempenhoOrganizacao> agrupadorAnaliseDesempenhoOrganizacaos = new ArrayList<AgrupadorAnaliseDesempenhoOrganizacao>();
	private Collection<AnaliseDesempenhoOrganizacao> analiseDesempenhoOrganizacaos = new ArrayList<AnaliseDesempenhoOrganizacao>();
	private Boolean compartilharColaboradores;
	private String msgResultadoAvaliacao;
	private Long avaliacaoId;
	private String reportTitle;
	private CompetenciasConsideradas competenciasConsideradas = new CompetenciasConsideradas();
	private String primeiraAssinatura;
	private String segundaAssinatura;
	
	private Long limiteQtdCompetenciaPorPagina = 1L;
	
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
			addActionSuccess("Email(s) enviado(s) com sucesso.");
		} catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Erro ao enviar email(s).");
		}
		
		return Action.SUCCESS;
	}
	
	public String prepareParticipantes() throws Exception
	{
		setVideoAjuda(1264L);
		
		empresaId = getEmpresaSistema().getId();
		compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
		empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores, empresaId, SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()));
		
		avaliacaoDesempenho = avaliacaoDesempenhoManager.findById(avaliacaoDesempenho.getId());
		participantesAvaliados = participanteAvaliacaoDesempenhoManager.findParticipantes(avaliacaoDesempenho.getId(), TipoParticipanteAvaliacao.AVALIADO);
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		
		participantesAvaliadores = participanteAvaliacaoDesempenhoManager.findParticipantes(avaliacaoDesempenho.getId(), TipoParticipanteAvaliacao.AVALIADOR);
		for (ParticipanteAvaliacaoDesempenho avaliador : participantesAvaliadores) {
			avaliador.getColaborador().setAvaliados(new ArrayList<Colaborador>());
			for (ColaboradorQuestionario colaboradorQuestionario : colaboradorQuestionarioManager.findAvaliadosByAvaliador(avaliacaoDesempenho.getId(), avaliador.getColaborador().getId(), FiltroSituacaoAvaliacao.TODAS.getOpcao(), false, false, null)) {
				colaboradorQuestionario.getColaborador().setColaboradorQuestionario(colaboradorQuestionario);
				avaliador.getColaborador().getAvaliados().add(colaboradorQuestionario.getColaborador());
			}
		}
		
		return Action.SUCCESS;
	}
	
	public String prepareCompetencias() throws Exception
	{
		setVideoAjuda(1264L);
		
		empresaId = getEmpresaSistema().getId();
		avaliacaoDesempenho = avaliacaoDesempenhoManager.findById(avaliacaoDesempenho.getId());

		faixaSalariais = configuracaoCompetenciaAvaliacaoDesempenhoManager.findFaixasSalariaisByCompetenciasConfiguradasParaAvaliacaoDesempenho(avaliacaoDesempenho.getId());
		faixaSalariais.addAll(participanteAvaliacaoDesempenhoManager.findFaixasSalariaisDosAvaliadosComCompetenciasByAvaliacaoDesempenho(avaliacaoDesempenho,new CollectionUtil<FaixaSalarial>().convertCollectionToArrayIds(faixaSalariais)));

		avaliadors = participanteAvaliacaoDesempenhoManager.findColaboradoresParticipantes(avaliacaoDesempenho.getId(), TipoParticipanteAvaliacao.AVALIADOR);
		
		for (Colaborador avaliador : avaliadors) {
			avaliador.setFaixaSalariaisAvaliados(new ArrayList<FaixaSalarial>());
			for (FaixaSalarial faixaSalarialAvaliado : participanteAvaliacaoDesempenhoManager.findFaixasSalariaisDosAvaliadosByAvaliador(avaliacaoDesempenho.getId(), avaliador.getId()) ) {
				faixaSalarialAvaliado.setConfiguracaoCompetenciaAvaliacaoDesempenhos(configuracaoCompetenciaAvaliacaoDesempenhoManager.findByAvaliador(avaliador.getId(), faixaSalarialAvaliado.getId(), avaliacaoDesempenho.getId()));
				
				avaliador.getFaixaSalariaisAvaliados().add(faixaSalarialAvaliado);
			}
		}
		
		if (avaliacaoDesempenho.isLiberada()) 
			addActionMessage("Não é possível configurar as competências pois a avaliação está liberada.");
		
		return Action.SUCCESS;
	}
	
	public String saveCompetencias() throws Exception
	{
		try {
			avaliacaoDesempenho = avaliacaoDesempenhoManager.findByIdProjection(avaliacaoDesempenho.getId());
			
			configuracaoCompetenciaAvaliacaoDesempenhos.removeAll(Collections.singleton(null));
			configuracaoCompetenciaAvaliacaoDesempenhoManager.save(new ArrayList<ConfiguracaoCompetenciaAvaliacaoDesempenho>(configuracaoCompetenciaAvaliacaoDesempenhos), avaliacaoDesempenho);
			configuracaoCompetenciaAvaliacaoDesempenhoManager.removeNotIn(configuracaoCompetenciaAvaliacaoDesempenhos, avaliacaoDesempenho.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
			prepareCompetencias();
			return Action.ERROR;
		}

		prepareCompetencias();
		return Action.SUCCESS;
	}
	
	public String prepareResultado()
	{
		try {
			compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
			empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores, empresaId, SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()));
			avaliacaoDesempenho = avaliacaoDesempenhoManager.findById(avaliacaoDesempenho.getId());
			participantes = colaboradorManager.findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), true, null, null, false, null);
			
			colaboradorsCheckList = CheckListBoxUtil.populaCheckListBox(participantes, "getId", "getNome", null);
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
	
	public String saveParticipantes() throws Exception {
		try {
			avaliacaoDesempenho = avaliacaoDesempenhoManager.findByIdProjection(avaliacaoDesempenho.getId());
			participanteAvaliacaoDesempenhoManager.save(avaliacaoDesempenho, participantesAvaliados, participantesAvaliadores, colaboradorQuestionarios, colaboradorQuestionariosRemovidos, participantesAvaliadosRemovidos, participantesAvaliadoresRemovidos);
			addActionSuccess("Gravado com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Não foi possível gravar.");
		}
		
		prepareParticipantes();
		return Action.SUCCESS;
	}

	public String prepareAnaliseDesempenhoCompetenciaColaborador()
	{
		try {
			setVideoAjuda(1264L);
			compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
			empresasPermitidasIds = new CollectionUtil<Empresa>().convertCollectionToArrayIds(empresaManager.findEmpresasPermitidas(compartilharColaboradores, empresaId, SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession())));
			
			avaliacaoDesempenhos = avaliacaoDesempenhoManager.findComCompetencia(getEmpresaSistema().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Action.SUCCESS;
	}
	
	public String analiseDesempenhoCompetenciaColaborador()
	{
		if(relatorioDetalhado)
			return competenciaColaboradorDetalhado();
		else
			return competenciaColaboradorResumido();
	}
	
	public String competenciaColaboradorResumido()
	{
		try {
			AvaliacaoDesempenho avaliacao = avaliacaoDesempenhoManager.findById(avaliacaoDesempenho.getId());
			ResultadoAvaliacaoDesempenho resultadoAvaliacaoDesempenho = avaliacaoDesempenhoManager.getResultadoAvaliacaoDesempenho(avaliacao, avaliado.getId());
			
			if(resultadoAvaliacaoDesempenho.getCompetencias().size() == 0){
				addActionMessage("Não existem competências para o avaliado informado.");
				prepareAnaliseDesempenhoCompetenciaColaborador();
				return INPUT;
			}
			
			resultados.add(resultadoAvaliacaoDesempenho);
			parametros = RelatorioUtil.getParametrosRelatorio("Análise de Desempenho das Competências do Colaborador", getEmpresaSistema(), avaliacao.getTitulo());
			parametros.put("PRIMEIRAASSINATURA", primeiraAssinatura);
			parametros.put("SEGUNDAASSINATURA", segundaAssinatura);
			
		} catch (Exception e) {
			addActionError("Problema ao gerar relatório.");
			e.printStackTrace();
			prepareAnaliseDesempenhoCompetenciaColaborador();
			return Action.INPUT;
		}

		return "successResumido";
	}
	
	@SuppressWarnings({ "unchecked", "static-access", "rawtypes" })
	public String competenciaColaboradorDetalhado(){
		try {
			avaliacaoDesempenho = avaliacaoDesempenhoManager.findByIdProjection(avaliacaoDesempenho.getId());
			Collection<Long> avaliadoresIds = new CollectionUtil().convertArrayToCollection(new StringUtil().stringToLong(avaliadores));
			RelatorioAnaliseDesempenhoColaborador relatorioAnaliseDesempenhoColaborador = configuracaoNivelCompetenciaManager.montaRelatorioAnaliseDesempenhoColaborador(avaliacaoDesempenho.getId(), avaliado.getId(), avaliadoresIds, notaMinimaMediaGeralCompetencia, agruparPorCargo); 
			
			resultadosCompetenciaColaborador =  relatorioAnaliseDesempenhoColaborador.getResultadosCompetenciaColaborador();
			if(resultadosCompetenciaColaborador.size() == 0){
				addActionMessage("Não existem competências para o avaliado informado.");
				prepareAnaliseDesempenhoCompetenciaColaborador();
				return INPUT;
			}
			
			relatorioAnaliseDesempenhoColaborador.setAvaliado(colaboradorManager.findByIdHistoricoAtual(avaliado.getId(), false));
			
			parametros = RelatorioUtil.getParametrosRelatorio("Resultado das Competências do Colaborador", getEmpresaSistema(), avaliacaoDesempenho.getTitulo());
			parametros.put("VALORMAXIMOGRAFICO", relatorioAnaliseDesempenhoColaborador.getValorMaximoGrafico() + 1);
			parametros.put("AVALIADONOME", relatorioAnaliseDesempenhoColaborador.getAvaliado().getNome());
			parametros.put("NOTAMINIMAMEDIAGERALCOMPETENCIA", relatorioAnaliseDesempenhoColaborador.getNotaMinimaMediaGeralCompetencia());
			parametros.put("CARGOFAIXA", relatorioAnaliseDesempenhoColaborador.getAvaliado().getCargoFaixa());
			parametros.put("AREANOME", relatorioAnaliseDesempenhoColaborador.getAvaliado().getAreaOrganizacional().getNome());
			parametros.put("MATRICULA", relatorioAnaliseDesempenhoColaborador.getAvaliado().getMatricula());
			parametros.put("DATAADMISSAO", relatorioAnaliseDesempenhoColaborador.getAvaliado().getDataAdmissaoFormatada());
			parametros.put("TAMANHOCOLECTIONNIVELCOMPETENCIAS", relatorioAnaliseDesempenhoColaborador.getTamanhoCollectionNiveisCompetencias());
			parametros.put("RESULTADOCOMPETENCIAFINAL", new JRBeanCollectionDataSource(relatorioAnaliseDesempenhoColaborador.getResultadoCompetenciaFinal()));
			parametros.put("NIVEISCOMPETENCIADESCRICAO", relatorioAnaliseDesempenhoColaborador.getNiveisCompetenciasDescricao());
			parametros.put("NIVEISCOMPETENCIAORDEM", relatorioAnaliseDesempenhoColaborador.getNiveisCompetenciasOrdem());
			parametros.put("LEGENDA", relatorioAnaliseDesempenhoColaborador.getLegenda());
			parametros.put("PRIMEIRAASSINATURA", primeiraAssinatura);
			parametros.put("SEGUNDAASSINATURA", segundaAssinatura);

		} catch (Exception e) {
			addActionError("Problema ao gerar relatório.");
			e.printStackTrace();
			prepareAnaliseDesempenhoCompetenciaColaborador();
			return Action.INPUT;
		}

		return "successDetalhado";
	}
	
	public String prepareAnaliseDesempenhoCompetenciaOrganizacao()
	{
		try {
			avaliacaoDesempenhos = avaliacaoDesempenhoManager.findComCompetencia(getEmpresaSistema().getId());
			if(avaliacaoDesempenhos.isEmpty()){
				addActionWarning("Não existe avaliação de desempenho que tenha competência avaliada.");
				return Action.INPUT;
			}
			avaliacoesCheckList = CheckListBoxUtil.populaCheckListBox(avaliacaoDesempenhos, "getId", "getTitulo", null);
		} catch (Exception e) {
			e.printStackTrace();
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}
	
	public String imprimeAnaliseDesempenhoCompetenciaOrganizacao()
	{
		try {
			if(competenciasCheckAux == null && competenciasCheck == null){
				addActionMessage("Não existem competências a serem informados no relatório.");
				prepareAnaliseDesempenhoCompetenciaOrganizacao();
				return INPUT;
			}
			
			analiseDesempenhoOrganizacaos = avaliacaoDesempenhoManager.findAnaliseDesempenhoOrganizacao(
					StringUtil.stringToLong(avaliacoesCheck),
					(estabelecimentosCheck == null ? estabelecimentosCheckAux : estabelecimentosCheck),
					(cargosCheck == null ? cargosCheckAux : cargosCheck), 
					(areasCheck == null ? areasCheckAux : areasCheck),
					(competenciasCheck == null ? competenciasCheckAux : competenciasCheck), 
					agrupamentoDasCompetencias, 
					getEmpresaSistema().getId());

			if(analiseDesempenhoOrganizacaos.isEmpty()){
				addActionMessage("Não existem respostas para o filtro informado.");
				prepareAnaliseDesempenhoCompetenciaOrganizacao();
				return INPUT;
			}

			agrupadorAnaliseDesempenhoOrganizacaoParaSepararEmPaginas();
			reportTitle = "Análise de Desempenho das Competências da Organização";
			parametros = RelatorioUtil.getParametrosRelatorio(reportTitle, getEmpresaSistema(), "Agrupado por " + new AnaliseDesempenhoOrganizacao().getAgrupamento(agrupamentoDasCompetencias));

		} catch (Exception e) {
			addActionError("Não foi possível gerar o relatório.");
			e.printStackTrace();
			prepareAnaliseDesempenhoCompetenciaOrganizacao();
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}

	private void agrupadorAnaliseDesempenhoOrganizacaoParaSepararEmPaginas() throws CloneNotSupportedException {
		Long limiteQtdAnaliseDesempOrganiPorPagina = 20L;
		
		AgrupadorAnaliseDesempenhoOrganizacao agrupadorAnaliseDesempenhoOrganizacao = new AgrupadorAnaliseDesempenhoOrganizacao();
		agrupadorAnaliseDesempenhoOrganizacao.setAnaliseDesempenhoOrganizacaos(new ArrayList<AnaliseDesempenhoOrganizacao>());
		String competenciaNome = "";
		Long qtdCompetencia = 0L;
		
		for (AnaliseDesempenhoOrganizacao analiseDesempenhoOrganizacao : analiseDesempenhoOrganizacaos) {
			if((agrupadorAnaliseDesempenhoOrganizacao.getAnaliseDesempenhoOrganizacaos().size() >= limiteQtdAnaliseDesempOrganiPorPagina 
				|| qtdCompetencia >= limiteQtdCompetenciaPorPagina) && (!analiseDesempenhoOrganizacao.getCompetenciaNome().equals(competenciaNome))){
					agrupadorAnaliseDesempenhoOrganizacaos.add((AgrupadorAnaliseDesempenhoOrganizacao) agrupadorAnaliseDesempenhoOrganizacao.clone());
					agrupadorAnaliseDesempenhoOrganizacao = new AgrupadorAnaliseDesempenhoOrganizacao();
					agrupadorAnaliseDesempenhoOrganizacao.setAnaliseDesempenhoOrganizacaos(new ArrayList<AnaliseDesempenhoOrganizacao>());
					competenciaNome = analiseDesempenhoOrganizacao.getCompetenciaNome();
					qtdCompetencia = 1L;
			}
			
			if(!analiseDesempenhoOrganizacao.getCompetenciaNome().equals(competenciaNome)){
				competenciaNome = analiseDesempenhoOrganizacao.getCompetenciaNome();
				qtdCompetencia++;
			}
			
			agrupadorAnaliseDesempenhoOrganizacao.getAnaliseDesempenhoOrganizacaos().add(analiseDesempenhoOrganizacao);
		}
		
		agrupadorAnaliseDesempenhoOrganizacaos.add((AgrupadorAnaliseDesempenhoOrganizacao) agrupadorAnaliseDesempenhoOrganizacao.clone());
	}
	
	public String imprimeAnaliseDesempenhoCompetenciaOrganizacaoXls()
	{
		String result = imprimeAnaliseDesempenhoCompetenciaOrganizacao();
		if(Action.INPUT.equals(result))
			return Action.INPUT;
		
		if(agrupamentoDasCompetencias.equals(AnaliseDesempenhoOrganizacao.POR_AREA))
			return "successArea";
		else if(agrupamentoDasCompetencias.equals(AnaliseDesempenhoOrganizacao.POR_CARGO))
			return "successCargo";
		
		return "successEmpresa";
	}
	
	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		temAvaliacoesRespondidas = !colaboradorQuestionarioManager.findRespondidasByAvaliacaoDesempenho(avaliacaoDesempenho.getId()).isEmpty();
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		avaliacaoDesempenho.setEmpresa(getEmpresaSistema());
		avaliacaoDesempenhoManager.save(avaliacaoDesempenho);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try {
			avaliacaoDesempenho.setEmpresa(getEmpresaSistema());
			avaliacaoDesempenhoManager.update(avaliacaoDesempenho);
			
			colaboradorQuestionarioManager.updateAvaliacaoFromColaboradorQuestionarioByAvaliacaoDesempenho(avaliacaoDesempenho);
			
			if( avaliacaoDesempenho.getAvaliacao() != null && avaliacaoDesempenho.getAvaliacao().getId() != null) {
				Avaliacao avaliacao = avaliacaoManager.findById(avaliacaoDesempenho.getAvaliacao().getId());
				
				if ( !avaliacao.isAvaliarCompetenciasCargo() )
					configuracaoCompetenciaAvaliacaoDesempenhoManager.removeByAvaliacaoDesempenho(avaliacaoDesempenho.getId());	
			}
			
			addActionSuccess("Avaliação de desempenho atualizada com sucesso.");
		} catch (Exception e) {
			addActionError("Não foi possível atualizar a avaliação de desempenho.");
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	public String clonar()
	{
		try
		{
			Long[] empresasIds = LongUtil.arrayStringToArrayLong(empresasCheck);
			if (empresasIds != null && empresasIds.length > 0)
				avaliacaoDesempenhoManager.clonar(avaliacaoDesempenho.getId(), clonarParticipantes, empresasIds);
			else
				avaliacaoDesempenhoManager.clonar(avaliacaoDesempenho.getId(), clonarParticipantes, getEmpresaSistema().getId());
				
			addActionSuccess("Avaliação de desempenho clonada com sucesso.");
		} 
		catch (Exception e) {
			addActionError("Não foi possível clonar a avaliação de desempenho.");
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		setVideoAjuda(1264L);
		avaliacaos = avaliacaoManager.findAllSelect(null, null, getEmpresaSistema().getId(), true, TipoModeloAvaliacao.DESEMPENHO, null);
		
		setTotalSize(avaliacaoDesempenhoManager.findCountTituloModeloAvaliacao(null, null, periodoInicial, periodoFinal, getEmpresaSistema().getId(), nomeBusca, avaliacaoId, null));
		avaliacaoDesempenhos = avaliacaoDesempenhoManager.findTituloModeloAvaliacao(getPage(), getPagingSize(), periodoInicial, periodoFinal, getEmpresaSistema().getId(), nomeBusca, avaliacaoId, null);
		
		Collection<Empresa> empresas = empresaManager.findEmpresasPermitidas(true , null, getUsuarioLogado().getId(), "ROLE_MOV_QUESTIONARIO");
   		empresasCheckList =  CheckListBoxUtil.populaCheckListBox(empresas, "getId", "getNome", null);
		
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			avaliacaoDesempenhoManager.remover(avaliacaoDesempenho.getId());
			addActionSuccess("Avaliação de desempenho excluída com sucesso.");
		}
		catch (AvaliacaoRespondidaException e)
		{
			addActionWarning(e.getMessage());
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
			addActionSuccess("Respostas da avaliação de desempenho excluídas com sucesso.");
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
			avaliacaoDesempenhoManager.liberarEmLote(avaliacoesCheck);
			addActionSuccess("Avaliações liberadas com sucesso.");
			return Action.SUCCESS;
		}
		catch (FortesException e) 
		{
			addActionWarning("Não foi possível realizar a operação 'Liberar avaliações em lote': " +
					"<br />Existem avaliações com número insuficiente de participantes ou " +
					"avaliação que não permite a autoavaliação com apenas um participante. " +
					"<br />Avaliações de Desempenho:" +
					"<br /> " + e.getMessage());
			e.printStackTrace();
			return Action.ERROR;
		}
		catch (Exception e) 
		{
			addActionError("Não foi possível liberar as avaliações.");
			e.printStackTrace();
			return Action.ERROR;
		}
	}
	
	public String liberar() 
	{
		try 
		{
			Collection<Colaborador> avaliados = colaboradorManager.findParticipantesDistinctByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), true, null);
			Collection<Colaborador> avaliadores = colaboradorManager.findParticipantesDistinctByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), false, null);

			avaliacaoDesempenho = avaliacaoDesempenhoManager.findByIdProjection(avaliacaoDesempenho.getId());
			colaboradorQuestionarioManager.validaAssociacao(avaliados, avaliadores, avaliacaoDesempenho.isPermiteAutoAvaliacao());
			
			avaliacaoDesempenhoManager.liberarOrBloquear(avaliacaoDesempenho, true);
			
			if( DateUtil.between(DateUtil.criarDataMesAno(new Date()), avaliacaoDesempenho.getInicio(), avaliacaoDesempenho.getFim()) )
				avaliacaoDesempenhoManager.enviarLembreteAoLiberar(avaliacaoDesempenho.getId(), getEmpresaSistema());
			
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
			avaliacaoDesempenhoManager.liberarOrBloquear(avaliacaoDesempenho, false);
			addActionSuccess("Avaliação bloqueada com sucesso.");
		}
		catch (AvaliacaoRespondidaException e)
		{
			addActionWarning(e.getMessage());
		}
		catch (Exception e) 
		{
			addActionError("Não foi possível bloquear esta avaliação.");
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	private void prepareList(){
		empresaId = getEmpresaSistema().getId();
		empresas = empresaManager.findEmpresasPermitidas(true, empresaId, SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()));
		
		if(avaliador == null)
			avaliador = SecurityUtil.getColaboradorSession(ActionContext.getContext().getSession());
		
		if(SecurityUtil.verifyRole((SessionMap) ActionContext.getContext().getSession(), new String[]{"ROLE_RESPONDER_AVALIACAO_DESEMP_POR_OUTRO_USUARIO"}) )
			avaliacaoDesempenhos = avaliacaoDesempenhoManager.findByAvaliador(null, true, empresaId );//pega todas liberadas
		else
			avaliacaoDesempenhos = avaliacaoDesempenhoManager.findByAvaliador(avaliador.getId(), true, empresaId);
		
		if(avaliacaoDesempenho == null && ! avaliacaoDesempenhos.isEmpty())
			avaliacaoDesempenho = (AvaliacaoDesempenho) avaliacaoDesempenhos.toArray()[0];
		
		if(avaliacaoDesempenho != null){
			if(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_RESPONDER_AVALIACAO_DESEMP_POR_OUTRO_USUARIO"}))
				avaliadors = colaboradorManager.findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), false, null, null, false, null);

			colaboradorQuestionarios = colaboradorQuestionarioManager.findAvaliadosByAvaliador(avaliacaoDesempenho.getId(), avaliador.getId(), respondida, false, true, false);
			existeColaboradorQuestionarioRespondidoParcialmente();
		}
		
		if(exibeResultadoAutoAvaliacao)
		{
			colaboradorQuestionario = colaboradorQuestionarioManager.findByIdProjection(colaboradorQuestionarioId);
			this.msgResultadoAvaliacao = "";
			if(colaboradorQuestionario.getAvaliacao()!= null && colaboradorQuestionario.getAvaliacao().getId() != null)
				this.msgResultadoAvaliacao = colaboradorQuestionario.getAvaliacao().getCabecalho().replace("\r\n","<br>");
					
			this.msgResultadoAvaliacao = this.msgResultadoAvaliacao + "<br/><span>Pontuação da Avaliação: " + colaboradorQuestionario.getPerformanceFormatada() + "</span>" +
					"<br/><span>Pontuação da Competência: " + colaboradorQuestionario.getPerformanceNivelCompetenciaFormatada() + "</span>" +
					"<br/><h4>Pontuação Final: " + colaboradorQuestionario.getPerformanceFinal() + "</h4>";
		}
	}

	private void existeColaboradorQuestionarioRespondidoParcialmente(){
		if(!SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_MOV_AVALIACAO_GRAVAR_PARCIALMENTE"})){
			if(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_RESPONDER_AVALIACAO_DESEMP_POR_OUTRO_USUARIO"}))
				exibeMenuRespondidoParcialmente = colaboradorQuestionarioManager.existeColaboradorQuestionarioRespondidoParcialmente(avaliacaoDesempenho.getId(), null);
			else
				exibeMenuRespondidoParcialmente = colaboradorQuestionarioManager.existeColaboradorQuestionarioRespondidoParcialmente(avaliacaoDesempenho.getId(), avaliador.getId());
		}		
		else{
			exibeMenuRespondidoParcialmente = true;
		}
	}
	
	public String avaliacaoDesempenhoQuestionarioList()
	{
		setVideoAjuda(1264L);
		prepareList();
		
		return Action.SUCCESS;
	}
	
	public String avaliacaoDesempenhoRespostasList()
	{
		setRespondida('R');
		prepareList();
		
		if(avaliacaoDesempenho != null && avaliacaoDesempenho.getId() != null ) {
			avaliacaoDesempenho = avaliacaoDesempenhoManager.findByIdProjection(avaliacaoDesempenho.getId());
		}
		
		return Action.SUCCESS;
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

	public String[] getAvaliados() {
		return avaliados;
	}

	public void setAvaliados(String[] avaliados) {
		this.avaliados = avaliados;
	}

	public String[] getAvaliadores() {
		return avaliadores;
	}

	public void setAvaliadores(String[] avaliadores) {
		this.avaliadores = avaliadores;
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

	public boolean isTemAvaliacoesRespondidas() {
		return temAvaliacoesRespondidas;
	}

	public void setTemAvaliacoesRespondidas(boolean temAvaliacoesRespondidas) {
		this.temAvaliacoesRespondidas = temAvaliacoesRespondidas;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public Collection<ColaboradorQuestionario> getColaboradorQuestionarios() {
		return colaboradorQuestionarios;
	}
	
	public void setColaboradorQuestionarios(
			Collection<ColaboradorQuestionario> colaboradorQuestionarios) {
		this.colaboradorQuestionarios = colaboradorQuestionarios;
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

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public void setParticipanteAvaliacaoDesempenhoManager(
			ParticipanteAvaliacaoDesempenhoManager participanteAvaliacaoDesempenhoManager) {
		this.participanteAvaliacaoDesempenhoManager = participanteAvaliacaoDesempenhoManager;
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

	public boolean isExibeResultadoAutoAvaliacao() {
		return exibeResultadoAutoAvaliacao;
	}

	public void setExibeResultadoAutoAvaliacao(boolean exibeResultadoAutoAvaliacao) {
		this.exibeResultadoAutoAvaliacao = exibeResultadoAutoAvaliacao;
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

	public void setExibirObsAvaliadores(boolean exibirObsAvaliadores) {
		this.exibirObsAvaliadores = exibirObsAvaliadores;
	}

	public Date getPeriodoInicial() {
		return periodoInicial;
	}

	public void setPeriodoInicial(Date periodoInicial) {
		this.periodoInicial = periodoInicial;
	}

	public Date getPeriodoFinal() {
		return periodoFinal;
	}

	public void setPeriodoFinal(Date periodoFinal) {
		this.periodoFinal = periodoFinal;
	}

	public void setClonarParticipantes(boolean clonarParticipantes) {
		this.clonarParticipantes = clonarParticipantes;
	}

	public Collection<FaixaSalarial> getFaixaSalariais() {
		return faixaSalariais;
	}

	public Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> getConfiguracaoCompetenciaAvaliacaoDesempenhos() {
		return configuracaoCompetenciaAvaliacaoDesempenhos;
	}

	public void setConfiguracaoCompetenciaAvaliacaoDesempenhos(
			Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> configuracaoCompetenciaAvaliacaoDesempenhos) {
		this.configuracaoCompetenciaAvaliacaoDesempenhos = configuracaoCompetenciaAvaliacaoDesempenhos;
	}

	public void setConfiguracaoCompetenciaAvaliacaoDesempenhoManager(
			ConfiguracaoCompetenciaAvaliacaoDesempenhoManager configuracaoCompetenciaAvaliacaoDesempenhoManager) {
		this.configuracaoCompetenciaAvaliacaoDesempenhoManager = configuracaoCompetenciaAvaliacaoDesempenhoManager;
	}

	public Collection<ParticipanteAvaliacaoDesempenho> getParticipantesAvaliados() {
		return participantesAvaliados;
	}

	public void setParticipantesAvaliados(
			Collection<ParticipanteAvaliacaoDesempenho> participantesAvaliados) {
		this.participantesAvaliados = participantesAvaliados;
	}

	public Collection<ParticipanteAvaliacaoDesempenho> getParticipantesAvaliadores() {
		return participantesAvaliadores;
	}

	public void setParticipantesAvaliadores(
			Collection<ParticipanteAvaliacaoDesempenho> participantesAvaliadores) {
		this.participantesAvaliadores = participantesAvaliadores;
	}

	public void setAvaliado(Colaborador avaliado) {
		this.avaliado = avaliado;
	}

	public Colaborador getAvaliado() {
		return avaliado;
	}

	public boolean isExibeMenuRespondidoParcialmente() {
		return exibeMenuRespondidoParcialmente;
	}

	public String[] getAvaliadosCheck() {
		return avaliadosCheck;
	}

	public void setAvaliadosCheck(String[] avaliadosCheck) {
		this.avaliadosCheck = avaliadosCheck;
	}

	public Collection<CheckBox> getCargosCheckList() {
		return cargosCheckList;
	}

	public Collection<CheckBox> getCargosAvaliadoCheckList() {
		return cargosAvaliadoCheckList;
	}

	public Integer getNotaMinimaMediaGeralCompetencia() {
		return notaMinimaMediaGeralCompetencia;
	}

	public void setNotaMinimaMediaGeralCompetencia(Integer notaMinimaMediaGeralCompetencia) {
		this.notaMinimaMediaGeralCompetencia = notaMinimaMediaGeralCompetencia;
	}

	public Collection<CheckBox> getAvaliadoresCheckList() {
		return avaliadoresCheckList;
	}

	public boolean isRelatorioDetalhado() {
		return relatorioDetalhado;
	}

	public void setRelatorioDetalhado(boolean relatorioDetalhado) {
		this.relatorioDetalhado = relatorioDetalhado;
	}

	public void setConfiguracaoNivelCompetenciaManager(ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) {
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}

	public boolean isAgruparPorCargo() {
		return agruparPorCargo;
	}

	public void setAgruparPorCargo(boolean agruparPorCargo) {
		this.agruparPorCargo = agruparPorCargo;
	}

	public Collection<ResultadoCompetenciaColaborador> getResultadosCompetenciaColaborador() {
		return resultadosCompetenciaColaborador;
	}

	public Long[] getColaboradorQuestionariosRemovidos() {
		return colaboradorQuestionariosRemovidos;
	}

	public void setColaboradorQuestionariosRemovidos(Long[] colaboradorQuestionariosRemovidos) {
		this.colaboradorQuestionariosRemovidos = colaboradorQuestionariosRemovidos;
	}

	public Long[] getParticipantesAvaliadosRemovidos() {
		return participantesAvaliadosRemovidos;
	}

	public void setParticipantesAvaliadosRemovidos(Long[] participantesAvaliadosRemovidos) {
		this.participantesAvaliadosRemovidos = participantesAvaliadosRemovidos;
	}

	public Long[] getParticipantesAvaliadoresRemovidos() {
		return participantesAvaliadoresRemovidos;
	}

	public void setParticipantesAvaliadoresRemovidos(Long[] participantesAvaliadoresRemovidos) {
		this.participantesAvaliadoresRemovidos = participantesAvaliadoresRemovidos;
	}

	public Long[] getEmpresasPermitidasIds() {
		return empresasPermitidasIds;
	}

	public void setEmpresasPermitidasIds(Long[] empresasPermitidasIds) {
		this.empresasPermitidasIds = empresasPermitidasIds;
	}
	
	public void setEstabelecimentosCheck(Long[] estabelecimentosCheck) {
		this.estabelecimentosCheck = estabelecimentosCheck;
	}

	public void setEstabelecimentosCheckAux(Long[] estabelecimentosCheckAux) {
		this.estabelecimentosCheckAux = estabelecimentosCheckAux;
	}

	public void setCompetenciasCheck(Long[] competenciasCheck) {
		this.competenciasCheck = competenciasCheck;
	}
	
	public void setCompetenciasCheckAux(Long[] competenciasCheckAux) {
		this.competenciasCheckAux = competenciasCheckAux;
	}

	public void setCargosCheck(Long[] cargosCheck) {
		this.cargosCheck = cargosCheck;
	}

	public Long[] getCargosCheckAux() {
		return cargosCheckAux;
	}

	public void setCargosCheckAux(Long[] cargosCheckAux) {
		this.cargosCheckAux = cargosCheckAux;
	}

	public void setAreasCheck(Long[] areasCheck) {
		this.areasCheck = areasCheck;
	}

	public void setAreasCheckAux(Long[] areasCheckAux) {
		this.areasCheckAux = areasCheckAux;
	}
	
	public void setAgrupamentoDasCompetencias(String agrupamentoDasCompetencias) {
		this.agrupamentoDasCompetencias = agrupamentoDasCompetencias;
	}

	public String getReportTitle() {
		return reportTitle;
	}
	
	public String getAnaliseDesempenhoOrganizacaoPorEmpresa() {
		return AnaliseDesempenhoOrganizacao.POR_EMPRESA;
	}
	
	public String getAnaliseDesempenhoOrganizacaoPorArea() {
		return AnaliseDesempenhoOrganizacao.POR_AREA;
	}
	
	public String getAnaliseDesempenhoOrganizacaoPorCargo() {
		return AnaliseDesempenhoOrganizacao.POR_CARGO;
	}
	
	public LinkedHashMap<String, String> getlistaAgrupamentoDasCompetencias() {
		return new AnaliseDesempenhoOrganizacao().getListaAgrupamentoDasCompetencias();
	}

	public CompetenciasConsideradas getCompetenciasConsideradas() {
		return competenciasConsideradas;
	}

	public Collection<AgrupadorAnaliseDesempenhoOrganizacao> getAgrupadorAnaliseDesempenhoOrganizacaos() {
		return agrupadorAnaliseDesempenhoOrganizacaos;
	}

	public void setAnaliseDesempenhoOrganizacaos(
			Collection<AnaliseDesempenhoOrganizacao> analiseDesempenhoOrganizacaos) {
		this.analiseDesempenhoOrganizacaos = analiseDesempenhoOrganizacaos;
	}

	public void setLimiteQtdCompetenciaPorPagina(Long limiteQtdCompetenciaPorPagina) {
		this.limiteQtdCompetenciaPorPagina = limiteQtdCompetenciaPorPagina;
	}

	public Long getLimiteQtdCompetenciaPorPagina() {
		return limiteQtdCompetenciaPorPagina;
	}

	public String[] getAvaliacoesCheck() {
		return avaliacoesCheck;
	}

	public Long[] getCargosCheck() {
		return cargosCheck;
	}

	public Long[] getAreasCheck() {
		return areasCheck;
	}

	public Long[] getCompetenciasCheck() {
		return competenciasCheck;
	}
	
	public String getPrimeiraAssinatura() {
		return primeiraAssinatura;
	}

	public void setPrimeiraAssinatura(String primeiraAssinatura) {
		this.primeiraAssinatura = primeiraAssinatura;
	}

	public String getSegundaAssinatura() {
		return segundaAssinatura;
	}

	public void setSegundaAssinatura(String segundaAssinatura) {
		this.segundaAssinatura = segundaAssinatura;
	}
}
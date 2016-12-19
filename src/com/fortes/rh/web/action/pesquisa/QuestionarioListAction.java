package com.fortes.rh.web.action.pesquisa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.pesquisa.AspectoManager;
import com.fortes.rh.business.pesquisa.EntrevistaManager;
import com.fortes.rh.business.pesquisa.FichaMedicaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Entrevista;
import com.fortes.rh.model.pesquisa.FichaMedica;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioRelatorio;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioResultadoPerguntaObjetiva;
import com.fortes.rh.model.pesquisa.relatorio.ResultadoQuestionario;
import com.fortes.rh.model.relatorio.PerguntaFichaMedica;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class QuestionarioListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private QuestionarioManager questionarioManager;
	private PerguntaManager perguntaManager;
	private AspectoManager aspectoManager;
    private AreaOrganizacionalManager areaOrganizacionalManager;
    private EntrevistaManager entrevistaManager;
    private FichaMedicaManager fichaMedicaManager;
    private EmpresaManager empresaManager;
    private EstabelecimentoManager estabelecimentoManager;
    private TurmaManager turmaManager;
    private CursoManager cursoManager;
    private ParametrosDoSistemaManager parametrosDoSistemaManager;
    private CargoManager cargoManager;

	private Questionario questionario;
	private ColaboradorQuestionario colaboradorQuestionario;
	private TipoPergunta tipoPergunta = new TipoPergunta();
	private Map<String, Object> parametros;

	private Collection<Entrevista> entrevistas = new ArrayList<Entrevista>();
	private Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
	private Collection<Aspecto> aspectos = new ArrayList<Aspecto>();
	private Collection<QuestionarioRelatorio> dataSource;
    private Collection<Pergunta> perguntasSemAspecto = new ArrayList<Pergunta>();
    private Collection<Resposta> respostas = new ArrayList<Resposta>();
    private Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();
    private Collection<QuestionarioResultadoPerguntaObjetiva> resultadoObjetivas = new ArrayList<QuestionarioResultadoPerguntaObjetiva>();
    private Collection<PerguntaFichaMedica> perguntaFichaMedicas = new ArrayList<PerguntaFichaMedica>();
    
    private Collection<FichaMedica> fichaMedicas;

	private boolean preview;
	private TipoQuestionario tipoQuestionario = new TipoQuestionario();

	private String[] areasCheck;
	private Collection<CheckBox> areaOrganizacionalsCheckList = new ArrayList<CheckBox>();
	private String[] cargosCheck;
	private Collection<CheckBox> cargosCheckList = new ArrayList<CheckBox>();
	private String[] aspectosCheck;
	private Collection<CheckBox> aspectosCheckList = new ArrayList<CheckBox>();
 	private String[] perguntasCheck;
	private Collection<CheckBox> perguntasCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentosCheck;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private String[] empresasCheck;
	private Collection<CheckBox> empresasCheckList = new ArrayList<CheckBox>();

	private boolean exibirCabecalho;
	private boolean exibirRespostas;
	private boolean exibirComentarios;
	private boolean agruparPorAspectos;
	private String urlVoltar;

	private Date periodoIni;
	private Date periodoFim;

	private String areasIds;//Não apague ta sendo usado no ftl
	private String estabelecimentosIds;//Não apague ta sendo usado no ftl
	private String filtroQuestionario = "";
	private Long turmaId;
	private Long cursoId;
	private String actionMsg;
	private boolean imprimirFormaEconomica;
	
	private String assinatura1;
	private String assinatura2;
	private String assinatura3;
	
	private Collection<Empresa> empresas;
	private Long empresaId;
	private Long[] empresaIds;//para o filtro "todas"

	private Collection<ResultadoQuestionario> resultadoQuestionarios;
	
	private boolean exibirNomesColaboradores; // entrevista

    public String prepareAplicar()
	{
    	questionario = questionarioManager.findByIdProjection(questionario.getId());
    	perguntas = perguntaManager.getPerguntasRespostaByQuestionario(questionario.getId());

    	urlVoltar = TipoQuestionario.getUrlVoltarList(questionario.getTipo(), cursoId);

    	return Action.SUCCESS;
	}

    public String prepareAplicarByAspecto()
	{
    	questionario = questionarioManager.findByIdProjection(questionario.getId());
    	aspectos = aspectoManager.findByQuestionario(questionario.getId());

    	//Pega todas as perguntas depois retira as com aspectos, coloca dentro de perguntas e depois agrupa as que possuem aspectos
    	Collection<Pergunta> perguntasAll = perguntaManager.getPerguntasRespostaByQuestionario(questionario.getId());
    	perguntas = perguntaManager.getPerguntasSemAspecto(perguntasAll);

    	int ordemInicial = perguntas == null ? 1 : perguntas.size() + 1;
    	aspectos = aspectoManager.agruparPerguntasByAspecto(aspectos, perguntasAll, ordemInicial);

    	urlVoltar = TipoQuestionario.getUrlVoltarList(questionario.getTipo(), cursoId);

    	return Action.SUCCESS;
	}

	public String aplicarByOrdem() throws Exception
	{
		try
		{
			questionarioManager.aplicarPorAspecto(questionario.getId() , false);
			questionario = questionarioManager.findByIdProjection(questionario.getId());
		}
		catch (Exception e)
		{
			setActionErr(e.getMessage());
		}

		if(questionario.verificaTipo(TipoQuestionario.ENTREVISTA))
			return "success_entrevista";
		else if (questionario.verificaTipo(TipoQuestionario.AVALIACAOTURMA))
			return "success_avaliacaoturma";
		else if (questionario.verificaTipo(TipoQuestionario.FICHAMEDICA))
			return "success_fichamedica";

		return Action.SUCCESS;//Pesquisa
	}

	public String aplicarByAspecto() throws Exception
	{
		try
		{
			aspectos = aspectoManager.findByQuestionario(questionario.getId());
			perguntas = perguntaManager.getPerguntasRespostaByQuestionario(questionario.getId());
	    	Collection<Pergunta> perguntasSemAspecto = perguntaManager.getPerguntasSemAspecto(perguntas);

	    	int ordemInicial = perguntasSemAspecto == null ? 1 : perguntasSemAspecto.size() + 1;

	    	aspectos = aspectoManager.agruparPerguntasByAspecto(aspectos, perguntas, ordemInicial);
			perguntas = aspectoManager.desagruparPerguntasByAspecto(aspectos);

			if(perguntasSemAspecto != null)
				perguntaManager.salvarPerguntasByOrdem(perguntasSemAspecto);

			perguntaManager.salvarPerguntasByOrdem(perguntas);

			questionarioManager.aplicarPorAspecto(questionario.getId(), true);
			questionario = questionarioManager.findByIdProjection(questionario.getId());
		}
		catch (Exception e)
		{
			setActionErr(e.getMessage());
		}

		if(questionario.verificaTipo(TipoQuestionario.ENTREVISTA))
			return "success_entrevista";
		else if (questionario.verificaTipo(TipoQuestionario.AVALIACAOTURMA))
			return "success_avaliacaoturma";
		else if (questionario.verificaTipo(TipoQuestionario.FICHAMEDICA))
			return "success_fichamedica";

		return Action.SUCCESS;//Pesquisa
	}

	 public String imprimir()
    {
    	questionario = questionarioManager.findByIdProjection(questionario.getId());
   	   	dataSource = questionarioManager.getQuestionarioRelatorio(questionario);

   	   	String titulo = ""; 
   	   	if(questionario.getTipo() == TipoQuestionario.AVALIACAOTURMA)
   	   		titulo = questionario.getTitulo();
   	   	else
   	   		titulo = TipoQuestionario.getDescricaoMaisc(questionario.getTipo());
   	   	
   	   	String filtro = TipoQuestionario.getFiltro(questionario, filtroQuestionario);

    	parametros = RelatorioUtil.getParametrosRelatorio(titulo, getEmpresaSistema(), filtro);
    	parametros.put("FORMA_ECONOMICA", imprimirFormaEconomica);

    	return Action.SUCCESS;
    }

	 public String imprimirFichaMedica()
	 {
		try
		{
			Long colaboradorQuestionarioId = null;
			if(colaboradorQuestionario != null)
				colaboradorQuestionarioId = colaboradorQuestionario.getId();

			parametros = new HashMap<String, Object>();
			perguntaFichaMedicas = questionarioManager.montaImpressaoFichaMedica(questionario.getId(), colaboradorQuestionarioId, parametros);
			
			parametros.put("ASSINATURA1", assinatura1);
	    	parametros.put("ASSINATURA2", assinatura2);
	    	parametros.put("ASSINATURA3", assinatura3);
			
			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			actionMsg = "Erro ao gerar resultado da ficha médica.";

			return Action.INPUT;
		}
	 }

	public String liberar() throws Exception
    {
        try
		{
        	questionarioManager.liberarQuestionario(questionario.getId(), getEmpresaSistema());
		}
		catch (Exception e)
		{
			setActionErr(e.getMessage());
		}

		return Action.SUCCESS;
    }
	
	public String enviarLembrete()
	{
		try {
			questionarioManager.enviaEmailNaoRespondida(getEmpresaSistema(), questionario.getId());
			addActionMessage("Email(s) enviado(s) com sucesso.");
		} catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Erro ao enviar email(s).");
		}	
		return Action.SUCCESS;
	}

	public String prepareResultadoEntrevista() 
	{
		questionario = new Questionario();
		questionario.setTipo(TipoQuestionario.ENTREVISTA);

		entrevistas = entrevistaManager.findAllSelect(getEmpresaSistema().getId(), null);

		areaOrganizacionalsCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		areaOrganizacionalsCheckList = CheckListBoxUtil.marcaCheckListBox(areaOrganizacionalsCheckList, areasCheck);
		
		Collection<Estabelecimento> estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		try {
			estabelecimentosCheckList = CheckListBoxUtil.populaCheckListBox(estabelecimentos, "getId", "getDescricaoComEmpresa", null);
			estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);
		} catch (Exception e) {
			e.printStackTrace();
		}

		urlVoltar = "menu";

		return Action.SUCCESS;
	}

    public String prepareResultado() throws Exception
    {
    	empresaId = getEmpresaSistema().getId();
    	
   		if(parametrosDoSistemaManager.findById(1L).getCompartilharCursos())
   			empresas = cursoManager.findAllEmpresasParticipantes(cursoId);
   		else
   			empresas = empresaManager.findDistinctEmpresasByQuestionario(questionario.getId());
    	
   		empresaIds = new CollectionUtil<Empresa>().convertCollectionToArrayIds(empresas);
		
    	questionario = questionarioManager.findByIdProjection(questionario.getId());
    	
    	if(questionario.verificaTipo(TipoQuestionario.ENTREVISTA)) {
    		entrevistas = entrevistaManager.findAllSelect(getEmpresaSistema().getId(), null);
    		empresaIds = new Long[]{empresaId};
    	}
    	
    	if(questionario.verificaTipo(TipoQuestionario.FICHAMEDICA))
    		fichaMedicas = fichaMedicaManager.findAllSelect(getEmpresaSistema().getId(), null);

		if(questionario.verificaTipo(TipoQuestionario.PESQUISA) || questionario.verificaTipo(TipoQuestionario.AVALIACAOTURMA)) {
			Collection<Empresa> empresas = empresaManager.findEmpresasPermitidas(true , null, getUsuarioLogado().getId(), "ROLE_MOV_QUESTIONARIO");
			empresasCheckList = CheckListBoxUtil.populaCheckListBox(empresas, "getId", "getNome", null);
			empresasCheckList = CheckListBoxUtil.marcaCheckListBox(empresasCheckList, empresasCheck);
			empresaIds = new CollectionUtil<Empresa>().convertCollectionToArrayIds(empresas);
		}
		
		areaOrganizacionalsCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(empresaIds);
		
		areaOrganizacionalsCheckList = CheckListBoxUtil.marcaCheckListBox(areaOrganizacionalsCheckList, areasCheck);
		aspectosCheckList = aspectoManager.populaCheckOrderNome(questionario.getId());
		aspectosCheckList = CheckListBoxUtil.marcaCheckListBox(aspectosCheckList, aspectosCheck);
		perguntasCheckList = perguntaManager.populaCheckOrderTexto(questionario.getId());
		perguntasCheckList = CheckListBoxUtil.marcaCheckListBox(perguntasCheckList, perguntasCheck);

		Collection<Estabelecimento> estabelecimentos = estabelecimentoManager.findAllSelect(empresaIds);
		estabelecimentosCheckList = CheckListBoxUtil.populaCheckListBox(estabelecimentos, "getId", "getDescricaoComEmpresa", null);
		
		urlVoltar = TipoQuestionario.getUrlVoltarList(questionario.getTipo(), cursoId);

    	return Action.SUCCESS;
    }

	public String prepareResultadoFichaMedica()
    {
    	questionario = new Questionario();
    	questionario.setTipo(TipoQuestionario.FICHAMEDICA);
    	
    	fichaMedicas = fichaMedicaManager.findAllSelect(getEmpresaSistema().getId(), null);
    	
    	areaOrganizacionalsCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
    	areaOrganizacionalsCheckList = CheckListBoxUtil.marcaCheckListBox(areaOrganizacionalsCheckList, areasCheck);
    	
    	urlVoltar = TipoQuestionario.getUrlVoltarList(questionario.getTipo(), null);
    	
    	return Action.SUCCESS;
    }

    public String imprimeResultado() throws Exception 
    {
    	try
    	{
	    	questionario = questionarioManager.findByIdProjection(questionario.getId());
	    	perguntas = perguntaManager.findByQuestionarioAspectoPergunta(questionario.getId(), LongUtil.arrayStringToArrayLong(aspectosCheck), LongUtil.arrayStringToArrayLong(perguntasCheck), agruparPorAspectos);
	
	    	if(perguntas.isEmpty())
	    	{
	    		addActionMessage("Não existe pergunta para questionario ou aspecto(s).");
	    		prepareResultadoQuestionario();
	    		return Action.INPUT;
	    	}
	
	   	   	String filtro = TipoQuestionario.getFiltro(questionario, filtroQuestionario);
	   	   	String titulo = TipoQuestionario.getDescricaoMaisc(questionario.getTipo());
	   	   	String complementoFiltro = "";
	   	   	
	   	  	if(turmaId != null)
	    	{
	    		Turma turma = turmaManager.findByIdProjection(turmaId);
	    		complementoFiltro += "Curso: " + turma.getCurso().getNome() + " /" + "Turma: " + turma.getDescricao();
	    	}
	   	   	
	   	   	// No caso da Entrevista, setamos como anônima de acordo com opção "exibir nomes"
	    	if (questionario.verificaTipo(TipoQuestionario.ENTREVISTA) || questionario.verificaTipo(TipoQuestionario.AVALIACAOTURMA))
	    		questionario.setAnonimo(!exibirNomesColaboradores);
	
	    	CollectionUtil<Pergunta> clu = new CollectionUtil<Pergunta>();
	    	Long[] perguntasIds = clu.convertCollectionToArrayIds(perguntas);
	    	Long[] areaIds = LongUtil.arrayStringToArrayLong(areasCheck);
	    	Long[] cargoIds = LongUtil.arrayStringToArrayLong(cargosCheck);
	    	Long[] estabelecimentoIds = LongUtil.arrayStringToArrayLong(estabelecimentosCheck);
	
	    	areasIds = StringUtil.converteArrayToString(areasCheck);
	    	estabelecimentosIds = StringUtil.converteArrayToString(estabelecimentosCheck);
	    	
	    	Long[] empresaIds = LongUtil.arrayStringToArrayLong(empresasCheck);
	    	
	    	if(empresaIds.length > 0 && estabelecimentoIds.length == 0 && areaIds.length == 0 && cargoIds.length == 0) {
	    		estabelecimentoIds = new CollectionUtil<Estabelecimento>().convertCollectionToArrayIds(estabelecimentoManager.findAllSelect(empresaIds));
	    		areaIds = new CollectionUtil<AreaOrganizacional>().convertCollectionToArrayIds(areaOrganizacionalManager.findByEmpresasIds(empresaIds, null));
	    	}

			resultadoQuestionarios = questionarioManager.montaResultado(perguntas, perguntasIds, estabelecimentoIds, areaIds, cargoIds, periodoIni, periodoFim, questionario.verificaTipo(TipoQuestionario.ENTREVISTA), turmaId, questionario);
    		
    		String estabelecimentos = estabelecimentoManager.nomeEstabelecimentos(estabelecimentoIds, getEmpresaSistema().getId());
    		String areas = areaOrganizacionalManager.nomeAreas(areaIds);
    		
    		if(StringUtils.isNotBlank(estabelecimentos))
    			complementoFiltro += "\nEstab.: " + estabelecimentos;
    		if(StringUtils.isNotBlank(areas))
    			complementoFiltro += "\nÁreas: " + areas;
    		
    		parametros = RelatorioUtil.getParametrosRelatorio(titulo, getEmpresaSistema(), filtro + complementoFiltro);
    		parametros.put("AGRUPAR_ASPECTO", agruparPorAspectos);
    		parametros.put("EXIBIR_RESPOSTAS_SUBJETIVAS", exibirRespostas);
    		parametros.put("EXIBIR_RESPOSTAS_NAO_SUBJETIVAS", exibirRespostas);
    		parametros.put("EXIBIR_COMENTARIOS", exibirComentarios);
    		parametros.put("EXIBIR_CABECALHO", exibirCabecalho);
    		parametros.put("QUESTIONARIO_ANONIMO", questionario.isAnonimo());
    		parametros.put("QUESTIONARIO_CABECALHO", questionario.getCabecalho());
    		parametros.put("TOTAL_COLAB_RESP", questionario.getTotalColab());
		}
		catch (Exception e)
		{
			addActionMessage(e.getMessage());
			e.printStackTrace();
			prepareResultadoQuestionario();
			return Action.INPUT;
		}

    	return Action.SUCCESS;
    }

    private String prepareResultadoQuestionario() throws Exception {
		if (questionario != null)
		{
			if (questionario.getTipo() == TipoQuestionario.ENTREVISTA)
				return prepareResultadoEntrevista();
			
			if (questionario.getTipo() == TipoQuestionario.FICHAMEDICA)
				return prepareResultadoFichaMedica();
		}
		
		return prepareResultado();
	}

	public boolean isImprimirFormaEconomica()
    {
    	return imprimirFormaEconomica;
    }
    
    public void setImprimirFormaEconomica(boolean imprimirFormaEconomica)
    {
    	this.imprimirFormaEconomica = imprimirFormaEconomica;
    }

	public void setPerguntaManager(PerguntaManager perguntaManager)
	{
		this.perguntaManager = perguntaManager;
	}

	public Questionario getQuestionario()
	{
		return questionario;
	}

	public void setQuestionario(Questionario questionario)
	{
		this.questionario = questionario;
	}

	public void setQuestionarioManager(QuestionarioManager questionarioManager)
	{
		this.questionarioManager = questionarioManager;
	}

	public Collection<QuestionarioRelatorio> getDataSource()
	{
		return dataSource;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public Collection<Pergunta> getPerguntas()
	{
		return perguntas;
	}

	public Collection<Aspecto> getAspectos()
	{
		return aspectos;
	}

	public void setAspectoManager(AspectoManager aspectoManager)
	{
		this.aspectoManager = aspectoManager;
	}

	public TipoPergunta getTipoPergunta()
	{
		return tipoPergunta;
	}

	public boolean isPreview()
	{
		return preview;
	}

	public void setPreview(boolean preview)
	{
		this.preview = preview;
	}

	public TipoQuestionario getTipoQuestionario()
	{
		return tipoQuestionario;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public boolean isAgruparPorAspectos()
	{
		return agruparPorAspectos;
	}

	public void setAgruparPorAspectos(boolean agruparPorAspectos)
	{
		this.agruparPorAspectos = agruparPorAspectos;
	}

	public String[] getAreasCheck()
	{
		return areasCheck;
	}

	public void setAreasCheck(String[] areasCheck)
	{
		this.areasCheck = areasCheck;
	}

	public String[] getAspectosCheck()
	{
		return aspectosCheck;
	}

	public void setAspectosCheck(String[] aspectosCheck)
	{
		this.aspectosCheck = aspectosCheck;
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
	
	public boolean isExibirComentarios()
	{
		return exibirComentarios;
	}

	public void setExibirComentarios(boolean exibirComentarios)
	{
		this.exibirComentarios = exibirComentarios;
	}

	public boolean isExibirRespostas()
	{
		return exibirRespostas;
	}

	public void setExibirRespostas(boolean exibirRespostas)
	{
		this.exibirRespostas = exibirRespostas;
	}

	public String[] getPerguntasCheck()
	{
		return perguntasCheck;
	}

	public void setPerguntasCheck(String[] perguntasCheck)
	{
		this.perguntasCheck = perguntasCheck;
	}

	public Collection<CheckBox> getAreasOrganizacionalsCheckList()
	{
		return areaOrganizacionalsCheckList;
	}

	public Collection<CheckBox> getAspectosCheckList()
	{
		return aspectosCheckList;
	}

	public Collection<ColaboradorResposta> getColaboradorRespostas()
	{
		return colaboradorRespostas;
	}

	public Collection<CheckBox> getPerguntasCheckList()
	{
		return perguntasCheckList;
	}

	public Collection<Pergunta> getPerguntasSemAspecto()
	{
		return perguntasSemAspecto;
	}

	public Collection<Resposta> getRespostas()
	{
		return respostas;
	}

	public Collection<QuestionarioResultadoPerguntaObjetiva> getResultadoObjetivas()
	{
		return resultadoObjetivas;
	}

	public String getUrlVoltar()
	{
		return urlVoltar;
	}

	public String getAreasIds()
	{
		return areasIds;
	}

	public void setAreasIds(String areasIds)
	{
		this.areasIds = areasIds;
	}

	public void setEntrevistaManager(EntrevistaManager entrevistaManager)
	{
		this.entrevistaManager = entrevistaManager;
	}

	public Collection<Entrevista> getEntrevistas()
	{
		return entrevistas;
	}

	public Date getPeriodoFim()
	{
		return periodoFim;
	}

	public void setPeriodoFim(Date periodoFim)
	{
		this.periodoFim = periodoFim;
	}

	public Date getPeriodoIni()
	{
		return periodoIni;
	}

	public void setPeriodoIni(Date periodoIni)
	{
		this.periodoIni = periodoIni;
	}

	public void setUrlVoltar(String urlVoltar)
	{
		this.urlVoltar = urlVoltar;
	}

	public String getFiltroQuestionario()
	{
		return filtroQuestionario;
	}

	public void setFiltroQuestionario(String filtroQuestionario)
	{
		this.filtroQuestionario = filtroQuestionario;
	}

	public Long getTurmaId()
	{
		return turmaId;
	}

	public void setTurmaId(Long turmaId)
	{
		this.turmaId = turmaId;
	}

	public Long getCursoId()
	{
		return cursoId;
	}

	public void setCursoId(Long cursoId)
	{
		this.cursoId = cursoId;
	}

	public Collection<ResultadoQuestionario> getResultadoQuestionarios()
	{
		return resultadoQuestionarios;
	}

	public Collection<PerguntaFichaMedica> getPerguntaFichaMedicas()
	{
		return perguntaFichaMedicas;
	}

	public void setColaboradorQuestionario(ColaboradorQuestionario colaboradorQuestionario)
	{
		this.colaboradorQuestionario = colaboradorQuestionario;
	}

	public String getActionMsg()
	{
		return actionMsg;
	}

	public void setActionMsg(String actionMsg)
	{
		this.actionMsg = actionMsg;
	}

	public void setFichaMedicaManager(FichaMedicaManager fichaMedicaManager) {
		this.fichaMedicaManager = fichaMedicaManager;
	}

	public Collection<FichaMedica> getFichaMedicas() {
		return fichaMedicas;
	}

	public Collection<Empresa> getEmpresas() {
		return empresas;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public Long[] getEmpresaIds() {
		return empresaIds;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public void setEstabelecimentosCheck(String[] estabelecimentosCheck)
	{
		this.estabelecimentosCheck = estabelecimentosCheck;
	}

	public Collection<CheckBox> getEstabelecimentosCheckList()
	{
		return estabelecimentosCheckList;
	}

	public String getEstabelecimentosIds()
	{
		return estabelecimentosIds;
	}

	public void setEstabelecimentosIds(String estabelecimentosIds)
	{
		this.estabelecimentosIds = estabelecimentosIds;
	}

	public boolean isExibirNomesColaboradores() {
		return exibirNomesColaboradores;
	}

	public void setExibirNomesColaboradores(boolean exibirNomesColaboradores) {
		this.exibirNomesColaboradores = exibirNomesColaboradores;
	}

	public boolean isExibirCabecalho()
	{
		return exibirCabecalho;
	}

	public void setExibirCabecalho(boolean exibirCabecalho)
	{
		this.exibirCabecalho = exibirCabecalho;
	}

	public Collection<CheckBox> getCargosCheckList() {
		return cargosCheckList;
	}

	public void setCargosCheckList(Collection<CheckBox> cargosCheckList) {
		this.cargosCheckList = cargosCheckList;
	}
	
	public void setCargosCheck(String[] cargosCheck) {
		this.cargosCheck = cargosCheck;
	}

	public Collection<CheckBox> getAreaOrganizacionalsCheckList() {
		return areaOrganizacionalsCheckList;
	}

	public void setTurmaManager(TurmaManager turmaManager) {
		this.turmaManager = turmaManager;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}
	
	public void setCargoManager(CargoManager cargoManager) {
		this.cargoManager = cargoManager;
	}

	public void setCursoManager(CursoManager cursoManager) {
		this.cursoManager = cursoManager;
	}

	public String getAssinatura1() {
		return assinatura1;
	}

	public void setAssinatura1(String assinatura1) {
		this.assinatura1 = assinatura1;
	}

	public String getAssinatura2() {
		return assinatura2;
	}

	public void setAssinatura2(String assinatura2) {
		this.assinatura2 = assinatura2;
	}

	public String getAssinatura3() {
		return assinatura3;
	}

	public void setAssinatura3(String assinatura3) {
		this.assinatura3 = assinatura3;
	}
}
package com.fortes.rh.web.action.pesquisa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.PesquisaManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.FichaMedica;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioRelatorio;
import com.fortes.rh.model.relatorio.PerguntaFichaMedica;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

public class ColaboradorRespostaEditAction extends MyActionSupportEdit implements ModelDriven
{
	private static final long serialVersionUID = 1L;
	
	private ColaboradorRespostaManager colaboradorRespostaManager = null;
    private PesquisaManager pesquisaManager;
    private QuestionarioManager questionarioManager;
    private ColaboradorManager colaboradorManager;
    private CandidatoManager candidatoManager;
    private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
    private boolean exibirImprimir;
    
    private Collection<QuestionarioRelatorio> dataSource;
    private Map<String, Object> parametros;
    private Pesquisa pesquisa;
    private Questionario questionario;
    private ColaboradorResposta colaboradorResposta;
    private ColaboradorQuestionario colaboradorQuestionario;
    private Colaborador colaborador;
    private Candidato candidato;
    private FichaMedica fichaMedica;
    private Collection<PerguntaFichaMedica> perguntasRespondidas = new ArrayList<PerguntaFichaMedica>();

    private TipoPergunta tipoPergunta = new TipoPergunta();
    private String respostas;

    private String voltarPara;
    private String retorno;
    private String tela = "";
    private TipoQuestionario tipoQuestionario = new TipoQuestionario();

    private boolean inserirFichaMedica;
    private Boolean validarFormulario;// default
    private char vinculo;

    private Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();
    private Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();

	private Long turmaId;
    
    private boolean respondePorOutroUsuario;

	private Date respondidaEm;
	private String empresaCodigo;
	private String matricula;
    

    public String prepareEditFichaMedica() throws Exception
    {
    	colaboradorQuestionario = colaboradorQuestionarioManager.findByIdProjection(colaboradorQuestionario.getId());
    	
    	respondidaEm = colaboradorQuestionario.getRespondidaEm();
    	
    	questionario = colaboradorQuestionario.getQuestionario();
    	questionario.setTipo(TipoQuestionario.FICHAMEDICA);
    	
    	voltarPara = "../../sesmt/fichaMedica/listPreenchida.action";
    	
    	if(colaboradorQuestionario.getCandidato().getId() != null)
    	{
    		candidato = colaboradorQuestionario.getCandidato();
    		vinculo = 'A';
    	}
    	else if(colaboradorQuestionario.getColaborador().getId() != null)
    	{
    		colaborador = colaboradorQuestionario.getColaborador();
    		vinculo = 'C';
    	}
    	
    	prepareResponderQuestionario();
    	return Action.SUCCESS;
    }
    
    public String prepareResponderFichaMedica() throws Exception
    {
    	respondidaEm = new Date();
    	
    	return prepareResponderQuestionarioPorOutroUsuario();
    }
    
    public String prepareResponderQuestionarioPorOutroUsuario() throws Exception
    {
    	respondePorOutroUsuario = true; //flag para o ftl (obrigatoriedade de campos subjetivos e etc.)
    	validarFormulario = true;
    	
    	return prepareResponderQuestionario();
    }
    
    public String prepareResponderQuestionarioEmLote() throws Exception
    {
    	questionario = questionarioManager.findResponderQuestionario(questionario);
    	colaboradors = colaboradorManager.findByQuestionarioNaoRespondido(questionario.getId());
    	
    	return Action.SUCCESS;
    }
    public String prepareResponderQuestionario() throws Exception
    {
        questionario = questionarioManager.findResponderQuestionario(questionario);
        if(questionario.verificaTipo(TipoQuestionario.FICHAMEDICA) && vinculo == 'A')// 'A' = Candidato e 'C' = Colaborador
        {
        	candidato = candidatoManager.findByCandidatoId(candidato.getId());
        	colaborador = new Colaborador(candidato.getNome(), "", candidato.getId(), null, null);//Envia dados do Candidato para o ftl encapsulado no Colaborador

        	if(!inserirFichaMedica){
        		if(colaboradorQuestionario != null && colaboradorQuestionario.getId() != null)
        			colaboradorRespostas = colaboradorRespostaManager.findByColaboradorQuestionario(colaboradorQuestionario.getId());
        		else
        			colaboradorRespostas = colaboradorRespostaManager.findByQuestionarioCandidato(questionario.getId(), candidato.getId(), null);
        	}
        }
        else {
        	if(questionario.verificaTipo(TipoQuestionario.PESQUISA) || questionario.verificaTipo(TipoQuestionario.AVALIACAOTURMA)){
	        	Colaborador colaboradorLogado = colaboradorManager.findByUsuario(getUsuarioLogado(), getEmpresaSistema().getId());
	        	if (respondePorOutroUsuario || (colaboradorLogado != null && colaborador.getId().equals(colaboradorLogado.getId())))
	        		colaborador = colaboradorManager.findColaboradorByIdProjection(colaborador.getId());
	        	else {
	        		addActionError("Permissão negada. Não foi possível acessar a avaliação do colaborador.");
	        		return Action.ERROR;
	        	}
        	}
        	else{
        		colaborador = colaboradorManager.findColaboradorByIdProjection(colaborador.getId());
        	}
        }

        if(questionario.getPerguntas().isEmpty())
        	addActionMessage("Não existe pergunta neste questionario.");
        
        if(questionario.verificaTipo(TipoQuestionario.ENTREVISTA) || questionario.verificaTipo(TipoQuestionario.AVALIACAOTURMA) || (questionario.verificaTipo(TipoQuestionario.FICHAMEDICA) && vinculo == 'C' && !inserirFichaMedica ))
        {
        	if(colaboradorQuestionario != null && colaboradorQuestionario.getId() != null)
        		colaboradorRespostas = colaboradorRespostaManager.findByColaboradorQuestionario(colaboradorQuestionario.getId());
    		else 
    		{
    			colaboradorRespostas = colaboradorRespostaManager.findByQuestionarioColaborador(questionario.getId(), colaborador.getId(), turmaId, null);
    			colaboradorQuestionario = colaboradorQuestionarioManager.findByQuestionario(questionario.getId(), colaborador.getId(), turmaId);
    		}
        }
        
        retorno = voltarPara;

        return Action.SUCCESS;
    }

    public String prepareResponderQuestionarioTrafego() throws Exception
    {
    	questionario = questionarioManager.findResponderQuestionario(questionario);
   		colaborador = colaboradorManager.findColaboradorByIdProjection(colaborador.getId());
    	
    	if(questionario.getPerguntas().isEmpty())
    		addActionMessage("Não existe pergunta neste questionario.");
    		
    	voltarPara = "../../pesquisa/trafego/list.action?empresaCodigo=" + empresaCodigo + "&matricula=" + matricula;
    	
    	return Action.SUCCESS;
    }
    
    public String salvaQuestionarioRespondidoTrafego() throws Exception
    {
    	try {			
    		colaboradorRespostaManager.salvaQuestionarioRespondido(respostas, questionario, colaborador.getId(), turmaId, vinculo, respondidaEm, null, false);
    		setActionMsg("Respostas gravadas com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			setActionMsg("Erro ao gravar Respostas.");
		}
		
		return Action.SUCCESS;
    }

    public String salvaQuestionarioRespondido() throws Exception
    {
    	String actionMsgTemp = null;
    	Long colaboradorQuestionarioId = null;
    	if(colaboradorQuestionario != null && colaboradorQuestionario.getId() != null)
    		colaboradorQuestionarioId = colaboradorQuestionario.getId();
    	
    	if(questionario != null && questionario.getTipo() == TipoQuestionario.PESQUISA && colaboradorQuestionarioManager.isRespondeuPesquisaByColaboradorIdAndQuestionarioId(colaborador.getId(), questionario.getId())){
    		actionMsgTemp = "Não foi possível gravar as respostas, pois a pesquisa já possui resposta.";
    	}else{
    		colaboradorRespostaManager.salvaQuestionarioRespondido(respostas, questionario, colaborador.getId(), turmaId, vinculo, respondidaEm, colaboradorQuestionarioId, inserirFichaMedica);
    	}

        if (tela.equals("index")) {
        	if(actionMsgTemp == null)
        		setActionMsg("Respostas gravadas com sucesso.");
        	retorno = "../../index.action?actionMsg=" + getActionMsg();
            return Action.SUCCESS;
        } else if(voltarPara.equals("../../sesmt/fichaMedica/prepareInsertFicha.action") || voltarPara.equals("../../sesmt/fichaMedica/listPreenchida.action"))  {
        	setActionMsg("Respostas gravadas com sucesso.");
        	retorno = voltarPara + "?actionMsg=" + getActionMsg();
        	return Action.SUCCESS;
        } else {
        	retorno = voltarPara;
        	if(actionMsgTemp != null){
        		setActionMsg(actionMsgTemp);
        		retorno += "&actionMsg=" + getActionMsg();
        	}
            return "colaboradorQuestionario";
        }
    }
    
    public String imprimirEntrevistaDesligamento() 
    {
    	colaboradorQuestionario = colaboradorQuestionarioManager.findColaboradorComEntrevistaDeDesligamento(colaborador.getId());
		try
		{
			prepareResponderQuestionario();
			perguntasRespondidas = questionarioManager.montaPerguntasComRespostas(questionario.getPerguntas(), colaboradorRespostas, false, false);
			
			parametros = new HashMap<String, Object>();//tem que ser aqui
			parametros.put("SUBREPORT_DIR", ServletActionContext.getServletContext().getRealPath("/WEB-INF/report/") + java.io.File.separator);
			parametros.put("TITULO", "Entrevista de Desligamento");
	    	parametros.put("RODAPE", colaboradorQuestionario.getQuestionario().getCabecalho());
	    	parametros.put("COLABORADOR", colaboradorQuestionario.getColaborador().getNome());
			
			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return Action.INPUT;
		}
    }
    
    public String imprimirAvaliacaoTurma()
    {
    	colaboradorQuestionario = colaboradorQuestionarioManager.findColaborador(colaborador.getId(), questionario.getId(), turmaId);
    	try
    	{
    		prepareResponderQuestionario();
    		perguntasRespondidas = questionarioManager.montaPerguntasComRespostas(questionario.getPerguntas(), colaboradorRespostas, true, true);
    		
    		StringBuilder filtro = new StringBuilder();
    		filtro.append("Curso: ");
    		filtro.append(colaboradorQuestionario.getTurma().getCurso().getNome());
    		filtro.append("\n");
    		filtro.append("Turma: ");
    		filtro.append(colaboradorQuestionario.getTurma().getDescricao());
    		filtro.append("\n");
    		filtro.append("Colaborador: ");
    		filtro.append(colaboradorQuestionario.getColaborador().getNome());
    		filtro.append("\n");
    		filtro.append("Data Respondida: ");
    		filtro.append(DateUtil.formataDiaMesAno(colaboradorQuestionario.getRespondidaEm()));
    		filtro.append("\n");
    		
    		parametros = RelatorioUtil.getParametrosRelatorio("Avaliação da turma " + questionario.getTitulo(), getEmpresaSistema(), filtro.toString());
    		
    		parametros.put("OBSCABECALHO", questionario.getCabecalho());
    		
    		return Action.SUCCESS;
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		return Action.INPUT;
    	}
    }
    
    public String excluirRespostas() throws Exception{
    	try {
    		colaboradorRespostaManager.removeFicha(colaboradorQuestionario.getId());
    		
    		if(questionario.verificaTipo(TipoQuestionario.ENTREVISTA))
				colaboradorManager.updateRespondeuEntrevistaDesligamento(colaborador.getId(), false);
    		
    		return Action.SUCCESS;
    	} catch (Exception e) {
    		e.printStackTrace();
    		addActionError("Não foi possível excluir as respostas.");
    		prepareResponderQuestionario();
    		return Action.INPUT;
    	}
    }
    
    public Object getModel()
    {
        return colaboradorResposta;
    }


    public ColaboradorResposta getColaboradorResposta()
    {
        return colaboradorResposta;
    }


    public void setColaboradorResposta(ColaboradorResposta colaboradorResposta)
    {
        this.colaboradorResposta = colaboradorResposta;
    }


    public ColaboradorRespostaManager getColaboradorRespostaManager()
    {
        return colaboradorRespostaManager;
    }


    public void setColaboradorRespostaManager(ColaboradorRespostaManager colaboradorRespostaManager)
    {
        this.colaboradorRespostaManager = colaboradorRespostaManager;
    }


    public Pesquisa getPesquisa()
    {
        return pesquisa;
    }


    public void setPesquisa(Pesquisa pesquisa)
    {
        this.pesquisa = pesquisa;
    }


    public PesquisaManager getPesquisaManager()
    {
        return pesquisaManager;
    }


    public void setPesquisaManager(PesquisaManager pesquisaManager)
    {
        this.pesquisaManager = pesquisaManager;
    }

    public String getRespostas()
    {
        return respostas;
    }

    public void setRespostas(String respostas)
    {
        this.respostas = respostas;
    }

    public String getVoltarPara()
    {
        return voltarPara;
    }

    public void setVoltarPara(String voltarPara)
    {
        this.voltarPara = voltarPara;
    }

    public ColaboradorQuestionario getColaboradorQuestionario()
    {
        return colaboradorQuestionario;
    }

    public void setColaboradorQuestionario(ColaboradorQuestionario colaboradorQuestionario)
    {
        this.colaboradorQuestionario = colaboradorQuestionario;
    }

    public Colaborador getColaborador()
    {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador)
    {
        this.colaborador = colaborador;
    }

    public String getRetorno()
    {
        return retorno;
    }

    public void setRetorno(String retorno)
    {
        this.retorno = retorno;
    }

    public TipoPergunta getTipoPergunta()
    {
        return tipoPergunta;
    }

    public String getTela()
    {
        return tela;
    }

    public void setTela(String tela)
    {
        this.tela = tela;
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

    public TipoQuestionario getTipoQuestionario()
    {
        return tipoQuestionario;
    }

    public void setColaboradorManager(ColaboradorManager colaboradorManager)
    {
        this.colaboradorManager = colaboradorManager;
    }

	public Collection<ColaboradorResposta> getColaboradorRespostas()
	{
		return colaboradorRespostas;
	}

	public Boolean getValidarFormulario()
	{
		return validarFormulario;
	}

	public void setValidarFormulario(Boolean validarFormulario)
	{
		this.validarFormulario = validarFormulario;
	}

	public Long getTurmaId()
	{
		return turmaId;
	}

	public void setTurmaId(Long turmaId)
	{
		this.turmaId = turmaId;
	}

	public Candidato getCandidato()
	{
		return candidato;
	}

	public void setCandidato(Candidato candidato)
	{
		this.candidato = candidato;
	}

	public void setCandidatoManager(CandidatoManager candidatoManager)
	{
		this.candidatoManager = candidatoManager;
	}

	public char getVinculo()
	{
		return vinculo;
	}

	public void setVinculo(char vinculo)
	{
		this.vinculo = vinculo;
	}

	public FichaMedica getFichaMedica()
	{
		return fichaMedica;
	}

	public void setFichaMedica(FichaMedica fichaMedica)
	{
		this.fichaMedica = fichaMedica;
	}

	public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager)
	{
		this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
	}

	public boolean isRespondePorOutroUsuario() {
		return respondePorOutroUsuario;
	}

	public Date getRespondidaEm() {
		return respondidaEm;
	}

	public void setRespondidaEm(Date respondidaEm) {
		this.respondidaEm = respondidaEm;
	}

	public String getEmpresaCodigo() {
		return empresaCodigo;
	}

	public void setEmpresaCodigo(String empresaCodigo) {
		this.empresaCodigo = empresaCodigo;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public Collection<QuestionarioRelatorio> getDataSource() {
		return dataSource;
	}

	public Collection<PerguntaFichaMedica> getPerguntasRespondidas() {
		return perguntasRespondidas;
	}

	public boolean isExibirImprimir() {
		return exibirImprimir;
	}

	public void setExibirImprimir(boolean exibirImprimir) {
		this.exibirImprimir = exibirImprimir;
	}

    public Collection<Colaborador> getColaboradors() {
		return colaboradors;
	}

	public boolean isInserirFichaMedica() {
		return inserirFichaMedica;
	}

	public void setInserirFichaMedica(boolean inserirFichaMedica) {
		this.inserirFichaMedica = inserirFichaMedica;
	}

}
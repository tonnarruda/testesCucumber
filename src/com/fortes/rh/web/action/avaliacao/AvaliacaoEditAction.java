package com.fortes.rh.web.action.avaliacao;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.avaliacao.PeriodoExperienciaManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioRelatorio;
import com.fortes.rh.util.BooleanUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class AvaliacaoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private AvaliacaoManager avaliacaoManager;
	private PerguntaManager perguntaManager;
	private PeriodoExperienciaManager periodoExperienciaManager;
	private EmpresaManager empresaManager;
	private ColaboradorManager colaboradorManager;
	private ColaboradorRespostaManager colaboradorRespostaManager;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	
	private Avaliacao avaliacao;
	
	private Collection<Avaliacao> avaliacaos;
	private Collection<Pergunta> perguntas;
	
    private Collection<CheckBox> empresasCheckList = new ArrayList<CheckBox>();
	private String[] empresasCheck;
	
	private String urlVoltar;
	private TipoPergunta tipoPergunta = new TipoPergunta();
	private boolean preview = true;
	
	private Map<String, Object> parametros;
	private Collection<QuestionarioRelatorio> dataSource;
	private Collection<ColaboradorResposta> colaboradorRespostas;
	private Collection<PeriodoExperiencia> periodoExperiencias;

	private boolean imprimirFormaEconomica = false;
	private char modeloAvaliacao = 'D'; 
	private TipoModeloAvaliacao tipoModeloAvaliacao = new TipoModeloAvaliacao();
	
	private Colaborador colaborador;
	private ColaboradorQuestionario colaboradorQuestionario; 
	private Curso curso;
	private Turma turma;
	private AvaliacaoCurso avaliacaoCurso;

	private boolean telaInicial;
	private String titulo;
	private char ativos = 'T';
	
	private void prepare() throws Exception
	{
		if(avaliacao != null && avaliacao.getId() != null)
			avaliacao = (Avaliacao) avaliacaoManager.findById(avaliacao.getId());
		
		periodoExperiencias = periodoExperienciaManager.findAllSelect(getEmpresaSistema().getId(), false);
	}
	
	public String clonar() throws Exception
	{
		avaliacaoManager.clonar(avaliacao.getId(), LongUtil.arrayStringToArrayLong(empresasCheck));
		return Action.SUCCESS;
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		avaliacao = new Avaliacao();
		avaliacao.setAtivo(true);
		
		avaliacao.setTipoModeloAvaliacao(modeloAvaliacao);
		
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}


	public String insert() throws Exception
	{
		avaliacao.setEmpresa(getEmpresaSistema());
		avaliacaoManager.save(avaliacao);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		avaliacaoManager.update(avaliacao);
		
		if(telaInicial)
			return "TELA_INICIAL";
		else
			return Action.SUCCESS;
	}

	public String visualizar() throws Exception
	{
		avaliacao = avaliacaoManager.findById(avaliacao.getId());
		perguntas = perguntaManager.getPerguntasRespostaByQuestionarioAgrupadosPorAspecto(avaliacao.getId(), false);
    	urlVoltar = "list.action?modeloAvaliacao=" + modeloAvaliacao;
    	
		return Action.SUCCESS;
	}
	
	public String prepareResponderAvaliacaoAluno() throws Exception
	{
		avaliacao = avaliacaoManager.findById(avaliacao.getId());
		perguntas = perguntaManager.getPerguntasRespostaByQuestionarioAgrupadosPorAspecto(avaliacao.getId(), false);
		
		colaborador = colaboradorManager.findByIdDadosBasicos(colaborador.getId(), StatusRetornoAC.CONFIRMADO);
		
		colaboradorQuestionario = colaboradorQuestionarioManager.findByColaboradorAvaliacao(colaborador, avaliacao);
		
		if (colaboradorQuestionario != null && colaboradorQuestionario.getRespondida())
			colaboradorRespostas = colaboradorRespostaManager.findByColaboradorQuestionario(colaboradorQuestionario.getId());
		else
			colaboradorRespostas = colaboradorQuestionarioManager.populaQuestionario(avaliacao);

		for (Pergunta pergunta : perguntas)
		{
			for (ColaboradorResposta colaboradorResposta : colaboradorRespostas)
			{
				if (colaboradorResposta.getPergunta().equals(pergunta))
				{
					pergunta.addColaboradorResposta(colaboradorResposta);
					if (! colaboradorResposta.getPergunta().getTipo().equals(TipoPergunta.MULTIPLA_ESCOLHA))
						break;
				}
			}
		}
		
		urlVoltar = "../turma/prepareAproveitamento.action?turma.id=" + turma.getId() + "&curso.id=" + curso.getId() + "&avaliacaoCurso.id=" + avaliacaoCurso.getId();
		
		return Action.SUCCESS;
	}
	
	public String responderAvaliacaoAluno() throws Exception
	{
		colaboradorQuestionario.setRespondida(true);
		colaboradorQuestionario.setTurma(turma);
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setAvaliacao(avaliacao);
		if (colaboradorQuestionario.getRespondidaEm() == null)
			colaboradorQuestionario.setRespondidaEm(new Date());
		
		try
		{
			colaboradorQuestionarioManager.saveOrUpdate(colaboradorQuestionario);
			colaboradorRespostaManager.update(getColaboradorRespostasDasPerguntas(), colaboradorQuestionario, getUsuarioLogado().getId());
			
			addActionSuccess("Respostas gravadas com sucesso");
			
			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			prepareResponderAvaliacaoAluno();
			addActionError("Ocorreu um erro ao gravar as respostas da avaliação");
			return Action.INPUT;
		}
	}
	
	private Collection<ColaboradorResposta> getColaboradorRespostasDasPerguntas() 
	{
		Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();
		for (Pergunta pergunta : perguntas)
		{
			// desagrupando os colaboradorRespostas que vieram agrupados por pergunta
			if (pergunta.getColaboradorRespostas() != null)
			{
				colaboradorRespostas.addAll(pergunta.getColaboradorRespostas());
			
				setComentariosDasRespostasMultiplaEscolha(pergunta.getColaboradorRespostas());
			}
		}
		return colaboradorRespostas;
	}
	
	// O comentário de resposta multipla só vem na primeira e precisa ser replicado para todas (problema no modelo)
	private void setComentariosDasRespostasMultiplaEscolha(Collection<ColaboradorResposta> colabRespostas) {
		
		String comentario = null;
		
		if (!colabRespostas.isEmpty()) {
			
			ColaboradorResposta primeiroColabResposta = ((ColaboradorResposta)colabRespostas.toArray()[0]);
			
			if (primeiroColabResposta.getPergunta().getTipo() == TipoPergunta.MULTIPLA_ESCOLHA 
					&& primeiroColabResposta.getPergunta().isComentario())
			{
				comentario = primeiroColabResposta.getComentario();
			
				for (ColaboradorResposta colabResposta : colabRespostas)
						colabResposta.setComentario(comentario);
			}
		}
	}

	public String list() throws Exception
	{
		setVideoAjuda(778L);
		
		setTotalSize(avaliacaoManager.getCount(getEmpresaSistema().getId(), BooleanUtil.getValueCombo(ativos), modeloAvaliacao, titulo));
		avaliacaos = avaliacaoManager.findAllSelect(getPage(), getPagingSize(), getEmpresaSistema().getId(), BooleanUtil.getValueCombo(ativos), modeloAvaliacao, titulo);
		
		Collection<Empresa> empresas = empresaManager.findEmpresasPermitidas(true , null, getUsuarioLogado().getId(), "ROLE_MOV_QUESTIONARIO");
   		empresasCheckList =  CheckListBoxUtil.populaCheckListBox(empresas, "getId", "getNome");
		
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		avaliacaoManager.remove(avaliacao.getId());
		addActionSuccess("Modelo de avaliação excluído com sucesso.");

		return Action.SUCCESS;
	}
	
	public String imprimir()
    {
    	avaliacao = avaliacaoManager.findById(avaliacao.getId());
   	   	dataSource = avaliacaoManager.getQuestionarioRelatorio(avaliacao);

   	   	String titulo = "Avaliação";
   	   	String filtro = avaliacao.getTitulo();
   	   	
    	parametros = RelatorioUtil.getParametrosRelatorio(titulo, getEmpresaSistema(), filtro);
    	parametros.put("FORMA_ECONOMICA", imprimirFormaEconomica );
    	
    	return Action.SUCCESS;
    }
	
	public Avaliacao getAvaliacao()
	{
		if(avaliacao == null)
			avaliacao = new Avaliacao();
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao)
	{
		this.avaliacao = avaliacao;
	}

	public void setAvaliacaoManager(AvaliacaoManager avaliacaoManager)
	{
		this.avaliacaoManager = avaliacaoManager;
	}
	
	public Collection<Avaliacao> getAvaliacaos()
	{
		return avaliacaos;
	}

	public Collection<Pergunta> getPerguntas()
	{
		return perguntas;
	}

	public String getUrlVoltar()
	{
		return urlVoltar;
	}

	public void setPerguntaManager(PerguntaManager perguntaManager)
	{
		this.perguntaManager = perguntaManager;
	}

	public TipoPergunta getTipoPergunta()
	{
		return tipoPergunta;
	}

	public boolean isPreview()
	{
		return preview;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public Collection<QuestionarioRelatorio> getDataSource() {
		return dataSource;
	}

	public void setImprimirFormaEconomica(boolean imprimirFormaEconomica)
	{
		this.imprimirFormaEconomica = imprimirFormaEconomica;
	}

	public char getModeloAvaliacao() {
		return modeloAvaliacao;
	}

	public void setModeloAvaliacao(char modeloAvaliacao) {
		this.modeloAvaliacao = modeloAvaliacao;
	}

	public TipoModeloAvaliacao getTipoModeloAvaliacao() {
		return tipoModeloAvaliacao;
	}

	public Collection<PeriodoExperiencia> getPeriodoExperiencias() {
		return periodoExperiencias;
	}

	public void setPeriodoExperienciaManager(PeriodoExperienciaManager periodoExperienciaManager) {
		this.periodoExperienciaManager = periodoExperienciaManager;
	}

	public boolean isTelaInicial() {
		return telaInicial;
	}

	public void setTelaInicial(boolean telaInicial) {
		this.telaInicial = telaInicial;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Collection<CheckBox> getEmpresasCheckList() {
		return empresasCheckList;
	}

	public void setEmpresasCheckList(Collection<CheckBox> empresasCheckList) {
		this.empresasCheckList = empresasCheckList;
	}

	public String[] getEmpresasCheck() {
		return empresasCheck;
	}

	public void setEmpresasCheck(String[] empresasCheck) {
		this.empresasCheck = empresasCheck;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public char getAtivos() {
		return ativos;
	}

	public void setAtivos(char ativos) {
		this.ativos = ativos;
	}

	public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager) {
		this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
	}

	public Collection<ColaboradorResposta> getColaboradorRespostas() {
		return colaboradorRespostas;
	}

	public void setColaboradorRespostas(
			Collection<ColaboradorResposta> colaboradorRespostas) {
		this.colaboradorRespostas = colaboradorRespostas;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public void setColaboradorRespostaManager(
			ColaboradorRespostaManager colaboradorRespostaManager) {
		this.colaboradorRespostaManager = colaboradorRespostaManager;
	}

	public ColaboradorQuestionario getColaboradorQuestionario() {
		return colaboradorQuestionario;
	}

	public void setColaboradorQuestionario(
			ColaboradorQuestionario colaboradorQuestionario) {
		this.colaboradorQuestionario = colaboradorQuestionario;
	}

	public void setPerguntas(Collection<Pergunta> perguntas) {
		this.perguntas = perguntas;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public AvaliacaoCurso getAvaliacaoCurso() {
		return avaliacaoCurso;
	}

	public void setAvaliacaoCurso(AvaliacaoCurso avaliacaoCurso) {
		this.avaliacaoCurso = avaliacaoCurso;
	}
}
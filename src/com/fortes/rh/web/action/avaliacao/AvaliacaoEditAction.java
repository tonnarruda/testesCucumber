package com.fortes.rh.web.action.avaliacao;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.avaliacao.PeriodoExperienciaManager;
import com.fortes.rh.business.desenvolvimento.AvaliacaoCursoManager;
import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
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
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.BooleanUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class AvaliacaoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	@Autowired private ColaboradorCertificacaoManager colaboradorCertificacaoManager;
	@Autowired private ColaboradorRespostaManager colaboradorRespostaManager;
	@Autowired private PeriodoExperienciaManager periodoExperienciaManager;
	@Autowired private ColaboradorTurmaManager colaboradorTurmaManager;
	@Autowired private PlatformTransactionManager transactionManager;
	@Autowired private AvaliacaoCursoManager avaliacaoCursoManager;
	@Autowired private CertificacaoManager certificacaoManager;
	@Autowired private ColaboradorManager colaboradorManager;
	@Autowired private AvaliacaoManager avaliacaoManager;
	@Autowired private PerguntaManager perguntaManager;
	@Autowired private EmpresaManager empresaManager;
	
	private Avaliacao avaliacao;
	private boolean exibirPeso;
	
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
	private boolean agruparPorAspecto;
	private TipoModeloAvaliacao tipoModeloAvaliacao = new TipoModeloAvaliacao();
	
	private Colaborador colaborador;
	private ColaboradorQuestionario colaboradorQuestionario; 
	private Curso curso;
	private Turma turma;
	private AvaliacaoCurso avaliacaoCurso;

	private boolean telaInicial;
	private String titulo;
	private char ativos = 'T';
	private Collection<ColaboradorQuestionario> colaboradorQuestionarios;
	private Long colaboradorTurmaId;
	private boolean colaboradorCertificado;
	private Integer pontuacaoMaximaQuestionario = 0;
	
	private void prepare() throws Exception
	{
		Long avaliacaoId = null;
		if(avaliacao != null && avaliacao.getId() != null){
			avaliacao = (Avaliacao) avaliacaoManager.findById(avaliacao.getId());
			
			if(avaliacao.getTipoModeloAvaliacao() == TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA)
				avaliacaoId = avaliacao.getId();
		}
		
		periodoExperiencias = periodoExperienciaManager.findPeriodosAtivosAndPeriodoDaAvaliacaoId(getEmpresaSistema().getId(), avaliacaoId);
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
		
		if(modeloAvaliacao == 'A')
			modeloAvaliacao = 'D';
		
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
    	exibirPeso = true;
    	
		return Action.SUCCESS;
	}
	
	public String prepareResponderAvaliacaoAluno() throws Exception
	{
		avaliacao = avaliacaoManager.findById(avaliacaoCurso.getAvaliacao().getId());
		perguntas = perguntaManager.getPerguntasRespostaByQuestionarioAgrupadosPorAspecto(avaliacaoCurso.getAvaliacao().getId(), false);
		colaborador = colaboradorManager.findByIdDadosBasicos(colaborador.getId(), StatusRetornoAC.CONFIRMADO);
		colaboradorQuestionario = colaboradorQuestionarioManager.findByColaboradorAvaliacaoCurso(colaborador.getId(), avaliacaoCurso.getId(), turma.getId());
		avaliacaoCurso = avaliacaoCursoManager.findById(avaliacaoCurso.getId());
		
		if(getEmpresaSistema().isControlarVencimentoPorCertificacao()){
			if(colaboradorQuestionario == null)
				colaboradorQuestionario = new ColaboradorQuestionario();
			
			colaboradorQuestionario.setAvaliacao(avaliacaoCurso.getAvaliacao());
			Collection<ColaboradorResposta> colaboradorRespostas = colaboradorRespostaManager.findByColaboradorQuestionario(colaboradorQuestionario.getId());
			pontuacaoMaximaQuestionario = colaboradorRespostaManager.calculaPontuacaoMaximaQuestionario(colaboradorQuestionario, colaboradorRespostas, null);
			colaboradorCertificado = colaboradorCertificacaoManager.isCertificadoByColaboradorTurmaId(colaboradorTurmaId);
		}
		
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
		colaboradorQuestionario.setAvaliacao(null);
		colaboradorQuestionario.setAvaliacaoCurso(avaliacaoCurso);
		
		if (colaboradorQuestionario.getRespondidaEm() == null)
			colaboradorQuestionario.setRespondidaEm(new Date());
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try
		{
			colaboradorQuestionarioManager.saveOrUpdate(colaboradorQuestionario);
			
			Collection<ColaboradorResposta> colaboradorRespostasDasPerguntas = perguntaManager.getColaboradorRespostasDasPerguntas(perguntas);			
			colaboradorQuestionario.setAvaliacao(avaliacaoCurso.getAvaliacao());
			colaboradorRespostaManager.update(colaboradorRespostasDasPerguntas, colaboradorQuestionario, getUsuarioLogado().getId(), null, null);
			
			transactionManager.commit(status);
			
			boolean colaboradotumaAprovado = colaboradorTurmaManager.aprovarOrReprovarColaboradorTurma(colaboradorTurmaId, turma.getId(), curso.getId());
			checaCertificacao(colaboradotumaAprovado);
			addActionSuccess("Respostas gravadas com sucesso");
			
			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			transactionManager.rollback(status);
			e.printStackTrace();
			prepareResponderAvaliacaoAluno();
			addActionError("Ocorreu um erro ao gravar as respostas da avaliação");
			return Action.INPUT;
		}
	}
	
	private void checaCertificacao(boolean colaboradotumaAprovado) {
		if(getEmpresaSistema().isControlarVencimentoPorCertificacao()){	
			if(colaboradotumaAprovado)
				colaboradorCertificacaoManager.certificaColaborador(colaboradorTurmaId, null, null, certificacaoManager);
			else
				colaboradorCertificacaoManager.descertificarColaboradorByColaboradorTurma(colaboradorTurmaId);
		}
	}
	
	public String list() throws Exception
	{
		setVideoAjuda(778L);
		
		setTotalSize(avaliacaoManager.getCount(getEmpresaSistema().getId(), BooleanUtil.getValueCombo(ativos), modeloAvaliacao, titulo));
		avaliacaos = avaliacaoManager.findAllSelect(getPage(), getPagingSize(), getEmpresaSistema().getId(), BooleanUtil.getValueCombo(ativos), modeloAvaliacao, titulo);
		
		Collection<Empresa> empresas = empresaManager.findEmpresasPermitidas(true , null, getUsuarioLogado().getId(), "ROLE_MOV_QUESTIONARIO");
   		empresasCheckList =  CheckListBoxUtil.populaCheckListBox(empresas, "getId", "getNome", null);
		
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
   	   	dataSource = avaliacaoManager.getQuestionarioRelatorioCollection(avaliacao, agruparPorAspecto);

   	   	String titulo = "Avaliação";
   	   	String filtro = avaliacao.getTitulo();
   	   	
    	parametros = RelatorioUtil.getParametrosRelatorio(titulo, getEmpresaSistema(), filtro);
    	parametros.put("FORMA_ECONOMICA", imprimirFormaEconomica );
    	
    	return Action.SUCCESS;
    }
	
	public String minhasAvaliacoesList()
	{
		preview = false;
		
		Long usuarioColaboradorId = SecurityUtil.getColaboradorSession(ActionContext.getContext().getSession()).getId();
		
		colaboradorQuestionarios = colaboradorQuestionarioManager.findAutoAvaliacao(usuarioColaboradorId);
		
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

	public boolean isImprimirFormaEconomica() {
		return imprimirFormaEconomica;
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

	public char getAtivos() {
		return ativos;
	}

	public void setAtivos(char ativos) {
		this.ativos = ativos;
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
	
	public Collection<ColaboradorQuestionario> getColaboradorQuestionarios()
	{
		return colaboradorQuestionarios;
	}

	public boolean isExibirPeso() {
		return exibirPeso;
	}

	public void setExibirPeso(boolean exibirPeso) {
		this.exibirPeso = exibirPeso;
	}

	public void setAgruparPorAspecto(boolean agruparPorAspecto) {
		this.agruparPorAspecto = agruparPorAspecto;
	}

	public void setColaboradorTurmaId(Long colaboradorTurmaId) {
		this.colaboradorTurmaId = colaboradorTurmaId;
	}

	public Long getColaboradorTurmaId() {
		return colaboradorTurmaId;
	}

	public boolean isColaboradorCertificado() {
		return colaboradorCertificado;
	}

	public void setColaboradorCertificado(boolean colaboradorCertificado) {
		this.colaboradorCertificado = colaboradorCertificado;
	}

	public Integer getPontuacaoMaximaQuestionario() {
		return pontuacaoMaximaQuestionario;
	}
}
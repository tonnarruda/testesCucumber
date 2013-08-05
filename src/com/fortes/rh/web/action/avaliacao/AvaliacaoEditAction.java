package com.fortes.rh.web.action.avaliacao;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.avaliacao.PeriodoExperienciaManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.geral.Empresa;
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
	private boolean imprimirFormaEconomica = false;
	private char modeloAvaliacao = 'D'; 
	private TipoModeloAvaliacao tipoModeloAvaliacao = new TipoModeloAvaliacao();
	private Collection<PeriodoExperiencia> periodoExperiencias;
	private boolean telaInicial;
	private String titulo;
	private char ativos = 'T';
	
	private Long cursoId;
	private Long turmaId;
	private Long avaliacaoCursoId;
	
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
		urlVoltar = "../turma/prepareAproveitamento.action?turma.id=" + turmaId + "&curso.id=" + cursoId + "&avaliacaoCurso.id=" + avaliacaoCursoId;
		
		return Action.SUCCESS;
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

	public void setCursoId(Long cursoId) {
		this.cursoId = cursoId;
	}

	public void setTurmaId(Long turmaId) {
		this.turmaId = turmaId;
	}

	public void setAvaliacaoCursoId(Long avaliacaoCursoId) {
		this.avaliacaoCursoId = avaliacaoCursoId;
	}
}
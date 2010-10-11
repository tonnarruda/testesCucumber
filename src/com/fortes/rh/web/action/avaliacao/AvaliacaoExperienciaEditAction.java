package com.fortes.rh.web.action.avaliacao;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioRelatorio;
import com.fortes.rh.model.pesquisa.relatorio.ResultadoQuestionario;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class AvaliacaoExperienciaEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private AvaliacaoManager avaliacaoManager;
	private PerguntaManager perguntaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	
	private Avaliacao avaliacaoExperiencia;
	
	private Collection<Avaliacao> avaliacaoExperiencias;
	private Collection<Pergunta> perguntas;
	
	private String urlVoltar;
	private TipoPergunta tipoPergunta = new TipoPergunta();
	private boolean preview = true;
	
	private Map<String, Object> parametros;
	private Collection<QuestionarioRelatorio> dataSource;
	
	private String[] areasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] aspectosCheck;
	private Collection<CheckBox> aspectosCheckList = new ArrayList<CheckBox>();
 	private String[] perguntasCheck;
	private Collection<CheckBox> perguntasCheckList = new ArrayList<CheckBox>();
	
	private Date periodoIni;
	private Date periodoFim;
	
	private boolean exibirRespostas;
	private boolean exibirComentarios;
	private boolean exibirCabecalho;
	private boolean agruparPorAspectos;
	private Collection<ResultadoQuestionario> resultados;

	
	public String prepareResultado()
	{
		avaliacaoExperiencias = avaliacaoManager.findAllSelect(getEmpresaSistema().getId(), null);
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
    	areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
		return SUCCESS;
	}
	
	public String imprimeResultado() throws Exception 
    {
		try
    	{
			avaliacaoExperiencia = avaliacaoManager.findById(avaliacaoExperiencia.getId());
	    	perguntas = perguntaManager.findByQuestionarioAspectoPergunta(avaliacaoExperiencia.getId(), LongUtil.arrayStringToArrayLong(aspectosCheck), LongUtil.arrayStringToArrayLong(perguntasCheck), agruparPorAspectos);
	
	    	if(perguntas.isEmpty())
	    	{
	    		addActionMessage("Não existe pergunta para a avaliação ou aspecto(s).");
	    		prepareResultado();
	    		return Action.INPUT;
	    	}
	   	   	
	    	parametros = RelatorioUtil.getParametrosRelatorio("Avaliação do Período de Experiência", getEmpresaSistema(), avaliacaoExperiencia.getTitulo());
	    	parametros.put("AGRUPAR_ASPECTO", agruparPorAspectos);
	    	parametros.put("EXIBIR_CABECALHO", exibirCabecalho);
	    	parametros.put("EXIBIR_RESPOSTAS_SUBJETIVAS", exibirRespostas);
	    	parametros.put("EXIBIR_COMENTARIOS", exibirComentarios);
	    	parametros.put("QUESTIONARIO_ANONIMO", false);
	    	parametros.put("QUESTIONARIO_CABECALHO", avaliacaoExperiencia.getCabecalho());
	    	
	    	CollectionUtil<Pergunta> clu = new CollectionUtil<Pergunta>();
	    	Long[] perguntasIds = clu.convertCollectionToArrayIds(perguntas);
	    	Long[] areaIds = LongUtil.arrayStringToArrayLong(areasCheck);
	
    		resultados = avaliacaoManager.montaResultado(perguntas, perguntasIds, areaIds, periodoIni, periodoFim, avaliacaoExperiencia);
    		parametros.put("TOTAL_COLAB_RESP", avaliacaoExperiencia.getTotalColab());
		}
		catch (Exception e)
		{
			addActionMessage(e.getMessage());
			e.printStackTrace();
			prepareResultado();
			return Action.INPUT;
		}

    	return Action.SUCCESS;
    }

	public Avaliacao getAvaliacaoExperiencia()
	{
		if(avaliacaoExperiencia == null)
			avaliacaoExperiencia = new Avaliacao();
		return avaliacaoExperiencia;
	}

	public void setAvaliacaoExperiencia(Avaliacao avaliacaoExperiencia)
	{
		this.avaliacaoExperiencia = avaliacaoExperiencia;
	}

	public void setAvaliacaoManager(AvaliacaoManager avaliacaoManager)
	{
		this.avaliacaoManager = avaliacaoManager;
	}
	
	public Collection<Avaliacao> getAvaliacaoExperiencias()
	{
		return avaliacaoExperiencias;
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

	public String[] getAreasCheck() {
		return areasCheck;
	}

	public void setAreasCheck(String[] areasCheck) {
		this.areasCheck = areasCheck;
	}

	public Collection<CheckBox> getAreasCheckList() {
		return areasCheckList;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public String[] getAspectosCheck() {
		return aspectosCheck;
	}

	public void setAspectosCheck(String[] aspectosCheck) {
		this.aspectosCheck = aspectosCheck;
	}

	public String[] getPerguntasCheck() {
		return perguntasCheck;
	}

	public void setPerguntasCheck(String[] perguntasCheck) {
		this.perguntasCheck = perguntasCheck;
	}

	public Collection<CheckBox> getAspectosCheckList() {
		return aspectosCheckList;
	}

	public Collection<CheckBox> getPerguntasCheckList() {
		return perguntasCheckList;
	}

	public boolean isAgruparPorAspectos() {
		return agruparPorAspectos;
	}

	public void setAgruparPorAspectos(boolean agruparPorAspectos) {
		this.agruparPorAspectos = agruparPorAspectos;
	}

	public boolean isExibirCabecalho() {
		return exibirCabecalho;
	}

	public void setExibirCabecalho(boolean exibirCabecalho) {
		this.exibirCabecalho = exibirCabecalho;
	}

	public Date getPeriodoIni() {
		return periodoIni;
	}

	public void setPeriodoIni(Date periodoIni) {
		this.periodoIni = periodoIni;
	}

	public Date getPeriodoFim() {
		return periodoFim;
	}

	public void setPeriodoFim(Date periodoFim) {
		this.periodoFim = periodoFim;
	}

	public Collection<ResultadoQuestionario> getResultados() {
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

}

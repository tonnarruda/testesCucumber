package com.fortes.rh.web.action.avaliacao;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioRelatorio;
import com.fortes.rh.model.pesquisa.relatorio.ResultadoQuestionario;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class AvaliacaoExperienciaEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private EmpresaManager empresaManager;
	@Autowired private PerguntaManager perguntaManager;
	@Autowired private AvaliacaoManager avaliacaoManager;
	@Autowired private AreaOrganizacionalManager areaOrganizacionalManager;
	@Autowired private ParametrosDoSistemaManager parametrosDoSistemaManager;
	@Autowired private ColaboradorRespostaManager colaboradorRespostaManager;
	
	private Avaliacao avaliacaoExperiencia;
	
	private Collection<Avaliacao> avaliacaoExperienciasAtivas;
	private Collection<Avaliacao> avaliacaoExperienciasInativas;
	private Collection<Pergunta> perguntas;
	
	private String urlVoltar;
	private TipoPergunta tipoPergunta = new TipoPergunta();
	private boolean preview = true;
	
	private Map<String, Object> parametros;
	private Collection<QuestionarioRelatorio> dataSource;
	private Collection<Empresa> empresas;
	private Long[] empresaIds;//repassado para o DWR
	private Empresa empresa;
	
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
	private boolean ocultarNomeColaboradores;
	private boolean ocultarQtdRespostas;
	private boolean exibirCabecalho;
	private boolean exibirObsAvaliadores;
	private boolean agruparPorAspectos;
	private Collection<ResultadoQuestionario> resultados;
	private boolean compartilharColaboradores;
	private Character tipoModeloAvaliacao;
	
	public String prepareResultado()
	{
		compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
		empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores, getEmpresaSistema().getId(), getUsuarioLogado().getId());
		CollectionUtil<Empresa> clu = new CollectionUtil<Empresa>();
		empresaIds = clu.convertCollectionToArrayIds(empresas);//usado pelo DWR

		avaliacaoExperienciasAtivas = avaliacaoManager.findAllSelect(null, null, getEmpresaSistema().getId(), true, TipoModeloAvaliacao.DESEMPENHO, null);
		avaliacaoExperienciasInativas = avaliacaoManager.findAllSelect(null, null, getEmpresaSistema().getId(), false, TipoModeloAvaliacao.DESEMPENHO, null);
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
	   	   	
	    	parametros = RelatorioUtil.getParametrosRelatorio(defineModeloDeAvaliacaoDoRelatorio(), getEmpresaSistema(), avaliacaoExperiencia.getTitulo());
	    	parametros.put("AGRUPAR_ASPECTO", agruparPorAspectos);
	    	parametros.put("EXIBIR_CABECALHO", exibirCabecalho);
	    	parametros.put("OCULTAR_NOME_COLABORADOR", ocultarNomeColaboradores);
	    	parametros.put("EXIBIR_RESPOSTAS_SUBJETIVAS", true);
	    	parametros.put("EXIBIR_RESPOSTAS_NAO_SUBJETIVAS", exibirRespostas);
	    	parametros.put("EXIBIR_COMENTARIOS", exibirComentarios);
	    	parametros.put("QUESTIONARIO_ANONIMO", false);
	    	parametros.put("QUESTIONARIO_CABECALHO", avaliacaoExperiencia.getCabecalho());
	    	
	    	CollectionUtil<Pergunta> clu = new CollectionUtil<Pergunta>();
	    	Long[] perguntasIds = clu.convertCollectionToArrayIds(perguntas);
	    	Long[] areaIds = LongUtil.arrayStringToArrayLong(areasCheck);
	    	
    		resultados = avaliacaoManager.montaResultado(perguntas, perguntasIds, areaIds, periodoIni, periodoFim, avaliacaoExperiencia, empresa.getId(), tipoModeloAvaliacao, ocultarQtdRespostas);
    		parametros.put("TOTAL_COLAB_RESP", avaliacaoExperiencia.getTotalColab());
    		
    		String obsAval = "";
    		if(exibirObsAvaliadores)
    			obsAval = avaliacaoManager.montaObsAvaliadores(colaboradorRespostaManager.findInPerguntaIdsAvaliacao(perguntasIds, areaIds, periodoIni, periodoFim, empresa.getId(), tipoModeloAvaliacao));
    		parametros.put("OBS_AVALIADOS", obsAval);
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

	private String defineModeloDeAvaliacaoDoRelatorio()
	{
		TipoModeloAvaliacao tipo = new TipoModeloAvaliacao();
		
		if(tipoModeloAvaliacao == null)
			return tipo.get(avaliacaoExperiencia.getTipoModeloAvaliacao());
		else
			return tipo.get(tipoModeloAvaliacao);
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

	public String[] getAreasCheck() {
		return areasCheck;
	}

	public void setAreasCheck(String[] areasCheck) {
		this.areasCheck = areasCheck;
	}

	public Collection<CheckBox> getAreasCheckList() {
		return areasCheckList;
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

	public boolean isOcultarQtdRespostas() {
		return ocultarQtdRespostas;
	}

	public void setOcultarQtdRespostas(boolean ocultarQtdRespostas) {
		this.ocultarQtdRespostas = ocultarQtdRespostas;
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

	public void setExibirComentarios(boolean exibirComentarios) {
		this.exibirComentarios = exibirComentarios;
	}

	public boolean isOcultarNomeColaboradores() {
		return ocultarNomeColaboradores;
	}

	public void setOcultarNomeColaboradores(boolean ocultarNomeColaboradores) {
		this.ocultarNomeColaboradores = ocultarNomeColaboradores;
	}

	public Collection<Empresa> getEmpresas() {
		return empresas;
	}

	public Long[] getEmpresaIds() {
		return empresaIds;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Collection<Avaliacao> getAvaliacaoExperienciasAtivas() {
		return avaliacaoExperienciasAtivas;
	}

	public Collection<Avaliacao> getAvaliacaoExperienciasInativas() {
		return avaliacaoExperienciasInativas;
	}

	public void setExibirObsAvaliadores(boolean exibirObsAvaliadores) {
		this.exibirObsAvaliadores = exibirObsAvaliadores;
	}

	public void setTipoModeloAvaliacao(Character tipoModeloAvaliacao)
	{
		this.tipoModeloAvaliacao = tipoModeloAvaliacao;
	}
}
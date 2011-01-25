package com.fortes.rh.web.action.avaliacao;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.tigris.subversion.svnant.Add;

import bsh.StringUtil;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.avaliacao.PeriodoExperienciaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class PeriodoExperienciaEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private PeriodoExperienciaManager periodoExperienciaManager;
	private PeriodoExperiencia periodoExperiencia;
	private Collection<PeriodoExperiencia> periodoExperiencias;			
	private AvaliacaoDesempenho avaliacaoDesempenho;
	private Collection<AvaliacaoDesempenho> avaliacaoDesempenhos;
	
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private Avaliacao modeloAvaliacao;
	private AvaliacaoManager avaliacaoManager;
	private Collection<Avaliacao> modeloAvaliacaos;
	private Pergunta pergunta;
	private Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
	private PerguntaManager perguntaManager;
	
	private Date dataReferencia;
	private Date periodoIni;
	private Date periodoFim;
	private String[] areasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private Collection<Empresa> empresas;
	private EmpresaManager empresaManager;
	
	private String[] estabelecimentoCheck;
	private Collection<CheckBox> estabelecimentoCheckList = new ArrayList<CheckBox>();
	
	private Collection<Colaborador> colaboradores;

	private ColaboradorManager colaboradorManager;
	private Map<String, Object> parametros;
	private Integer diasDeAcompanhamento;
		
	private void prepare() throws Exception
	{
		if(periodoExperiencia != null && periodoExperiencia.getId() != null)
			periodoExperiencia = (PeriodoExperiencia) periodoExperienciaManager.findById(periodoExperiencia.getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		periodoExperiencia.setEmpresa(getEmpresaSistema());
		periodoExperienciaManager.save(periodoExperiencia);
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		periodoExperienciaManager.update(periodoExperiencia);
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		periodoExperiencias = periodoExperienciaManager.findAllSelect(getEmpresaSistema().getId());
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		periodoExperienciaManager.remove(periodoExperiencia.getId());
		addActionMessage("Período de Acompanhamento de Experiência excluído com sucesso.");

		return Action.SUCCESS;
	}
	
	public String prepareRelatorioAcopanhamentoExperiencia() throws Exception{
		prepare();
		
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
    	estabelecimentoCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());
    	
		return Action.SUCCESS;
	}  
	
	public String prepareRelatorioRankingPerformance() throws Exception{
				
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
    	estabelecimentoCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());
    	modeloAvaliacaos = avaliacaoManager.findAllSelect(getEmpresaSistema().getId(), null, TipoModeloAvaliacao.AVALIACAO_DESEMPENHO);
    	
		return Action.SUCCESS;
		
	}
	
	public String imprimeRelatorioPeriodoDeAcompanhamentoDeExperiencia() throws Exception 
    {
		try {
			dataReferencia = getDataReferencia();
			colaboradores = colaboradorManager.findAdmitidosNoPeriodo(dataReferencia, getEmpresaSistema(), areasCheck, estabelecimentoCheck, diasDeAcompanhamento);
			
			String filtro = "Data de Referência " + DateUtil.formataDiaMesAno(dataReferencia) + "\n"; 
			parametros = RelatorioUtil.getParametrosRelatorio("Relatório De Acompanhamento De Experiencia", getEmpresaSistema(), filtro);
			
			String rodape = periodoExperienciaManager.findRodapeDiasDoPeriodoDeExperiencia(getEmpresaSistema().getId()); 
			parametros.put("rodapePeriodoExperiencia", rodape);
		}
		catch (Exception e)
		{
			addActionMessage(e.getMessage());
			e.printStackTrace();
			prepareRelatorioAcopanhamentoExperiencia();
			return Action.INPUT;
		}

    	return Action.SUCCESS;
    }

	public String imprimeRelatorioRankingPerformancePeriodoDeExperiencia() throws Exception 
	{
		try 
		{
			colaboradores = colaboradorManager.findColabPeriodoExperiencia(getEmpresaSistema().getId(), periodoIni, periodoFim, modeloAvaliacao.getId(), areasCheck, estabelecimentoCheck);
			Avaliacao modelo = avaliacaoManager.findById(modeloAvaliacao.getId());
			String filtroCabecalho = "Período de " + DateUtil.formataDiaMesAno(periodoIni) + " a " + DateUtil.formataDiaMesAno(periodoFim) + "\n" + "Modelo de Avaliação: " + modelo.getTitulo(); 
			parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Ranking de Performace de Avaliação de Desempenho", getEmpresaSistema(), filtroCabecalho);
			
		}
		catch (Exception e)
		{
			addActionMessage(e.getMessage());
			e.printStackTrace();
			prepareRelatorioRankingPerformance();
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}
	
	public PeriodoExperiencia getPeriodoExperiencia()
	{
		if(periodoExperiencia == null)
			periodoExperiencia = new PeriodoExperiencia();
		return periodoExperiencia;
	}

	public void setPeriodoExperiencia(PeriodoExperiencia periodoExperiencia)
	{
		this.periodoExperiencia = periodoExperiencia;
	}

	public void setPeriodoExperienciaManager(PeriodoExperienciaManager periodoExperienciaManager)
	{
		this.periodoExperienciaManager = periodoExperienciaManager;
	}
	
	public Collection<PeriodoExperiencia> getPeriodoExperiencias()
	{
		return periodoExperiencias;
	}

	public AvaliacaoDesempenho getAvaliacaoDesempenho() {
		return avaliacaoDesempenho;
	}

	public Collection<AvaliacaoDesempenho> getAvaliacaoDesempenhos() {
		return avaliacaoDesempenhos;
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

	public Collection<CheckBox> getAreasCheckList() {
		return areasCheckList;
	}

	public void setAreasCheckList(Collection<CheckBox> areasCheckList) {
		this.areasCheckList = areasCheckList;
	}

	public Collection<CheckBox> getEstabelecimentoCheckList() {
		return estabelecimentoCheckList;
	}

	public void setEstabelecimentoCheckList(Collection<CheckBox> estabelecimentoCheckList) {
		this.estabelecimentoCheckList = estabelecimentoCheckList;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

		public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}

		public Pergunta getPergunta() {
			return pergunta;
		}

		public PerguntaManager getPerguntaManager() {
			return perguntaManager;
		}

		public Collection<Pergunta> getPerguntas() {
			return perguntas;
		}

		public void setColaboradorManager(ColaboradorManager colaboradorManager) {
			this.colaboradorManager = colaboradorManager;
		}

		public Collection<Colaborador> getColaboradores() {
			return colaboradores;
		}

		public Map<String, Object> getParametros() {
			return parametros;
		}

		public void setPeriodoExperiencias(Collection<PeriodoExperiencia> periodoExperiencias) {
			this.periodoExperiencias = periodoExperiencias;
		}

		public PeriodoExperienciaManager getPeriodoExperienciaManager() {
			return periodoExperienciaManager;
		}

		public String[] getAreasCheck() {
			return areasCheck;
		}

		public void setAreasCheck(String[] areasCheck) {
			this.areasCheck = areasCheck;
		}

		public String[] getEstabelecimentoCheck() {
			return estabelecimentoCheck;
		}

		public void setEstabelecimentoCheck(String[] estabelecimentoCheck) {
			this.estabelecimentoCheck = estabelecimentoCheck;
		}

		public String getDataDoDia() 
		{
			return DateUtil.formataDiaMesAno(new Date());
		}

		public Date getDataReferencia() {
			return dataReferencia;
		}

		public void setDataReferencia(Date dataReferencia) {
			this.dataReferencia = dataReferencia;
		}

		public Collection<Empresa> getEmpresas() {
			return empresas;
		}

		public void setEmpresas(Collection<Empresa> empresas) {
			this.empresas = empresas;
		}

		public EmpresaManager getEmpresaManager() {
			return empresaManager;
		}

		public void setEmpresaManager(EmpresaManager empresaManager) {
			this.empresaManager = empresaManager;
		}

		public ColaboradorManager getColaboradorManager() {
			return colaboradorManager;
		}

		public void setAvaliacaoManager(AvaliacaoManager avaliacaoManager) {
			this.avaliacaoManager = avaliacaoManager;
		}

		public Collection<Avaliacao> getModeloAvaliacaos() {
			return modeloAvaliacaos;
		}

		public void setModeloAvaliacaos(Collection<Avaliacao> modeloAvaliacaos) {
			this.modeloAvaliacaos = modeloAvaliacaos;
		}

		public void setModeloAvaliacao(Avaliacao modeloAvaliacao) {
			this.modeloAvaliacao = modeloAvaliacao;
		}

		public Integer getDiasDeAcompanhamento() {
			return diasDeAcompanhamento;
		}

		public void setDiasDeAcompanhamento(Integer diasDeAcompanhamento) {
			this.diasDeAcompanhamento = diasDeAcompanhamento;
		}
}
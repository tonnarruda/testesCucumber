package com.fortes.rh.web.action.avaliacao;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.avaliacao.PeriodoExperienciaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.avaliacao.relatorio.AcompanhamentoExperienciaColaborador;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.relatorio.CartaoAcompanhamentoExperienciaVO;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class PeriodoExperienciaEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private PeriodoExperienciaManager periodoExperienciaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private AvaliacaoDesempenhoManager avaliacaoDesempenhoManager;
	private AvaliacaoManager avaliacaoManager;
	private EstabelecimentoManager estabelecimentoManager;
	private PerguntaManager perguntaManager;
	private EmpresaManager empresaManager;
	private ColaboradorManager colaboradorManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;

	private PeriodoExperiencia periodoExperiencia;
	private Collection<PeriodoExperiencia> periodoExperiencias;			
	private AvaliacaoDesempenho avaliacaoDesempenho;
	private Collection<AvaliacaoDesempenho> avaliacaoDesempenhos;
	private Avaliacao avaliacao;
	private Collection<Avaliacao> avaliacoes;

	private Pergunta pergunta;
	private Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
	
	private Date dataReferencia;
	private Date periodoIni;
	private Date periodoFim;
	private Collection<Empresa> empresas;
	private Empresa empresa;
	
	private String[] areasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentoCheck;
	private Collection<CheckBox> estabelecimentoCheckList = new ArrayList<CheckBox>();
	private String[] avaliacaoCheck;
	private Collection<CheckBox> avaliacaoCheckList = new ArrayList<CheckBox>();
	private String[] periodoCheck;
	private Collection<CheckBox> periodoCheckList = new ArrayList<CheckBox>();
	private String[] colaboradorsCheck;
	private Collection<CheckBox> colaboradorsCheckList = new ArrayList<CheckBox>();
	
	private Collection<Colaborador> colaboradores;

	private Map<String, Object> parametros;
	private Integer diasDeAcompanhamento;
	private Integer tempoDeEmpresa;
	private String reportFilter;
	private String reportTitle;
	private List<AcompanhamentoExperienciaColaborador> acompanhamentos;
	private boolean considerarAutoAvaliacao;
	
	private Estabelecimento estabelecimento;
	private Collection<Estabelecimento> estabelecimentos;
	private String observacoes;
	private Collection<CartaoAcompanhamentoExperienciaVO> cartoesAcompanhamentoExperienciaVOs;
	private boolean compartilharColaboradores;
		
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
		periodoExperiencias = periodoExperienciaManager.findAllSelect(getEmpresaSistema().getId(), false);
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		periodoExperienciaManager.remove(periodoExperiencia.getId());
		addActionMessage("Período de Acompanhamento de Experiência excluído com sucesso.");

		return Action.SUCCESS;
	}
	
	public String prepareRelatorioAcompanhamentoExperienciaPrevisto() throws Exception{
		prepare();
		
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
    	estabelecimentoCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());
    	periodoCheckList = periodoExperienciaManager.populaCheckBox(getEmpresaSistema().getId());
    	
		return Action.SUCCESS;
	}

	public String prepareRelatorioAcompanhamentoExperiencia() throws Exception{
		prepare();
		
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		estabelecimentoCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());
		periodoCheckList = periodoExperienciaManager.populaCheckBox(getEmpresaSistema().getId());
		
		return Action.SUCCESS;
	}
	
	public String prepareRelatorioRankingPerformance() throws Exception
	{
		empresa = getEmpresaSistema();
		
		compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
		empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores, empresa.getId(), getUsuarioLogado().getId(), null);
		
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(empresa.getId());
    	estabelecimentoCheckList = estabelecimentoManager.populaCheckBox(empresa.getId());
    	avaliacaoCheckList = avaliacaoDesempenhoManager.populaCheckBox(empresa.getId(), true, TipoModeloAvaliacao.DESEMPENHO);
    	
    	avaliacoes = avaliacaoManager.findAllSelectComAvaliacaoDesempenho(getEmpresaSistema().getId(), true);
    	
    	if(avaliacao != null  && avaliacao.getId() != null)
    		colaboradorsCheckList = CheckListBoxUtil.populaCheckListBox(colaboradorManager.findByAvaliacoes(StringUtil.stringToLong(avaliacaoCheck)), "getId", "getNome");
    	
		return Action.SUCCESS;
		
	}
	
	public String imprimeRelatorioPeriodoDeAcompanhamentoDeExperienciaPrevisto() throws Exception 
    {
		try {
			dataReferencia = getDataReferencia();

			if(periodoCheck == null || !(periodoCheck.length > 0))
				periodoExperiencias = periodoExperienciaManager.findAllSelect(getEmpresaSistema().getId(), false);
			else
				periodoExperiencias = periodoExperienciaManager.findById(periodoCheck);
			
			colaboradores = colaboradorManager.getAvaliacoesExperienciaPendentes(dataReferencia, getEmpresaSistema(), areasCheck, estabelecimentoCheck, tempoDeEmpresa, diasDeAcompanhamento, periodoExperiencias);
			
			String filtro = "Data de Referência " + DateUtil.formataDiaMesAno(dataReferencia) + "\n"; 
			parametros = RelatorioUtil.getParametrosRelatorio("Relatório De Acompanhamento De Experiência Previsto", getEmpresaSistema(), filtro);
			
			String rodape = periodoExperienciaManager.findRodapeDiasDoPeriodoDeExperiencia(periodoExperiencias); 
			parametros.put("rodapePeriodoExperiencia", rodape);
		}
		catch (Exception e)
		{
			addActionMessage(e.getMessage());
			e.printStackTrace();
			prepareRelatorioAcompanhamentoExperienciaPrevisto();
			return Action.INPUT;
		}

    	return Action.SUCCESS;
    }

	public String imprimeRelatorioPeriodoDeAcompanhamentoDeExperiencia() throws Exception 
	{
		try {
			dataReferencia = getDataReferencia();
			
			periodoExperiencias = periodoExperienciaManager.findById(periodoCheck);
			
			acompanhamentos = colaboradorManager.getAvaliacoesExperienciaPendentesPeriodo(dataReferencia, getEmpresaSistema(), areasCheck, estabelecimentoCheck, tempoDeEmpresa, periodoExperiencias);
			
			String filtro = "Data de Referência: " + DateUtil.formataDiaMesAno(dataReferencia) + "\n"; 
			parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Acompanhamento de Experiência", getEmpresaSistema(), filtro);
			
			String rodape = periodoExperienciaManager.findRodapeDiasDoPeriodoDeExperiencia(periodoExperiencias); 
			parametros.put("rodapePeriodoExperiencia", rodape);
			
			int coluna = 1;
			for (PeriodoExperiencia periodo : periodoExperiencias)
				parametros.put("tituloPeriodo" + coluna++, periodo.getDiasDescricao());
				
		}
		catch (Exception e)
		{
			addActionMessage(e.getMessage());
			e.printStackTrace();
			prepareRelatorioAcompanhamentoExperiencia();
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}

	public String imprimeRelatorioRankingPerformancePeriodoDeExperiencia() throws Exception 
	{
		try 
		{
			colaboradores = colaboradorManager.findColabPeriodoExperiencia(empresa.getId(), periodoIni, periodoFim, avaliacaoCheck, areasCheck, estabelecimentoCheck, colaboradorsCheck);

			String msgAval = "";
			if(avaliacaoCheck != null && avaliacaoCheck.length == 1)
			{
				avaliacaoDesempenho = avaliacaoDesempenhoManager.findById(StringUtil.stringToLong(avaliacaoCheck)[0]);
				String avalAnonima = "";
				if(avaliacaoDesempenho.isAnonima())
					avalAnonima = " (Anônima)";
				
				msgAval = "Avaliação: " + avaliacaoDesempenho.getTitulo() + avalAnonima;
			}
			reportFilter = "Período de " + DateUtil.formataDiaMesAno(periodoIni) + " a " + DateUtil.formataDiaMesAno(periodoFim) + "\n" + msgAval;
			reportTitle = "Relatório de Ranking de Performace de Avaliação de Desempenho";

			parametros = RelatorioUtil.getParametrosRelatorio(reportTitle, getEmpresaSistema(), reportFilter);			
			parametros.put("exibirNomeAvaliacao", !msgAval.equals(""));
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

	public String impRankPerformAvDesempenho() throws Exception 
	{
		try 
		{
			colaboradores = colaboradorManager.findColabPeriodoExperienciaAgrupadoPorModelo(getEmpresaSistema().getId(), periodoIni, periodoFim, avaliacao.getId(), areasCheck, estabelecimentoCheck, colaboradorsCheck, considerarAutoAvaliacao);

			reportFilter = "Período de " + DateUtil.formataDiaMesAno(periodoIni) + " a " + DateUtil.formataDiaMesAno(periodoFim) ;
			reportTitle = "Relatório de Ranking de Performance de Avaliação de Desempenho";
			
			parametros = RelatorioUtil.getParametrosRelatorio(reportTitle, getEmpresaSistema(), reportFilter);			
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
	
	public String prepareCartoesAcompanhamentoPeriodoExperiencia()
	{
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		periodoCheckList = periodoExperienciaManager.populaCheckBoxDistinctDias(getEmpresaSistema().getId());
		
		return Action.SUCCESS;
	}
	
	public String imprimeCartoesAcompanhamentoPeriodoExperiencia()
	{
		try 
		{
			Long[] colaboradoresIds = StringUtil.stringToLong(colaboradorsCheck);
			Long[] dias = StringUtil.stringToLong(periodoCheck);
			cartoesAcompanhamentoExperienciaVOs = colaboradorManager.montaCartoesPeriodoExperiencia(colaboradoresIds, dias, observacoes);

			reportFilter = "Admitidos a partir de " + DateUtil.formataDiaMesAno(dataReferencia);
			reportTitle = "Acompanhamento do Período de Experiência";
			
			parametros = RelatorioUtil.getParametrosRelatorio(reportTitle, getEmpresaSistema(), reportFilter);			
		}
		catch (Exception e)
		{
			addActionMessage(e.getMessage());
			e.printStackTrace();
			prepareCartoesAcompanhamentoPeriodoExperiencia();
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
	
	public Integer getDiasDeAcompanhamento() {
		return diasDeAcompanhamento;
	}
	
	public void setDiasDeAcompanhamento(Integer diasDeAcompanhamento) {
		this.diasDeAcompanhamento = diasDeAcompanhamento;
	}
	
	public Integer getTempoDeEmpresa() {
		return tempoDeEmpresa;
	}
	
	public void setTempoDeEmpresa(Integer tempoDeEmpresa) {
		this.tempoDeEmpresa = tempoDeEmpresa;
	}
	
	public String getReportFilter() {
		return reportFilter;
	}
	
	public String getReportTitle() {
		return reportTitle;
	}
	
	public void setAvaliacaoDesempenhoManager(AvaliacaoDesempenhoManager avaliacaoDesempenhoManager) {
		this.avaliacaoDesempenhoManager = avaliacaoDesempenhoManager;
	}
	
	public void setAvaliacaoDesempenho(AvaliacaoDesempenho avaliacaoDesempenho) {
		this.avaliacaoDesempenho = avaliacaoDesempenho;
	}

	public String[] getPeriodoCheck() {
		return periodoCheck;
	}

	public void setPeriodoCheck(String[] periodoCheck) {
		this.periodoCheck = periodoCheck;
	}

	public Collection<CheckBox> getPeriodoCheckList() {
		return periodoCheckList;
	}

	public void setPeriodoCheckList(Collection<CheckBox> periodoCheckList) {
		this.periodoCheckList = periodoCheckList;
	}

	public List<AcompanhamentoExperienciaColaborador> getAcompanhamentos() {
		return acompanhamentos;
	}

	public Collection<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	public void setAvaliacaoManager(AvaliacaoManager avaliacaoManager) {
		this.avaliacaoManager = avaliacaoManager;
	}

	public String[] getColaboradorsCheck() {
		return colaboradorsCheck;
	}

	public void setColaboradorsCheck(String[] colaboradorsCheck) {
		this.colaboradorsCheck = colaboradorsCheck;
	}

	public Collection<CheckBox> getColaboradorsCheckList() {
		return colaboradorsCheckList;
	}

	public boolean isConsiderarAutoAvaliacao() {
		return considerarAutoAvaliacao;
	}

	public void setConsiderarAutoAvaliacao(boolean considerarAutoAvaliacao) {
		this.considerarAutoAvaliacao = considerarAutoAvaliacao;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public Collection<Estabelecimento> getEstabelecimentos() {
		return estabelecimentos;
	}

	public void setEstabelecimentos(Collection<Estabelecimento> estabelecimentos) {
		this.estabelecimentos = estabelecimentos;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Collection<CartaoAcompanhamentoExperienciaVO> getCartoesAcompanhamentoExperienciaVOs() {
		return cartoesAcompanhamentoExperienciaVOs;
	}

	public String[] getAvaliacaoCheck() {
		return avaliacaoCheck;
	}

	public void setAvaliacaoCheck(String[] avaliacaoCheck) {
		this.avaliacaoCheck = avaliacaoCheck;
	}

	public Collection<CheckBox> getAvaliacaoCheckList() {
		return avaliacaoCheckList;
	}

	public void setAvaliacaoCheckList(Collection<CheckBox> avaliacaoCheckList) {
		this.avaliacaoCheckList = avaliacaoCheckList;
	}

	public void setParametrosDoSistemaManager(
			ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public boolean isCompartilharColaboradores() {
		return compartilharColaboradores;
	}

	public void setCompartilharColaboradores(boolean compartilharColaboradores) {
		this.compartilharColaboradores = compartilharColaboradores;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}
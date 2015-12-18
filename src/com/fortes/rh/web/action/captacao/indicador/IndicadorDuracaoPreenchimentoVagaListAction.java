package com.fortes.rh.web.action.captacao.indicador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.DuracaoPreenchimentoVagaManager;
import com.fortes.rh.business.captacao.HistoricoCandidatoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.StatusSolicitacao;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class IndicadorDuracaoPreenchimentoVagaListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private Solicitacao solicitacao;
	private Collection indicadorDuracaoPreenchimentoVagas;
	private IndicadorDuracaoPreenchimentoVaga indicadorDuracaoPreenchimentoVaga;
	private SolicitacaoManager solicitacaoManager;
	private CandidatoManager candidatoManager;
	private ColaboradorManager colaboradorManager;
	private HistoricoCandidatoManager historicoCandidatoManager;

	private Date dataDe;
	private Date dataAte;
	private String[] areasCheck;
	private String[] estabelecimentosCheck;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> solicitacaosCheck = new ArrayList<CheckBox>();
	
	private Collection<FaixaSalarial> faixaSalarials;

	private String[] solicitacaosCheckIds;

	private AreaOrganizacionalManager areaOrganizacionalManager;
	private DuracaoPreenchimentoVagaManager duracaoPreenchimentoVagaManager;
	private EstabelecimentoManager estabelecimentoManager;

	private Collection<IndicadorDuracaoPreenchimentoVaga> indicador;
	private Map<String, Object> parametros = new HashMap<String, Object>();

	private String reportFilter;
	private String reportTitle;
	private int qtdCandidatosCadastrados;
	private int qtdCandidatosAtendidos;
	private int qtdVagasPreenchidas;
	private int qtdEtapasRealizadas;
	private double qtdCandidatosAtendidosPorVaga;
	private double indiceProcSeletivo;
	private boolean indicadorResumido;
	private boolean considerarContratacaoFutura;
	
	private LinkedHashMap<Character, String> tiposDataStatusAprovacaoSolicitacao;
	private char dataStatusAprovacaoSolicitacao;
	private char statusSolicitacao;

	private String grfContratadosFaixa = "";
	private String grfContratadosArea = "";
	private String grfContratadosMotivo = "";
	private String grfDivulgacaoVaga = "";

	public String painel() throws Exception
	{
		setVideoAjuda(734L);
		
		Date hoje = new Date();
		if (dataAte == null)
			dataAte = hoje;
		if (dataDe == null)
			dataDe = DateUtil.retornaDataAnteriorQtdMeses(hoje, 2, true);
		
		montaTiposDataStatusAprovacaoSolicitacao();
		Collection<Solicitacao> solicitacoes = solicitacaoManager.findByEmpresaEstabelecimentosAreas(getEmpresaSistema().getId(), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), LongUtil.arrayStringToArrayLong(areasCheck));
		
		estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		solicitacaosCheck = CheckListBoxUtil.populaCheckListBox(solicitacoes, "getId", "getDescricaoFormatada");
		
		CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);
		CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
    	CheckListBoxUtil.marcaCheckListBox(solicitacaosCheck, solicitacaosCheckIds);
    	    	
		faixaSalarials = solicitacaoManager.findQtdVagasDisponiveis(getEmpresaSistema().getId(), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(solicitacaosCheckIds), dataDe, dataAte, dataStatusAprovacaoSolicitacao);
		qtdCandidatosCadastrados = candidatoManager.findQtdCadastrados(getEmpresaSistema().getId(), dataDe, dataAte);
		qtdCandidatosAtendidos = candidatoManager.findQtdAtendidos(getEmpresaSistema().getId(), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(solicitacaosCheckIds), dataDe, dataAte);
		qtdEtapasRealizadas = historicoCandidatoManager.findQtdEtapasRealizadas(getEmpresaSistema().getId(), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(solicitacaosCheckIds), dataDe, dataAte); 
		qtdVagasPreenchidas = colaboradorManager.findQtdVagasPreenchidas(getEmpresaSistema().getId(), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(solicitacaosCheckIds), dataDe, dataAte);
		qtdCandidatosAtendidosPorVaga = (qtdVagasPreenchidas > 0) ? (double)qtdCandidatosAtendidos/qtdVagasPreenchidas : 0; 
		
		indiceProcSeletivo = colaboradorManager.calculaIndiceProcessoSeletivo(getEmpresaSistema().getId(), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), LongUtil.arrayStringToArrayLong(areasCheck), dataAte);
		
		try {
			indicadorDuracaoPreenchimentoVagas = duracaoPreenchimentoVagaManager.gerarIndicadorDuracaoPreenchimentoVagas(dataDe, dataAte,  LongUtil.arrayStringToCollectionLong(areasCheck), LongUtil.arrayStringToCollectionLong(estabelecimentosCheck), getEmpresaSistema().getId(), LongUtil.arrayStringToArrayLong(solicitacaosCheckIds), considerarContratacaoFutura);
		} catch (Exception e) {
			
		}

		Collection<DataGrafico> graficoContratadosFaixa = solicitacaoManager.findQtdContratadosPorFaixa(getEmpresaSistema().getId(), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(solicitacaosCheckIds), dataDe, dataAte);
		grfContratadosFaixa = StringUtil.toJSON(graficoContratadosFaixa, null);

		Collection<DataGrafico> graficoContratadosArea = solicitacaoManager.findQtdContratadosPorArea(getEmpresaSistema().getId(), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(solicitacaosCheckIds), dataDe, dataAte);
		grfContratadosArea = StringUtil.toJSON(graficoContratadosArea, null);

		Collection<DataGrafico> graficoContratadosMotivo = solicitacaoManager.findQtdContratadosPorMotivo(getEmpresaSistema().getId(), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(solicitacaosCheckIds), dataDe, dataAte);
		grfContratadosMotivo = StringUtil.toJSON(graficoContratadosMotivo, null);
		
		Collection<DataGrafico> graficoDivulgacaoVaga = candidatoManager.countComoFicouSabendoVagas(getEmpresaSistema().getId(), dataDe, dataAte);
		grfDivulgacaoVaga = StringUtil.toJSON(graficoDivulgacaoVaga, null);
		
		return Action.SUCCESS;
	}

	private void montaTiposDataStatusAprovacaoSolicitacao() 
	{
		tiposDataStatusAprovacaoSolicitacao = new  LinkedHashMap<Character, String>();
		tiposDataStatusAprovacaoSolicitacao.put('S', "Solicitação de Pessoal");
		tiposDataStatusAprovacaoSolicitacao.put('A', "Aprovação da Solicitação de Pessoal");
	}

	public String prepareMotivo() throws Exception
	{
		prepare();
		setVideoAjuda(787L);
		montaTiposDataStatusAprovacaoSolicitacao();
		
		return Action.SUCCESS;
	}
	
	public String prepare() throws Exception
	{
		setVideoAjuda(786L);
		
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());

		return Action.SUCCESS;
	}

	public String list() throws Exception  
	{
		Collection<Long> areasOrganizacionais = LongUtil.arrayStringToCollectionLong(areasCheck);
		Collection<Long> estabelecimentos = LongUtil.arrayStringToCollectionLong(estabelecimentosCheck);

		try
		{
			indicador = duracaoPreenchimentoVagaManager.gerarIndicadorDuracaoPreenchimentoVagas(dataDe, dataAte, areasOrganizacionais,estabelecimentos, getEmpresaSistema().getId(), null, considerarContratacaoFutura);

			reportFilter = "Período: " + DateUtil.formataDiaMesAno(dataDe) + " a " + DateUtil.formataDiaMesAno(dataAte);
			reportTitle = "Indicador de Duração para preenchimento de Vagas";

			parametros = getParametrosRelatorio(reportTitle, reportFilter);

			return Action.SUCCESS;
		}
		catch (ColecaoVaziaException e)
		{
			prepare();
			areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
			estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);

			addActionMessage(e.getMessage());

			return Action.INPUT;
		}
	}

	public String listMotivos() throws Exception 
	{
		Collection<Long> areasOrganizacionais = LongUtil.arrayStringToCollectionLong(areasCheck);
		Collection<Long> estabelecimentos = LongUtil.arrayStringToCollectionLong(estabelecimentosCheck);

		try
		{
			indicador = duracaoPreenchimentoVagaManager.gerarIndicadorMotivoPreenchimentoVagas(dataDe, dataAte, areasOrganizacionais,estabelecimentos, getEmpresaSistema().getId(), statusSolicitacao, dataStatusAprovacaoSolicitacao, indicadorResumido);
			
			StatusSolicitacao status = new StatusSolicitacao();
			reportFilter = status.get(statusSolicitacao) + " - Período: " + DateUtil.formataDiaMesAno(dataDe) + " a " + DateUtil.formataDiaMesAno(dataAte);
			
			parametros = getParametrosRelatorio("Indicador - Estatísticas de Vagas por Motivo", reportFilter);
			
			if (indicadorResumido)
				return "indicadorResumido";
			else
				return Action.SUCCESS;
		} 
		catch (ColecaoVaziaException e)
		{
			prepareMotivo();
			addActionMessage(e.getMessage());
			return Action.INPUT;
		}
	}

	private Map<String, Object> getParametrosRelatorio(String titulo, String filtro)
	{
		return RelatorioUtil.getParametrosRelatorio(titulo, getEmpresaSistema(), filtro);
	}

	public Collection getIndicadorDuracaoPreenchimentoVagas() {
		return indicadorDuracaoPreenchimentoVagas;
	}

	public IndicadorDuracaoPreenchimentoVaga getIndicadorDuracaoPreenchimentoVaga()
	{
		if(indicadorDuracaoPreenchimentoVaga == null)
			indicadorDuracaoPreenchimentoVaga = new IndicadorDuracaoPreenchimentoVaga();
		return indicadorDuracaoPreenchimentoVaga;
	}

	public void setIndicadorDuracaoPreenchimentoVaga(IndicadorDuracaoPreenchimentoVaga indicadorDuracaoPreenchimentoVaga)
	{
		this.indicadorDuracaoPreenchimentoVaga=indicadorDuracaoPreenchimentoVaga;
	}

	public void setSolicitacao(Solicitacao solicitacao)
	{
		this.solicitacao=solicitacao;
	}

	public Date getDataAte()
	{
		return dataAte;
	}

	public void setDataAte(Date dataAte)
	{
		this.dataAte = dataAte;
	}

	public Date getDataDe()
	{
		return dataDe;
	}

	public void setDataDe(Date dataDe)
	{
		this.dataDe = dataDe;
	}

	public String[] getAreasCheck()
	{
		return areasCheck;
	}

	public void setAreasCheck(String[] areasCheck)
	{
		this.areasCheck = areasCheck;
	}

	public Collection<CheckBox> getAreasCheckList()
	{
		return areasCheckList;
	}

	public void setAreasCheckList(Collection<CheckBox> areasCheckList)
	{
		this.areasCheckList = areasCheckList;
	}

	public Solicitacao getSolicitacao()
	{
		return solicitacao;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setIndicadorDuracaoPreenchimentoVagas(Collection indicadorDuracaoPreenchimentoVagas)
	{
		this.indicadorDuracaoPreenchimentoVagas = indicadorDuracaoPreenchimentoVagas;
	}

	public Collection<IndicadorDuracaoPreenchimentoVaga> getIndicador()
	{
		return indicador;
	}

	public void setIndicador(Collection<IndicadorDuracaoPreenchimentoVaga> indicador)
	{
		this.indicador = indicador;
	}

	public void setDuracaoPreenchimentoVagaManager(DuracaoPreenchimentoVagaManager duracaoPreenchimentoVagaManager)
	{
		this.duracaoPreenchimentoVagaManager = duracaoPreenchimentoVagaManager;
	}

	public Collection<CheckBox> getEstabelecimentosCheckList()
	{
		return estabelecimentosCheckList;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public String[] getEstabelecimentosCheck()
	{
		return estabelecimentosCheck;
	}

	public void setEstabelecimentosCheck(String[] estabelecimentosCheck)
	{
		this.estabelecimentosCheck = estabelecimentosCheck;
	}

	public void setEstabelecimentosCheckList(Collection<CheckBox> estabelecimentosCheckList)
	{
		this.estabelecimentosCheckList = estabelecimentosCheckList;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros)
	{
		this.parametros = parametros;
	}

	public String getReportFilter()
	{
		return reportFilter;
	}

	public String getReportTitle()
	{
		return reportTitle;
	}

	public char getStatusSolicitacao()
	{
		return statusSolicitacao;
	}

	public void setStatusSolicitacao(char statusSolicitacao)
	{
		this.statusSolicitacao = statusSolicitacao;
	}

	public Collection<FaixaSalarial> getFaixaSalarials()
	{
		return faixaSalarials;
	}

	public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager)
	{
		this.solicitacaoManager = solicitacaoManager;
	}

	public int getQtdCandidatosCadastrados()
	{
		return qtdCandidatosCadastrados;
	}

	public void setCandidatoManager(CandidatoManager candidatoManager)
	{
		this.candidatoManager = candidatoManager;
	}

	public String getGrfContratadosFaixa()
	{
		return grfContratadosFaixa;
	}

	public String getGrfContratadosArea()
	{
		return grfContratadosArea;
	}

	public String getGrfContratadosMotivo()
	{
		return grfContratadosMotivo;
	}

	public String getGrfDivulgacaoVaga()
	{
		return grfDivulgacaoVaga;
	}

	public int getQtdCandidatosAtendidos()
	{
		return qtdCandidatosAtendidos;
	}

	public int getQtdVagasPreenchidas()
	{
		return qtdVagasPreenchidas;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public double getQtdCandidatosAtendidosPorVaga()
	{
		return qtdCandidatosAtendidosPorVaga;
	}

	public double getIndiceProcSeletivo()
	{
		return indiceProcSeletivo;
	}

	public Collection<CheckBox> getSolicitacaosCheck()
	{
		return solicitacaosCheck;
	}

	public void setSolicitacaosCheck(Collection<CheckBox> solicitacaosCheck)
	{
		this.solicitacaosCheck = solicitacaosCheck;
	}

	public String[] getSolicitacaosCheckIds()
	{
		return solicitacaosCheckIds;
	}

	public void setSolicitacaosCheckIds(String[] solicitacaosCheckIds)
	{
		this.solicitacaosCheckIds = solicitacaosCheckIds;
	}

	public int getQtdEtapasRealizadas()
	{
		return qtdEtapasRealizadas;
	}

	public void setHistoricoCandidatoManager(HistoricoCandidatoManager historicoCandidatoManager)
	{
		this.historicoCandidatoManager = historicoCandidatoManager;
	}

	public void setIndicadorResumido(boolean indicadorResumido) {
		this.indicadorResumido = indicadorResumido;
	}

	public void setDataStatusAprovacaoSolicitacao(char dataStatusAprovacaoSolicitacao) {
		this.dataStatusAprovacaoSolicitacao = dataStatusAprovacaoSolicitacao;
	}

	public char getDataStatusAprovacaoSolicitacao() {
		return dataStatusAprovacaoSolicitacao;
	}

	public LinkedHashMap<Character, String> getTiposDataStatusAprovacaoSolicitacao() {
		return tiposDataStatusAprovacaoSolicitacao;
	}

	public void setConsiderarContratacaoFutura(boolean considerarContratacaoFutura) {
		this.considerarContratacaoFutura = considerarContratacaoFutura;
	}
}
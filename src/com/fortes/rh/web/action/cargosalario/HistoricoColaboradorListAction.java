package com.fortes.rh.web.action.cargosalario;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.BeanComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;

import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaColaboradorManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.FaturamentoMensalManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorOcorrenciaManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.SituacaoColaborador;
import com.fortes.rh.model.cargosalario.relatorio.RelatorioPromocoes;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoRelatorio;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.relatorio.TurnOverCollection;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.ComparatorString;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.EmpresaUtil;
import com.fortes.rh.util.ExceptionUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class HistoricoColaboradorListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	@Autowired private HistoricoColaboradorManager historicoColaboradorManager;
	@Autowired private AreaOrganizacionalManager areaOrganizacionalManager;
	@Autowired private EstabelecimentoManager estabelecimentoManager;
	@Autowired private ColaboradorManager colaboradorManager;
	@Autowired private EmpresaManager empresaManager;
	@Autowired private ColaboradorOcorrenciaManager colaboradorOcorrenciaManager;
	@Autowired private ParametrosDoSistemaManager parametrosDoSistemaManager;
	@Autowired private CargoManager cargoManager;
	@Autowired private FaturamentoMensalManager faturamentoMensalManager;
	@Autowired private ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager;

	private Collection<HistoricoColaborador> historicoColaboradors;

	private HistoricoColaborador historicoColaborador;
	private Colaborador colaborador;

	private Collection<CheckBox> ocorrenciasCheckList = new ArrayList<CheckBox>();
	private String[] ocorrenciasCheck;
	private Collection<CheckBox> empresasCheckList = new ArrayList<CheckBox>();
	private String[] empresasCheck;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentosCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] areasCheck;
	private Collection<CheckBox> cargosCheckList = new ArrayList<CheckBox>();
	private String[] cargosCheck;
	private Collection<CheckBox> vinculosCheckList = new ArrayList<CheckBox>();
	private String[] vinculosCheck;
	private Collection<CheckBox> vinculosTurnoverCheckList = new ArrayList<CheckBox>();
	private String[] vinculosTurnoverCheck;
	private Collection<CheckBox> vinculosTempoServicoCheckList = new ArrayList<CheckBox>();
	private String[] vinculosTempoServicoCheck;
	private Collection<CheckBox> areasPieChartCheckList = new ArrayList<CheckBox>();
	private String[] areasPieChartCheck;
	private Date dataBase;
	private Date dataIni;
	private Date dataFim;
	private Date dataIniDeslig;
	private Date dataFimDeslig;
	private Date dataIniTurn;
	private Date dataFimTurn;
	private String dataMesAnoIni;
	private String dataMesAnoFim;
	private Integer[] tempoServicoTurnoverIni;
	private Integer[] tempoServicoTurnoverFim;
	private Integer[] tempoServicoIni;
	private Integer[] tempoServicoFim;
	private Integer[] mesesParaMultiplicar;
	
	private String origemSituacao = "T";
	private char agruparPor; 
	private boolean imprimirDesligados; 

	private Collection<RelatorioPromocoes> dataSource = new ArrayList<RelatorioPromocoes>();
	private Collection<SituacaoColaborador> dataSourceSituacoesColaborador = new ArrayList<SituacaoColaborador>();
	private Collection<TurnOverCollection> turnOverCollections = new ArrayList<TurnOverCollection>();
	private Map<String, Object> parametros = new HashMap<String, Object>();
	private StatusRetornoAC statusRetornoAC = new StatusRetornoAC();
	private boolean integradoAC;

	private Collection<Empresa> empresas;
	private Long[] empresaIds;//repassado para o DWR
	private Long empresaId;
	private Long[] historicoColaboradorIds;
	private Empresa empresa;
	private String grfFormacaoEscolars = "";
	private String grfFaixaEtarias = "";
	private String grfSexo = "";
	private String grfEstadoCivil = "";
	private String grfDeficiencia = "";
	private String grfDesligamento = "";
	private String grfEvolucaoFolha = "";
	private String grfEvolucaoFaturamento = "";
	private String grfEvolucaoAbsenteismo = "";
	private String grfEvolucaoTurnover = "";
	private String grfPromocaoHorizontal = "";
	private String grfPromocaoVertical = "";
	private String grfPromocaoHorizontalArea = "";
	private String grfPromocaoVerticalArea = "";
	private String grfBarraPromocaoHorizontalArea = "";
	private String grfBarraPromocaoVerticalArea = "";
	private String grfColocacao = "";
	private String grfOcorrencia = "";
	private String grfProvidencia = "";
	private String grfTurnoverTempoServico = "";
	private String grfColaboradoresTempoServico = "";
	private int qtdColaborador = 0;
	private int qtdItensDesligamento = 20;
	private int qtdItensOcorrencia = 20;
	private Integer countAdmitidos;
	private Integer countDemitidos;
	private Double turnover;
	private Double valorTotalFolha = 0.0;
	private Long areaId;

	private Double percentualDissidio;
	private String grfSalarioAreas;
	private String grfPromocaoAreas;
	private String json;
	private Long[] retiraDissidioIds;
	private int mesesSemReajuste;
	private int abaMarcada = 1;

	private boolean sugerir = true;
	private Boolean compartilharColaboradores;
	private boolean aplicaDissidio;
	private AreaOrganizacional areaOrganizacioanal;
	private String reportTitle;
	private String reportFilter;
	private Character tipo = TipoRelatorio.PDF;
	private Long[] empresasPermitidas;
	private String voltar;
	
	private Collection<AreaOrganizacional> areasSelect = new ArrayList<AreaOrganizacional>();
	
	public String painelIndicadores() throws Exception
	{
		compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
		empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores , getEmpresaSistema().getId(), getUsuarioLogado().getId(), "ROLE_INFO_PAINEL_IND");
   		empresasCheckList =  CheckListBoxUtil.populaCheckListBox(empresas, "getId", "getNome", null);
		empresaIds = new CollectionUtil<Empresa>().convertCollectionToArrayIds(empresas);
		
		vinculosCheckList = CheckListBoxUtil.populaCheckListBox(new Vinculo());
		vinculosCheckList = CheckListBoxUtil.marcaCheckListBox(vinculosCheckList, vinculosCheck);
		
		vinculosTurnoverCheckList = CheckListBoxUtil.populaCheckListBox(new Vinculo());
		vinculosTurnoverCheckList = CheckListBoxUtil.marcaCheckListBox(vinculosTurnoverCheckList, vinculosTurnoverCheck);
		
		vinculosTempoServicoCheckList = CheckListBoxUtil.populaCheckListBox(new Vinculo());
		vinculosTempoServicoCheckList = CheckListBoxUtil.marcaCheckListBox(vinculosTempoServicoCheckList, vinculosTempoServicoCheck);
   		
		if(empresa == null || empresa.getId() == null)
			empresa = getEmpresaSistema();
			
		Long[] areasIds = LongUtil.arrayStringToArrayLong(areasCheck);
		Long[] cargosIds = LongUtil.arrayStringToArrayLong(cargosCheck);
		Long[] estabelecimentosIds = LongUtil.arrayStringToArrayLong(estabelecimentosCheck);
		Long[] ocorrenciasIds = LongUtil.arrayStringToArrayLong(ocorrenciasCheck);
		
		Date hoje = new Date();
		dataBase = (dataBase == null) ? hoje : dataBase;
		dataFim = (dataFim == null) ? hoje : dataFim;
		dataIni = (dataIni == null) ? DateUtil.retornaDataAnteriorQtdMeses(hoje, 12, true) : dataIni;
		dataFimDeslig = (dataFimDeslig == null) ? hoje : dataFimDeslig;
		dataIniDeslig = (dataIniDeslig == null) ? DateUtil.retornaDataAnteriorQtdMeses(hoje, 12, true) : dataIniDeslig;
		dataFimTurn = (dataFimTurn == null) ? hoje : dataFimTurn;
		dataIniTurn = (dataIniTurn == null) ? DateUtil.retornaDataAnteriorQtdMeses(hoje, 12, true) : dataIniTurn;
		if (dataMesAnoIni == null || dataMesAnoIni.equals("  /    ") || dataMesAnoIni.equals(""))
			dataMesAnoIni = DateUtil.formataMesAno(DateUtil.retornaDataAnteriorQtdMeses(hoje, 9, true));
		if (dataMesAnoFim == null || dataMesAnoFim.equals("  /    ") || dataMesAnoFim.equals(""))
			dataMesAnoFim = DateUtil.formataMesAno(DateUtil.incrementaMes(hoje, 3));

		if (tempoServicoTurnoverIni == null || tempoServicoTurnoverIni.length == 0)
		{
			tempoServicoTurnoverIni = new Integer[] { 0, 3, 5, 7, 9, 11 };
			tempoServicoTurnoverFim = new Integer[] { 2, 4, 6, 8, 10, 12 };
		}
		
		if (tempoServicoIni == null)
		{
			tempoServicoIni = new Integer[] {};
			tempoServicoFim = new Integer[] {};
			mesesParaMultiplicar = new Integer[] {};
		}
			
		Collection<Long> empresaIds = LongUtil.arrayStringToCollectionLong(empresasCheck);
		if(empresaIds.isEmpty())
		{
			empresasCheck = new String[]{Long.toString(getEmpresaSistema().getId())};
			empresaIds = Arrays.asList(getEmpresaSistema().getId());
		}
		
		Collection<Ocorrencia> ocorrencias  = colaboradorManager.getOcorrenciasByPeriodo(dataIni, dataFim, empresaIds, estabelecimentosIds, areasIds, cargosIds, qtdItensOcorrencia);
		ocorrenciasCheckList = CheckListBoxUtil.populaCheckListBox(ocorrencias, "getId", "getDescricaoComEmpresa", null);
		ocorrenciasCheckList = CheckListBoxUtil.marcaCheckListBox(ocorrenciasCheckList, ocorrenciasCheck);
		
		empresasCheckList = CheckListBoxUtil.marcaCheckListBox(empresasCheckList, empresasCheck);
		
		estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(LongUtil.collectionStringToArrayLong(empresaIds));
		estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);
		
		Collection<DataGrafico> graficoformacaoEscolars = colaboradorManager.countFormacaoEscolar(dataBase, empresaIds, estabelecimentosIds, areasIds, cargosIds, vinculosCheck);
		Collection<DataGrafico> graficofaixaEtaria = colaboradorManager.countFaixaEtaria(dataBase, empresaIds, estabelecimentosIds, areasIds, cargosIds, vinculosCheck);
		Collection<DataGrafico> graficoSexo = colaboradorManager.countSexo(dataBase, empresaIds, estabelecimentosIds, areasIds, cargosIds, vinculosCheck);
		Collection<DataGrafico> graficoEstadoCivil = colaboradorManager.countEstadoCivil(dataBase, empresaIds, estabelecimentosIds, areasIds, cargosIds, vinculosCheck);
		Collection<DataGrafico> graficoDeficiencia = colaboradorManager.countDeficiencia(dataBase, empresaIds, estabelecimentosIds, areasIds, cargosIds, vinculosCheck);
		Collection<DataGrafico> graficoColocacao = colaboradorManager.countColocacao(dataBase, empresaIds, estabelecimentosIds, areasIds, cargosIds, vinculosCheck);
		
		Collection<DataGrafico> graficoOcorrencia = colaboradorManager.countOcorrencia(dataIni, dataFim, empresaIds, estabelecimentosIds, areasIds, cargosIds, ocorrenciasIds, qtdItensOcorrencia);
		Collection<DataGrafico> graficoProvidencia = colaboradorManager.countProvidencia(dataIni, dataFim, empresaIds, estabelecimentosIds, areasIds, cargosIds, ocorrenciasIds, qtdItensOcorrencia);

		Collection<DataGrafico> graficoDesligamento = colaboradorManager.countMotivoDesligamento(dataIniDeslig, dataFimDeslig, empresaIds, estabelecimentosIds, areasIds, cargosIds, qtdItensDesligamento);
		
		Collection<DataGrafico> graficoTurnoverTempoServico = colaboradorManager.montaGraficoTurnoverTempoServico(tempoServicoTurnoverIni, tempoServicoTurnoverFim, dataIniTurn, dataFimTurn, empresaIds, LongUtil.arrayLongToCollectionLong(estabelecimentosIds), LongUtil.arrayLongToCollectionLong(areasIds), LongUtil.arrayLongToCollectionLong(cargosIds), new CollectionUtil<String>().convertArrayToCollection(vinculosTurnoverCheck));
		
		Collection<DataGrafico> graficoColaboradoresTempoServico = colaboradorManager.montaGraficoColaboradoresTempoServico(tempoServicoIni, tempoServicoFim, mesesParaMultiplicar, empresaIds, LongUtil.arrayLongToCollectionLong(estabelecimentosIds), LongUtil.arrayLongToCollectionLong(areasIds), LongUtil.arrayLongToCollectionLong(cargosIds), new CollectionUtil<String>().convertArrayToCollection(vinculosTempoServicoCheck));

		Collection<Object[]> graficoEvolucaoAbsenteismo = colaboradorOcorrenciaManager.montaGraficoAbsenteismo(dataMesAnoIni, dataMesAnoFim, empresaIds, LongUtil.arrayLongToCollectionLong(estabelecimentosIds), LongUtil.arrayLongToCollectionLong(areasIds), LongUtil.arrayLongToCollectionLong(cargosIds), getEmpresaSistema());
		grfEvolucaoAbsenteismo = StringUtil.toJSON(graficoEvolucaoAbsenteismo, null);
		
		countAdmitidos = colaboradorManager.countAdmitidosDemitidosTurnover(dataIniTurn, dataFimTurn, empresaIds, estabelecimentosIds, areasIds, cargosIds, true);
		countDemitidos = colaboradorManager.countAdmitidosDemitidosTurnover(dataIniTurn, dataFimTurn, empresaIds, estabelecimentosIds, areasIds, cargosIds, false);
		qtdColaborador = colaboradorManager.getCountAtivos(dataBase, empresaIds, estabelecimentosIds, areasIds, cargosIds);
		
		grfFormacaoEscolars = StringUtil.toJSON(graficoformacaoEscolars, null);
		grfFaixaEtarias = StringUtil.toJSON(graficofaixaEtaria, null);
		grfSexo = StringUtil.toJSON(graficoSexo, null);
		grfEstadoCivil = StringUtil.toJSON(graficoEstadoCivil, null);
		grfDeficiencia = StringUtil.toJSON(graficoDeficiencia, null);
		grfDesligamento = StringUtil.toJSON(graficoDesligamento, null);
		grfColocacao = StringUtil.toJSON(graficoColocacao, null);
		grfOcorrencia = StringUtil.toJSON(graficoOcorrencia, null);
		grfProvidencia = StringUtil.toJSON(graficoProvidencia, null);
		grfTurnoverTempoServico = StringUtil.toJSON(graficoTurnoverTempoServico, null);
		grfColaboradoresTempoServico = StringUtil.toJSON(graficoColaboradoresTempoServico, null);
		
		turnOverCollections = new ArrayList<TurnOverCollection>();
		for (Long empresaId: empresaIds) 
			turnOverCollections.add(colaboradorManager.montaTurnOver(dataIniTurn, dataFimTurn, empresaId, LongUtil.arrayLongToCollectionLong(estabelecimentosIds), LongUtil.arrayLongToCollectionLong(areasIds), LongUtil.arrayLongToCollectionLong(cargosIds), new CollectionUtil<String>().convertArrayToCollection(vinculosTurnoverCheck), 3));
		
		grfEvolucaoTurnover = colaboradorManager.montaGraficoTurnover(turnOverCollections, empresas);
		
		return Action.SUCCESS;
	}
	
	public String painelIndicadoresCS() throws Exception
	{
		if(empresa == null || empresa.getId() == null)
			empresa = getEmpresaSistema();
		
		empresaId = empresa.getId();//utilizado no ajax do ftl
		
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(empresa.getId());
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
		
		areasSelect = areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.ATIVA, null, empresa.getId());
		areasSelect = areaOrganizacionalManager.montaFamilia(areasSelect);
		CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
		areasSelect = cu1.sortCollectionStringIgnoreCase(areasSelect, "descricao");
		
		areasPieChartCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(empresa.getId());
		areasPieChartCheckList = CheckListBoxUtil.marcaCheckListBox(areasPieChartCheckList, areasPieChartCheck);
		
		compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
		empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores, getEmpresaSistema().getId(),SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_CES_PAINEL_IND");
		
		Date hoje = new Date();
		if (dataBase == null)
			dataBase = hoje;
		if (dataMesAnoIni == null || dataMesAnoIni.equals("  /    ") || dataMesAnoIni.equals(""))
			dataMesAnoIni = DateUtil.formataMesAno(DateUtil.retornaDataAnteriorQtdMeses(hoje, 9, true));
		if (dataMesAnoFim == null || dataMesAnoFim.equals("  /    ") || dataMesAnoFim.equals(""))
			dataMesAnoFim = DateUtil.formataMesAno(DateUtil.incrementaMes(hoje, 3));
		
		
		AreaOrganizacional area;
		if(areaOrganizacioanal != null && areaOrganizacioanal.getId() != null )
			area = areaOrganizacionalManager.findById(areaOrganizacioanal.getId());
		else
			area = new AreaOrganizacional();
		
		
		Collection<DataGrafico> graficoSalarioArea  = colaboradorManager.montaSalarioPorArea(dataBase, empresa.getId(), area);
		
		Long[] areasIds = LongUtil.arrayStringToArrayLong(areasCheck);
		
		Long[] areasPieChartIds = LongUtil.arrayStringToArrayLong(areasPieChartCheck);

		Collection<Object[]> graficoEvolucaoFolha   = colaboradorManager.montaGraficoEvolucaoFolha(DateUtil.criarDataMesAno(dataMesAnoIni), DateUtil.criarDataMesAno(dataMesAnoFim), empresa.getId(), areasIds);
		Collection<Object[]> graficoEvolucaoFaturamento   = faturamentoMensalManager.findByPeriodo(DateUtil.criarDataMesAno(dataMesAnoIni), DateUtil.criarDataMesAno(dataMesAnoFim), empresa.getId());
		
		grfSalarioAreas  = StringUtil.toJSON(graficoSalarioArea, null);
		grfEvolucaoFolha = StringUtil.toJSON(graficoEvolucaoFolha, null);
		grfEvolucaoFaturamento = StringUtil.toJSON(graficoEvolucaoFaturamento, null);

		valorTotalFolha  = 0.0;
		for (DataGrafico dataGrafico : graficoSalarioArea) 
			valorTotalFolha += dataGrafico.getData();

		dataIni = DateUtil.criarDataMesAno(dataMesAnoIni);
		dataFim = DateUtil.criarDataMesAno(dataMesAnoFim);
		
		calculaGraficosPromocao(areasIds, areasPieChartIds);
		
		return Action.SUCCESS;
	}

	private void calculaGraficosPromocao(Long[] areasIds, Long[] areasPieChartIds) 
	{
		Map<Character, Collection<Object[]>> graficosPromocao = historicoColaboradorManager.montaPromocoesHorizontalEVertical(dataIni, dataFim, empresa.getId(), areasIds);
		grfPromocaoHorizontal = StringUtil.toJSON(graficosPromocao.get('H'), null);
		grfPromocaoVertical = StringUtil.toJSON(graficosPromocao.get('V'), null);

		Map<Character, Collection<DataGrafico>> graficosPromocaoAreas = historicoColaboradorManager.montaPromocoesHorizontalEVerticalPorArea(dataIni, dataFim, empresa.getId(), false, areasPieChartIds);
		
		grfBarraPromocaoHorizontalArea = convertDataGraficoInObject(graficosPromocaoAreas.get('H'));
		grfBarraPromocaoVerticalArea = convertDataGraficoInObject(graficosPromocaoAreas.get('V'));

		grfPromocaoHorizontalArea = StringUtil.toJSON(removeValoresVaziosDataGrafico(graficosPromocaoAreas.get('H')), null);
		grfPromocaoVerticalArea = StringUtil.toJSON(removeValoresVaziosDataGrafico(graficosPromocaoAreas.get('V')), null);
	}

	private Collection<DataGrafico> removeValoresVaziosDataGrafico(Collection<DataGrafico> dataGraficos) 
	{
		Collection<DataGrafico>  dataGraficosRetorno = new ArrayList<DataGrafico>();
		
		if(dataGraficos != null && dataGraficos.size()>0){
			for (DataGrafico dataGrafico : dataGraficos) 
				if(dataGrafico.getData() > 0)
					dataGraficosRetorno.add(dataGrafico);
		}
		return dataGraficosRetorno;
	}

	private String convertDataGraficoInObject(Collection<DataGrafico> collectionDataGrafico) 
	{
		Collection<Object[]>  graficoPromocaoHorizontal = new ArrayList<Object[]>();
		if(collectionDataGrafico != null && collectionDataGrafico.size()>0){
			for (DataGrafico dataGrafico : collectionDataGrafico) 
				graficoPromocaoHorizontal.add(new Object[]{dataGrafico.getLabel(), dataGrafico.getData()});
		}
		
		return  StringUtil.toJSON(graficoPromocaoHorizontal, null);
	}

	public String grfSalarioAreasFilhas() throws Exception
	{
		AreaOrganizacional areaOrganizacional = areaOrganizacionalManager.findByIdProjection(areaId);
		Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findAllListAndInativas(null, null, null);
		AreaOrganizacional area = areaOrganizacionalManager.getAreaMae(areas, areaOrganizacional);
		
		Collection<DataGrafico> graficoSalarioArea  = colaboradorManager.montaSalarioPorArea(dataBase, getEmpresaSistema().getId(), area);
		json = StringUtil.toJSON(graficoSalarioArea, null);
		
		return Action.SUCCESS;
	}
	
	public String grfPromocaoHorizontalAreasFilhas() throws Exception
	{
		Map<Character, Collection<DataGrafico>> graficosPromocaoAreas = historicoColaboradorManager.montaPromocoesHorizontalEVerticalPorArea(dataIni, dataFim, empresaId, true, areaId);
		json = StringUtil.toJSON(graficosPromocaoAreas.get('H'), null);
		
		return Action.SUCCESS;
	}
	
	public String grfPromocaoVerticalAreasFilhas() throws Exception
	{
		Map<Character, Collection<DataGrafico>> graficosPromocaoAreas = historicoColaboradorManager.montaPromocoesHorizontalEVerticalPorArea(dataIni, dataFim, empresaId, true, areaId);
		json = StringUtil.toJSON(graficosPromocaoAreas.get('V'), null);
		
		return Action.SUCCESS;
	}

	public String prepareRelatorioPromocoes() throws Exception
	{
		compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
		empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores, getEmpresaSistema().getId(),SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_REL_PROMOCAO");
		
		CollectionUtil<Empresa> clu = new CollectionUtil<Empresa>();
		empresaIds = clu.convertCollectionToArrayIds(empresas);
		
		empresa = getEmpresaSistema();

		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String imprimirRelatorioPromocoes() throws Exception
	{
		if(empresa == null)
			empresa = getEmpresaSistema();
		
		Collection<RelatorioPromocoes> promocoes = historicoColaboradorManager.getPromocoes(LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), dataIni, dataFim, EmpresaUtil.empresasSelecionadas(empresa.getId(), empresasPermitidas));
		
		if (promocoes.isEmpty())
		{
			ResourceBundle bundle = ResourceBundle.getBundle("application");
			addActionMessage(bundle.getString("error.relatorio.vazio"));
			setActionMsg(bundle.getString("error.relatorio.vazio"));

			prepareRelatorioPromocoes();
			return Action.INPUT;
		}

		try
		{
			Comparator comp = new BeanComparator("estabelecimento.nome", new ComparatorString());
			Collections.sort((List) promocoes, comp);
			setDataSource(promocoes);

			String filtro = "Período: " + DateUtil.formataDiaMesAno(dataIni) + " à " + DateUtil.formataDiaMesAno(dataFim);
			parametros = RelatorioUtil.getParametrosRelatorio("Indicador de Promoções", getEmpresaSistema(), filtro);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			prepareRelatorioPromocoes();
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}
	
	public String prepareRelatorioUltimasPromocoes() throws Exception
	{
		dataBase = new Date();
		
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String imprimirRelatorioUltimasPromocoes() throws Exception
	{
		Collection<SituacaoColaborador> ultimasPromocoes = historicoColaboradorManager.getColaboradoresSemReajuste(null, null, dataBase, getEmpresaSistema().getId(), mesesSemReajuste);

		if (ultimasPromocoes.isEmpty())
		{
			ResourceBundle bundle = ResourceBundle.getBundle("application");
			addActionMessage(bundle.getString("error.relatorio.vazio"));
			setActionMsg(bundle.getString("error.relatorio.vazio"));

			prepareRelatorioPromocoes();
			return Action.INPUT;
		}

		try
		{
			Comparator<SituacaoColaborador> comp = new BeanComparator("estabelecimento.nome", new ComparatorString());
			Collections.sort((List<SituacaoColaborador>) ultimasPromocoes, comp);
			setDataSourceSituacoesColaborador(ultimasPromocoes);

			String filtro = "Data de referência: " + DateUtil.formataDiaMesAno(dataBase) + "\nHá " + mesesSemReajuste + " meses sem reajuste";
			parametros = RelatorioUtil.getParametrosRelatorio("Colaboradores Sem Reajuste Salarial", getEmpresaSistema(), filtro);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			prepareRelatorioPromocoes();
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		if (colaborador != null && colaborador.getId() != null)
		{
			colaborador = colaboradorManager.findColaboradorById(colaborador.getId());

			Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, getEmpresaSistema().getId());
			areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);
			historicoColaborador = historicoColaboradorManager.getHistoricoAtualOuFuturo(colaborador.getId());
			if(historicoColaborador != null)
				historicoColaborador.setAreaOrganizacional(areaOrganizacionalManager.getAreaOrganizacional(areaOrganizacionals, historicoColaborador.getAreaOrganizacional().getId()));

			historicoColaboradors = historicoColaboradorManager.progressaoColaborador(colaborador.getId(), getEmpresaSistema().getId());

			integradoAC = getEmpresaSistema().isAcIntegra();
			if(colaborador.isNaoIntegraAc())
				integradoAC = false;

			return Action.SUCCESS;
		}
		else
		{
			addActionError("Colaborador não selecionado.");
			return Action.INPUT;
		}
	}

	public String historicoColaboradorList() throws Exception
	{
		colaborador = colaboradorManager.findColaboradorByIdProjection(colaborador.getId());

		integradoAC = getEmpresaSistema().isAcIntegra();
		if(colaborador.isNaoIntegraAc())
			integradoAC = false;

		historicoColaboradors = historicoColaboradorManager.findByColaborador(colaborador.getId(), getEmpresaSistema().getId());

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try	{
			
			if(configuracaoNivelCompetenciaColaboradorManager.existeConfigCompetenciaParaAFaixaDestehistorico(historicoColaborador.getId())){
				addActionWarning("Este histórico não pode ser removido, pois o colaborador perderá o vínculo de sua cargo/faixa salarial com suas competências.");
				return historicoColaboradorList();
			}
			
			historicoColaboradorManager.removeHistoricoAndReajuste(historicoColaborador.getId(), colaborador.getId(), getEmpresaSistema(), true);
			addActionSuccess("Situação excluída com sucesso.");
			
		} catch (NestedRuntimeException e) { // TODO rever necessidade desse catch
			
			addActionMessage("Não foi possível excluir a situação.");
			
		} catch (IntegraACException e) {
			
			if (e.getMensagemDetalhada() != null)
				addActionError(e.getMensagemDetalhada());
			else
				addActionError(e.getMessage());
			
		} catch (Exception e) {
			
			addActionMessage(ExceptionUtil.getMensagem(e, "Não foi possível excluir a situação."));
			
		}

		return historicoColaboradorList();
	}
	
	public String prepareRelatorioSituacoes()
	{
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());
		return Action.SUCCESS;
	}
	
	public String relatorioSituacoes()
	{
		try 
		{
			historicoColaboradors = historicoColaboradorManager.montaRelatorioSituacoes(getEmpresaSistema().getId(), dataIni, dataFim, LongUtil.arrayStringToArrayLong(estabelecimentosCheck), LongUtil.arrayStringToArrayLong(areasCheck), origemSituacao, agruparPor, imprimirDesligados);
			
			String filtro = "Período : " + DateUtil.formataDiaMesAno(dataIni) + " a " + DateUtil.formataDiaMesAno(dataFim);
			String titulo = "Relatório de Situações";
			populaTituloFiltro(titulo, filtro);
			parametros = RelatorioUtil.getParametrosRelatorio(titulo, getEmpresaSistema(), filtro);
			
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			prepareRelatorioSituacoes();
			areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
			estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);

			return Action.INPUT;
		}
		catch (Exception e)
		{
			addActionError("Erro ao gerar relatório");
			e.printStackTrace();
			prepareRelatorioSituacoes();

			return Action.INPUT;
		}
		
		if (agruparPor == 'M')
			return "successAgruparPorData";
		else if (agruparPor == 'C')
			return "successAgruparPorColaborador";
		else
			return "successAgruparPorArea";
	}
	
	private void populaTituloFiltro(String titulo, String filtro) 
	{
		reportTitle = titulo;
		reportFilter = filtro;
	}
	
	public String prepareAjusteDissidio() 
	{
		if(aplicaDissidio)
		{
			historicoColaboradorManager.setMotivo(historicoColaboradorIds, "D");
			historicoColaboradorManager.setMotivo(retiraDissidioIds, "P");
			sugerir = false;			
		}

		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
		cargosCheckList = cargoManager.populaCheckBox(false, getEmpresaSistema().getId());
		CheckListBoxUtil.marcaCheckListBox(cargosCheckList, cargosCheck);
		estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());
		CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);
		historicoColaboradors = historicoColaboradorManager.findSemDissidioByDataPercentual(dataIni, dataFim, percentualDissidio, getEmpresaSistema().getId(), cargosCheck, areasCheck, estabelecimentosCheck);		
		
		if (dataIni != null && percentualDissidio != null && (historicoColaboradors == null || historicoColaboradors.isEmpty()))
			addActionMessage("Não existem colaboradores a serem listados");
		
		return Action.SUCCESS;
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

	public Date getDataFim()
	{
		return dataFim;
	}

	public void setDataFim(Date dataFim)
	{
		this.dataFim = dataFim;
	}

	public Date getDataIni()
	{
		return dataIni;
	}

	public void setDataIni(Date dataIni)
	{
		this.dataIni = dataIni;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public void setHistoricoColaboradors(Collection<HistoricoColaborador> historicoColaboradors)
	{
		this.historicoColaboradors = historicoColaboradors;
	}

	public void setHistoricoColaborador(HistoricoColaborador historicoColaborador)
	{
		this.historicoColaborador = historicoColaborador;
	}

	public Collection getHistoricoColaboradors()
	{
		return historicoColaboradors;
	}

	public HistoricoColaborador getHistoricoColaborador()
	{
		if (historicoColaborador == null)
		{
			historicoColaborador = new HistoricoColaborador();
		}
		return historicoColaborador;
	}

	public Map getParametros()
	{
		return parametros;
	}

	public void setDataSource(Collection<RelatorioPromocoes> dataSource)
	{
		this.dataSource = dataSource;
	}

	public Collection<RelatorioPromocoes> getDataSource()
	{
		return dataSource;
	}

	public String[] getEstabelecimentosCheck()
	{
		return estabelecimentosCheck;
	}

	public void setEstabelecimentosCheck(String[] estabelecimentosCheck)
	{
		this.estabelecimentosCheck = estabelecimentosCheck;
	}

	public Collection<CheckBox> getEstabelecimentosCheckList()
	{
		return estabelecimentosCheckList;
	}

	public void setEstabelecimentosCheckList(Collection<CheckBox> estabelecimentosCheckList)
	{
		this.estabelecimentosCheckList = estabelecimentosCheckList;
	}

	public StatusRetornoAC getStatusRetornoAC()
	{
		return statusRetornoAC;
	}

	public boolean isIntegradoAC()
	{
		return integradoAC;
	}

	public String getOrigemSituacao() {
		return origemSituacao;
	}

	public void setOrigemSituacao(String origemSituacao) {
		this.origemSituacao = origemSituacao;
	}

	public Long[] getEmpresaIds()
	{
		return empresaIds;
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public Collection<Empresa> getEmpresas()
	{
		return empresas;
	}

	public String getGrfFormacaoEscolars() {
		return grfFormacaoEscolars;
	}

	public String getGrfFaixaEtarias() {
		return grfFaixaEtarias;
	}

	public String getGrfSexo() {
		return grfSexo;
	}

	public String getGrfEstadoCivil() {
		return grfEstadoCivil;
	}

	public String getGrfDeficiencia() {
		return grfDeficiencia;
	}

	public String getGrfDesligamento() {
		return grfDesligamento;
	}

	public Date getDataBase() {
		return dataBase;
	}

	public void setDataBase(Date dataBase) {
		this.dataBase = dataBase;
	}

	public String getGrfColocacao() {
		return grfColocacao;
	}

	public int getQtdColaborador() {
		return qtdColaborador;
	}

	public int getQtdItensDesligamento() {
		return qtdItensDesligamento;
	}

	public void setQtdItensDesligamento(int qtdItensDesligamento) {
		this.qtdItensDesligamento = qtdItensDesligamento;
	}

	public Integer getCountAdmitidos() {
		return countAdmitidos;
	}

	public Integer getCountDemitidos() {
		return countDemitidos;
	}

	public String getTurnover() {
		NumberFormat formata = new DecimalFormat("##0.00");
		return formata.format(turnover); 
	}

	public String getGrfSalarioAreas() {
		return grfSalarioAreas;
	}
	
	public String getValorTotalFolha() {
		DecimalFormat formata = (DecimalFormat) DecimalFormat.getInstance(new Locale("pt", "BR"));
		formata.applyPattern("###,##0.00");
		return "R$ " + formata.format(valorTotalFolha);
	}
	
	public String getGrfEvolucaoFolha() {
		return grfEvolucaoFolha;
	}

	public String getDataMesAnoIni() {
		return dataMesAnoIni;
	}

	public void setDataMesAnoIni(String dataMesAnoIni) {
		this.dataMesAnoIni = dataMesAnoIni;
	}

	public String getDataMesAnoFim() {
		return dataMesAnoFim;
	}

	public void setDataMesAnoFim(String dataMesAnoFim) {
		this.dataMesAnoFim = dataMesAnoFim;
	}

	public String getJson() {
		return json;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getGrfEvolucaoAbsenteismo() {
		return grfEvolucaoAbsenteismo;
	}

	public String getGrfEvolucaoTurnover() {
		return grfEvolucaoTurnover;
	}

	public Double getPercentualDissidio() {
		return percentualDissidio;
	}

	public void setPercentualDissidio(Double percentualDissidio) {
		this.percentualDissidio = percentualDissidio;
	}

	public void setHistoricoColaboradorIds(Long[] historicoIds) {
		this.historicoColaboradorIds = historicoIds;
	}

	public Long[] getHistoricoColaboradorIds() {
		return historicoColaboradorIds;
	}

	public boolean isSugerir() {
		return sugerir;
	}

	public void setSugerir(boolean sugerir) {
		this.sugerir = sugerir;
	}

	public void setRetiraDissidioIds(Long[] retiraDissidioIds) {
		this.retiraDissidioIds = retiraDissidioIds;
	}

	public Collection<SituacaoColaborador> getDataSourceSituacoesColaborador() {
		return dataSourceSituacoesColaborador;
	}

	public void setDataSourceSituacoesColaborador(Collection<SituacaoColaborador> dataSourceSituacoesColaborador) {
		this.dataSourceSituacoesColaborador = dataSourceSituacoesColaborador;
	}

	public String getGrfPromocaoHorizontal() {
		return grfPromocaoHorizontal;
	}

	public String getGrfPromocaoVertical() {
		return grfPromocaoVertical;
	}

	public int getMesesSemReajuste() {
		return mesesSemReajuste;
	}

	public void setMesesSemReajuste(int mesesSemReajuste) {
		this.mesesSemReajuste = mesesSemReajuste;
	}

	public Boolean getCompartilharColaboradores() {
		return compartilharColaboradores;
	}

	public Collection<CheckBox> getCargosCheckList() {
		return cargosCheckList;
	}

	public void setCargosCheckList(Collection<CheckBox> cargosCheckList) {
		this.cargosCheckList = cargosCheckList;
	}

	public String[] getCargosCheck() {
		return cargosCheck;
	}

	public void setCargosCheck(String[] cargosCheck) {
		this.cargosCheck = cargosCheck;
	}

	public void setCargoManager(CargoManager cargoManager) {
		this.cargoManager = cargoManager;
	}

	public void setAplicaDissidio(boolean aplicaDissidio) {
		this.aplicaDissidio = aplicaDissidio;
	}

	public String getGrfEvolucaoFaturamento() {
		return grfEvolucaoFaturamento;
	}

	public char getAgruparPor() {
		return agruparPor;
	}

	public void setAgruparPor(char agruparPor) {
		this.agruparPor = agruparPor;
	}

	public boolean isImprimirDesligados() {
		return imprimirDesligados;
	}

	public void setImprimirDesligados(boolean imprimirDesligados) {
		this.imprimirDesligados = imprimirDesligados;
	}

	public Collection<CheckBox> getEmpresasCheckList() {
		return empresasCheckList;
	}

	public void setEmpresasCheck(String[] empresasCheck) {
		this.empresasCheck = empresasCheck;
	}

	public String getGrfOcorrencia() {
		return grfOcorrencia;
	}

	public String getGrfProvidencia() {
		return grfProvidencia;
	}

	public Collection<CheckBox> getVinculosCheckList() {
		return vinculosCheckList;
	}

	public void setVinculosCheckList(Collection<CheckBox> vinculosCheckList) {
		this.vinculosCheckList = vinculosCheckList;
	}
	
	public Collection<CheckBox> getVinculosTurnoverCheckList() {
		return vinculosTurnoverCheckList;
	}
	
	public void setVinculosTurnoverCheckList(Collection<CheckBox> vinculosTurnoverCheckList) {
		this.vinculosTurnoverCheckList = vinculosTurnoverCheckList;
	}
	
	public Collection<CheckBox> getVinculosTempoServicoCheckList() {
		return vinculosTempoServicoCheckList;
	}

	public void setVinculosTempoServicoCheckList(
			Collection<CheckBox> vinculosTempoServicoCheckList) {
		this.vinculosTempoServicoCheckList = vinculosTempoServicoCheckList;
	}

	public String[] getVinculosCheck() {
		return vinculosCheck;
	}

	public void setVinculosCheck(String[] vinculosCheck) {
		this.vinculosCheck = vinculosCheck;
	}
	
	public String[] getVinculosTurnoverCheck() {
		return vinculosTurnoverCheck;
	}

	public void setVinculosTurnoverCheck(String[] vinculosTurnoverCheck) {
		this.vinculosTurnoverCheck = vinculosTurnoverCheck;
	}

	public String[] getVinculosTempoServicoCheck() {
		return vinculosTempoServicoCheck;
	}

	public void setVinculosTempoServicoCheck(String[] vinculosTempoServicoCheck) {
		this.vinculosTempoServicoCheck = vinculosTempoServicoCheck;
	}

	public Date getDataIniTurn() {
		return dataIniTurn;
	}

	public void setDataIniTurn(Date dataIniTurn) {
		this.dataIniTurn = dataIniTurn;
	}

	public Date getDataFimTurn() {
		return dataFimTurn;
	}

	public void setDataFimTurn(Date dataFimTurn) {
		this.dataFimTurn = dataFimTurn;
	}

	public int getQtdItensOcorrencia() {
		return qtdItensOcorrencia;
	}

	public void setQtdItensOcorrencia(int qtdItensOcorrencia) {
		this.qtdItensOcorrencia = qtdItensOcorrencia;
	}

	public Date getDataIniDeslig() {
		return dataIniDeslig;
	}

	public void setDataIniDeslig(Date dataIniDeslig) {
		this.dataIniDeslig = dataIniDeslig;
	}

	public Date getDataFimDeslig() {
		return dataFimDeslig;
	}

	public void setDataFimDeslig(Date dataFimDeslig) {
		this.dataFimDeslig = dataFimDeslig;
	}

	public int getAbaMarcada() {
		return abaMarcada;
	}

	public void setAbaMarcada(int abaMarcada) {
		this.abaMarcada = abaMarcada;
	}

	public Integer[] getTempoServicoIni() {
		return tempoServicoIni;
	}

	public void setTempoServicoIni(Integer[] tempoServicoIni) {
		this.tempoServicoIni = tempoServicoIni;
	}

	public Integer[] getTempoServicoFim() {
		return tempoServicoFim;
	}

	public void setTempoServicoFim(Integer[] tempoServicoFim) {
		this.tempoServicoFim = tempoServicoFim;
	}
	
	public Integer[] getMesesParaMultiplicar() {
		return mesesParaMultiplicar;
	}
	
	public void setMesesParaMultiplicar(Integer[] mesesParaMultiplicar) {
		this.mesesParaMultiplicar = mesesParaMultiplicar;
	}

	public Integer[] getTempoServicoTurnoverIni() {
		return tempoServicoTurnoverIni;
	}

	public void setTempoServicoTurnoverIni(Integer[] tempoServicoTurnoverIni) {
		this.tempoServicoTurnoverIni = tempoServicoTurnoverIni;
	}

	public Integer[] getTempoServicoTurnoverFim() {
		return tempoServicoTurnoverFim;
	}

	public void setTempoServicoTurnoverFim(Integer[] tempoServicoTurnoverFim) {
		this.tempoServicoTurnoverFim = tempoServicoTurnoverFim;
	}

	public String getGrfTurnoverTempoServico() {
		return grfTurnoverTempoServico;
	}
	
	public String getGrfColaboradoresTempoServico() {
		return grfColaboradoresTempoServico;
	}

	public Collection<TurnOverCollection> getTurnOverCollections() {
		return turnOverCollections;
	}

	public void setAreaOrganizacioanal(AreaOrganizacional areaOrganizacioanal) {
		this.areaOrganizacioanal = areaOrganizacioanal;
	}

	public AreaOrganizacional getAreaOrganizacioanal() {
		return areaOrganizacioanal;
	}
	
	public String getReportTitle() {
		return reportTitle;
	}

	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}
	
	public String getReportFilter() {
		return reportFilter;
	}

	public void setReportFilter(String reportFilter) {
		this.reportFilter = reportFilter;
	}

	public Character getTipo() {
		return tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}
	
	public void setEmpresasPermitidas(Long[] empresasPermitidas) {
		this.empresasPermitidas = empresasPermitidas;
	}

	public Collection<CheckBox> getOcorrenciasCheckList() {
		return ocorrenciasCheckList;
	}

	public void setOcorrenciasCheckList(Collection<CheckBox> ocorrenciasCheckList) {
		this.ocorrenciasCheckList = ocorrenciasCheckList;
	}

	public String[] getOcorrenciasCheck() {
		return ocorrenciasCheck;
	}

	public void setOcorrenciasCheck(String[] ocorrenciasCheck) {
		this.ocorrenciasCheck = ocorrenciasCheck;
	}

	public void setAreasPieChartCheckList(
			Collection<CheckBox> areasPieChartCheckList) {
		this.areasPieChartCheckList = areasPieChartCheckList;
	}

	public void setAreasPieChartCheck(String[] areasPieChartCheck) {
		this.areasPieChartCheck = areasPieChartCheck;
	}

	public Collection<CheckBox> getAreasPieChartCheckList() {
		return areasPieChartCheckList;
	}

	public String[] getAreasPieChartCheck() {
		return areasPieChartCheck;
	}

	public String getGrfPromocaoAreas() {
		return grfPromocaoAreas;
	}

	public String getGrfPromocaoHorizontalArea() {
		return grfPromocaoHorizontalArea;
	}

	public String getGrfPromocaoVerticalArea() {
		return grfPromocaoVerticalArea;
	}

	public String getGrfBarraPromocaoHorizontalArea() {
		return grfBarraPromocaoHorizontalArea;
	}

	public String getGrfBarraPromocaoVerticalArea() {
		return grfBarraPromocaoVerticalArea;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public String getVoltar() {
		return voltar;
	}

	public void setVoltar(String voltar) {
		this.voltar = voltar;
	}

	public Collection<AreaOrganizacional> getAreasSelect() {
		return areasSelect;
	}
}
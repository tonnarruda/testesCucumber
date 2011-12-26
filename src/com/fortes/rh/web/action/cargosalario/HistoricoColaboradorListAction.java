package com.fortes.rh.web.action.cargosalario;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
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
import org.springframework.core.NestedRuntimeException;

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
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.relatorio.TurnOver;
import com.fortes.rh.model.geral.relatorio.TurnOverCollection;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.ComparatorString;
import com.fortes.rh.util.DateUtil;
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
	
	private HistoricoColaboradorManager historicoColaboradorManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private ColaboradorManager colaboradorManager;
	private EmpresaManager empresaManager;
	private ColaboradorOcorrenciaManager colaboradorOcorrenciaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private CargoManager cargoManager;
	private FaturamentoMensalManager faturamentoMensalManager;

	private Collection<HistoricoColaborador> historicoColaboradors;

	private HistoricoColaborador historicoColaborador;
	private Colaborador colaborador;

	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentosCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] areasCheck;
	private Collection<CheckBox> cargosCheckList = new ArrayList<CheckBox>();
	private String[] cargosCheck;
	private Date dataBase;
	private Date dataIni;
	private Date dataFim;
	private String dataMesAnoIni;
	private String dataMesAnoFim;
	
	private String origemSituacao = "T";
	
	private Collection<RelatorioPromocoes> dataSource = new ArrayList<RelatorioPromocoes>();
	private Collection<SituacaoColaborador> dataSourceSituacoesColaborador = new ArrayList<SituacaoColaborador>();
	private Map<String, Object> parametros = new HashMap<String, Object>();
	private StatusRetornoAC statusRetornoAC = new StatusRetornoAC();
	private boolean integradoAC;

	private Collection<Empresa> empresas;
	private Long[] empresaIds;//repassado para o DWR
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
	private int qtdColaborador = 0;
	private int qtdItensDesligamento = 20;
	private String grfColocacao = "";
	private Integer countAdmitidos;
	private Integer countDemitidos;
	private Double turnover;
	private Double valorTotalFolha = 0.0;
	private Long areaId;

	private Double percentualDissidio;
	private String grfSalarioAreas;
	private String json;
	private Long[] retiraDissidioIds;
	private int mesesSemReajuste;

	private boolean sugerir = true;
	private Boolean compartilharColaboradores;
	private boolean aplicaDissidio;
	
	public String painelIndicadores() throws Exception
	{
		if(empresa == null || empresa.getId() == null)
			empresa = getEmpresaSistema();
			
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(empresa.getId());
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
		
		Long[] areasIds = LongUtil.arrayStringToArrayLong(areasCheck);
		
		compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
		empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores, getEmpresaSistema().getId(),SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_INFO_PAINEL_IND");
		
		Date hoje = new Date();
		if (dataBase == null)
			dataBase = hoje;
		if (dataFim == null)
			dataFim = hoje;
		if (dataIni == null)
			dataIni = DateUtil.retornaDataAnteriorQtdMeses(hoje, 12, true);
		if (dataMesAnoIni == null || dataMesAnoIni.equals("  /    ") || dataMesAnoIni.equals(""))
			dataMesAnoIni = DateUtil.formataMesAno(DateUtil.retornaDataAnteriorQtdMeses(hoje, 9, true));
		if (dataMesAnoFim == null || dataMesAnoFim.equals("  /    ") || dataMesAnoFim.equals(""))
			dataMesAnoFim = DateUtil.formataMesAno(DateUtil.incrementaMes(hoje, 3));
		
		Collection<DataGrafico> graficoformacaoEscolars = colaboradorManager.countFormacaoEscolar(dataBase, empresa.getId(), areasIds);
		Collection<DataGrafico> graficofaixaEtaria = colaboradorManager.countFaixaEtaria(dataBase, empresa.getId(), areasIds);
		Collection<DataGrafico> graficoSexo = colaboradorManager.countSexo(dataBase, empresa.getId(), areasIds);
		Collection<DataGrafico> graficoEstadoCivil = colaboradorManager.countEstadoCivil(dataBase, empresa.getId(), areasIds);
		Collection<DataGrafico> graficoDesligamento = colaboradorManager.countMotivoDesligamento(dataIni, dataFim, empresa.getId(), qtdItensDesligamento, areasIds);
		Collection<DataGrafico> graficoDeficiencia = colaboradorManager.countDeficiencia(dataBase, empresa.getId(), areasIds);
		Collection<DataGrafico> graficoColocacao = colaboradorManager.countColocacao(dataBase, empresa.getId(), areasIds);
		
		Collection<Object[]> graficoEvolucaoAbsenteismo = colaboradorOcorrenciaManager.montaGraficoAbsenteismo(dataMesAnoIni, dataMesAnoFim, empresa.getId(), LongUtil.arrayLongToCollectionLong(areasIds));
		grfEvolucaoAbsenteismo = StringUtil.toJSON(graficoEvolucaoAbsenteismo, null);
		
		countAdmitidos = colaboradorManager.countAdmitidosDemitidosTurnover(dataIni, dataFim, empresa, areasIds, true);
		countDemitidos = colaboradorManager.countAdmitidosDemitidosTurnover(dataIni, dataFim, empresa, areasIds, false);
		qtdColaborador = colaboradorManager.getCountAtivos(dataBase, empresa.getId(), areasIds);
		
		grfFormacaoEscolars = StringUtil.toJSON(graficoformacaoEscolars, null);
		grfFaixaEtarias = StringUtil.toJSON(graficofaixaEtaria, null);
		grfSexo = StringUtil.toJSON(graficoSexo, null);
		grfEstadoCivil = StringUtil.toJSON(graficoEstadoCivil, null);
		grfDeficiencia = StringUtil.toJSON(graficoDeficiencia, null);
		grfDesligamento = StringUtil.toJSON(graficoDesligamento, null);
		grfColocacao  = StringUtil.toJSON(graficoColocacao, null);
		
		TurnOverCollection turnOverCollection = new TurnOverCollection();
		Collection<TurnOver> turnOvers = colaboradorManager.montaTurnOver(dataIni, dataFim, empresa, null, LongUtil.arrayLongToCollectionLong(areasIds), null, 1);
		turnOverCollection.setTurnOvers(turnOvers);
		turnover = turnOverCollection.getMedia();
		
		Collection<Object[]> graficoEvolucaoTurnover = colaboradorManager.montaGraficoTurnover(turnOvers);
		grfEvolucaoTurnover = StringUtil.toJSON(graficoEvolucaoTurnover, null);
		
		return Action.SUCCESS;
	}
	
	public String painelIndicadoresCS() throws Exception
	{
		if(empresa == null || empresa.getId() == null)
			empresa = getEmpresaSistema();
			
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(empresa.getId());
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
		
		compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
		empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores, getEmpresaSistema().getId(),SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_INFO_PAINEL_IND");
		
		Date hoje = new Date();
		if (dataBase == null)
			dataBase = hoje;
		if (dataMesAnoIni == null || dataMesAnoIni.equals("  /    ") || dataMesAnoIni.equals(""))
			dataMesAnoIni = DateUtil.formataMesAno(DateUtil.retornaDataAnteriorQtdMeses(hoje, 9, true));
		if (dataMesAnoFim == null || dataMesAnoFim.equals("  /    ") || dataMesAnoFim.equals(""))
			dataMesAnoFim = DateUtil.formataMesAno(DateUtil.incrementaMes(hoje, 3));
		
		Collection<DataGrafico> graficoSalarioArea  = colaboradorManager.montaSalarioPorArea(dataBase, empresa.getId(), new AreaOrganizacional());
		
		Long[] areasIds = LongUtil.arrayStringToArrayLong(areasCheck);

		/*
		 * Grafico de Evolucao Salarial
		 */
		Collection<Object[]> graficoEvolucaoFolha   = colaboradorManager.montaGraficoEvolucaoFolha(DateUtil.criarDataMesAno(dataMesAnoIni), DateUtil.criarDataMesAno(dataMesAnoFim), empresa.getId(), areasIds);
		Collection<Object[]> graficoEvolucaoFaturamento   = faturamentoMensalManager.findByPeriodo(DateUtil.criarDataMesAno(dataMesAnoIni), DateUtil.criarDataMesAno(dataMesAnoFim), empresa.getId());
		
		grfSalarioAreas  = StringUtil.toJSON(graficoSalarioArea, null);
		grfEvolucaoFolha = StringUtil.toJSON(graficoEvolucaoFolha, null);
		grfEvolucaoFaturamento = StringUtil.toJSON(graficoEvolucaoFaturamento, null);

		valorTotalFolha  = 0.0;
		for (DataGrafico dataGrafico : graficoSalarioArea) 
			valorTotalFolha += dataGrafico.getData();
		
		/*
		 * Grafico de Promocoes
		 */
		dataIni = DateUtil.criarDataMesAno(dataMesAnoIni);
		dataFim = DateUtil.criarDataMesAno(dataMesAnoFim);
		
		Map<Character, Collection<Object[]>> graficosPromocao = historicoColaboradorManager.montaPromocoesHorizontalEVertical(dataIni, dataFim, empresa.getId(), areasIds);
		
		grfPromocaoHorizontal = StringUtil.toJSON(graficosPromocao.get('H'), null);
		grfPromocaoVertical = StringUtil.toJSON(graficosPromocao.get('V'), null);
		
		return Action.SUCCESS;
	}

	public String grfSalarioAreasFilhas() throws Exception
	{
		AreaOrganizacional areaOrganizacional = areaOrganizacionalManager.findByIdProjection(areaId);
		Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findAllListAndInativa(null, null, null);
		AreaOrganizacional area = areaOrganizacionalManager.getAreaMae(areas, areaOrganizacional);
		
		Collection<DataGrafico> graficoSalarioArea  = colaboradorManager.montaSalarioPorArea(dataBase, getEmpresaSistema().getId(), area);
		json = StringUtil.toJSON(graficoSalarioArea, null);
		
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
		Collection<RelatorioPromocoes> promocoes = historicoColaboradorManager.getPromocoes(LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), dataIni, dataFim, empresa==null?getEmpresaSistema().getId():empresa.getId());

		if (promocoes.isEmpty())
		{
			ResourceBundle bundle = ResourceBundle.getBundle("application");
			addActionError(bundle.getString("error.relatorio.vazio"));
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
			addActionError(bundle.getString("error.relatorio.vazio"));
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

			Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findAllListAndInativa(getEmpresaSistema().getId(), AreaOrganizacional.TODAS, null);
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
			addActionError("Colaborador não selecionado");
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
		try
		{
			historicoColaboradorManager.removeHistoricoAndReajuste(historicoColaborador.getId(), colaborador.getId(), getEmpresaSistema());
			addActionMessage("Situação excluída com sucesso.");
		}
		catch (NestedRuntimeException e) // TODO rever necessidade desse catch
		{
			addActionError("Não foi possível excluir a Situação.");
		}
		catch (IntegraACException e)
		{
			if (e.getMensagemDetalhada() != null)
				addActionError(e.getMensagemDetalhada());
			else
				addActionError(e.getMessage());
		}
		catch (Exception e)
		{
			addActionMessage(e.getMessage());
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
			historicoColaboradors = historicoColaboradorManager.montaRelatorioSituacoes(getEmpresaSistema().getId(), dataIni, dataFim, LongUtil.arrayStringToArrayLong(estabelecimentosCheck), LongUtil.arrayStringToArrayLong(areasCheck), origemSituacao);
			
			String filtro = "Período : " + DateUtil.formataDiaMesAno(dataIni) + " a " + DateUtil.formataDiaMesAno(dataFim);
			parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Situações", getEmpresaSistema(), filtro);
			
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
		
		return Action.SUCCESS;
	}
	
	public String prepareAjusteDissidio() 
	{
		if(aplicaDissidio)
		{
			historicoColaboradorManager.setMotivo(historicoColaboradorIds, "D");
			historicoColaboradorManager.setMotivo(retiraDissidioIds, "P");
			sugerir = false;			
		}

		cargosCheckList = cargoManager.populaCheckBox(getEmpresaSistema().getId());
		CheckListBoxUtil.marcaCheckListBox(cargosCheckList, cargosCheck);
		
		historicoColaboradors = historicoColaboradorManager.findSemDissidioByDataPercentual(dataBase, percentualDissidio, getEmpresaSistema().getId(), cargosCheck);		
		
		if (dataBase != null && percentualDissidio != null && (historicoColaboradors == null || historicoColaboradors.isEmpty()))
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

	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager)
	{
		this.historicoColaboradorManager = historicoColaboradorManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
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

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
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

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
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

	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
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

	public void setColaboradorOcorrenciaManager(ColaboradorOcorrenciaManager colaboradorOcorrenciaManager) {
		this.colaboradorOcorrenciaManager = colaboradorOcorrenciaManager;
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

	public void setDataSourceSituacoesColaborador(
			Collection<SituacaoColaborador> dataSourceSituacoesColaborador) {
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

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
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

	public void setFaturamentoMensalManager(FaturamentoMensalManager faturamentoMensalManager) {
		this.faturamentoMensalManager = faturamentoMensalManager;
	}
}
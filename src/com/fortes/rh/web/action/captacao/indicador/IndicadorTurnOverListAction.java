package com.fortes.rh.web.action.captacao.indicador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.relatorio.TaxaDemissaoCollection;
import com.fortes.rh.model.geral.relatorio.TurnOverCollection;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class IndicadorTurnOverListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private String dataDe;
	private String dataAte;
	private String[] areasCheck;
	private String[] estabelecimentosCheck;
	private String[] cargosCheck;
	private String[] vinculosCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> cargosCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> vinculosCheckList = new ArrayList<CheckBox>();

	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private ColaboradorManager colaboradorManager;
	private CargoManager cargoManager;
	private EmpresaManager empresaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;

	private Map<String, Object> parametros = new HashMap<String, Object>();
	
	private Collection<TurnOverCollection> dataSource;
	private Collection<TaxaDemissaoCollection> taxaDemissoesCollection;
	private Collection<Empresa> empresas;
	private Collection<Colaborador> colaboradores;
	private Empresa empresa;
	private int filtrarPor = 1;
	private Character agruparPor = 'S';
	
	private boolean agruparPorTempoServico;
	private Integer[] tempoServicoIni;
	private Integer[] tempoServicoFim;
	private String formulaTaxaDemissao = "[ ( Demitidos no perído - Demitidos por Redução de quadro ) / ( ( Ativos Início Mês + Ativos no fim do mês ) / 2 ) ] * 100";
	
	private Map<Long, String> empresasFormulas;
	private String reportFilter;
	private String reportTitle;

	@SuppressWarnings("unchecked")
	public String prepare() throws Exception
	{
		if(empresa == null || empresa.getId() == null)
			empresa = getEmpresaSistema();
		
		empresas = empresaManager.findEmpresasPermitidas(parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores(), empresa.getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_REL_TURNOVER");
		empresasFormulas = new CollectionUtil<Empresa>().convertCollectionToMap(empresaManager.findToList(new String[]{"id", "formulaTurnover"}, new String[]{"id", "formulaTurnover"}, new String[]{"nome"}), "getId", "getFormulaTurnoverDescricao"); 
		
		vinculosCheckList = CheckListBoxUtil.populaCheckListBox(new Vinculo());
		
		return Action.SUCCESS;
	}

	public String turnOver() throws Exception 
	{
		Date dataIni = DateUtil.criarDataMesAno(dataDe);
		Date dataFim = DateUtil.getUltimoDiaMes(DateUtil.criarDataMesAno(dataAte));

		if(empresa == null || empresa.getId() == null)
			empresa = getEmpresaSistema();
		
		if(DateUtil.mesesEntreDatas(dataIni, dataFim) >= 12)//imundo, tem que ser maior igual
		{
			addActionMessage("Não é permitido um período maior que 12 meses para a geração deste relatório");
			prepare();
			return Action.INPUT;
		}
		
		try 
		{
			CollectionUtil<String> cUtil = new CollectionUtil<String>();
			empresa = empresaManager.findByIdProjection(empresa.getId());//os managers/parametroRelatorio precisam da empresa com turnoverPorSolicitacao, logoUrl

			reportTitle = "Turnover (rotatividade de colaboradores)";
			reportFilter = montaFiltroRelatorio();
			
			parametros = RelatorioUtil.getParametrosRelatorio(reportTitle, empresa, reportFilter);
			parametros.put("FORMULA", empresa.getFormulaTurnover());
			parametros.put("FORMULA_DESCRICAO", empresa.getFormulaTurnoverDescricao());
			
			if(!agruparPor.equals('S'))
				filtrarPor=(agruparPor=='A'  ? 1: 2);
			
			if (agruparPorTempoServico)
			{	
				colaboradores = colaboradorManager.findDemitidosTurnoverTempoServico(tempoServicoIni, tempoServicoFim, empresa.getId(), dataIni, dataFim, LongUtil.arrayStringToCollectionLong(estabelecimentosCheck), LongUtil.arrayStringToCollectionLong(areasCheck), LongUtil.arrayStringToCollectionLong(cargosCheck), cUtil.convertArrayToCollection(vinculosCheck), filtrarPor, agruparPor);
				if(colaboradores == null || colaboradores.size() == 0)
					throw new ColecaoVaziaException("Não existem dados para o filtro informado.");
				
				return selecionaRelatorioTempoServico();
			}
			else{
				if(agruparPor=='A' || agruparPor=='C'){
					dataSource = colaboradorManager.montaTurnOverAreaOuCargo(dataIni, dataFim, empresa.getId(), LongUtil.arrayStringToCollectionLong(estabelecimentosCheck), LongUtil.arrayStringToCollectionLong(areasCheck), LongUtil.arrayStringToCollectionLong(cargosCheck), cUtil.convertArrayToCollection(vinculosCheck), filtrarPor, agruparPor);
				}
				else{
					dataSource = Arrays.asList(colaboradorManager.montaTurnOver(dataIni, dataFim, empresa.getId(), LongUtil.arrayStringToCollectionLong(estabelecimentosCheck), LongUtil.arrayStringToCollectionLong(areasCheck), LongUtil.arrayStringToCollectionLong(cargosCheck), cUtil.convertArrayToCollection(vinculosCheck), filtrarPor));
				}
				
				return selecionaRelatorioTurnOver();
			}
		
		} catch (ColecaoVaziaException e) {

			addActionMessage(e.getMessage());
			prepare();
			return Action.INPUT;
		}
	}
	
	private String selecionaRelatorioTurnOver() {
		
		String resultNome = "";
	
		if(agruparPor== 'A' || agruparPor== 'C')
			resultNome="success_turnover_agrupado_por_area_ou_cargo";
		else
			resultNome=Action.SUCCESS;
		
		return resultNome;
	}

	private String selecionaRelatorioTempoServico() {
		
		String resultNome = "";
		
		if(agruparPor== 'A')
			resultNome="success_temposervico_agrupado_por_area";
		else if(agruparPor == 'C')	
			resultNome="success_temposervico_agrupado_por_cargo";
		else
			resultNome="success_temposervico";

		return resultNome;
	}

	public String taxaDeDemissao() throws Exception 
	{
		Date dataIni = DateUtil.criarDataMesAno(dataDe);
		Date dataFim = DateUtil.getUltimoDiaMes(DateUtil.criarDataMesAno(dataAte));

		if(empresa == null || empresa.getId() == null)
			empresa = getEmpresaSistema();
		
		if(DateUtil.mesesEntreDatas(dataIni, dataFim) >= 12)
		{
			addActionMessage("Não é permitido um período maior que 12 meses para a geração deste relatório");
			prepare();
			return Action.INPUT;
		}
		
		try 
		{
			CollectionUtil<String> cUtil = new CollectionUtil<String>();
			
			String filtro = montaFiltroRelatorio();

			parametros = RelatorioUtil.getParametrosRelatorio("Taxa de Demissão", empresaManager.findByIdProjection(empresa.getId()), filtro);
			parametros.put("FORMULA", formulaTaxaDemissao);
			
			taxaDemissoesCollection = new ArrayList<TaxaDemissaoCollection>();
			taxaDemissoesCollection.add(colaboradorManager.montaTaxaDemissao(dataIni, dataFim, empresa.getId(), LongUtil.arrayStringToCollectionLong(estabelecimentosCheck), LongUtil.arrayStringToCollectionLong(areasCheck), LongUtil.arrayStringToCollectionLong(cargosCheck), cUtil.convertArrayToCollection(vinculosCheck), filtrarPor));

			return Action.SUCCESS;
		} 
		catch (ColecaoVaziaException e) {

			addActionMessage(e.getMessage());
			prepare();
			return Action.INPUT;
		}
	}

	private String montaFiltroRelatorio() {
		String filtro =  "Período: " + dataDe + " a " + dataAte;
		
		if (estabelecimentosCheck != null && estabelecimentosCheck.length > 0)
			filtro +=  "\nEstabelecimentos: " + StringUtil.subStr(estabelecimentoManager.nomeEstabelecimentos(LongUtil.arrayStringToArrayLong(estabelecimentosCheck), null), 90, "...");
		else
			filtro +=  "\nTodos os Estabelecimentos";
		
		if(agruparPor=='A'){
			filtro +=  "\nAgrupado por Área Organizacional";
		} else if(agruparPor=='C'){
			filtro +=  "\nAgrupado por Cargo";
		}else {
			if (filtrarPor == 1){
				if (areasCheck != null && areasCheck.length > 0)
					filtro +=  "\nÁreas Organizacionais: " + areaOrganizacionalManager.nomeAreas(LongUtil.arrayStringToArrayLong(areasCheck));
				else
					filtro +=  "\nTodas as Áreas Organizacionais";
			}else{
				if (cargosCheck != null && cargosCheck.length > 0)
					filtro +=  "\nCargos: " + cargoManager.nomeCargos(LongUtil.arrayStringToArrayLong(cargosCheck));
				else
					filtro +=  "\nTodos os Cargos";
			}
		}
		
		return filtro;
	}

	public String getDataAte()
	{
		return dataAte;
	}

	public void setDataAte(String dataAte)
	{
		this.dataAte = dataAte;
	}

	public String getDataDe()
	{
		return dataDe;
	}

	public void setDataDe(String dataDe)
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

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public void setParametros(HashMap<String, Object> parametros)
	{
		this.parametros = parametros;
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

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public String[] getCargosCheck()
	{
		return cargosCheck;
	}

	public void setCargosCheck(String[] cargosCheck)
	{
		this.cargosCheck = cargosCheck;
	}

	public Collection<CheckBox> getCargosCheckList()
	{
		return cargosCheckList;
	}

	public void setCargosCheckList(Collection<CheckBox> cargosCheckList)
	{
		this.cargosCheckList = cargosCheckList;
	}

	public void setCargoManager(CargoManager cargoManager)
	{
		this.cargoManager = cargoManager;
	}

	public int getFiltrarPor() {
		return filtrarPor;
	}

	public void setFiltrarPor(int filtrarPor) {
		this.filtrarPor = filtrarPor;
	}

	public Collection<Empresa> getEmpresas() {
		return empresas;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public String[] getVinculosCheck() {
		return vinculosCheck;
	}

	public void setVinculosCheck(String[] vinculosCheck) {
		this.vinculosCheck = vinculosCheck;
	}

	public Collection<CheckBox> getVinculosCheckList() {
		return vinculosCheckList;
	}

	public void setVinculosCheckList(Collection<CheckBox> vinculosCheckList) {
		this.vinculosCheckList = vinculosCheckList;
	}

	public boolean isAgruparPorTempoServico() {
		return agruparPorTempoServico;
	}

	public void setAgruparPorTempoServico(boolean agruparPorTempoServico) {
		this.agruparPorTempoServico = agruparPorTempoServico;
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

	public Collection<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public void setColaboradores(Collection<Colaborador> colaboradores) {
		this.colaboradores = colaboradores;
	}

	public Map<Long, String> getEmpresasFormulas() {
		return empresasFormulas;
	}

	public Collection<TaxaDemissaoCollection> getTaxaDemissoesCollection() {
		return taxaDemissoesCollection;
	}

	public String getFormulaTaxaDemissao() {
		return formulaTaxaDemissao;
	}

	public Character getAgruparPor() {
		return agruparPor;
	}

	public void setAgruparPor(Character agruparPor) {
		this.agruparPor = agruparPor;
	}

	public Collection<TurnOverCollection> getDataSource() {
		return dataSource;
	}

	public void setDataSource(Collection<TurnOverCollection> dataSource) {
		this.dataSource = dataSource;
	}

	public String getReportFilter() {
		return reportFilter;
	}

	public String getReportTitle() {
		return reportTitle;
	}
}
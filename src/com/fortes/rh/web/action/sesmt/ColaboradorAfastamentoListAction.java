package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AfastamentoManager;
import com.fortes.rh.business.sesmt.ColaboradorAfastamentoManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Afastamento;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.model.sesmt.relatorio.ColaboradorAfastamentoMatriz;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;


public class ColaboradorAfastamentoListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private ColaboradorAfastamentoManager colaboradorAfastamentoManager = null;
	private AfastamentoManager afastamentoManager;
	private EstabelecimentoManager estabelecimentoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;

	private String[] estabelecimentosCheck;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private String[] areasCheck; 
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] motivosCheck; 
	private Collection<CheckBox> motivosCheckList = new ArrayList<CheckBox>();
	
	private Collection<ColaboradorAfastamento> colaboradorAfastamentos = null;
	private Collection<Afastamento> afastamentos;
	private ColaboradorAfastamento colaboradorAfastamento;
	private Collection<ColaboradorAfastamentoMatriz> colaboradorAfastamentoMatrizes;
	private String matriculaBusca;
	private String nomeBusca;
	private Map<String,Object> parametros = new HashMap<String, Object>();
	
	private boolean ordenaColaboradorPorNome;
	private boolean agruparPorCid;
	private boolean agruparPorArea;
	private char afastadoPeloINSS = 'T';
	private char agruparPor;
	private char ordenarPor;
	private char totalizarDiasPor;
	private String reportFilter;
	private String reportTitle;
	
	private final char COLABORADOR = 'O';
	private final char MES = 'M';
	private final char CID = 'C';
	private final char AREA = 'A';
	
	private boolean exibirTempoServico;
	
	public String list() throws Exception
	{
		if (!validaPeriodo())
			return SUCCESS;

		setTotalSize(colaboradorAfastamentoManager.getCount(getEmpresaSistema().getId(), matriculaBusca, nomeBusca, estabelecimentosCheck, colaboradorAfastamento));
		colaboradorAfastamentos = colaboradorAfastamentoManager.findAllSelect(getPage(), getPagingSize(), getEmpresaSistema().getId(), matriculaBusca, nomeBusca, estabelecimentosCheck, null, colaboradorAfastamento, new String[]{"data","colaboradorNome"}, true, 'T');
	
		afastamentos = afastamentoManager.findAll(new String[] {"descricao"});

		return SUCCESS;
	}

	private boolean validaPeriodo()
	{
		ColaboradorAfastamento tmp = getColaboradorAfastamento();
		if (tmp.getInicio() != null && tmp.getFim() != null)
		{
			if (tmp.getFim().before(tmp.getInicio()))
			{
				addActionError("Data final anterior à data inicial do período.");
				return false;
			}
		}
		return true;
	}

	public String delete() throws Exception
	{
		colaboradorAfastamentoManager.remove(colaboradorAfastamento.getId());

		return SUCCESS;
	}

	public String prepareRelatorio() throws Exception
	{
		afastamentos = afastamentoManager.findAll(new String[] {"descricao"});

		Collection<Estabelecimento> estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		estabelecimentosCheckList = CheckListBoxUtil.populaCheckListBox(estabelecimentos, "getId", "getNome");
		estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);

		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
		return SUCCESS;
	}
	
	public String relatorioAfastamentosXls() throws Exception{
		String tempoServico = "";
		if (exibirTempoServico) {
			tempoServico = "_tempo_servico";
		}
		return relatorioAfastamentos() + tempoServico + "Xls";
	}
	
	@SuppressWarnings("static-access")
	public String relatorioAfastamentos() throws Exception
	{
		try	{
			if (!validaPeriodo())
				return INPUT;

			Collection<String> ordenarPor = new ArrayList<String>();
			
			if (agruparPor == CID)
				ordenarPor.add("cid");
			else if (agruparPor == AREA)
				ordenarPor.add("areaNome");
			else if (agruparPor == MES)
				ordenarPor.add("data");
			else if (agruparPor == COLABORADOR)
				ordenarPor.add("colaboradorNome");
			
			if(ordenaColaboradorPorNome && !ordenarPor.contains("colaboradorNome"))
				ordenarPor.add("colaboradorNome");
			else if(!ordenarPor.contains("data"))
				ordenarPor.add("data");
			
			reportTitle = "Afastamentos";
			reportFilter = getPeriodoFormatado();
			
			//cuidado com os parametros desse metodo eles são unha e carne com o relatorio gerado, os parametros são fundamentais
			colaboradorAfastamentos = colaboradorAfastamentoManager.findRelatorioAfastamentos(getEmpresaSistema().getId(), nomeBusca, estabelecimentosCheck, areasCheck, colaboradorAfastamento, new StringUtil().converteCollectionToArrayString(ordenarPor), afastadoPeloINSS);
			parametros = RelatorioUtil.getParametrosRelatorio(reportTitle, getEmpresaSistema(), reportFilter);
			
			if(agruparPor == MES)
			{
				if(ordenaColaboradorPorNome)
					return "afastamentos_por_mes_colab";
				
				return "afastamentos_por_mes_data";
			}
			else if(agruparPor == CID)
				return "afastamentos_por_cid";
			else if(agruparPor == COLABORADOR)
				return "afastamentos_por_colaborador";
			else if(agruparPor == AREA)
				return "afastamentos_por_area";
			else
				return "afastamentos";
			
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			prepareRelatorio();
			return INPUT;
		}
	}
	
	public String prepareRelatorioResumoAfastamentos() throws Exception
	{
		Collection<Estabelecimento> estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		estabelecimentosCheckList = CheckListBoxUtil.populaCheckListBox(estabelecimentos, "getId", "getNome");
		estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);
		
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);

		afastamentos = afastamentoManager.findAll(new String[] {"descricao"});
		motivosCheckList = CheckListBoxUtil.populaCheckListBox(afastamentos, "getId", "getDescricao");
		motivosCheckList = CheckListBoxUtil.marcaCheckListBox(motivosCheckList, motivosCheck);
		
		return SUCCESS;
	}
	
	public String relatorioResumoAfastamentos() throws Exception
	{
		try
		{
			if (!validaPeriodo())
			{
				prepareRelatorioResumoAfastamentos();				
				return Action.INPUT;
			}
			
			if (DateUtil.mesesEntreDatas(colaboradorAfastamento.getInicio(), colaboradorAfastamento.getFim()) >= 12)//imundo, tem que ser maior igual
			{
				prepareRelatorioResumoAfastamentos();
				addActionWarning("Não é permitido um período maior que 12 meses para a geração deste relatório");
				return Action.INPUT;
			}
			
			colaboradorAfastamentoMatrizes = colaboradorAfastamentoManager.montaMatrizResumo(getEmpresaSistema().getId(), estabelecimentosCheck, areasCheck, motivosCheck, colaboradorAfastamento, ordenarPor, totalizarDiasPor, agruparPorArea);
			
			parametros = RelatorioUtil.getParametrosRelatorio("Afastamentos", getEmpresaSistema(), getPeriodoFormatado());
			parametros.put("AGRUPAR_POR_AREA", agruparPorArea);
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			prepareRelatorioResumoAfastamentos();
			return INPUT;
		}
		catch (Exception e)
		{
			addActionError(e.getMessage());
			prepareRelatorioResumoAfastamentos();
			return INPUT;
		}

		return Action.SUCCESS;
	}

	private String getPeriodoFormatado()
	{
		String periodoFormatado = "-";
		if (colaboradorAfastamento.getInicio() != null && colaboradorAfastamento.getFim() != null)
			periodoFormatado = "Período: " + DateUtil.formataDiaMesAno(colaboradorAfastamento.getInicio()) + " - " + DateUtil.formataDiaMesAno(colaboradorAfastamento.getFim());

		return periodoFormatado;
	}

	public Collection<ColaboradorAfastamento> getColaboradorAfastamentos()
	{
		return colaboradorAfastamentos;
	}

	public ColaboradorAfastamento getColaboradorAfastamento()
	{
		if(colaboradorAfastamento == null){
			colaboradorAfastamento = new ColaboradorAfastamento();
		}
		return colaboradorAfastamento;
	}

	public void setColaboradorAfastamento(ColaboradorAfastamento colaboradorAfastamento){
		this.colaboradorAfastamento=colaboradorAfastamento;
	}

	public void setColaboradorAfastamentoManager(ColaboradorAfastamentoManager colaboradorAfastamentoManager){
		this.colaboradorAfastamentoManager=colaboradorAfastamentoManager;
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

	public Collection<Afastamento> getAfastamentos()
	{
		return afastamentos;
	}

	public void setAfastamentoManager(AfastamentoManager afastamentoManager)
	{
		this.afastamentoManager = afastamentoManager;
	}
	
	public String getMatriculaBusca() {
		return matriculaBusca;
	}

	public void setMatriculaBusca(String matriculaBusca) {
		this.matriculaBusca = matriculaBusca;
	}

	public String getNomeBusca()
	{
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca)
	{
		this.nomeBusca = nomeBusca;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public boolean isOrdenaColaboradorPorNome() {
		return ordenaColaboradorPorNome;
	}

	public void setOrdenaColaboradorPorNome(boolean ordenaColaboradorPorNome) {
		this.ordenaColaboradorPorNome = ordenaColaboradorPorNome;
	}

	public char getAfastadoPeloINSS() {
		return afastadoPeloINSS;
	}

	public void setAfastadoPeloINSS(char afastadoPeloINSS) {
		this.afastadoPeloINSS = afastadoPeloINSS;
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

	public boolean isAgruparPorCid() {
		return agruparPorCid;
	}

	public void setAgruparPorCid(boolean agruparPorCid) {
		this.agruparPorCid = agruparPorCid;
	}

	public char getAgruparPor() {
		return agruparPor;
	}

	public void setAgruparPor(char agruparPor) {
		this.agruparPor = agruparPor;
	}

	public Collection<CheckBox> getMotivosCheckList() {
		return motivosCheckList;
	}

	public void setMotivosCheck(String[] motivosCheck) {
		this.motivosCheck = motivosCheck;
	}

	public void setOrdenarPor(char ordenarPor) {
		this.ordenarPor = ordenarPor;
	}

	public void setAgruparPorArea(boolean agruparPorArea) {
		this.agruparPorArea = agruparPorArea;
	}

	public Collection<ColaboradorAfastamentoMatriz> getColaboradorAfastamentoMatrizes() {
		return colaboradorAfastamentoMatrizes;
	}

	public char getTotalizarDiasPor() {
		return totalizarDiasPor;
	}

	public void setTotalizarDiasPor(char totalizarDiasPor) {
		this.totalizarDiasPor = totalizarDiasPor;
	}

	public String getReportFilter() {
		return reportFilter;
	}

	public String getReportTitle() {
		return reportTitle;
	}

	public boolean isExibirTempoServico() {
		return exibirTempoServico;
	}

	public void setExibirTempoServico(boolean exibirTempoServico) {
		this.exibirTempoServico = exibirTempoServico;
	}
}
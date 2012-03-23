package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiManager;
import com.fortes.rh.business.sesmt.TipoEPIManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.dicionario.SituacaoSolicitacaoEpi;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;


@SuppressWarnings("serial")
public class SolicitacaoEpiListAction extends MyActionSupportList
{
	private SolicitacaoEpiManager solicitacaoEpiManager;
	private ColaboradorManager colaboradorManager;
	private EpiManager epiManager;
	private Collection<SolicitacaoEpi> solicitacaoEpis;
	private Collection<SolicitacaoEpiItem> solicitacaoEpiItems;
	private String[] epiCheck;
	private Collection<CheckBox> epiCheckList = new ArrayList<CheckBox>(); 
	private Date dataIni;
	private Date dataFim;
	private String nomeBusca;
	private String matriculaBusca;
	private char situacao = 'T';
	private Colaborador colaborador = new Colaborador();
	
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private String[] areasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentoCheck;
	private Collection<CheckBox> estabelecimentoCheckList = new ArrayList<CheckBox>();
	private String[] colaboradorCheck;
	private Collection<CheckBox> colaboradorCheckList = new ArrayList<CheckBox>();

	private String[] tipoEPICheck;
	private Collection<CheckBox> tipoEPICheckList = new ArrayList<CheckBox>();
	private TipoEPIManager tipoEPIManager;
	
	private SolicitacaoEpi solicitacaoEpi;
	private boolean entrega;

	//Relatório EPIs a vencer
	private Date vencimento;
	private char agruparPor = 'E'; // Ordenação do relatório (Epi ou Colaborador)
	private Collection<SolicitacaoEpi> dataSource;
	private Collection<SolicitacaoEpiItemEntrega> dataSourceEntrega;
	private Map<String,Object> parametros = new HashMap<String, Object>();
	private boolean exibirVencimentoCA;

	public String list() throws Exception
	{
		colaborador.setNome(nomeBusca);
		colaborador.setMatricula(matriculaBusca);

		setTotalSize(solicitacaoEpiManager.getCount(getEmpresaSistema().getId(), dataIni, dataFim, colaborador, situacao));
		solicitacaoEpis = solicitacaoEpiManager.findAllSelect(getPage(), getPagingSize(), getEmpresaSistema().getId(), dataIni, dataFim, colaborador, situacao);

		if (solicitacaoEpis == null || solicitacaoEpis.isEmpty())
			addActionMessage("Nenhuma Solicitação de EPIs a ser listada.");

		return SUCCESS;
	}

	public String imprimir() throws Exception
	{
		colaborador.setNome(nomeBusca);
		colaborador.setMatricula(matriculaBusca);
		
		solicitacaoEpis = solicitacaoEpiManager.findAllSelect(0, 0, getEmpresaSistema().getId(), dataIni, dataFim, colaborador, situacao);
		
		if (solicitacaoEpis == null || solicitacaoEpis.isEmpty())
			addActionMessage("Nenhuma Solicitação de EPIs a ser listada.");
		
		parametros = RelatorioUtil.getParametrosRelatorio("Solicitações de EPIs", getEmpresaSistema(), (dataIni != null && dataFim != null) ? "Período: " + DateUtil.formataDiaMesAno(dataIni) + " a " + DateUtil.formataDiaMesAno(dataFim) : "");
		
		return SUCCESS;
	}

	public String delete() throws Exception
	{
		if (solicitacaoEpi.getSituacaoSolicitacaoEpi() == SituacaoSolicitacaoEpi.ENTREGUE)
		{
			addActionError("A solicitação não pôde ser excluída porque já foi entregue.");
		}
		else
		{
			
			solicitacaoEpiManager.remove(solicitacaoEpi.getId());
			addActionMessage("Solicitação de EPIs excluída com sucesso.");			
		}

		return SUCCESS;
	}

	public String prepareRelatorioVencimentoEpi()
	{
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
    	estabelecimentoCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());
		tipoEPICheckList = tipoEPIManager.getByEmpresa(getEmpresaSistema().getId());
		return SUCCESS;
	}

	public String relatorioVencimentoEpi()
	{
		try
		{
			dataSource = solicitacaoEpiManager.findRelatorioVencimentoEpi(getEmpresaSistema().getId(), vencimento, agruparPor, exibirVencimentoCA, tipoEPICheck, areasCheck, estabelecimentoCheck);
			parametros = RelatorioUtil.getParametrosRelatorio("EPIs com Prazo a Vencer em " + DateUtil.formataDiaMesAno(vencimento), getEmpresaSistema(), null);
			parametros.put("DATA", vencimento);
			parametros.put("EXIBIRVENCIMENTOCA", exibirVencimentoCA); // atente p/ as condicionais dentro do Relatorio vencimentoEpi_agrupaEpi.jrxml

			switch (agruparPor)
			{
				case 'C':
					return "success_agrupar_colaborador";
				case 'E':
					return SUCCESS;
				default:
					return INPUT;
			}
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			prepareRelatorioVencimentoEpi();
			return INPUT;
		}
	}

	public String prepareRelatorioEntregaEpi()
	{
		epiCheckList = epiManager.populaCheckToEpi(getEmpresaSistema().getId());
		colaboradorCheckList = colaboradorManager.populaCheckBox(getEmpresaSistema().getId());
		return SUCCESS;
	}
	
	public String relatorioEntregaEpi()
	{
		try
		{
			dataSourceEntrega = solicitacaoEpiManager.findRelatorioEntregaEpi(getEmpresaSistema().getId(), dataIni, dataFim, epiCheck, colaboradorCheck, agruparPor);
			parametros = RelatorioUtil.getParametrosRelatorio("EPIs Entregues " + DateUtil.formataDiaMesAno(vencimento), getEmpresaSistema(), null);
			
			switch (agruparPor)
			{
				case 'C':
					return "success_agrupar_colaborador";
				case 'E':
					return SUCCESS;
				default:
					return INPUT;
			}
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			prepareRelatorioEntregaEpi();
			return INPUT;
		}
	}

	@SuppressWarnings("unchecked")
	public Collection getSolicitacaoEpis() {
		return solicitacaoEpis;
	}

	public SolicitacaoEpi getSolicitacaoEpi(){
		if(solicitacaoEpi == null){
			solicitacaoEpi = new SolicitacaoEpi();
		}
		return solicitacaoEpi;
	}

	public void setSolicitacaoEpi(SolicitacaoEpi solicitacaoEpi){
		this.solicitacaoEpi=solicitacaoEpi;
	}

	public void setSolicitacaoEpiManager(SolicitacaoEpiManager solicitacaoEpiManager){
		this.solicitacaoEpiManager=solicitacaoEpiManager;
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

	public String getMatriculaBusca()
	{
		return matriculaBusca;
	}

	public void setMatriculaBusca(String matriculaBusca)
	{
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

	public Collection<SolicitacaoEpiItem> getSolicitacaoEpiItems()
	{
		return solicitacaoEpiItems;
	}

	public Date getVenc()
	{
		return vencimento;
	}

	public void setVenc(Date venc)
	{
		this.vencimento = venc;
	}

	public Collection<SolicitacaoEpi> getDataSource()
	{
		return dataSource;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public char getAgruparPor()
	{
		return agruparPor;
	}
	public void setAgruparPor(char agruparPor)
	{
		this.agruparPor = agruparPor;
	}

	public void setEpiManager(EpiManager epiManager) {
		this.epiManager = epiManager;
	}

	public Collection<CheckBox> getEpiCheckList() {
		return epiCheckList;
	}

	public void setEpiCheckList(Collection<CheckBox> epiCheckList) {
		this.epiCheckList = epiCheckList;
	}

	public String[] getEpiCheck() {
		return epiCheck;
	}

	public void setEpiCheck(String[] epiCheck) {
		this.epiCheck = epiCheck;
	}

	public boolean isExibirVencimentoCA() {
		return exibirVencimentoCA;
	}

	public void setExibirVencimentoCA(boolean exibirVencimentoCA) {
		this.exibirVencimentoCA = exibirVencimentoCA;
	}

	public String[] getTipoEPICheck() {
		return tipoEPICheck;
	}

	public void setTipoEPICheck(String[] tipoEPICheck) {
		this.tipoEPICheck = tipoEPICheck;
	}

	public Collection<CheckBox> getTipoEPICheckList() {
		return tipoEPICheckList;
	}

	public void setTipoEPICheckList(Collection<CheckBox> tipoEPICheckList) {
		this.tipoEPICheckList = tipoEPICheckList;
	}

	public void setTipoEPIManager(TipoEPIManager tipoEPIManager) {
		this.tipoEPIManager = tipoEPIManager;
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

	public void setAreasCheckList(Collection<CheckBox> areasCheckList) {
		this.areasCheckList = areasCheckList;
	}

	public String[] getEstabelecimentoCheck() {
		return estabelecimentoCheck;
	}

	public void setEstabelecimentoCheck(String[] estabelecimentoCheck) {
		this.estabelecimentoCheck = estabelecimentoCheck;
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

	public Collection<CheckBox> getColaboradorCheckList() {
		return colaboradorCheckList;
	}



	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public void setColaboradorCheck(String[] colaboradorCheck) {
		this.colaboradorCheck = colaboradorCheck;
	}

	public String[] getColaboradorCheck() {
		return colaboradorCheck;
	}

	public boolean isEntrega() {
		return entrega;
	}

	public void setEntrega(boolean entrega) {
		this.entrega = entrega;
	}

	public char getSituacao() {
		return situacao;
	}

	public void setSituacao(char situacao) {
		this.situacao = situacao;
	}

	public Collection<SolicitacaoEpiItemEntrega> getDataSourceEntrega() {
		return dataSourceEntrega;
	}
}
package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.EpcManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.HistoricoAmbienteManager;
import com.fortes.rh.business.sesmt.RiscoAmbienteManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
import com.fortes.rh.model.sesmt.relatorio.MapaDeRisco;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class AmbienteEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private AmbienteManager ambienteManager;
	private HistoricoAmbienteManager historicoAmbienteManager;
	private EstabelecimentoManager estabelecimentoManager;
	private EpcManager epcManager;
	private RiscoManager riscoManager;
	private RiscoAmbienteManager riscoAmbienteManager;
	private EpiManager epiManager;

	private Ambiente ambiente;
	private HistoricoAmbiente historicoAmbiente;

	private Collection<HistoricoAmbiente> historicoAmbientes = new ArrayList<HistoricoAmbiente>();
	private Collection<MapaDeRisco> mapasDeRisco = new ArrayList<MapaDeRisco>();
	
	private Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
	private Collection<Estabelecimento> estabelecimentos;
	private Collection<Epc> epcs;
	private Collection<Risco> riscos;
	private Collection<RiscoAmbiente> riscosAmbientes;

	private Collection<CheckBox> epcCheckList = new ArrayList<CheckBox>();
	private String[] epcCheck;
	private String[] estabelecimentoCheck;
	private Collection<CheckBox> estabelecimentoCheckList = new ArrayList<CheckBox>();
	private String[] ambienteCheck;
	private Collection<CheckBox> ambienteCheckList = new ArrayList<CheckBox>();
	
	private String[] riscoChecks;
	private String[] epcEficazChecks;

	private Map<String, Object> parametros;
	
	private void prepare() throws Exception
	{
		if(ambiente != null && ambiente.getId() != null)
			ambiente = ambienteManager.findByIdProjection(ambiente.getId());
		
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
	}

	public String prepareInsert() throws Exception
	{
		riscosAmbientes = riscoManager.findRiscosAmbientesByEmpresa(getEmpresaSistema().getId());

		epcs = epcManager.findAllSelect(getEmpresaSistema().getId());
		epcCheckList = epcManager.populaCheckBox(getEmpresaSistema().getId());
		
		prepare();

		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();

		if(ambiente == null || ambiente.getEmpresa() == null || ambiente.getEmpresa().getId() == null || !ambiente.getEmpresa().getId().equals(getEmpresaSistema().getId()))
		{
			addActionError("O Ambiente solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
			return Action.ERROR;
		}
		
		historicoAmbientes = historicoAmbienteManager.findToList(new String[]{"id","descricao","data"}, new String[]{"id","descricao","data"}, new String[]{"ambiente.id"}, new Object[]{ambiente.getId()}, new String[]{"data desc"});
		
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		ambiente.setEmpresa(getEmpresaSistema());
		ambienteManager.saveAmbienteHistorico(ambiente, historicoAmbiente, riscoChecks, riscosAmbientes, epcCheck, getEmpresaSistema().getControlaRiscoPor());

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		String[] key = new String[]{"id","empresa.id"};
		Object[] values = new Object[]{ambiente.getId(), getEmpresaSistema().getId()};

		if(!ambienteManager.verifyExists(key, values))
		{
			addActionError("O Ambiente solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
			return Action.ERROR;
		}

		ambiente.setEmpresa(getEmpresaSistema());
		ambienteManager.update(ambiente);

		return Action.SUCCESS;
	}
	
	public String list() throws Exception
	{
		setTotalSize(ambienteManager.getCount(getEmpresaSistema().getId(), ambiente));
		ambientes = ambienteManager.findAmbientes(getPage(), getPagingSize(), getEmpresaSistema().getId(), ambiente);

		return Action.SUCCESS;
	}

	public String imprimirLista() throws Exception
	{
		ambientes = ambienteManager.findAmbientes(0, 0, getEmpresaSistema().getId(), ambiente);
		if (ambientes.isEmpty()) 
		{
			addActionMessage("Não existem dados para o filtro informado");
			list();
			return Action.INPUT;
		}
		parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Ambientes", getEmpresaSistema(),"");
		
		return Action.SUCCESS;
	}

	public String prepareRelatorioMapaDeRisco()
	{
		estabelecimentoCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());
		estabelecimentoCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentoCheckList, estabelecimentoCheck);

		ambienteCheckList = ambienteManager.populaCheckBoxByEstabelecimentos(LongUtil.arrayStringToArrayLong(estabelecimentoCheck));
		ambienteCheckList = CheckListBoxUtil.marcaCheckListBox(ambienteCheckList, ambienteCheck);

		return Action.SUCCESS;
	}

	public String imprimirRelatorioMapaDeRisco() throws Exception
	{
		try	{
			Collection<RiscoAmbiente> riscosAmbientes = new ArrayList<RiscoAmbiente>();
			for (Long ambienteId : LongUtil.arrayStringToArrayLong(ambienteCheck)) {
				riscosAmbientes = riscoAmbienteManager.findByAmbiente(ambienteId); 
				
				if (riscosAmbientes.isEmpty()) 
					continue;
				
				MapaDeRisco mapaDeRisco = new MapaDeRisco();
				mapaDeRisco.setAmbiente(ambienteManager.findByIdProjection(ambienteId));
				mapaDeRisco.setRiscoAmbientes(riscosAmbientes);
				mapaDeRisco.setEpis(epiManager.findEpisDoAmbiente(ambienteId, new Date()));
				mapaDeRisco.setQtdDeColaboradoresHomens(ambienteManager.getQtdColaboradorByAmbiente(ambienteId, new Date(), Sexo.MASCULINO));
				mapaDeRisco.setQtdDeColaboradoresMulheres(ambienteManager.getQtdColaboradorByAmbiente(ambienteId, new Date(), Sexo.FEMININO));
				mapasDeRisco.add(mapaDeRisco);
			}

			if (mapasDeRisco.isEmpty()) 
			{
				addActionMessage("Não existem riscos ou o grau dos riscos não foi definido para os ambientes selecionados.");
				prepareRelatorioMapaDeRisco();
				return Action.INPUT;
			}

			parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Ambientes", getEmpresaSistema(),"");

			return Action.SUCCESS;
		} catch (Exception e) {
			addActionError("Ocorreu uma inconsistência ao gerar o relatório.");
			e.printStackTrace();
			prepareRelatorioMapaDeRisco();
			return Action.INPUT;
		}
	}

	public String delete() throws Exception
	{
		ambienteManager.removeCascade(ambiente.getId());
		addActionMessage("Ambiente excluído com sucesso.");

		return Action.SUCCESS;
	}

	public Ambiente getAmbiente()
	{
		if(ambiente == null)
			ambiente = new Ambiente();
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente)
	{
		this.ambiente = ambiente;
	}

	public void setAmbienteManager(AmbienteManager ambienteManager)
	{
		this.ambienteManager = ambienteManager;
	}

	public HistoricoAmbiente getHistoricoAmbiente()
	{
		return historicoAmbiente;
	}

	public void setHistoricoAmbiente(HistoricoAmbiente historicoAmbiente)
	{
		this.historicoAmbiente = historicoAmbiente;
	}

	public Collection<HistoricoAmbiente> getHistoricoAmbientes()
	{
		return historicoAmbientes;
	}

	public void setHistoricoAmbientes(Collection<HistoricoAmbiente> historicoAmbientes)
	{
		this.historicoAmbientes = historicoAmbientes;
	}

	public Collection<MapaDeRisco> getMapasDeRisco() {
		return mapasDeRisco;
	}

	public void setMapasDeRisco(Collection<MapaDeRisco> mapasDeRisco) {
		this.mapasDeRisco = mapasDeRisco;
	}

	public void setHistoricoAmbienteManager(HistoricoAmbienteManager historicoAmbienteManager)
	{
		this.historicoAmbienteManager = historicoAmbienteManager;
	}

	public void setEstabelecimentoManager(
			EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public Collection<Estabelecimento> getEstabelecimentos() {
		return estabelecimentos;
	}

	public Collection<CheckBox> getEpcCheckList() {
		return epcCheckList;
	}

	public void setEpcCheckList(Collection<CheckBox> epcCheckList) {
		this.epcCheckList = epcCheckList;
	}

	public String[] getEpcCheck() {
		return epcCheck;
	}

	public void setEpcCheck(String[] epcCheck) {
		this.epcCheck = epcCheck;
	}

	public void setEpcManager(EpcManager epcManager) {
		this.epcManager = epcManager;
	}

	public Collection<Epc> getEpcs() {
		return epcs;
	}

	public Collection<Risco> getRiscos() {
		return riscos;
	}

	public void setRiscoManager(RiscoManager riscoManager) {
		this.riscoManager = riscoManager;
	}

	public String[] getRiscoChecks() {
		return riscoChecks;
	}

	public void setRiscoChecks(String[] riscoChecks) {
		this.riscoChecks = riscoChecks;
	}

	public String[] getEpcEficazChecks() {
		return epcEficazChecks;
	}

	public void setEpcEficazChecks(String[] epcEficazChecks) {
		this.epcEficazChecks = epcEficazChecks;
	}

	public Collection<Ambiente> getAmbientes() {
		return ambientes;
	}

	public Collection<RiscoAmbiente> getRiscosAmbientes() {
		return riscosAmbientes;
	}

	public void setRiscosAmbientes(Collection<RiscoAmbiente> riscosAmbientes) {
		this.riscosAmbientes = riscosAmbientes;
	}

	public String[] getEstabelecimentosCheck() {
		return estabelecimentoCheck;
	}

	public void setEstabelecimentoCheck(String[] estabelecimentoCheck) {
		this.estabelecimentoCheck = estabelecimentoCheck;
	}

	public Collection<CheckBox> getEstabelecimentoCheckList() {
		return estabelecimentoCheckList;
	}

	public void setEstabelecimentoCheckList(
			Collection<CheckBox> estabelecimentoCheckList) {
		this.estabelecimentoCheckList = estabelecimentoCheckList;
	}

	public String[] getAmbienteCheck() {
		return ambienteCheck;
	}

	public void setAmbienteCheck(String[] ambienteCheck) {
		this.ambienteCheck = ambienteCheck;
	}

	public Collection<CheckBox> getAmbienteCheckList() {
		return ambienteCheckList;
	}

	public void setAmbienteCheckList(Collection<CheckBox> ambienteCheckList) {
		this.ambienteCheckList = ambienteCheckList;
	}

	public void setRiscoAmbienteManager(RiscoAmbienteManager riscoAmbienteManager) {
		this.riscoAmbienteManager = riscoAmbienteManager;
	}
	
	public void setEpiManager(EpiManager epiManager) {
		this.epiManager = epiManager;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}
}
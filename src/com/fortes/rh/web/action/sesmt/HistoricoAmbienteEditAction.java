package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.EpcManager;
import com.fortes.rh.business.sesmt.HistoricoAmbienteManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.dicionario.LocalAmbiente;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class HistoricoAmbienteEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;

	private HistoricoAmbienteManager historicoAmbienteManager;
	private EstabelecimentoManager estabelecimentoManager;
	private EpcManager epcManager;
	private RiscoManager riscoManager;
	private AmbienteManager ambienteManager;

	private HistoricoAmbiente historicoAmbiente;
	private Ambiente ambiente;
	
	private Collection<HistoricoAmbiente> historicoAmbientes = new ArrayList<HistoricoAmbiente>();
	private Collection<RiscoAmbiente> riscosAmbientes;
	private Collection<Estabelecimento> estabelecimentos;
	
	private Collection<CheckBox> epcCheckList = new ArrayList<CheckBox>();
	private String[] epcCheck;
	
	private String[] riscoChecks;
	private String[] epcEficazChecks;
	
	private void prepare() throws Exception
	{
		riscosAmbientes = riscoManager.findRiscosAmbientesByEmpresa(getEmpresaSistema().getId());
		
		if(historicoAmbiente != null && historicoAmbiente.getId() != null)
			historicoAmbiente = historicoAmbienteManager.findById(historicoAmbiente.getId());
		
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		ambiente = ambienteManager.findByIdProjection(ambiente.getId());
		epcCheckList = CheckListBoxUtil.populaCheckListBox(epcManager.findAllSelect(getEmpresaSistema().getId()), "getId", "getDescricao", null);
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		getUltimoHistorico();
		epcCheckList = CheckListBoxUtil.marcaCheckListBox(epcCheckList, historicoAmbiente.getEpcs(), "getId");
		
		return Action.SUCCESS;
	}

	private void getUltimoHistorico() 
	{
		historicoAmbiente = historicoAmbienteManager.findUltimoHistorico(ambiente.getId());
		getHistoricoAmbiente().setId(null);
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		epcCheckList = CheckListBoxUtil.marcaCheckListBox(epcCheckList, historicoAmbiente.getEpcs(), "getId");
		
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		try {
			historicoAmbiente.setAmbiente(ambiente);
			historicoAmbienteManager.saveOrUpdate(historicoAmbiente, riscoChecks, riscosAmbientes, epcCheck);
			addActionSuccess("Histórico do ambiente cadastrado com sucesso.");
			return Action.SUCCESS;
		
		} catch (FortesException e) {
			addActionError(e.getMessage());
			prepareInsert(); 
			return Action.INPUT;
		
		} catch (Exception e) {
			addActionError("Ocorreu um erro ao gravar o histórico do ambiente.");
			prepareInsert(); 
			return Action.INPUT;
		}
	}

	public String update() throws Exception
	{
		try {
			historicoAmbienteManager.saveOrUpdate(historicoAmbiente, riscoChecks, riscosAmbientes, epcCheck);
			addActionSuccess("Histórico do ambiente atualizado com sucesso.");
			return Action.SUCCESS;
		
		} catch (FortesException e) {
			addActionError(e.getMessage());
			prepareUpdate(); 
			return Action.INPUT;
		
		} catch (Exception e) {
			addActionError("Ocorreu um erro ao atualizar o histórico do ambiente.");
			prepareUpdate(); 
			return Action.INPUT;
		}
	}
	
	public String delete() throws Exception
	{
		historicoAmbienteManager.removeCascade(historicoAmbiente.getId());
		addActionSuccess("Histórico do ambiente excluído com sucesso.");
		return Action.SUCCESS;
	}
	
	
	public String list(){
		ambiente = ambienteManager.findEntidadeComAtributosSimplesById(ambiente.getId());
		historicoAmbientes = historicoAmbienteManager.findToList(new String[]{"id","descricao","data","nomeAmbiente"}, new String[]{"id","descricao","data","nomeAmbiente"}, new String[]{"ambiente.id"}, new Object[]{ambiente.getId()}, new String[]{"data desc"});
		return Action.SUCCESS;
	}
	

	public HistoricoAmbiente getHistoricoAmbiente()
	{
		if(historicoAmbiente == null)
			historicoAmbiente = new HistoricoAmbiente();
		return historicoAmbiente;
	}

	public void setHistoricoAmbiente(HistoricoAmbiente historicoAmbiente)
	{
		this.historicoAmbiente = historicoAmbiente;
	}

	public void setHistoricoAmbienteManager(HistoricoAmbienteManager historicoAmbienteManager)
	{
		this.historicoAmbienteManager = historicoAmbienteManager;
	}

	public Ambiente getAmbiente()
	{
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente)
	{
		this.ambiente = ambiente;
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

	public void setEpcManager(EpcManager epcManager) {
		this.epcManager = epcManager;
	}

	public void setRiscoManager(RiscoManager riscoManager) {
		this.riscoManager = riscoManager;
	}

	public void setAmbienteManager(AmbienteManager ambienteManager)
	{
		this.ambienteManager = ambienteManager;
	}

	public Collection<RiscoAmbiente> getRiscosAmbientes() {
		return riscosAmbientes;
	}

	public void setRiscosAmbientes(Collection<RiscoAmbiente> riscosAmbientes) {
		this.riscosAmbientes = riscosAmbientes;
	}
	
	public Collection<HistoricoAmbiente> getHistoricoAmbientes()
	{
		return historicoAmbientes;
	}

	public void setHistoricoAmbientes(Collection<HistoricoAmbiente> historicoAmbientes)
	{
		this.historicoAmbientes = historicoAmbientes;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}
	
	public Collection<Estabelecimento> getEstabelecimentos() {
		return estabelecimentos;
	}

	public Map<Integer, String> getLocaisAmbiente() {
		return LocalAmbiente.mapLocalAmbiente();
	}
}
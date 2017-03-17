package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.EpcManager;
import com.fortes.rh.business.sesmt.HistoricoAmbienteManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.exception.FortesException;
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

	@Autowired private HistoricoAmbienteManager historicoAmbienteManager;
	@Autowired private EpcManager epcManager;
	@Autowired private RiscoManager riscoManager;
	@Autowired private AmbienteManager ambienteManager;

	private HistoricoAmbiente historicoAmbiente;
	private Ambiente ambiente;
	
	private Collection<RiscoAmbiente> riscosAmbientes;
	
	private Collection<CheckBox> epcCheckList = new ArrayList<CheckBox>();
	private String[] epcCheck;
	
	private String[] riscoChecks;
	private String[] epcEficazChecks;
	
	
	private void prepare() throws Exception
	{
		riscosAmbientes = riscoManager.findRiscosAmbientesByEmpresa(getEmpresaSistema().getId());
		
		if(historicoAmbiente != null && historicoAmbiente.getId() != null)
			historicoAmbiente = historicoAmbienteManager.findById(historicoAmbiente.getId());
		
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

//	private void populaRiscosMarcados() 
//	{
//		Collection<Risco> riscos = new ArrayList<Risco>();
//		for (RiscoAmbiente riscoAmbiente : historicoAmbiente.getRiscoAmbientes())
//		{
//			riscos.add(riscoAmbiente.getRisco());
//		}
//		
//		riscoChecks = new CollectionUtil<Risco>().convertCollectionToArrayIds(riscos);
//	}

	public String insert() throws Exception
	{
		try {
			historicoAmbiente.setAmbiente(ambiente);
			historicoAmbienteManager.save(historicoAmbiente, riscoChecks, riscosAmbientes, epcCheck);
	
			return Action.SUCCESS;
		
		} catch (FortesException e) {
			addActionError(e.getMessage());
			prepareInsert(); 
			return Action.INPUT;
		
		} catch (Exception e) {
			addActionError("Ocorreu um erro ao gravar o histórico do ambiente");
			prepareInsert(); 
			return Action.INPUT;
		}
	}

	public String update() throws Exception
	{
		try {
			historicoAmbienteManager.save(historicoAmbiente, riscoChecks, riscosAmbientes, epcCheck);
	
			return Action.SUCCESS;
		
		} catch (FortesException e) {
			addActionError(e.getMessage());
			prepareUpdate(); 
			return Action.INPUT;
		
		} catch (Exception e) {
			addActionError("Ocorreu um erro ao gravar o histórico do ambiente");
			prepareUpdate(); 
			return Action.INPUT;
		}
	}
	
	public String delete() throws Exception
	{
		historicoAmbienteManager.removeCascade(historicoAmbiente.getId());

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

	public Collection<RiscoAmbiente> getRiscosAmbientes() {
		return riscosAmbientes;
	}

	public void setRiscosAmbientes(Collection<RiscoAmbiente> riscosAmbientes) {
		this.riscosAmbientes = riscosAmbientes;
	}
}
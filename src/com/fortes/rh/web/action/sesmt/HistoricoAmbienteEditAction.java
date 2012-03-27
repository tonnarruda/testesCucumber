package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.EpcManager;
import com.fortes.rh.business.sesmt.HistoricoAmbienteManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class HistoricoAmbienteEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;

	private HistoricoAmbienteManager historicoAmbienteManager;
	private EpcManager epcManager;
	private RiscoManager riscoManager;
	private AmbienteManager ambienteManager;

	private HistoricoAmbiente historicoAmbiente;
	private Ambiente ambiente;
	
	private Collection<Risco> riscos;
	private Collection<RiscoAmbiente> riscosAmbientes;
	
	private Collection<CheckBox> epcCheckList = new ArrayList<CheckBox>();
	private String[] epcCheck;
	
	private String[] riscoChecks;
	private String[] epcEficazChecks;
	
	
	private void prepare() throws Exception
	{
		riscos = riscoManager.findAllSelect(getEmpresaSistema().getId());
		riscosAmbientes = new ArrayList<RiscoAmbiente>();
		if (riscos != null)
		{
			RiscoAmbiente riscoAmbiente;
			for (Risco risco : riscos) {
				riscoAmbiente = new RiscoAmbiente();
				riscoAmbiente.setRisco(risco);
				riscosAmbientes.add(riscoAmbiente);
			}
		}
		
		if(historicoAmbiente != null && historicoAmbiente.getId() != null)
			historicoAmbiente = historicoAmbienteManager.findById(historicoAmbiente.getId());
		
		ambiente = ambienteManager.findByIdProjection(ambiente.getId());
		epcCheckList = CheckListBoxUtil.populaCheckListBox(epcManager.findAllSelect(getEmpresaSistema().getId()), "getId", "getDescricao");
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
		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbienteManager.save(historicoAmbiente, riscoChecks, riscosAmbientes, epcCheck, getEmpresaSistema().getControlaRiscoPor());
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		historicoAmbienteManager.save(historicoAmbiente, riscoChecks, riscosAmbientes, epcCheck, getEmpresaSistema().getControlaRiscoPor());
		return Action.SUCCESS;
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

	public Collection<Risco> getRiscos() {
		return riscos;
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
}
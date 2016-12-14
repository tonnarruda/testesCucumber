package com.fortes.rh.web.action.sesmt;


import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.sesmt.FasePcmatManager;
import com.fortes.rh.business.sesmt.MedidaRiscoFasePcmatManager;
import com.fortes.rh.business.sesmt.MedidaSegurancaManager;
import com.fortes.rh.business.sesmt.RiscoFasePcmatManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.model.sesmt.FasePcmat;
import com.fortes.rh.model.sesmt.MedidaRiscoFasePcmat;
import com.fortes.rh.model.sesmt.MedidaSeguranca;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoFasePcmat;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class RiscoFasePcmatEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private RiscoManager riscoManager;
	private FasePcmatManager fasePcmatManager;
	private RiscoFasePcmatManager riscoFasePcmatManager;
	private MedidaSegurancaManager medidaSegurancaManager;
	private MedidaRiscoFasePcmatManager medidaRiscoFasePcmatManager;
	
	private FasePcmat fasePcmat;
	private RiscoFasePcmat riscoFasePcmat;
	private Long pcmatId;
	private Long ultimoPcmatId = 0L;
	
	private Collection<RiscoFasePcmat> riscosFasePcmat;
	private Collection<Risco> riscos;
	private Collection<MedidaSeguranca> medidasSeguranca;
	
	private Long[] medidasCheck;
	private Collection<CheckBox> medidasCheckList = new ArrayList<CheckBox>();
	
	private void prepare() throws Exception
	{
		riscos = riscoManager.findAllSelect(getEmpresaSistema().getId());
		
		medidasSeguranca = medidaSegurancaManager.findAllSelect(null, getEmpresaSistema().getId());
		medidasCheckList = CheckListBoxUtil.populaCheckListBox(medidasSeguranca, "getId", "getDescricao", null);
		
		if (riscoFasePcmat != null && riscoFasePcmat.getId() != null)
		{
			riscoFasePcmat = (RiscoFasePcmat) riscoFasePcmatManager.findById(riscoFasePcmat.getId());
			
			Collection<MedidaRiscoFasePcmat> medidasRiscoFasePcmat = medidaRiscoFasePcmatManager.findByRiscoFasePcmat(riscoFasePcmat.getId());
			medidasCheckList = CheckListBoxUtil.marcaCheckListBox(medidasCheckList, medidasRiscoFasePcmat, "getMedidaSegurancaId");
		}
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		try {
			riscoFasePcmatManager.saveRiscosMedidas(riscoFasePcmat, medidasCheck);
			addActionSuccess("Risco cadastrado com sucesso.");
			
		} catch (Exception e) {
			prepare();
			e.printStackTrace();
			addActionError("Não foi possível cadastrar o risco.");
			
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try {
			riscoFasePcmatManager.saveRiscosMedidas(riscoFasePcmat, medidasCheck);
			addActionSuccess("Risco atualizado com sucesso.");
			
		} catch (Exception e) {
			prepare();
			e.printStackTrace();
			addActionError("Não foi possível atualizar o risco.");
			
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		fasePcmat = fasePcmatManager.findByIdProjection(fasePcmat.getId());
		riscosFasePcmat = riscoFasePcmatManager.findByFasePcmat(fasePcmat.getId());
		
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			riscoFasePcmatManager.remove(riscoFasePcmat.getId());
			addActionSuccess("Risco excluído com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir este risco.");
		}

		return list();
	}

	public void setRiscoFasePcmatManager(RiscoFasePcmatManager riscoFasePcmatManager) {
		this.riscoFasePcmatManager = riscoFasePcmatManager;
	}

	public RiscoFasePcmat getRiscoFasePcmat() {
		return riscoFasePcmat;
	}

	public void setRiscoFasePcmat(RiscoFasePcmat riscoFasePcmat) {
		this.riscoFasePcmat = riscoFasePcmat;
	}

	public Collection<RiscoFasePcmat> getRiscosFasePcmat() {
		return riscosFasePcmat;
	}

	public void setRiscosFasePcmat(Collection<RiscoFasePcmat> riscosFasePcmat) {
		this.riscosFasePcmat = riscosFasePcmat;
	}

	public FasePcmat getFasePcmat() {
		return fasePcmat;
	}

	public void setFasePcmat(FasePcmat fasePcmat) {
		this.fasePcmat = fasePcmat;
	}

	public Collection<MedidaSeguranca> getMedidasSeguranca() {
		return medidasSeguranca;
	}

	public void setMedidaSegurancaManager(
			MedidaSegurancaManager medidaSegurancaManager) {
		this.medidaSegurancaManager = medidaSegurancaManager;
	}

	public void setMedidasCheck(Long[] medidasCheck) {
		this.medidasCheck = medidasCheck;
	}

	public Collection<CheckBox> getMedidasCheckList() {
		return medidasCheckList;
	}

	public Collection<Risco> getRiscos() {
		return riscos;
	}

	public void setRiscos(Collection<Risco> riscos) {
		this.riscos = riscos;
	}

	public void setRiscoManager(RiscoManager riscoManager) {
		this.riscoManager = riscoManager;
	}

	public void setFasePcmatManager(FasePcmatManager fasePcmatManager) {
		this.fasePcmatManager = fasePcmatManager;
	}

	public void setMedidaRiscoFasePcmatManager(
			MedidaRiscoFasePcmatManager medidaRiscoFasePcmatManager) {
		this.medidaRiscoFasePcmatManager = medidaRiscoFasePcmatManager;
	}

	public Long getUltimoPcmatId() {
		return ultimoPcmatId;
	}

	public void setUltimoPcmatId(Long ultimoPcmatId) {
		this.ultimoPcmatId = ultimoPcmatId;
	}

	public Long getPcmatId() {
		return pcmatId;
	}

	public void setPcmatId(Long pcmatId) {
		this.pcmatId = pcmatId;
	}
}

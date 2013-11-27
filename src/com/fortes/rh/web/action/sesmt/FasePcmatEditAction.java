package com.fortes.rh.web.action.sesmt;


import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.sesmt.FaseManager;
import com.fortes.rh.business.sesmt.FasePcmatManager;
import com.fortes.rh.business.sesmt.RiscoFasePcmatManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.model.sesmt.Fase;
import com.fortes.rh.model.sesmt.FasePcmat;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoFasePcmat;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class FasePcmatEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private FasePcmatManager fasePcmatManager;
	private FaseManager faseManager;
	private RiscoManager riscoManager;
	private RiscoFasePcmatManager riscoFasePcmatManager;
	
	private FasePcmat fasePcmat;
	private Pcmat pcmat;
	
	private Collection<FasePcmat> fasesPcmat;
	private Collection<Fase> fases;
	private Collection<Risco> riscos;
	
	private Long[] riscosCheck;
	private Collection<CheckBox> riscosCheckList = new ArrayList<CheckBox>();

	private void prepare() throws Exception
	{
		fases = faseManager.findAllSelect(null, getEmpresaSistema().getId());
		
		riscos = riscoManager.findAllSelect(getEmpresaSistema().getId());
		riscosCheckList = CheckListBoxUtil.populaCheckListBox(riscos, "getId", "getDescricao");
		
		if (fasePcmat != null && fasePcmat.getId() != null)
		{
			fasePcmat = (FasePcmat) fasePcmatManager.findById(fasePcmat.getId());
			
			Collection<RiscoFasePcmat> riscosFasePcmat = riscoFasePcmatManager.findByFasePcmat(fasePcmat.getId());
			riscosCheckList = CheckListBoxUtil.marcaCheckListBox(riscosCheckList, riscosFasePcmat, "getRiscoId");
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
			fasePcmatManager.saveFasePcmatRiscos(fasePcmat, riscosCheck);
			addActionSuccess("Fase cadastrada com sucesso.");
			
		} catch (Exception e) {
			prepare();
			e.printStackTrace();
			addActionError("Não foi possível cadastrar a fase.");
			
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try {
			fasePcmatManager.saveFasePcmatRiscos(fasePcmat, riscosCheck);
			addActionSuccess("Fase atualizada com sucesso.");
			
		} catch (Exception e) {
			prepare();
			e.printStackTrace();
			addActionError("Não foi possível atualizar a fase.");
			
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		fasesPcmat = fasePcmatManager.findByPcmat(pcmat.getId());
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			fasePcmatManager.remove(fasePcmat.getId());
			addActionSuccess("Fase excluída com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir esta fase.");
		}

		return list();
	}
	
	public FasePcmat getFasePcmat() {
		return fasePcmat;
	}

	public void setFasePcmat(FasePcmat fasePcmat) {
		this.fasePcmat = fasePcmat;
	}

	public Pcmat getPcmat() {
		return pcmat;
	}

	public void setPcmat(Pcmat pcmat) {
		this.pcmat = pcmat;
	}

	public Collection<FasePcmat> getFasesPcmat() {
		return fasesPcmat;
	}

	public void setFasesPcmat(Collection<FasePcmat> fasesPcmat) {
		this.fasesPcmat = fasesPcmat;
	}

	public void setFasePcmatManager(FasePcmatManager fasePcmatManager) {
		this.fasePcmatManager = fasePcmatManager;
	}

	public Collection<Fase> getFases() {
		return fases;
	}

	public void setFases(Collection<Fase> fases) {
		this.fases = fases;
	}

	public void setFaseManager(FaseManager faseManager) {
		this.faseManager = faseManager;
	}

	public Collection<CheckBox> getRiscosCheckList() {
		return riscosCheckList;
	}

	public void setRiscosCheck(Long[] riscosCheck) {
		this.riscosCheck = riscosCheck;
	}

	public void setRiscoManager(RiscoManager riscoManager) {
		this.riscoManager = riscoManager;
	}

	public void setRiscoFasePcmatManager(RiscoFasePcmatManager riscoFasePcmatManager) {
		this.riscoFasePcmatManager = riscoFasePcmatManager;
	}
}

package com.fortes.rh.web.action.sesmt;


import java.util.Collection;

import com.fortes.rh.business.sesmt.FaseManager;
import com.fortes.rh.business.sesmt.FasePcmatManager;
import com.fortes.rh.model.sesmt.Fase;
import com.fortes.rh.model.sesmt.FasePcmat;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class FasePcmatEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private FasePcmatManager fasePcmatManager;
	private FaseManager faseManager;
	
	private FasePcmat fasePcmat;
	private Pcmat pcmat;
	
	private Collection<FasePcmat> fasesPcmat;
	private Collection<Fase> fases;
	
	private void prepare() throws Exception
	{
		fases = faseManager.findAllSelect(null, getEmpresaSistema().getId());
		
		if (fasePcmat != null && fasePcmat.getId() != null)
			fasePcmat = (FasePcmat) fasePcmatManager.findById(fasePcmat.getId());
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
			fasePcmatManager.save(fasePcmat);
			
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
			fasePcmatManager.update(fasePcmat);
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
		fases = faseManager.findAllSelect(null, getEmpresaSistema().getId());
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
}

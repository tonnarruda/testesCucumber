package com.fortes.rh.web.action.sesmt;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

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
	@Autowired private FasePcmatManager fasePcmatManager;
	@Autowired private FaseManager faseManager;
	
	private FasePcmat fasePcmat;
	private Pcmat pcmat;
	private Long ultimoPcmatId = 0L;
	
	private Collection<FasePcmat> fasesPcmat;
	private Collection<Fase> fases;
	
	private void prepare() throws Exception
	{
		fases = faseManager.findAllSelect(null, getEmpresaSistema().getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		fasePcmat = (FasePcmat) fasePcmatManager.findById(fasePcmat.getId());
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		try {
			fasePcmatManager.save(fasePcmat);
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

	public Collection<Fase> getFases() {
		return fases;
	}

	public void setFases(Collection<Fase> fases) {
		this.fases = fases;
	}

	public Long getUltimoPcmatId() {
		return ultimoPcmatId;
	}

	public void setUltimoPcmatId(Long ultimoPcmatId) {
		this.ultimoPcmatId = ultimoPcmatId;
	}
}
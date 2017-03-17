package com.fortes.rh.web.action.sesmt;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.EpiPcmatManager;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiPcmat;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class EpiPcmatEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private EpiPcmatManager epiPcmatManager;
	@Autowired private EpiManager epiManager;
	
	private EpiPcmat epiPcmat;
	private Pcmat pcmat;
	private Long ultimoPcmatId = 0L;
	
	private Collection<EpiPcmat> epiPcmats;
	private Collection<Epi> epis;

	private void prepare() throws Exception
	{
		epis = epiManager.findAllSelect(getEmpresaSistema().getId());
		
		if(epiPcmat != null && epiPcmat.getId() != null)
			epiPcmat = (EpiPcmat) epiPcmatManager.findById(epiPcmat.getId());
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
			epiPcmatManager.save(epiPcmat);
			addActionSuccess("EPI cadastrado com sucesso.");
			
		} catch (Exception e) {
			prepare();
			e.printStackTrace();
			addActionError("Não foi possível cadastrar o EPI.");
			
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try {
			epiPcmatManager.update(epiPcmat);
			addActionSuccess("EPI atualizado com sucesso.");
			
		} catch (Exception e) {
			prepare();
			e.printStackTrace();
			addActionError("Não foi possível atualizar o EPI.");
			
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		epiPcmats = epiPcmatManager.findByPcmat(pcmat.getId());
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			epiPcmatManager.remove(epiPcmat.getId());
			addActionSuccess("EPI excluído com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir este EPI.");
		}

		return list();
	}
	
	public EpiPcmat getEpiPcmat()
	{
		if(epiPcmat == null)
			epiPcmat = new EpiPcmat();
		return epiPcmat;
	}

	public void setEpiPcmat(EpiPcmat epiPcmat)
	{
		this.epiPcmat = epiPcmat;
	}
	
	public Collection<EpiPcmat> getEpiPcmats()
	{
		return epiPcmats;
	}

	public Pcmat getPcmat() {
		return pcmat;
	}

	public void setPcmat(Pcmat pcmat) {
		this.pcmat = pcmat;
	}

	public Collection<Epi> getEpis() {
		return epis;
	}

	public Long getUltimoPcmatId() {
		return ultimoPcmatId;
	}

	public void setUltimoPcmatId(Long ultimoPcmatId) {
		this.ultimoPcmatId = ultimoPcmatId;
	}
}
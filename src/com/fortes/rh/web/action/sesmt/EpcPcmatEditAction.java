package com.fortes.rh.web.action.sesmt;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.EpcManager;
import com.fortes.rh.business.sesmt.EpcPcmatManager;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.model.sesmt.EpcPcmat;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class EpcPcmatEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private EpcPcmatManager epcPcmatManager;
	@Autowired private EpcManager epcManager;
	
	private EpcPcmat epcPcmat;
	private Pcmat pcmat;
	private Long ultimoPcmatId = 0L;
	
	private Collection<EpcPcmat> epcPcmats;
	private Collection<Epc> epcs;

	private void prepare() throws Exception
	{
		epcs = epcManager.findAllSelect(getEmpresaSistema().getId()); 
		
		if(epcPcmat != null && epcPcmat.getId() != null)
			epcPcmat = (EpcPcmat) epcPcmatManager.findById(epcPcmat.getId());
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
			epcPcmatManager.save(epcPcmat);
			addActionSuccess("EPC cadastrado com sucesso.");
			
		} catch (Exception e) {
			prepare();
			e.printStackTrace();
			addActionError("Não foi possível cadastrar o EPC.");
			
			return Action.INPUT;
		}
		return Action.SUCCESS;
		
		
	}

	public String update() throws Exception
	{
		try {
			epcPcmatManager.update(epcPcmat);
			addActionSuccess("EPC atualizado com sucesso.");
			
		} catch (Exception e) {
			prepare();
			e.printStackTrace();
			addActionError("Não foi possível atualizar o EPC.");
			
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		epcPcmats = epcPcmatManager.findByPcmat(pcmat.getId());
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			epcPcmatManager.remove(epcPcmat.getId());
			addActionSuccess("EPC excluído com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir este EPC.");
		}

		return list();
	}
	
	public EpcPcmat getEpcPcmat()
	{
		if(epcPcmat == null)
			epcPcmat = new EpcPcmat();
		return epcPcmat;
	}

	public void setEpcPcmat(EpcPcmat epcPcmat)
	{
		this.epcPcmat = epcPcmat;
	}
	
	public Collection<EpcPcmat> getEpcPcmats()
	{
		return epcPcmats;
	}

	public Collection<Epc> getEpcs() {
		return epcs;
	}

	public void setEpcs(Collection<Epc> epcs) {
		this.epcs = epcs;
	}

	public Pcmat getPcmat() {
		return pcmat;
	}

	public void setPcmat(Pcmat pcmat) {
		this.pcmat = pcmat;
	}

	public Long getUltimoPcmatId() {
		return ultimoPcmatId;
	}

	public void setUltimoPcmatId(Long ultimoPcmatId) {
		this.ultimoPcmatId = ultimoPcmatId;
	}
}
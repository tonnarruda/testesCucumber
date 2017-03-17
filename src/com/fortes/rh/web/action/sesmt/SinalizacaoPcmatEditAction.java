package com.fortes.rh.web.action.sesmt;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.SinalizacaoPcmatManager;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.model.sesmt.SinalizacaoPcmat;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class SinalizacaoPcmatEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private SinalizacaoPcmatManager sinalizacaoPcmatManager;
	
	private SinalizacaoPcmat sinalizacaoPcmat;
	private Pcmat pcmat;
	private Long ultimoPcmatId = 0L;
	
	private Collection<SinalizacaoPcmat> sinalizacaoPcmats;

	private void prepare() throws Exception
	{
		if (sinalizacaoPcmat != null && sinalizacaoPcmat.getId() != null)
			sinalizacaoPcmat = (SinalizacaoPcmat) sinalizacaoPcmatManager.findById(sinalizacaoPcmat.getId());
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
			sinalizacaoPcmatManager.save(sinalizacaoPcmat);
			addActionSuccess("Sinalização cadastrada com sucesso.");
			
		} catch (Exception e) {
			prepare();
			e.printStackTrace();
			addActionError("Não foi possível cadastrar a sinalização.");
			
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try {
			sinalizacaoPcmatManager.update(sinalizacaoPcmat);
			addActionSuccess("Sinalização atualizada com sucesso.");
			
		} catch (Exception e) {
			prepare();
			e.printStackTrace();
			addActionError("Não foi possível atualizar a atualização.");
			
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		sinalizacaoPcmats = sinalizacaoPcmatManager.findByPcmat(pcmat.getId());
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			sinalizacaoPcmatManager.remove(sinalizacaoPcmat.getId());
			addActionSuccess("Sinalização excluída com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir esta sinalização.");
		}

		return list();
	}
	
	public SinalizacaoPcmat getSinalizacaoPcmat()
	{
		if (sinalizacaoPcmat == null)
			sinalizacaoPcmat = new SinalizacaoPcmat();
		
		return sinalizacaoPcmat;
	}

	public void setSinalizacaoPcmat(SinalizacaoPcmat sinalizacaoPcmat)
	{
		this.sinalizacaoPcmat = sinalizacaoPcmat;
	}
	
	public Collection<SinalizacaoPcmat> getSinalizacaoPcmats()
	{
		return sinalizacaoPcmats;
	}

	public Pcmat getPcmat() 
	{
		return pcmat;
	}

	public void setPcmat(Pcmat pcmat) 
	{
		this.pcmat = pcmat;
	}

	public Long getUltimoPcmatId() 
	{
		return ultimoPcmatId;
	}

	public void setUltimoPcmatId(Long ultimoPcmatId) 
	{
		this.ultimoPcmatId = ultimoPcmatId;
	}
}
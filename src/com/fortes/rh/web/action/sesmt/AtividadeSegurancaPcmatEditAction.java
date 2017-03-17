package com.fortes.rh.web.action.sesmt;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.AtividadeSegurancaPcmatManager;
import com.fortes.rh.model.sesmt.AtividadeSegurancaPcmat;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class AtividadeSegurancaPcmatEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private AtividadeSegurancaPcmatManager atividadeSegurancaPcmatManager;
	
	private AtividadeSegurancaPcmat atividadeSegurancaPcmat;
	private Pcmat pcmat;
	private Long ultimoPcmatId = 0L;
	
	private Collection<AtividadeSegurancaPcmat> atividadesSegurancaPcmat;

	private void prepare() throws Exception
	{
		if (atividadeSegurancaPcmat != null && atividadeSegurancaPcmat.getId() != null)
			atividadeSegurancaPcmat = (AtividadeSegurancaPcmat) atividadeSegurancaPcmatManager.findById(atividadeSegurancaPcmat.getId());
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
			atividadeSegurancaPcmatManager.save(atividadeSegurancaPcmat);
			addActionSuccess("Atividade de segurança cadastrada com sucesso.");
			
		} catch (Exception e) {
			prepare();
			e.printStackTrace();
			addActionError("Não foi possível cadastrar a atividade de segurança.");
			
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try {
			atividadeSegurancaPcmatManager.update(atividadeSegurancaPcmat);
			addActionSuccess("Atividade de segurança atualizada com sucesso.");
			
		} catch (Exception e) {
			prepare();
			e.printStackTrace();
			addActionError("Não foi possível atualizada a atividade de segurança.");
			
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		atividadesSegurancaPcmat = atividadeSegurancaPcmatManager.findByPcmat(pcmat.getId());
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			atividadeSegurancaPcmatManager.remove(atividadeSegurancaPcmat.getId());
			addActionSuccess("Atividade de segurança excluída com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir esta atividade de segurança.");
		}

		return list();
	}
	
	public AtividadeSegurancaPcmat getAtividadeSegurancaPcmat()
	{
		if(atividadeSegurancaPcmat == null)
			atividadeSegurancaPcmat = new AtividadeSegurancaPcmat();
		return atividadeSegurancaPcmat;
	}

	public void setAtividadeSegurancaPcmat(AtividadeSegurancaPcmat atividadeSegurancaPcmat)
	{
		this.atividadeSegurancaPcmat = atividadeSegurancaPcmat;
	}

	public Collection<AtividadeSegurancaPcmat> getAtividadesSegurancaPcmat() 
	{
		return atividadesSegurancaPcmat;
	}

	public Pcmat getPcmat() 
	{
		return pcmat;
	}

	public void setPcmat(Pcmat pcmat) 
	{
		this.pcmat = pcmat;
	}

	public Long getUltimoPcmatId() {
		return ultimoPcmatId;
	}

	public void setUltimoPcmatId(Long ultimoPcmatId) {
		this.ultimoPcmatId = ultimoPcmatId;
	}
}
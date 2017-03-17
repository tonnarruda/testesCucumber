package com.fortes.rh.web.action.sesmt;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.MotivoSolicitacaoEpiManager;
import com.fortes.rh.model.sesmt.MotivoSolicitacaoEpi;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class MotivoSolicitacaoEpiEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private MotivoSolicitacaoEpiManager motivoSolicitacaoEpiManager;
	private MotivoSolicitacaoEpi motivoSolicitacaoEpi;
	private Collection<MotivoSolicitacaoEpi> motivoSolicitacaoEpis;

	private void prepare() throws Exception
	{
		if(motivoSolicitacaoEpi != null && motivoSolicitacaoEpi.getId() != null)
			motivoSolicitacaoEpi = (MotivoSolicitacaoEpi) motivoSolicitacaoEpiManager.findById(motivoSolicitacaoEpi.getId());
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
		try
		{
			motivoSolicitacaoEpiManager.save(motivoSolicitacaoEpi);
			addActionSuccess("Motivo da solicitação do EPI cadastrado com sucesso.");
		}
		catch (Exception e)
		{
			prepare();
			e.printStackTrace();
			addActionError("Não foi possível cadastrar este motivo da solicitação do EPI.");
			
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try
		{
			motivoSolicitacaoEpiManager.update(motivoSolicitacaoEpi);
			addActionSuccess("Motivo da solicitação do EPI atualizado com sucesso.");
		}
		catch (Exception e)
		{
			prepare();
			e.printStackTrace();
			addActionError("Não foi possível atualizar este motivo da solicitação do EPI.");
			
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		motivoSolicitacaoEpis = motivoSolicitacaoEpiManager.findAll(new String[] {"descricao"});
		
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			motivoSolicitacaoEpiManager.remove(motivoSolicitacaoEpi.getId());
			addActionSuccess("Motivo da solicitação do EPI excluído com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir este motivo da solicitação do EPI.");
		}

		return list();
	}
	
	public MotivoSolicitacaoEpi getMotivoSolicitacaoEpi()
	{
		if(motivoSolicitacaoEpi == null)
			motivoSolicitacaoEpi = new MotivoSolicitacaoEpi();
		return motivoSolicitacaoEpi;
	}

	public void setMotivoSolicitacaoEpi(MotivoSolicitacaoEpi motivoSolicitacaoEpi)
	{
		this.motivoSolicitacaoEpi = motivoSolicitacaoEpi;
	}
	
	public Collection<MotivoSolicitacaoEpi> getMotivoSolicitacaoEpis()
	{
		return motivoSolicitacaoEpis;
	}
}
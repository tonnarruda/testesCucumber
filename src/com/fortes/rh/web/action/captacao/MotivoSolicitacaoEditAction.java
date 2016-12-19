package com.fortes.rh.web.action.captacao;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.captacao.MotivoSolicitacaoManager;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"serial"})
public class MotivoSolicitacaoEditAction extends MyActionSupportEdit implements ModelDriven
{
	@Autowired private MotivoSolicitacaoManager motivoSolicitacaoManager;

	private MotivoSolicitacao motivoSolicitacao;
	
	private boolean exibeFlagTurnover;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		exibeFlagTurnover = getEmpresaSistema().isTurnoverPorSolicitacao();
		
		if(motivoSolicitacao != null && motivoSolicitacao.getId() != null)
			motivoSolicitacao = (MotivoSolicitacao) motivoSolicitacaoManager.findById(motivoSolicitacao.getId());
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
		motivoSolicitacaoManager.save(motivoSolicitacao);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		motivoSolicitacaoManager.update(motivoSolicitacao);
		return Action.SUCCESS;
	}

	public Object getModel()
	{
		return getMotivoSolicitacao();
	}

	public MotivoSolicitacao getMotivoSolicitacao()
	{
		if(motivoSolicitacao == null)
			motivoSolicitacao = new MotivoSolicitacao();
		return motivoSolicitacao;
	}

	public void setMotivoSolicitacao(MotivoSolicitacao motivoSolicitacao)
	{
		this.motivoSolicitacao = motivoSolicitacao;
	}

	public boolean isExibeFlagTurnover() {
		return exibeFlagTurnover;
	}

	public void setExibeFlagTurnover(boolean exibeFlagTurnover) {
		this.exibeFlagTurnover = exibeFlagTurnover;
	}
}
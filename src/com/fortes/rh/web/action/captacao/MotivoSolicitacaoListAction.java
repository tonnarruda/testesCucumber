package com.fortes.rh.web.action.captacao;

import java.util.Collection;

import com.fortes.rh.business.captacao.MotivoSolicitacaoManager;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class MotivoSolicitacaoListAction extends MyActionSupportList
{
	private MotivoSolicitacaoManager motivoSolicitacaoManager;
	private Collection<MotivoSolicitacao> motivoSolicitacaos;
	private MotivoSolicitacao motivoSolicitacao;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		motivoSolicitacaos = motivoSolicitacaoManager.findAll();

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		motivoSolicitacaoManager.remove(new Long[]{motivoSolicitacao.getId()});
		addActionMessage("Motivo de Solicitação excluído com sucesso.");

		return Action.SUCCESS;
	}

	public Collection<MotivoSolicitacao> getMotivoSolicitacaos() {
		return motivoSolicitacaos;
	}

	public MotivoSolicitacao getMotivoSolicitacao(){
		if(motivoSolicitacao == null){
			motivoSolicitacao = new MotivoSolicitacao();
		}
		return motivoSolicitacao;
	}

	public void setMotivoSolicitacao(MotivoSolicitacao motivoSolicitacao){
		this.motivoSolicitacao = motivoSolicitacao;
	}

	public void setMotivoSolicitacaoManager(MotivoSolicitacaoManager motivoSolicitacaoManager){
		this.motivoSolicitacaoManager = motivoSolicitacaoManager;
	}
}
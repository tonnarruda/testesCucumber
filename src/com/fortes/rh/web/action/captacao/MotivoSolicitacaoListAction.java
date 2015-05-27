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
	
	private boolean exibeFlagTurnover;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		
		exibeFlagTurnover = getEmpresaSistema().isTurnoverPorSolicitacao();
		
		setVideoAjuda(634L);
		motivoSolicitacaos = motivoSolicitacaoManager.findAll();

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		motivoSolicitacaoManager.remove(motivoSolicitacao.getId());
		addActionSuccess("Motivo de solicitação excluído com sucesso.");

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

	public boolean isExibeFlagTurnover() {
		return exibeFlagTurnover;
	}

	public void setExibeFlagTurnover(boolean exibeFlagTurnover) {
		this.exibeFlagTurnover = exibeFlagTurnover;
	}
}
package com.fortes.rh.web.action.captacao;

import java.util.Collection;

import com.fortes.rh.business.captacao.MotivoSolicitacaoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class MotivoSolicitacaoListAction extends MyActionSupportList
{
	private MotivoSolicitacaoManager motivoSolicitacaoManager;
	private EmpresaManager empresaManager;
	private Collection<MotivoSolicitacao> motivoSolicitacaos;
	private MotivoSolicitacao motivoSolicitacao;
	private boolean turnoverPorSolicitacao;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		setVideoAjuda(634L);
		motivoSolicitacaos = motivoSolicitacaoManager.findAll();
		Empresa empresa = empresaManager.findByIdProjection(getEmpresaSistema().getId());
		turnoverPorSolicitacao = empresa.isTurnoverPorSolicitacao();

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		motivoSolicitacaoManager.remove(motivoSolicitacao.getId());
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

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public boolean isTurnoverPorSolicitacao() {
		return turnoverPorSolicitacao;
	}

	public void setTurnoverPorSolicitacao(boolean turnoverPorSolicitacao) {
		this.turnoverPorSolicitacao = turnoverPorSolicitacao;
	}
}
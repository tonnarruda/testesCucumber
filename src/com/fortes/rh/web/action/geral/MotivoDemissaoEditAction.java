package com.fortes.rh.web.action.geral;

import com.fortes.rh.business.geral.MotivoDemissaoManager;
import com.fortes.rh.model.geral.MotivoDemissao;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"serial"})
public class MotivoDemissaoEditAction extends MyActionSupportEdit implements ModelDriven
{
	private MotivoDemissaoManager motivoDemissaoManager;
	
	private MotivoDemissao motivoDemissao;
	
	private boolean exibeFlagTurnover;
	
	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		exibeFlagTurnover = getEmpresaSistema().isTurnoverPorSolicitacao();
		
		if (motivoDemissao != null && motivoDemissao.getId() != null)
			motivoDemissao = (MotivoDemissao) motivoDemissaoManager.findById(motivoDemissao.getId());
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
		motivoDemissao.setEmpresa(getEmpresaSistema());
		motivoDemissaoManager.save(motivoDemissao);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		motivoDemissao.setEmpresa(getEmpresaSistema());
		motivoDemissaoManager.update(motivoDemissao);
		return Action.SUCCESS;
	}

	public Object getModel()
	{
		return getMotivoDemissao();
	}

	public MotivoDemissao getMotivoDemissao()
	{
		if(motivoDemissao == null)
			motivoDemissao = new MotivoDemissao();
		return motivoDemissao;
	}

	public void setMotivoDemissao(MotivoDemissao motivoDemissao)
	{
		this.motivoDemissao = motivoDemissao;
	}

	public void setMotivoDemissaoManager(MotivoDemissaoManager motivoDemissaoManager)
	{
		this.motivoDemissaoManager = motivoDemissaoManager;
	}

	public boolean isExibeFlagTurnover() {
		return exibeFlagTurnover;
	}

	public void setExibeFlagTurnover(boolean exibeFlagTurnover) {
		this.exibeFlagTurnover = exibeFlagTurnover;
	}
}
package com.fortes.rh.web.action.sesmt;

import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.sesmt.HistoricoFuncaoManager;
import com.fortes.rh.business.sesmt.RiscoFuncaoManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class HistoricoFuncaoListAction extends MyActionSupportList
{
	private HistoricoFuncaoManager historicoFuncaoManager;
	private RiscoFuncaoManager riscoFuncaoManager;
	private FuncaoManager funcaoManager;

	private HistoricoFuncao historicoFuncao;
	private Funcao funcao;
	private Cargo cargoTmp;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		riscoFuncaoManager.removeByHistoricoFuncao(historicoFuncao.getId());
		historicoFuncaoManager.remove(new Long[]{historicoFuncao.getId()});
		funcaoManager.atualizaNomeUltimoHistorico(funcao.getId());

		return Action.SUCCESS;
	}

	public void setHistoricoFuncaoManager(HistoricoFuncaoManager historicoFuncaoManager)
	{
		this.historicoFuncaoManager = historicoFuncaoManager;
	}

	public HistoricoFuncao getHistoricoFuncao()
	{
		return historicoFuncao;
	}

	public void setHistoricoFuncao(HistoricoFuncao historicoFuncao)
	{
		this.historicoFuncao = historicoFuncao;
	}

	public Cargo getCargoTmp()
	{
		return cargoTmp;
	}

	public void setCargoTmp(Cargo cargoTmp)
	{
		this.cargoTmp = cargoTmp;
	}

	public Funcao getFuncao()
	{
		return funcao;
	}

	public void setFuncao(Funcao funcao)
	{
		this.funcao = funcao;
	}

	public void setRiscoFuncaoManager(RiscoFuncaoManager riscoFuncaoManager) {
		this.riscoFuncaoManager = riscoFuncaoManager;
	}

	public void setFuncaoManager(FuncaoManager funcaoManager) {
		this.funcaoManager = funcaoManager;
	}
}
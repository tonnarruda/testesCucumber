package com.fortes.rh.web.action.sesmt;

import org.springframework.beans.factory.annotation.Autowired;

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
	@Autowired private HistoricoFuncaoManager historicoFuncaoManager;
	@Autowired private RiscoFuncaoManager riscoFuncaoManager;

	private HistoricoFuncao historicoFuncao;
	private Funcao funcao;
	private Cargo cargoTmp;

	private boolean veioDoSESMT;
	
	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		riscoFuncaoManager.removeByHistoricoFuncao(historicoFuncao.getId());
		historicoFuncaoManager.remove(new Long[]{historicoFuncao.getId()});

		if(veioDoSESMT)
			return "SUCESSO_VEIO_SESMT";
		else
			return Action.SUCCESS;
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

	public boolean isVeioDoSESMT()
	{
		return veioDoSESMT;
	}

	public void setVeioDoSESMT(boolean veioDoSESMT)
	{
		this.veioDoSESMT = veioDoSESMT;
	}
}
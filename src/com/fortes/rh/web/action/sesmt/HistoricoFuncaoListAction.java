package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.sesmt.HistoricoFuncaoManager;
import com.fortes.rh.business.sesmt.RiscoFuncaoManager;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.util.ExceptionUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class HistoricoFuncaoListAction extends MyActionSupportList
{
	private HistoricoFuncaoManager historicoFuncaoManager;
	private RiscoFuncaoManager riscoFuncaoManager;
	private FuncaoManager funcaoManager;

	private Collection<HistoricoFuncao> historicoFuncaos = new ArrayList<HistoricoFuncao>();
	private HistoricoFuncao historicoFuncao;
	private Funcao funcao;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String list(){
		funcao = funcaoManager.findByIdProjection(funcao.getId());
		historicoFuncaos = historicoFuncaoManager.findToList(new String[]{"id","descricao","data", "codigoCbo", "funcaoNome"}, new String[]{"id","descricao","data", "codigoCbo", "funcaoNome"}, new String[]{"funcao.id"}, new Object[]{funcao.getId()}, new String[]{"data desc"});

		return Action.SUCCESS; 
	}
	
	public String delete() throws Exception
	{
		try {
			riscoFuncaoManager.removeByHistoricoFuncao(historicoFuncao.getId());
			historicoFuncaoManager.remove(new Long[]{historicoFuncao.getId()});
			funcaoManager.atualizaNomeUltimoHistorico(funcao.getId());
			addActionSuccess("Histórico da função excluído com sucesso.");
		} catch (Exception e) {
			ExceptionUtil.traduzirMensagem(this, e, "Não foi possível excluir o histórico da função.");
			e.printStackTrace();
		}

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


	public Collection<HistoricoFuncao> getHistoricoFuncaos() {
		return historicoFuncaos;
	}


	public void setHistoricoFuncaos(Collection<HistoricoFuncao> historicoFuncaos) {
		this.historicoFuncaos = historicoFuncaos;
	}
}
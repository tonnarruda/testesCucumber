/* Autor: Robertson Freitas
 * Data: 19/06/2006
 * Requisito: RFA0023 */
package com.fortes.rh.web.action.captacao;

import java.util.Collection;

import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.util.ExceptionUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class EtapaSeletivaListAction extends MyActionSupportList
{
	private EtapaSeletivaManager etapaSeletivaManager;
	private Collection<EtapaSeletiva> etapaSeletivas;
	private EtapaSeletiva etapaSeletiva;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		setTotalSize(etapaSeletivaManager.getCount(getEmpresaSistema().getId()));
		etapaSeletivas = etapaSeletivaManager.findAllSelect(getPage(), getPagingSize(), getEmpresaSistema().getId());
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try {
			etapaSeletivaManager.remove(etapaSeletiva, getEmpresaSistema());
			addActionSuccess("Etapa Seletiva excluída com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionUtil.traduzirMensagem(this, e, "Não foi possível excluir esta Etapa Seletiva.");			
		}
		return Action.SUCCESS;
	}

	public Collection<EtapaSeletiva> getEtapaSeletivas()
	{
		return etapaSeletivas;
	}

	public EtapaSeletiva getEtapaSeletiva()
	{
		if(etapaSeletiva == null)
			etapaSeletiva = new EtapaSeletiva();

		return etapaSeletiva;
	}

	public void setEtapaSeletiva(EtapaSeletiva etapaSeletiva)
	{
		this.etapaSeletiva=etapaSeletiva;
	}

	public void setEtapaSeletivaManager(EtapaSeletivaManager etapaSeletivaManager)
	{
		this.etapaSeletivaManager=etapaSeletivaManager;
	}
}
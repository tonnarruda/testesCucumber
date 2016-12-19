/* Autor: Robertson Freitas
 * Data: 19/06/2006
 * Requisito: RFA0023 */
package com.fortes.rh.web.action.captacao;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class EtapaSeletivaListAction extends MyActionSupportList
{
	@Autowired private EtapaSeletivaManager etapaSeletivaManager;
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
		etapaSeletivaManager.remove(etapaSeletiva, getEmpresaSistema());
		addActionMessage("Etapa Seletiva excluída com sucesso.");
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
}
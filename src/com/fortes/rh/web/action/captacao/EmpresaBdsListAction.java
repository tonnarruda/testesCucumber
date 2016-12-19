/* Autor: Igo Coelho
 * Data: 29/06/2006
 * Requisito: RFA020 */
package com.fortes.rh.web.action.captacao;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.captacao.EmpresaBdsManager;
import com.fortes.rh.model.captacao.EmpresaBds;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class EmpresaBdsListAction extends MyActionSupportList
{
	@Autowired private EmpresaBdsManager empresaBdsManager;
	private Collection<EmpresaBds> empresaBdss;
	private EmpresaBds empresaBds;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		setTotalSize(empresaBdsManager.getCount(new String[] { "empresa.id" }, new Object[] { getEmpresaSistema().getId() }));
		empresaBdss = empresaBdsManager.find(getPage(), getPagingSize(), new String[] {"empresa.id"}, new Object[] { getEmpresaSistema().getId() }, new String[] { "nome" });

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			empresaBdsManager.remove(new Long[] { empresaBds.getId() });
			addActionMessage("Empresa BDS excluída com sucesso.");
		}
		catch (Exception e)
		{
			addActionError("Não foi possível excluir esta empresa BDS.");
			e.printStackTrace();
		}

		return list();
	}

	public Collection<EmpresaBds> getEmpresaBdss()
	{
		return empresaBdss;
	}

	public EmpresaBds getEmpresaBds()
	{

		if (this.empresaBds == null)
		{
			this.empresaBds = new EmpresaBds();
		}
		return this.empresaBds;
	}

	public void setEmpresaBds(EmpresaBds empresaBds)
	{
		this.empresaBds = empresaBds;
	}
}
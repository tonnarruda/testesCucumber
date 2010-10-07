/* Autor: Bruno Bachiega
 * Data: 7/06/2006
 * Requisito: RFA004 */
package com.fortes.rh.web.action.geral;

import java.util.Collection;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class AreaOrganizacionalListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;

	private AreaOrganizacionalManager areaOrganizacionalManager;

	private Collection<AreaOrganizacional> areaOrganizacionals;
	private AreaOrganizacional areaOrganizacional = new AreaOrganizacional();

	private boolean integradoAC;

	public String list() throws Exception
	{
		//foi retirado a paginação pois não dava para ordenar(Francisco, 03/06/09)
		areaOrganizacionals = areaOrganizacionalManager.findAllList(0, 0,areaOrganizacional.getNome(), getEmpresaSistema().getId(), AreaOrganizacional.TODAS);

		Collection<AreaOrganizacional> areasTmp = areaOrganizacionalManager.findAllList(getEmpresaSistema().getId(), AreaOrganizacional.TODAS);
		areasTmp = areaOrganizacionalManager.montaFamilia(areasTmp);
		areaOrganizacionals = areaOrganizacionalManager.getDistinctAreaMae(areasTmp, areaOrganizacionals);
		CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
		areaOrganizacionals = cu1.sortCollectionStringIgnoreCase(areaOrganizacionals, "descricao");

		integradoAC = getEmpresaSistema().isAcIntegra();

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		if(getEmpresaSistema().getId().equals(areaOrganizacional.getEmpresa().getId()))
		{
			areaOrganizacionalManager.deleteLotacaoAC(areaOrganizacional, getEmpresaSistema());
			addActionMessage("Área Organizacional excluída com sucesso.");
		}
		else
			addActionError("A Área Organizacional solicitada não existe na empresa " + getEmpresaSistema().getNome() +".");

		return Action.SUCCESS;
	}

	public Collection<AreaOrganizacional> getAreaOrganizacionals()
	{
		return areaOrganizacionals;
	}

	public AreaOrganizacional getAreaOrganizacional()
	{
		if(areaOrganizacional == null)
			areaOrganizacional = new AreaOrganizacional();
		return areaOrganizacional;
	}

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		this.areaOrganizacional=areaOrganizacional;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager=areaOrganizacionalManager;
	}

	public boolean isIntegradoAC()
	{
		return integradoAC;
	}

	public void setIntegradoAC(boolean integradoAC)
	{
		this.integradoAC = integradoAC;
	}
}
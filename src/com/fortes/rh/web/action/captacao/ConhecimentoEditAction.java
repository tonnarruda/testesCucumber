/* Autor: Robertson Freitas
 * Data: 16/06/2006
 * Requisito: RFA33 */
package com.fortes.rh.web.action.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"unchecked","serial"})
public class ConhecimentoEditAction extends MyActionSupportEdit implements ModelDriven
{
	//	managers
	private ConhecimentoManager conhecimentoManager = null;
	private AreaOrganizacionalManager areaOrganizacionalManager = null;

	private String[] areasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();

	//	POJOs
	private Conhecimento conhecimento;
	private AreaOrganizacional areaOrganizacional;
	private Collection<AreaOrganizacional> areaOrganizacionals;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(conhecimento != null && conhecimento.getId() != null)
			conhecimento = conhecimentoManager.findByIdProjection(conhecimento.getId());

		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, conhecimento.getAreaOrganizacionals(), "getId");
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		conhecimento.setAreaOrganizacionals(areaOrganizacionalManager.populaAreas(areasCheck));
		conhecimento.setEmpresa(getEmpresaSistema());
		conhecimentoManager.save(conhecimento);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		conhecimento.setAreaOrganizacionals(areaOrganizacionalManager.populaAreas(areasCheck));
		conhecimento.setEmpresa(getEmpresaSistema());
		conhecimentoManager.update(conhecimento);
		return Action.SUCCESS;
	}

	public Conhecimento getConhecimento()
	{
		if(conhecimento == null)
			conhecimento = new Conhecimento();
		return conhecimento;
	}

	public void setConhecimento(Conhecimento conhecimento)
	{
		this.conhecimento=conhecimento;
	}

	public Object getModel()
	{
		return getConhecimento();
	}

	public void setConhecimentoManager(ConhecimentoManager conhecimentoManager)
	{
		this.conhecimentoManager=conhecimentoManager;
	}

	public AreaOrganizacional getAreaOrganizacional()
	{
		return areaOrganizacional;
	}

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		this.areaOrganizacional = areaOrganizacional;
	}

	public AreaOrganizacionalManager getAreaOrganizacionalManager()
	{
		return areaOrganizacionalManager;
	}

	public void setAreaOrganizacionalManager(
			AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public Collection<AreaOrganizacional> getAreaOrganizacionals()
	{
		return areaOrganizacionals;
	}

	public void setAreaOrganizacionals(
			Collection<AreaOrganizacional> areaOrganizacionals)
	{
		this.areaOrganizacionals = areaOrganizacionals;
	}

	public ConhecimentoManager getConhecimentoManager()
	{
		return conhecimentoManager;
	}

	public String[] getAreasCheck()
	{
		return areasCheck;
	}

	public void setAreasCheck(String[] areasCheck)
	{
		this.areasCheck = areasCheck;
	}

	public Collection<CheckBox> getAreasCheckList()
	{
		return areasCheckList;
	}

	public void setAreasCheckList(Collection<CheckBox> areasCheckList)
	{
		this.areasCheckList = areasCheckList;
	}
}
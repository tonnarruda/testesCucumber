package com.fortes.rh.web.action.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.captacao.AtitudeManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class AtitudeEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private AtitudeManager atitudeManager;
	private Atitude atitude;
	private Collection<Atitude> atitudes;
	
	private AreaOrganizacionalManager areaOrganizacionalManager = null;
	
	private String[] areasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	
	private AreaOrganizacional areaOrganizacional;
	private Collection<AreaOrganizacional> areaOrganizacionals;


	private void prepare() throws Exception
	{
		if(atitude != null && atitude.getId() != null)
			atitude = (Atitude) atitudeManager.findById(atitude.getId());
	
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
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, atitude.getAreaOrganizacionals(), "getId");
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		atitude.setAreaOrganizacionals(areaOrganizacionalManager.populaAreas(areasCheck));
		atitude.setEmpresa(getEmpresaSistema());
		atitudeManager.save(atitude);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		atitude.setAreaOrganizacionals(areaOrganizacionalManager.populaAreas(areasCheck));
		atitude.setEmpresa(getEmpresaSistema());
		atitudeManager.update(atitude);
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		String[] properties = new String[]{"id","nome","observacao"};
		String[] sets = new String[]{"id","nome","observacao"};
		String[] keys = new String[]{"empresa.id"};
		Object[] values = new Object[]{getEmpresaSistema().getId()};
		String[] orders = new String[]{"nome"};

		setTotalSize(atitudeManager.getCount(keys, values));
		atitudes = atitudeManager.findToList(properties, sets, keys, values,getPage(), getPagingSize(), orders);

		return Action.SUCCESS;

	}

	public String delete() throws Exception
	{
		atitudeManager.remove(atitude.getId());
		addActionMessage("Atitude excluído com sucesso.");

		return Action.SUCCESS;
	}
	
	public Atitude getAtitude()
	{
		if(atitude == null)
			atitude = new Atitude();
		return atitude;
	}

	public void setAtitude(Atitude atitude)
	{
		this.atitude = atitude;
	}

	public void setAtitudeManager(AtitudeManager atitudeManager)
	{
		this.atitudeManager = atitudeManager;
	}
	
	public Collection<Atitude> getAtitudes()
	{
		return atitudes;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setAreasCheck(String[] areasCheck) {
		this.areasCheck = areasCheck;
	}

	public Collection<CheckBox> getAreasCheckList() {
		return areasCheckList;
	}

	public void setAreasCheckList(Collection<CheckBox> areasCheckList) {
		this.areasCheckList = areasCheckList;
	}

	public AreaOrganizacional getAreaOrganizacional() {
		return areaOrganizacional;
	}

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional) {
		this.areaOrganizacional = areaOrganizacional;
	}

	public Collection<AreaOrganizacional> getAreaOrganizacionals() {
		return areaOrganizacionals;
	}

	public void setAreaOrganizacionals(Collection<AreaOrganizacional> areaOrganizacionals) {
		this.areaOrganizacionals = areaOrganizacionals;
	}
}

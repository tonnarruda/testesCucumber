package com.fortes.rh.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.AreaInteresseManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"serial"})
public class AreaInteresseEditAction extends MyActionSupportEdit implements ModelDriven
{
	@Autowired private AreaOrganizacionalManager areaOrganizacionalManager;
	@Autowired private AreaInteresseManager areaInteresseManager;

	private AreaInteresse areaInteresse;
	private AreaOrganizacional areaOrganizacional;

	private String[] areasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	private void prepare() throws Exception {

		if(areaInteresse != null && areaInteresse.getId() != null) {
			areaInteresse = areaInteresseManager.findByIdProjection(areaInteresse.getId());
			areaInteresse.setAreasOrganizacionais(areaOrganizacionalManager.getAreasByAreaInteresse(areaInteresse.getId()));
		}

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
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areaInteresse.getAreasOrganizacionais(), "getId");
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		areaInteresse.setAreasOrganizacionais(areaOrganizacionalManager.populaAreas(areasCheck));
		areaInteresse.setEmpresa(getEmpresaSistema());
		areaInteresseManager.save(areaInteresse);

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		areaInteresse.setAreasOrganizacionais(areaOrganizacionalManager.populaAreas(areasCheck));
		areaInteresse.setEmpresa(getEmpresaSistema());
		//TODO BACALHAU , o update ta gigante deve acontecer com outras entidades.
		areaInteresseManager.update(areaInteresse);

		return Action.SUCCESS;
	}

	public AreaInteresse getAreaInteresse(){
		if(areaInteresse == null){
			areaInteresse = new AreaInteresse();
		}
		return areaInteresse;
	}

	public void setAreaInteresse(AreaInteresse areaInteresse){
		this.areaInteresse=areaInteresse;
	}

	public Object getModel()
	{
		return getAreaInteresse();
	}

	public AreaOrganizacional getAreaOrganizacional()
	{
		return areaOrganizacional;
	}

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		this.areaOrganizacional = areaOrganizacional;
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
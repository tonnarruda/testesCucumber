package com.fortes.rh.web.action.geral;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.geral.AreaInteresseManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.muantech.rollbar.java.RollbarNotifier;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"unchecked","serial"})
public class AreaInteresseEditAction extends MyActionSupportEdit implements ModelDriven
{
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private AreaInteresseManager areaInteresseManager;

	private AreaInteresse areaInteresse;
	private AreaOrganizacional areaOrganizacional;

	private String[] areasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	private void prepare() throws Exception {
		RollbarNotifier.init("https://api.rollbar.com/api/1/item/", "ec888be98c904dac80fed9eef1c6c4d3", "production");
		Map<String,Object> context = new HashMap<String,Object>();
	    context.put("platform","Java");
		try {
			throw new IOException("Erro ao gravar colaborador(teste rollbar).");
		} catch (Exception e) {
			RollbarNotifier.notify(e, context);
		}
		
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

	public void setAreaInteresseManager(AreaInteresseManager areaInteresseManager){
		this.areaInteresseManager=areaInteresseManager;
	}

	public AreaOrganizacional getAreaOrganizacional()
	{
		return areaOrganizacional;
	}

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		this.areaOrganizacional = areaOrganizacional;
	}

	public void setAreaOrganizacionalManager(
			AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
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
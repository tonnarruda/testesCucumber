package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.ClinicaAutorizadaManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.model.dicionario.TipoClinica;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class ClinicaAutorizadaEditAction extends MyActionSupportEdit
{
	@Autowired private ClinicaAutorizadaManager clinicaAutorizadaManager;
	@Autowired private ExameManager exameManager;

	private ClinicaAutorizada clinicaAutorizada;
	private Map tipos;
	private Collection<Exame> exames;

	private String[] examesCheck;
	private Collection<CheckBox> examesCheckList = new ArrayList<CheckBox>();
	private String tipoOutros = "";
	
	private void prepare() throws Exception
	{
		if(clinicaAutorizada != null && clinicaAutorizada.getId() != null)
			clinicaAutorizada = (ClinicaAutorizada) clinicaAutorizadaManager.findById(clinicaAutorizada.getId());

		tipos = new TipoClinica();
		exames = exameManager.findByEmpresaComAsoPadrao(getEmpresaSistema().getId());
		examesCheckList = CheckListBoxUtil.populaCheckListBox(exames, "getId", "getNome", null);
		tipoOutros = clinicaAutorizadaManager.findTipoOutrosDistinctByEmpresa(getEmpresaSistema().getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		clinicaAutorizada = clinicaAutorizadaManager.findById(clinicaAutorizada.getId());
		if(clinicaAutorizada == null || !getEmpresaSistema().getId().equals(clinicaAutorizada.getEmpresa().getId()))
		{
			addActionError("A clínica solicitada não existe na empresa " + getEmpresaSistema().getNome() +".");
			return Action.ERROR;
		}

		prepare();
		examesCheckList = CheckListBoxUtil.marcaCheckListBox(examesCheckList, clinicaAutorizada.getExames(), "getId");
		
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		clinicaAutorizada.setEmpresa(getEmpresaSistema());
		clinicaAutorizada.setExames(exameManager.populaExames(examesCheck));
		clinicaAutorizadaManager.save(clinicaAutorizada);
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		clinicaAutorizada.setEmpresa(getEmpresaSistema());
		clinicaAutorizada.setExames(exameManager.populaExames(examesCheck));
		clinicaAutorizadaManager.update(clinicaAutorizada);
		
		return Action.SUCCESS;
	}

	public Object getModel()
	{
		return getClinicaAutorizada();
	}

	public ClinicaAutorizada getClinicaAutorizada()
	{
		if(clinicaAutorizada == null)
			clinicaAutorizada = new ClinicaAutorizada();
		return clinicaAutorizada;
	}

	public void setClinicaAutorizada(ClinicaAutorizada clinicaAutorizada)
	{
		this.clinicaAutorizada = clinicaAutorizada;
	}

	public Map getTipos()
	{
		return tipos;
	}

	public void setTipos(Map tipos)
	{
		this.tipos = tipos;
	}

	public Collection<Exame> getExames()
	{
		return exames;
	}

	public void setExamesCheck(String[] examesCheck)
	{
		this.examesCheck = examesCheck;
	}

	public Collection<CheckBox> getExamesCheckList()
	{
		return examesCheckList;
	}

	public String getTipoOutros() {
		return tipoOutros;
	}
}
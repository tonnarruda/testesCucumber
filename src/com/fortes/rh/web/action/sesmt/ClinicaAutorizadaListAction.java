package com.fortes.rh.web.action.sesmt;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.ClinicaAutorizadaManager;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class ClinicaAutorizadaListAction extends MyActionSupportList
{
	@Autowired private ClinicaAutorizadaManager clinicaAutorizadaManager;

	private Collection<ClinicaAutorizada> clinicaAutorizadas;

	private ClinicaAutorizada clinicaAutorizada;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		String[] keys = new String[]{"empresa"};
		Object[] values = new Object[]{getEmpresaSistema()};
		String[] orders = new String[]{"nome"};

		setTotalSize(clinicaAutorizadaManager.getCount(keys, values));
		clinicaAutorizadas = clinicaAutorizadaManager.find(getPage(), getPagingSize(), keys, values, orders);

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			clinicaAutorizadaManager.remove(new Long[]{clinicaAutorizada.getId()});
			addActionMessage("Clínica/Médico Autorizada(o) excluída(o) com sucesso.");
		}
		catch (Exception e)
		{
			addActionError("Não foi possível excluir esta(e) Clínica/Médico.");
			e.printStackTrace();
		}

		return list();
	}

	public Collection getClinicaAutorizadas() {
		return clinicaAutorizadas;
	}

	public ClinicaAutorizada getClinicaAutorizada(){
		if(clinicaAutorizada == null){
			clinicaAutorizada = new ClinicaAutorizada();
		}
		return clinicaAutorizada;
	}

	public void setClinicaAutorizada(ClinicaAutorizada clinicaAutorizada){
		this.clinicaAutorizada=clinicaAutorizada;
	}
}
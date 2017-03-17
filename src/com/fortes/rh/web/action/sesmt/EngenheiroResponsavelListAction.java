package com.fortes.rh.web.action.sesmt;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.EngenheiroResponsavelManager;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class EngenheiroResponsavelListAction extends MyActionSupportList
{
	@Autowired private EngenheiroResponsavelManager engenheiroResponsavelManager;

	private Collection<EngenheiroResponsavel> engenheiroResponsavels;

	private EngenheiroResponsavel engenheiroResponsavel;

	public String list() throws Exception
	{
		String[] keys = new String[]{"empresa"};
		Object[] values = new Object[]{getEmpresaSistema()};
		String[] orders = new String[]{"nome"};

		setTotalSize(engenheiroResponsavelManager.getCount(keys, values));
		engenheiroResponsavels = engenheiroResponsavelManager.find(getPage() , getPagingSize(), keys, values, orders);

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			engenheiroResponsavelManager.remove(new Long[]{engenheiroResponsavel.getId()});
			addActionMessage("Engenheiro Responsável excluído com sucesso.");
		}
		catch (Exception e)
		{
			addActionError("Não foi possível excluir este Engenheiro Responsável.");
			e.printStackTrace();
		}

		return list();
	}

	public Collection<EngenheiroResponsavel> getEngenheiroResponsavels() {
		return engenheiroResponsavels;
	}

	public EngenheiroResponsavel getEngenheiroResponsavel(){
		if(engenheiroResponsavel == null){
			engenheiroResponsavel = new EngenheiroResponsavel();
		}
		return engenheiroResponsavel;
	}

	public void setEngenheiroResponsavel(EngenheiroResponsavel engenheiroResponsavel){
		this.engenheiroResponsavel=engenheiroResponsavel;
	}
}
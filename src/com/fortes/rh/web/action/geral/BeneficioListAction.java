package com.fortes.rh.web.action.geral;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.BeneficioManager;
import com.fortes.rh.model.geral.Beneficio;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class BeneficioListAction extends MyActionSupportList
{
	@Autowired private BeneficioManager beneficioManager;

	private Collection beneficios;

	private Beneficio beneficio;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		String[] properties = new String[]{"id","nome"};
		String[] sets = new String[]{"id","nome"};
		String[] keys = new String[]{"empresa.id"};
		Object[] values = new Object[]{getEmpresaSistema().getId()};
		String[] orders = new String[]{"nome"};

		setTotalSize(beneficioManager.getCount(keys, values));
		beneficios = beneficioManager.findToList(properties, sets, keys, values,getPage(), getPagingSize(), orders);

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			String[] key = new String[]{"id","empresa.id"};
			Object[] values = new Object[]{beneficio.getId(), getEmpresaSistema().getId()};

			if(beneficioManager.verifyExists(key, values))
			{
				beneficioManager.remove(new Long[]{beneficio.getId()});
				addActionMessage("Benefício excluído com sucesso.");
			}
		}
		catch (Exception e)
		{
			addActionError("O Benefício solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
			e.printStackTrace();
		}

		return list();
	}

	public Collection getBeneficios() {
		return beneficios;
	}

	public Beneficio getBeneficio(){
		if(beneficio == null){
			beneficio = new Beneficio();
		}
		return beneficio;
	}

	public void setBeneficio(Beneficio beneficio){
		this.beneficio=beneficio;
	}
}
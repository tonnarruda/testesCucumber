package com.fortes.rh.web.action.geral;

import java.util.Collection;

import com.fortes.rh.business.geral.AreaInteresseManager;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.util.ExceptionUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class AreaInteresseListAction extends MyActionSupportList
{
	private AreaInteresseManager areaInteresseManager;

	private Collection<AreaInteresse> areaInteresses;
	private AreaInteresse areaInteresse;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		String[] properties = new String[]{"id","nome","observacao"};
		String[] sets = new String[]{"id","nome","observacao"};
		String[] keys = new String[]{"empresa.id"};
		Object[] values = new Object[]{getEmpresaSistema().getId()};
		String[] orders = new String[]{"nome"};

		setTotalSize(areaInteresseManager.getCount(keys, values));
		areaInteresses = areaInteresseManager.findToList(properties, sets, keys, values, getPage(), getPagingSize(), orders );

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try {
			areaInteresseManager.remove(areaInteresse.getId());
			addActionSuccess("Área de Interesse excluída com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionUtil.traduzirMensagem(this, e, "Não foi possível excluir esta Área de Interesse.");
		}

		return Action.SUCCESS;
	}

	public Collection<AreaInteresse> getAreaInteresses() {
		return areaInteresses;
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

	public void setAreaInteresseManager(AreaInteresseManager areaInteresseManager){
		this.areaInteresseManager=areaInteresseManager;
	}
}
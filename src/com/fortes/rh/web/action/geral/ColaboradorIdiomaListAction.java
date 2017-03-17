package com.fortes.rh.web.action.geral;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.ColaboradorIdiomaManager;
import com.fortes.rh.model.geral.ColaboradorIdioma;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class ColaboradorIdiomaListAction extends MyActionSupportList
{
	@Autowired private ColaboradorIdiomaManager colaboradorIdiomaManager;

	private Collection<ColaboradorIdioma> colaboradorIdiomas;

	private ColaboradorIdioma colaboradorIdioma;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String list() throws Exception {
		colaboradorIdiomas = colaboradorIdiomaManager.findAll();

		return Action.SUCCESS;
	}

	public String delete() throws Exception {
		colaboradorIdiomaManager.remove(new Long[]{colaboradorIdioma.getId()});

		return Action.SUCCESS;
	}

	public Collection<ColaboradorIdioma> getColaboradorIdiomas() {
		return colaboradorIdiomas;
	}

	public ColaboradorIdioma getColaboradorIdioma(){
		if(colaboradorIdioma == null){
			colaboradorIdioma = new ColaboradorIdioma();
		}
		return colaboradorIdioma;
	}

	public void setColaboradorIdioma(ColaboradorIdioma colaboradorIdioma){
		this.colaboradorIdioma=colaboradorIdioma;
	}
}
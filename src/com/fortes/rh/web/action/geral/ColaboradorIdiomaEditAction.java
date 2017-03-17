package com.fortes.rh.web.action.geral;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.ColaboradorIdiomaManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorIdioma;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;
import com.opensymphony.xwork.Preparable;

@SuppressWarnings("serial")
public class ColaboradorIdiomaEditAction extends MyActionSupportEdit implements Preparable, ModelDriven
{
	@Autowired private ColaboradorIdiomaManager colaboradorIdiomaManager;
	@Autowired private ColaboradorManager colaboradorManager;

	private ColaboradorIdioma colaboradorIdioma;
	private Collection<Colaborador> colaboradors;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public void prepare() throws Exception
	{
		if(colaboradorIdioma != null && colaboradorIdioma.getId() != null) {
			colaboradorIdioma = (ColaboradorIdioma) colaboradorIdiomaManager.findById(colaboradorIdioma.getId());
		}

		this.colaboradors = colaboradorManager.find(new String[]{"empresa.id"},new Object[]{getEmpresaSistema().getId()},new String[]{"nomeComercial"});
	}

	public String prepareInsert() throws Exception {

		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception {
		return Action.SUCCESS;
	}

	public String insert() throws Exception {
		colaboradorIdiomaManager.save(colaboradorIdioma);

		return Action.SUCCESS;
	}

	public String update() throws Exception {
		colaboradorIdiomaManager.update(colaboradorIdioma);

		return Action.SUCCESS;
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

	public Object getModel()
	{
		return getColaboradorIdioma();
	}

	public Collection<Colaborador> getColaboradors() {
		return colaboradors;
	}
	public void setColaboradors(Collection<Colaborador> colaboradors) {
		this.colaboradors = colaboradors;
	}
}
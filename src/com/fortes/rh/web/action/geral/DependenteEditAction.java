/* Autor: Bruno Bachiega
 * Data: 16/06/2006
 * Requisito: RFA006 */
package com.fortes.rh.web.action.geral;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.DependenteManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Dependente;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings("serial")
public class DependenteEditAction extends MyActionSupportEdit implements ModelDriven
{
	@Autowired private DependenteManager dependenteManager;

	private Dependente dependente;

	private Colaborador colaborador;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest req = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		HttpSession session = req.getSession();
		colaborador = (Colaborador) session.getAttribute("Colaborador");
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		if(dependente != null && dependente.getId() != null) {
			dependente = (Dependente) dependenteManager.findById(dependente.getId());
		}

		return Action.SUCCESS;
	}

	public String insert() throws Exception {
		dependenteManager.save(dependente);

		return Action.SUCCESS;
	}

	public String update() throws Exception {
		dependenteManager.update(dependente);

		return Action.SUCCESS;
	}

	public Dependente getDependente(){
		if(dependente == null){
			dependente = new Dependente();
		}
		return dependente;
	}

	public Object getModel()
	{
		return getDependente();
	}

	public void setDependente(Dependente dependente){
		this.dependente=dependente;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}
}
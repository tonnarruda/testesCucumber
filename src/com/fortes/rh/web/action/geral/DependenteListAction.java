/* Autor: Bruno Bachiega
 * Data: 16/06/2006
 * Requisito: RFA006 */
package com.fortes.rh.web.action.geral;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.DependenteManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Dependente;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings("serial")
public class DependenteListAction extends MyActionSupportList
{
	@Autowired private DependenteManager dependenteManager;

	private Collection<Dependente> dependentes;

	private Dependente dependente;
	private Colaborador colaborador;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest req = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		HttpSession session = req.getSession();
		colaborador = (Colaborador) session.getAttribute("COLABORADOR_SESSION");

		String[] colaboradores = new String[]{"colaborador.id"};
        Object[] colabObj = new Object[]{colaborador.getId()};
        dependentes = dependenteManager.find(colaboradores, colabObj);

		return Action.SUCCESS;
	}

	public String delete() throws Exception {
		dependenteManager.remove(new Long[]{dependente.getId()});

		return Action.SUCCESS;
	}

	public Collection getDependentes() {
		return dependentes;
	}

	public Dependente getDependente(){
		if(dependente == null){
			dependente = new Dependente();
		}
		return dependente;
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
/* Autor: Igo Coelho
 * Data: 29/06/2006
 * Requisito: RFA020 */
package com.fortes.rh.web.action.captacao;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.captacao.EmpresaBdsManager;
import com.fortes.rh.model.captacao.EmpresaBds;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings("serial")
public class EmpresaBdsEditAction extends MyActionSupportEdit implements ModelDriven
{
	@Autowired private EmpresaBdsManager empresaBdsManager;
	private EmpresaBds empresaBds;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(empresaBds != null && empresaBds.getId() != null)
			empresaBds = (EmpresaBds) empresaBdsManager.findByIdProjection(empresaBds.getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		empresaBds.setEmpresa(SecurityUtil.getEmpresaSession(ActionContext.getContext().getSession()));
		empresaBdsManager.save(empresaBds);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		empresaBdsManager.update(empresaBds);
		return Action.SUCCESS;
	}

	public EmpresaBds getEmpresaBds(){
		if(empresaBds == null)
			empresaBds = new EmpresaBds();
		return empresaBds;
	}

	public void setEmpresaBds(EmpresaBds empresaBds)
	{
		this.empresaBds=empresaBds;
	}

	public Object getModel()
	{
		return getEmpresaBds();
	}
}
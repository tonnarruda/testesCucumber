package com.fortes.rh.web.action.desenvolvimento;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.desenvolvimento.DiaTurmaManager;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class DiaTurmaListAction extends MyActionSupportList
{
	@Autowired private DiaTurmaManager diaTurmaManager;

	private Collection<DiaTurma> diaTurmas;

	private DiaTurma diaTurma;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		diaTurmas = diaTurmaManager.findAll();

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		diaTurmaManager.remove(diaTurma.getId());

		return Action.SUCCESS;
	}

	public Collection<DiaTurma> getDiaTurmas()
	{
		return diaTurmas;
	}

	public DiaTurma getDiaTurma()
	{
		if (diaTurma == null)
			diaTurma = new DiaTurma();

		return diaTurma;
	}

	public void setDiaTurma(DiaTurma diaTurma)
	{
		this.diaTurma = diaTurma;
	}
}
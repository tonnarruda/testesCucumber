package com.fortes.rh.web.action.desenvolvimento;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.desenvolvimento.DiaTurmaManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"serial"})
public class DiaTurmaEditAction extends MyActionSupportEdit implements ModelDriven
{
	@Autowired private DiaTurmaManager diaTurmaManager;
	@Autowired private TurmaManager turmaManager;

	private DiaTurma diaTurma;

	private Collection<Turma> turmas;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(diaTurma != null && diaTurma.getId() != null)
			diaTurma = (DiaTurma) diaTurmaManager.findById(diaTurma.getId());

		this.turmas = turmaManager.findAll();
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
		diaTurmaManager.save(diaTurma);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		diaTurmaManager.update(diaTurma);
		return Action.SUCCESS;
	}

	public Object getModel()
	{
		return getDiaTurma();
	}

	public DiaTurma getDiaTurma()
	{
		if(diaTurma == null)
			diaTurma = new DiaTurma();
		return diaTurma;
	}

	public void setDiaTurma(DiaTurma diaTurma)
	{
		this.diaTurma = diaTurma;
	}

	public Collection<Turma> getTurmas()
	{
		return turmas;
	}
}
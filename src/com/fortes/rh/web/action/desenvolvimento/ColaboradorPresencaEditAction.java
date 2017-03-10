package com.fortes.rh.web.action.desenvolvimento;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.desenvolvimento.ColaboradorPresencaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.DiaTurmaManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"serial"})
public class ColaboradorPresencaEditAction extends MyActionSupportEdit implements ModelDriven
{
	@Autowired private ColaboradorPresencaManager colaboradorPresencaManager;
	@Autowired private ColaboradorTurmaManager colaboradorTurmaManager;
	@Autowired private DiaTurmaManager diaTurmaManager;

	private ColaboradorPresenca colaboradorPresenca;

	private Collection<ColaboradorTurma> colaboradorTurmas;
	private Collection<DiaTurma> diaTurmas;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(colaboradorPresenca != null && colaboradorPresenca.getId() != null)
			colaboradorPresenca = (ColaboradorPresenca) colaboradorPresencaManager.findById(colaboradorPresenca.getId());

		this.colaboradorTurmas = colaboradorTurmaManager.findAll();
		this.diaTurmas = diaTurmaManager.findAll();
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
		colaboradorPresencaManager.save(colaboradorPresenca);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		colaboradorPresencaManager.update(colaboradorPresenca);
		return Action.SUCCESS;
	}


	public Object getModel()
	{
		return getColaboradorPresenca();
	}

	public ColaboradorPresenca getColaboradorPresenca()
	{
		if(colaboradorPresenca == null)
			colaboradorPresenca = new ColaboradorPresenca();
		return colaboradorPresenca;
	}

	public void setColaboradorPresenca(ColaboradorPresenca colaboradorPresenca)
	{
		this.colaboradorPresenca = colaboradorPresenca;
	}

	public Collection<ColaboradorTurma> getColaboradorTurmas()
	{
		return colaboradorTurmas;
	}

	public void setColaboradorTurmas(Collection<ColaboradorTurma> colaboradorTurmas)
	{
		this.colaboradorTurmas = colaboradorTurmas;
	}

	public Collection<DiaTurma> getDiaTurmas()
	{
		return diaTurmas;
	}

	public void setDiaTurmas(Collection<DiaTurma> diaTurmas)
	{
		this.diaTurmas = diaTurmas;
	}
}
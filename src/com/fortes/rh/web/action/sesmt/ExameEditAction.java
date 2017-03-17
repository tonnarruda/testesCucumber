package com.fortes.rh.web.action.sesmt;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;

public class ExameEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;

	@Autowired private ExameManager exameManager;

	private Exame exame;

	private boolean jaValidouEmpresa = false;


	private void prepare() throws Exception
	{
		if(exame != null && exame.getId() != null)
			exame = (Exame) exameManager.findByIdProjection(exame.getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();

		if( ! jaValidouEmpresa)
			validaEmpresa();

		if(hasActionErrors())
			return ERROR;

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		valida();
		if(hasActionErrors())
		{
			prepareInsert();
			return ERROR;
		}

		exame.setEmpresa(getEmpresaSistema());
		exameManager.save(exame);

		return Action.SUCCESS;
	}

	private void valida()
	{
		if(StringUtils.isBlank(exame.getNome()))
			addActionError("Campo \"Nome\" em branco.");

		if(exame.getPeriodicidade() <= 0)
			exame.setPeriodicidade(0);

		// Se é update
		if(exame.getId() != null)
			validaEmpresa();
	}

	private void validaEmpresa()
	{
		Exame exameTmp = exameManager.findByIdProjection(exame.getId());

		if(! getEmpresaSistema().equals(exameTmp.getEmpresa()))
		{
			addActionError("O Exames solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
		}

		jaValidouEmpresa = true;
	}

	public String update() throws Exception
	{
		valida();
		if(hasActionErrors())
		{
			prepareUpdate();
			return ERROR;
		}

		exame.setEmpresa(getEmpresaSistema());
		exameManager.update(exame);

		return Action.SUCCESS;
	}

	public Exame getExame()
	{
		if(exame == null)
			exame = new Exame();
		return exame;
	}

	public void setExame(Exame exame)
	{
		this.exame = exame;
	}
}
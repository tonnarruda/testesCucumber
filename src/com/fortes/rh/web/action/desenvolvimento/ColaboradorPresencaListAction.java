package com.fortes.rh.web.action.desenvolvimento;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.desenvolvimento.ColaboradorPresencaManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class ColaboradorPresencaListAction extends MyActionSupportList
{
	@Autowired private ColaboradorPresencaManager colaboradorPresencaManager;

	private Collection colaboradorPresencas;

	private ColaboradorPresenca colaboradorPresenca;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		colaboradorPresencas = colaboradorPresencaManager.findAll();

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		colaboradorPresencaManager.remove(colaboradorPresenca.getId());

		return Action.SUCCESS;
	}

	public Collection getColaboradorPresencas()
	{
		return colaboradorPresencas;
	}

	public ColaboradorPresenca getColaboradorPresenca()
	{
		if (colaboradorPresenca == null)
			colaboradorPresenca = new ColaboradorPresenca();

		return colaboradorPresenca;
	}

	public void setColaboradorPresenca(ColaboradorPresenca colaboradorPresenca)
	{
		this.colaboradorPresenca = colaboradorPresenca;
	}
}
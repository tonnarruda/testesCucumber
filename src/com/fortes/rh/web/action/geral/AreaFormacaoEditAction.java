package com.fortes.rh.web.action.geral;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.AreaFormacaoManager;
import com.fortes.rh.model.geral.AreaFormacao;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"serial"})
public class AreaFormacaoEditAction extends MyActionSupportEdit implements ModelDriven
{
	@Autowired private AreaFormacaoManager areaFormacaoManager;

	private AreaFormacao areaFormacao;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(areaFormacao != null && areaFormacao.getId() != null)
			areaFormacao = (AreaFormacao) areaFormacaoManager.findById(areaFormacao.getId());

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
		areaFormacaoManager.save(areaFormacao);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		areaFormacaoManager.update(areaFormacao);
		return Action.SUCCESS;
	}

	public Object getModel()
	{
		return getAreaFormacao();
	}

	public AreaFormacao getAreaFormacao()
	{
		if(areaFormacao == null)
			areaFormacao = new AreaFormacao();
		return areaFormacao;
	}

	public void setAreaFormacao(AreaFormacao areaFormacao)
	{
		this.areaFormacao = areaFormacao;
	}
}
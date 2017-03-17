package com.fortes.rh.web.action.geral;


import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.MensagemManager;
import com.fortes.rh.model.geral.Mensagem;
import com.fortes.rh.web.action.MyActionSupport;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"serial"})
public class MensagemEditAction extends MyActionSupport implements ModelDriven
{
	@Autowired private MensagemManager mensagemManager;

	private Mensagem mensagem;


	/** Relationships **/


	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(mensagem != null && mensagem.getId() != null)
			mensagem = (Mensagem) mensagemManager.findById(mensagem.getId());

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
		mensagemManager.save(mensagem);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		mensagemManager.update(mensagem);
		return Action.SUCCESS;
	}


	public Object getModel()
	{
		return getMensagem();
	}

	public Mensagem getMensagem()
	{
		if(mensagem == null)
			mensagem = new Mensagem();
		return mensagem;
	}

	public void setMensagem(Mensagem mensagem)
	{
		this.mensagem = mensagem;
	}
}
package com.fortes.rh.web.action.geral;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.MensagemManager;
import com.fortes.rh.model.geral.Mensagem;
import com.fortes.rh.web.action.MyActionSupport;
import com.opensymphony.xwork.Action;


/**
* Demo: Simple CRUD Application with WebWork2
* 
* author $author
*/
@SuppressWarnings("serial")
public class MensagemListAction extends MyActionSupport
{
	@Autowired private MensagemManager mensagemManager;

	private Collection mensagems = null;

	private Mensagem mensagem;

	/**
	 * @see com.opensymphony.xwork.Action#execute()
	 */
	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String list() throws Exception {
		mensagems = mensagemManager.findAll();

		return Action.SUCCESS;
	}

	public String delete() throws Exception {		
		mensagemManager.remove(new Long[]{mensagem.getId()});

		return Action.SUCCESS;
	}

	/**
	 * @return
	 */
	public Collection getMensagems() {
		return mensagems;
	}


	public Mensagem getMensagem(){
		if(mensagem == null){
			mensagem = new Mensagem();
		}
		return mensagem;
	}

	public void setMensagem(Mensagem mensagem){
		this.mensagem=mensagem;
	}
}
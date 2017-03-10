package com.fortes.rh.web.action.pesquisa;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;


@SuppressWarnings("serial")
public class ColaboradorRespostaListAction extends MyActionSupportList
{
	@Autowired private ColaboradorRespostaManager colaboradorRespostaManager;

	private Collection<ColaboradorResposta> colaboradorRespostas;
	private ColaboradorResposta colaboradorResposta;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String list() throws Exception {
		colaboradorRespostas = colaboradorRespostaManager.findAll();

		return Action.SUCCESS;
	}

	public String delete() throws Exception {
		colaboradorRespostaManager.remove(new Long[]{colaboradorResposta.getId()});

		return Action.SUCCESS;
	}

	public ColaboradorResposta getColaboradorResposta(){
		if(colaboradorResposta == null){
			colaboradorResposta = new ColaboradorResposta();
		}
		return colaboradorResposta;
	}

	public void setColaboradorResposta(ColaboradorResposta colaboradorResposta){
		this.colaboradorResposta=colaboradorResposta;
	}

	public Collection<ColaboradorResposta> getColaboradorRespostas()
	{
		return colaboradorRespostas;
	}
}
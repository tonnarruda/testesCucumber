package com.fortes.rh.web.action.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.captacao.FormacaoManager;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings({"serial", "unchecked"})
public class FormacaoListAction extends MyActionSupportList
{
	@Autowired private FormacaoManager formacaoManager;
	private Collection<Formacao> formacaos;
	private Formacao formacao;
	private Long candidatoId;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		HttpServletResponse response = ServletActionContext.getResponse();

        response.addHeader("Expires", "0");
        response.addHeader("Pragma", "no-cache, no-store");
        response.addHeader("Content-Transfer-Encoding", "binary");
        response.addHeader("Cache-Control", "no-cache, no-store");

        Map session = ActionContext.getContext().getSession();
        formacaos = (Collection<Formacao>) session.get("SESSION_FORMACAO");

        if (candidatoId != null && formacaos == null)
        	formacaos = formacaoManager.findByCandidato(candidatoId);

        if (formacaos == null)
        	formacaos = new ArrayList<Formacao>();

        session.put("SESSION_FORMACAO",formacaos);

        return Action.SUCCESS;
    }

	public String delete() throws Exception
	{
		HttpServletResponse response = ServletActionContext.getResponse();

        response.addHeader("Expires", "0");
        response.addHeader("Pragma", "no-cache, no-store");
        response.addHeader("Content-Transfer-Encoding", "binary");
        response.addHeader("Cache-Control", "no-cache, no-store");

		Map session = ActionContext.getContext().getSession();
		Collection<Formacao> lista = (Collection<Formacao>) session.get("SESSION_FORMACAO");

		for (Formacao f : lista)
		{
			if (f.getId().equals(formacao.getId()))
			{
				lista.remove(f);
				break;
			}
		}

		session.put("SESSION_FORMACAO", lista);

		return Action.SUCCESS;
	}

	public Collection<Formacao> getFormacaos()
	{
		return formacaos;
	}

	public Formacao getFormacao(){
		if(formacao == null)
			formacao = new Formacao();
		return formacao;
	}

	public void setFormacao(Formacao formacao){
		this.formacao=formacao;
	}

	public Long getCandidatoId()
	{
		return candidatoId;
	}

	public void setCandidatoId(Long candidatoId)
	{
		this.candidatoId = candidatoId;
	}
}
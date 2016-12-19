package com.fortes.rh.web.action.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.captacao.CandidatoIdiomaManager;
import com.fortes.rh.business.captacao.IdiomaManager;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.model.captacao.Idioma;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings({"serial", "unchecked"})
public class IdiomaListAction extends MyActionSupportList
{
	@Autowired private IdiomaManager idiomaManager;
	@Autowired private CandidatoIdiomaManager candidatoIdiomaManager;
	private Collection<CandidatoIdioma> idiomasCandidato;
	private CandidatoIdioma candidatoIdioma;
	private Long candidatoId;
	private Collection<Idioma> idiomas;

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
        idiomasCandidato = (Collection<CandidatoIdioma>) session.get("SESSION_IDIOMA");

        if (candidatoId != null && idiomasCandidato == null)
        	idiomasCandidato = candidatoIdiomaManager.find(new String[]{"candidato.id"}, new Object[]{candidatoId});

        if (idiomasCandidato == null)
        	idiomasCandidato = new ArrayList<CandidatoIdioma>();

        session.put("SESSION_IDIOMA",idiomasCandidato);
        
        idiomas = idiomaManager.findAll(new String[]{"nome"});

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
        idiomasCandidato = (Collection<CandidatoIdioma>) session.get("SESSION_IDIOMA");

		for (CandidatoIdioma ci : idiomasCandidato)
		{
			if (ci.getId().equals(candidatoIdioma.getId()))
			{
				idiomasCandidato.remove(ci);
				break;
			}
		}

		session.put("SESSION_IDIOMA", idiomasCandidato);

		return Action.SUCCESS;
	}

	public CandidatoIdioma getCandidatoIdioma()
	{
		if (candidatoIdioma == null)
			candidatoIdioma = new CandidatoIdioma();
		return candidatoIdioma;
	}

	public void setCandidatoIdioma(CandidatoIdioma candidatoIdioma)
	{
		this.candidatoIdioma = candidatoIdioma;
	}

	public Collection<CandidatoIdioma> getIdiomasCandidato()
	{
		return idiomasCandidato;
	}

	public void setIdiomasCandidato(Collection<CandidatoIdioma> idiomasCandidato)
	{
		this.idiomasCandidato = idiomasCandidato;
	}

	public Long getCandidatoId()
	{
		return candidatoId;
	}

	public void setCandidatoId(Long candidatoId)
	{
		this.candidatoId = candidatoId;
	}

	public Collection<Idioma> getIdiomas() {
		return idiomas;
	}
}
package com.fortes.rh.web.action.captacao;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.captacao.IdiomaManager;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.model.captacao.Idioma;
import com.fortes.rh.model.dicionario.NivelIdioma;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"serial", "unchecked"})
public class IdiomaEditAction extends MyActionSupportEdit implements ModelDriven
{
	@Autowired private IdiomaManager idiomaManager;
	private Collection<Idioma> idiomas;
	private CandidatoIdioma candidatoIdioma;
	private Map nivelIdioma;

	private void prepare() throws Exception
	{
		HttpServletResponse response = ServletActionContext.getResponse();

        response.addHeader("Expires", "0");
        response.addHeader("Pragma", "no-cache, no-store");
        response.addHeader("Content-Transfer-Encoding", "binary");
        response.addHeader("Cache-Control", "no-cache, no-store");

		idiomas = idiomaManager.findAll(new String[]{"nome"});
		
		Map session = ActionContext.getContext().getSession();
		Collection<CandidatoIdioma> lista = (Collection<CandidatoIdioma>) session.get("SESSION_IDIOMA");
		
		for (CandidatoIdioma candidatoIdioma : lista) {
			idiomas.remove(candidatoIdioma.getIdioma());
		}
		nivelIdioma = new NivelIdioma();
	}

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String prepareInsert() throws Exception
	{
		prepare();

		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();

		Map session = ActionContext.getContext().getSession();
		Collection<CandidatoIdioma> lista = (Collection<CandidatoIdioma>) session.get("SESSION_IDIOMA");

		for (CandidatoIdioma ci : lista)
		{
			if (ci.getId().equals(candidatoIdioma.getId()))
			{
				candidatoIdioma = ci;
				break;
			}
		}

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		Map session = ActionContext.getContext().getSession();
		Collection<CandidatoIdioma> lista = (Collection<CandidatoIdioma>) session.get("SESSION_IDIOMA");

		Integer id =  (lista.size() + 1);
		candidatoIdioma.setId(Long.valueOf(id.toString()));
		//findById no idioma é muito pequno não precisa de projection
		candidatoIdioma.setIdioma(idiomaManager.findById(candidatoIdioma.getIdioma().getId()));

		lista.add(candidatoIdioma);
		session.put("SESSION_IDIOMA", lista);

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		Map session = ActionContext.getContext().getSession();
		Collection<CandidatoIdioma> lista = (Collection<CandidatoIdioma>) session.get("SESSION_IDIOMA");

		for (CandidatoIdioma ci : lista)
		{
			if (ci.getId().equals(candidatoIdioma.getId()))
			{
				lista.remove(ci);
				break;
			}
		}
		//findById no idioma é muito pequno não precisa de projection
		candidatoIdioma.setIdioma(idiomaManager.findById(candidatoIdioma.getIdioma().getId()));
		lista.add(candidatoIdioma);
		session.put("SESSION_IDIOMA", lista);

		return Action.SUCCESS;
	}

	public Object getModel()
	{
		return getCandidatoIdioma();
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

	public Collection<Idioma> getIdiomas()
	{
		return idiomas;
	}

	public void setIdiomas(Collection<Idioma> idiomas)
	{
		this.idiomas = idiomas;
	}

	public Map getNivelIdioma()
	{
		return nivelIdioma;
	}

	public void setNivelIdioma(Map nivelIdioma)
	{
		this.nivelIdioma = nivelIdioma;
	}
}
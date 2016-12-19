package com.fortes.rh.web.action.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.captacao.ExperienciaManager;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings({"serial", "unchecked"})
public class ExperienciaListAction extends MyActionSupportList
{
	@Autowired private ExperienciaManager experienciaManager;
	private Collection<Experiencia> experiencias;
	private Experiencia experiencia;
	private Long candidatoId;
	private Cargo nomeMercado;
	private Long empresaId;

	public Cargo getNomeMercado()
	{
		return nomeMercado;
	}

	public void setNomeMercado(Cargo nomeMercado)
	{
		this.nomeMercado = nomeMercado;
	}

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
        experiencias = (Collection<Experiencia>) session.get("SESSION_EXPERIENCIA");

        if (candidatoId != null && experiencias == null)
        	experiencias = experienciaManager.findByCandidato(candidatoId);

        if (experiencias == null)
        	experiencias = new ArrayList<Experiencia>();

        session.put("SESSION_EXPERIENCIA", experiencias);

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
		Collection<Experiencia> lista = (Collection<Experiencia>) session.get("SESSION_EXPERIENCIA");

		for (Experiencia e : lista)
		{
			if (e.getId().equals(experiencia.getId()))
			{
				lista.remove(e);
				break;
			}
		}

		session.put("SESSION_EXPERIENCIA", lista);

		return Action.SUCCESS;
	}

	public Collection<Experiencia> getExperiencias()
	{
		return experiencias;
	}

	public Experiencia getExperiencia()
	{
		if(experiencia == null)
			experiencia = new Experiencia();
		return experiencia;
	}

	public void setExperiencia(Experiencia experiencia)
	{
		this.experiencia = experiencia;
	}

	public Long getCandidatoId()
	{
		return candidatoId;
	}

	public void setCandidatoId(Long candidatoId)
	{
		this.candidatoId = candidatoId;
	}

	public Long getEmpresaId()
	{
		return empresaId;
	}

	public void setEmpresaId(Long empresaId)
	{
		this.empresaId = empresaId;
	}
}
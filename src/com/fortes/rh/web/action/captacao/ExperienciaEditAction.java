package com.fortes.rh.web.action.captacao;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings("unchecked")
public class ExperienciaEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;
	
	private Experiencia experiencia;
	private Collection<Cargo> nomeCargos;
	private Cargo cargo;
	private CargoManager cargoManager;
	private boolean checkNomeCargo = false;
	private Long empresaId;

	private void prepare() throws Exception
	{
		HttpServletResponse response = ServletActionContext.getResponse();

        response.addHeader("Expires", "0");
        response.addHeader("Pragma", "no-cache, no-store");
        response.addHeader("Content-Transfer-Encoding", "binary");
        response.addHeader("Cache-Control", "no-cache, no-store");

        Long empresaId;

        if(this.empresaId != null && this.empresaId > 0)
        	empresaId = this.empresaId;
        else
        	empresaId = getEmpresaSistema().getId();

        nomeCargos = cargoManager.findAllSelect("nomeMercado", null, Cargo.TODOS, empresaId);
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
		Collection<Experiencia> lista = (Collection<Experiencia>) session.get("SESSION_EXPERIENCIA");

		for (Experiencia e : lista)
		{
			if (e.getId().equals(experiencia.getId()))
			{
				experiencia = e;
				break;
			}
		}

		if(experiencia.getCargo() == null || experiencia.getCargo().getId() == null)
			checkNomeCargo = true;

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		Map session = ActionContext.getContext().getSession();
		Collection<Experiencia> lista = (Collection<Experiencia>) session.get("SESSION_EXPERIENCIA");

		Integer id =  (lista.size() + 1);
		experiencia.setId(Long.valueOf(id.toString()));

		lista.add(experiencia);
		session.put("SESSION_EXPERIENCIA", lista);

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
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

		lista.add(experiencia);
		session.put("SESSION_EXPERIENCIA", lista);

		return Action.SUCCESS;
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

	public Collection<Cargo> getNomeCargos()
	{
		return nomeCargos;
	}

	public void setNomeCargos(Collection<Cargo> nomeCargos)
	{
		this.nomeCargos = nomeCargos;
	}

	public void setCargoManager(CargoManager cargoManager)
	{
		this.cargoManager = cargoManager;
	}

	public Cargo getCargo()
	{
		return cargo;
	}

	public void setCargo(Cargo cargo)
	{
		this.cargo = cargo;
	}

	public boolean isCheckNomeCargo()
	{
		return checkNomeCargo;
	}

	public void setCheckNomeCargo(boolean checkNomeCargo)
	{
		this.checkNomeCargo = checkNomeCargo;
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
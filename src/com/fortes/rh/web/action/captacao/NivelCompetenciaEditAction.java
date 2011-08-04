package com.fortes.rh.web.action.captacao;


import java.util.Collection;

import com.fortes.rh.business.captacao.NivelCompetenciaManager;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class NivelCompetenciaEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private NivelCompetenciaManager nivelCompetenciaManager;
	private NivelCompetencia nivelCompetencia;
	private Collection<NivelCompetencia> nivelCompetencias;

	private void prepare() throws Exception
	{
		if(nivelCompetencia != null && nivelCompetencia.getId() != null)
			nivelCompetencia = (NivelCompetencia) nivelCompetenciaManager.findById(nivelCompetencia.getId());

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

	public String insert()
	{
		try
		{
			nivelCompetenciaManager.validaLimite(getEmpresaSistema().getId());
			nivelCompetencia.setEmpresa(getEmpresaSistema());
			nivelCompetenciaManager.save(nivelCompetencia);
			
			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			addActionMessage(e.getMessage());
			return Action.INPUT;
		}
	}

	public String update() throws Exception
	{
		nivelCompetenciaManager.update(nivelCompetencia);
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		nivelCompetencias = nivelCompetenciaManager.findAllSelect(getEmpresaSistema().getId());
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			nivelCompetenciaManager.remove(nivelCompetencia.getId());
			addActionMessage("Nível de Competência excluído com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir este Nível de Competência.");
		}

		return list();
	}
	
	public NivelCompetencia getNivelCompetencia()
	{
		if(nivelCompetencia == null)
			nivelCompetencia = new NivelCompetencia();
		return nivelCompetencia;
	}

	public void setNivelCompetencia(NivelCompetencia nivelCompetencia)
	{
		this.nivelCompetencia = nivelCompetencia;
	}

	public void setNivelCompetenciaManager(NivelCompetenciaManager nivelCompetenciaManager)
	{
		this.nivelCompetenciaManager = nivelCompetenciaManager;
	}
	
	public Collection<NivelCompetencia> getNivelCompetencias()
	{
		return nivelCompetencias;
	}
}

package com.fortes.rh.web.action.captacao;


import java.util.Collection;

import com.fortes.rh.business.captacao.NivelCompetenciaHistoricoManager;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class NivelCompetenciaHistoricoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private NivelCompetenciaHistoricoManager nivelCompetenciaHistoricoManager;
	private NivelCompetenciaHistorico nivelCompetenciaHistorico;
	private Collection<NivelCompetenciaHistorico> nivelCompetenciaHistoricos;

	public String list() throws Exception
	{
		nivelCompetenciaHistoricos = nivelCompetenciaHistoricoManager.find(new String[]{"empresa.id"}, new Object[]{getEmpresaSistema().getId()});
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			nivelCompetenciaHistoricoManager.remove(nivelCompetenciaHistorico.getId());
			addActionMessage("Histórico do nivel de competância excluído com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir este histórico do nivel de competância.");
		}

		return list();
	}
	
	public NivelCompetenciaHistorico getNivelCompetenciaHistorico()
	{
		if(nivelCompetenciaHistorico == null)
			nivelCompetenciaHistorico = new NivelCompetenciaHistorico();
		return nivelCompetenciaHistorico;
	}

	public void setNivelCompetenciaHistorico(NivelCompetenciaHistorico nivelCompetenciaHistorico)
	{
		this.nivelCompetenciaHistorico = nivelCompetenciaHistorico;
	}

	public void setNivelCompetenciaHistoricoManager(NivelCompetenciaHistoricoManager nivelCompetenciaHistoricoManager)
	{
		this.nivelCompetenciaHistoricoManager = nivelCompetenciaHistoricoManager;
	}
	
	public Collection<NivelCompetenciaHistorico> getNivelCompetenciaHistoricos()
	{
		return nivelCompetenciaHistoricos;
	}
}

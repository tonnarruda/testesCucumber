package com.fortes.rh.web.action.captacao;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.captacao.NivelCompetenciaHistoricoManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.util.ExceptionUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class NivelCompetenciaHistoricoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private NivelCompetenciaHistoricoManager nivelCompetenciaHistoricoManager;
	private NivelCompetenciaHistorico nivelCompetenciaHistorico;
	private Collection<NivelCompetenciaHistorico> nivelCompetenciaHistoricos;

	public String list() throws Exception
	{
		nivelCompetenciaHistoricos = nivelCompetenciaHistoricoManager.find(new String[]{"empresa.id"}, new Object[]{getEmpresaSistema().getId()});
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try{
			nivelCompetenciaHistoricoManager.removeNivelConfiguracaoHistorico(nivelCompetenciaHistorico.getId());
			addActionSuccess("Histórico dos níveis de competência excluído com sucesso.");
		
		}catch (FortesException e){
			addActionWarning(e.getMessage());
		}catch (Exception e){
			ExceptionUtil.traduzirMensagem(this, e, null);
			if(getActionWarnings().size() == 0)
				addActionError("Não foi possível excluir o histórico dos níveis de competência.");
			e.printStackTrace();
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

	public Collection<NivelCompetenciaHistorico> getNivelCompetenciaHistoricos()
	{
		return nivelCompetenciaHistoricos;
	}
}

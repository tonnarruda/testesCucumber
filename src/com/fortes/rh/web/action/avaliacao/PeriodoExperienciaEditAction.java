package com.fortes.rh.web.action.avaliacao;


import java.util.Collection;

import com.fortes.rh.business.avaliacao.PeriodoExperienciaManager;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class PeriodoExperienciaEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private PeriodoExperienciaManager periodoExperienciaManager;
	private PeriodoExperiencia periodoExperiencia;
	private Collection<PeriodoExperiencia> periodoExperiencias;

	private void prepare() throws Exception
	{
		if(periodoExperiencia != null && periodoExperiencia.getId() != null)
			periodoExperiencia = (PeriodoExperiencia) periodoExperienciaManager.findById(periodoExperiencia.getId());

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

	public String insert() throws Exception
	{
		periodoExperiencia.setEmpresa(getEmpresaSistema());
		periodoExperienciaManager.save(periodoExperiencia);
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		periodoExperienciaManager.update(periodoExperiencia);
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		periodoExperiencias = periodoExperienciaManager.findAllSelect(getEmpresaSistema().getId());
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		periodoExperienciaManager.remove(periodoExperiencia.getId());
		addActionMessage("Período de Acompanhamento de Experiência excluído com sucesso.");

		return Action.SUCCESS;
	}
	
	public PeriodoExperiencia getPeriodoExperiencia()
	{
		if(periodoExperiencia == null)
			periodoExperiencia = new PeriodoExperiencia();
		return periodoExperiencia;
	}

	public void setPeriodoExperiencia(PeriodoExperiencia periodoExperiencia)
	{
		this.periodoExperiencia = periodoExperiencia;
	}

	public void setPeriodoExperienciaManager(PeriodoExperienciaManager periodoExperienciaManager)
	{
		this.periodoExperienciaManager = periodoExperienciaManager;
	}
	
	public Collection<PeriodoExperiencia> getPeriodoExperiencias()
	{
		return periodoExperiencias;
	}
}
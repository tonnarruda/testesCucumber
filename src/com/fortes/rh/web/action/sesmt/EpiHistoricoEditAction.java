package com.fortes.rh.web.action.sesmt;

import java.util.Collection;

import com.fortes.rh.business.sesmt.EpiHistoricoManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiHistorico;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;

public class EpiHistoricoEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;
	
	private EpiHistoricoManager epiHistoricoManager;
	private EpiManager epiManager;

	private EpiHistorico epiHistorico;

	private Collection<Epi> epis;

	private Epi epi = new Epi();

	private boolean epiEhFardamento;

	public boolean isEpiEhFardamento()
	{
		return epiEhFardamento;
	}
	
	public String delete() throws Exception {
		
		epiHistoricoManager.remove(new Long[]{epiHistorico.getId()});

		return Action.SUCCESS;
	}

	public String prepareInsert() throws Exception
	{
		epi = epiManager.findById(epi.getId());
		epiEhFardamento = epi.getFardamento();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		epiHistorico = epiHistoricoManager.findById(epiHistorico.getId());
		epiEhFardamento = epiHistorico.getEpi().getFardamento();

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
    	if (dataJaCadastrada())
    	{
    		addActionError("Já existe um histórico cadastrado com esta data. Favor escolher outra data.");
    		return ERROR;
    	}
    	epiHistorico.setEpi(epi);
		epiHistoricoManager.save(epiHistorico);
		return Action.SUCCESS;
	}

	private boolean dataJaCadastrada()
	{
		Collection<EpiHistorico> historicos = epiHistoricoManager.find(new String[]{"data", "epi.id"}, new Object[]{epiHistorico.getData(), epiHistorico.getEpi().getId()});

		if(historicos.isEmpty())
			return false;
		else
		{
			boolean semProblema = true;
			for (EpiHistorico historico : historicos)
			{
				if(!historico.equals(epiHistorico))
				{
					semProblema = false;
				}
			}
			if(semProblema)
				return false;
			else
				return true;
		}
	}

	public String update() throws Exception
	{
    	if (dataJaCadastrada())
    	{
    		addActionError("Já existe um histórico cadastrado com esta data. Favor escolher outra data.");
    		return ERROR;
    	}

		epiHistoricoManager.update(epiHistorico);
		return Action.SUCCESS;
	}

	public Object getModel()
	{
		return getEpiHistorico();
	}

	public EpiHistorico getEpiHistorico()
	{
		if(epiHistorico == null)
			epiHistorico = new EpiHistorico();
		return epiHistorico;
	}

	public void setEpiHistorico(EpiHistorico epiHistorico)
	{
		this.epiHistorico = epiHistorico;
	}

	public void setEpiHistoricoManager(EpiHistoricoManager epiHistoricoManager)
	{
		this.epiHistoricoManager = epiHistoricoManager;
	}

	public Collection<Epi> getEpis()
	{
		return epis;
	}

	public void setEpis(Collection<Epi> epis)
	{
		this.epis = epis;
	}

	public void setEpiManager(EpiManager epiManager)
	{
		this.epiManager = epiManager;
	}

	public Epi getEpi()
	{
		return epi;
	}
}
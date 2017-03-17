package com.fortes.rh.web.action.sesmt;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.EpiHistoricoManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiHistorico;
import com.fortes.rh.util.ExceptionUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;

public class EpiHistoricoEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;
	
	@Autowired private EpiHistoricoManager epiHistoricoManager;
	@Autowired private EpiManager epiManager;

	private EpiHistorico epiHistorico;

	private Collection<Epi> epis;

	private Epi epi = new Epi();
	
	private boolean epiEhFardamento;
	private String epiNome = "";

	public boolean isEpiEhFardamento()
	{
		return epiEhFardamento;
	}
	
	public String delete() throws Exception {
		
		try {
			epiHistoricoManager.remove(epiHistorico.getId());
			addActionSuccess("Histórico do EPI excluído com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			
			ExceptionUtil.traduzirMensagem(this, e, null);
		}

		return Action.SUCCESS;
	}

	public String prepareInsert() throws Exception
	{
		epi = epiManager.findById(epi.getId());
		epiNome = epi.getNome();
		epiEhFardamento = epi.getFardamento();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		epiHistorico = epiHistoricoManager.findById(epiHistorico.getId());
		epiNome = epiHistorico.getEpi().getNome();
		epiEhFardamento = epiHistorico.getEpi().getFardamento();
		
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
    	if (dataJaCadastrada())
    	{
    		addActionMessage("Já existe um histórico cadastrado com esta data. Favor escolher outra data.");
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
    		addActionMessage("Já existe um histórico cadastrado com esta data. Favor escolher outra data.");
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

	public Collection<Epi> getEpis()
	{
		return epis;
	}

	public void setEpis(Collection<Epi> epis)
	{
		this.epis = epis;
	}

	public Epi getEpi()
	{
		return epi;
	}

	public String getEpiNome() {
		return epiNome;
	}
}
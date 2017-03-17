package com.fortes.rh.web.action.geral;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.ProvidenciaManager;
import com.fortes.rh.model.geral.Providencia;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class ProvidenciaEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private ProvidenciaManager providenciaManager;
	private Providencia providencia;
	private Collection<Providencia> providencias;

	private void prepare() throws Exception
	{
		if(providencia != null && providencia.getId() != null)
			providencia = (Providencia) providenciaManager.findById(providencia.getId());

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
		try {
			providencia.setEmpresa(getEmpresaSistema());
			providenciaManager.save(providencia);
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Erro ao gravar as informações da providência.");
			prepareInsert();
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try {
			providenciaManager.update(providencia);
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Erro ao editar as informações da providência.");
			prepareInsert();
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		providencias = providenciaManager.find(new String[]{"empresa.id"}, new Object[]{getEmpresaSistema().getId()}, new String[]{"descricao"});
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			providenciaManager.remove(providencia.getId());
			addActionMessage("Providência excluída com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir esta providência.");
		}

		return list();
	}
	
	public Providencia getProvidencia()
	{
		if(providencia == null)
			providencia = new Providencia();
		return providencia;
	}

	public void setProvidencia(Providencia providencia)
	{
		this.providencia = providencia;
	}
	
	public Collection<Providencia> getProvidencias()
	{
		return providencias;
	}
}
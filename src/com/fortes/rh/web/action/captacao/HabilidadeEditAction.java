package com.fortes.rh.web.action.captacao;


import java.util.Collection;

import com.fortes.rh.business.captacao.HabilidadeManager;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class HabilidadeEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private HabilidadeManager habilidadeManager;
	private Habilidade habilidade;
	private Collection<Habilidade> habilidades;

	private void prepare() throws Exception
	{
		if(habilidade != null && habilidade.getId() != null)
			habilidade = (Habilidade) habilidadeManager.findById(habilidade.getId());
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
		habilidade.setEmpresa(getEmpresaSistema());
		habilidadeManager.save(habilidade);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		habilidade.setEmpresa(getEmpresaSistema());
		habilidadeManager.update(habilidade);
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		String[] properties = new String[]{"id","nome"};
		String[] sets = new String[]{"id","nome"};
		String[] keys = new String[]{"empresa.id"};
		Object[] values = new Object[]{getEmpresaSistema().getId()};
		String[] orders = new String[]{"nome"};

		setTotalSize(habilidadeManager.getCount(keys, values));
		habilidades = habilidadeManager.findToList(properties, sets, keys, values,getPage(), getPagingSize(), orders);

		return Action.SUCCESS;
	}
	
	public String delete() throws Exception
	{
		habilidadeManager.remove(habilidade.getId());
		addActionMessage("Conhecimento excluído com sucesso.");

		return Action.SUCCESS;
	}
	
	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}
	
	public Habilidade getHabilidade()
	{
		if(habilidade == null)
			habilidade = new Habilidade();
		return habilidade;
	}

	public void setHabilidade(Habilidade habilidade)
	{
		this.habilidade = habilidade;
	}

	public void setHabilidadeManager(HabilidadeManager habilidadeManager)
	{
		this.habilidadeManager = habilidadeManager;
	}
	
	public Collection<Habilidade> getHabilidades()
	{
		return habilidades;
	}
}
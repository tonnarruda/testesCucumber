package com.fortes.rh.web.action.sesmt;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.AreaVivenciaManager;
import com.fortes.rh.model.sesmt.AreaVivencia;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class AreaVivenciaEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private AreaVivenciaManager areaVivenciaManager;
	
	private AreaVivencia areaVivencia;
	private Collection<AreaVivencia> areaVivencias;

	private String nome;
	
	private void prepare() throws Exception
	{
		if(areaVivencia != null && areaVivencia.getId() != null)
			areaVivencia = (AreaVivencia) areaVivenciaManager.findByIdProjection(areaVivencia.getId());

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
			areaVivencia.setEmpresa(getEmpresaSistema());
			areaVivenciaManager.save(areaVivencia);
			addActionSuccess("Área de vivência cadastrada com sucesso.");
			
			return Action.SUCCESS;
		} catch (Exception e) {
			prepare();
			e.printStackTrace();
			addActionError("Não foi possível cadastrar a área de vivência.");
			
			return Action.INPUT;
		}
	}

	public String update() throws Exception
	{
		try {
			areaVivenciaManager.update(areaVivencia);
			addActionSuccess("Área de vivência atualizada com sucesso.");
			
			return Action.SUCCESS;
		} catch (Exception e) {
			prepare();
			addActionError("Não foi possível atualizar a área de vivência.");
			e.printStackTrace();
			
			return Action.INPUT;
		}
	}

	public String list() throws Exception
	{
		areaVivencias = areaVivenciaManager.findAllSelect(nome, getEmpresaSistema().getId());
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			areaVivenciaManager.remove(areaVivencia.getId());
			addActionSuccess("Área de vivência excluída com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir esta área de vivência.");
		}

		return list();
	}
	
	public AreaVivencia getAreaVivencia()
	{
		if(areaVivencia == null)
			areaVivencia = new AreaVivencia();
		return areaVivencia;
	}

	public void setAreaVivencia(AreaVivencia areaVivencia)
	{
		this.areaVivencia = areaVivencia;
	}
	
	public Collection<AreaVivencia> getAreaVivencias()
	{
		return areaVivencias;
	}

	
	public String getNome()
	{
		return nome;
	}

	
	public void setNome(String nome)
	{
		this.nome = nome;
	}
}
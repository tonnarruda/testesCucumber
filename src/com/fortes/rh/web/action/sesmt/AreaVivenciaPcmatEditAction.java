package com.fortes.rh.web.action.sesmt;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.AreaVivenciaManager;
import com.fortes.rh.business.sesmt.AreaVivenciaPcmatManager;
import com.fortes.rh.model.sesmt.AreaVivencia;
import com.fortes.rh.model.sesmt.AreaVivenciaPcmat;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class AreaVivenciaPcmatEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private AreaVivenciaPcmatManager areaVivenciaPcmatManager;
	@Autowired private AreaVivenciaManager areaVivenciaManager;
	
	private AreaVivenciaPcmat areaVivenciaPcmat;
	private Pcmat pcmat;
	private Long ultimoPcmatId = 0L;
	
	private Collection<AreaVivenciaPcmat> areasVivenciaPcmat;
	private Collection<AreaVivencia> areasVivencia;

	private void prepare() throws Exception
	{
		areasVivencia = areaVivenciaManager.findAllSelect(null, getEmpresaSistema().getId());
		
		if (areaVivenciaPcmat != null && areaVivenciaPcmat.getId() != null)
			areaVivenciaPcmat = (AreaVivenciaPcmat) areaVivenciaPcmatManager.findById(areaVivenciaPcmat.getId());
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
			areaVivenciaPcmatManager.save(areaVivenciaPcmat); 
			addActionSuccess("Área de vivência cadastrada com sucesso.");
			
		} catch (Exception e) {
			prepare();
			e.printStackTrace();
			addActionError("Não foi possível cadastrar a área de vivência.");
			
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try {
			areaVivenciaPcmatManager.update(areaVivenciaPcmat);
			addActionSuccess("Área de vivência atualizada com sucesso.");
			
		} catch (Exception e) {
			prepare();
			e.printStackTrace();
			addActionError("Não foi possível atualizar a área de vivência.");
			
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		areasVivenciaPcmat = areaVivenciaPcmatManager.findByPcmat(pcmat.getId());
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			areaVivenciaPcmatManager.remove(areaVivenciaPcmat.getId());
			addActionSuccess("Área de vivência excluída com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir esta área de vivência.");
		}

		return list();
	}
	
	public AreaVivenciaPcmat getAreaVivenciaPcmat()
	{
		if(areaVivenciaPcmat == null)
			areaVivenciaPcmat = new AreaVivenciaPcmat();
		return areaVivenciaPcmat;
	}

	public void setAreaVivenciaPcmat(AreaVivenciaPcmat areaVivenciaPcmat)
	{
		this.areaVivenciaPcmat = areaVivenciaPcmat;
	}
	
	public Pcmat getPcmat() 
	{
		return pcmat;
	}

	public void setPcmat(Pcmat pcmat) 
	{
		this.pcmat = pcmat;
	}

	public Collection<AreaVivenciaPcmat> getAreasVivenciaPcmat() 
	{
		return areasVivenciaPcmat;
	}

	public void setAreasVivenciaPcmat(Collection<AreaVivenciaPcmat> areasVivenciaPcmat) 
	{
		this.areasVivenciaPcmat = areasVivenciaPcmat;
	}

	public Collection<AreaVivencia> getAreasVivencia() 
	{
		return areasVivencia;
	}

	public Long getUltimoPcmatId() {
		return ultimoPcmatId;
	}

	public void setUltimoPcmatId(Long ultimoPcmatId) {
		this.ultimoPcmatId = ultimoPcmatId;
	}
}
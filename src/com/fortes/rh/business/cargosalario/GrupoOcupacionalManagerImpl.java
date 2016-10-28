package com.fortes.rh.business.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.cargosalario.GrupoOcupacionalDao;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.web.tags.CheckBox;

@Component
public class GrupoOcupacionalManagerImpl extends GenericManagerImpl<GrupoOcupacional, GrupoOcupacionalDao> implements GrupoOcupacionalManager
{
	private CargoManager cargoManager;
	
	@Autowired
	GrupoOcupacionalManagerImpl(GrupoOcupacionalDao dao) {
		setDao(dao);
	}
	
	public Integer getCount(Long empresaId)
	{
		return getDao().getCount(empresaId);
	}

	public Collection<GrupoOcupacional> findAllSelect(Long empresaId)
	{
		return getDao().findAllSelect(0 ,0 , empresaId);
	}

	public Collection<GrupoOcupacional> findAllSelect(int page, int pagingSize, Long empresaId)
	{
		return getDao().findAllSelect(page ,pagingSize , empresaId);
	}

	public Collection<CheckBox> populaCheckOrderNome(Long empresaId)
	{
		Collection<CheckBox> checks = new ArrayList<CheckBox>();
		try
		{
			Collection<GrupoOcupacional> grupos = getDao().findAllSelect(0,0,empresaId);
			checks = CheckListBoxUtil.populaCheckListBox(grupos, "getId", "getNome", null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return checks;
	}

	public GrupoOcupacional findByIdProjection(Long grupoOcupacionalId)
	{
		GrupoOcupacional grupoOcupacional = getDao().findByIdProjection(grupoOcupacionalId); 

		if(grupoOcupacional != null)
			grupoOcupacional.setCargos(cargoManager.findByGrupoOcupacional(grupoOcupacional.getId()));
			
		return grupoOcupacional;
	}
	
	public Collection<GrupoOcupacional> findByEmpresasIds(Long... empresaIds) 
	{
		return getDao().findByEmpresasIds(empresaIds);
	}

	public void setCargoManager(CargoManager cargoManager)
	{
		this.cargoManager = cargoManager;
	}
}
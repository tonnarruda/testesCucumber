package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.util.CollectionUtil;

public class GrupoOcupacionalDWR
{
	private GrupoOcupacionalManager grupoOcupacionalManager;
	
	@SuppressWarnings("unchecked")
	public Map<Object, Object> getByEmpresa(Long empresaId)
	{
		Collection<GrupoOcupacional> grupoOcupacionals;
		
		if(empresaId == -1)//Caso a empresa passada seja -1, vai trazer todos
			grupoOcupacionals = grupoOcupacionalManager.findAll();
		else
			grupoOcupacionals = grupoOcupacionalManager.findAllSelect(empresaId);
		
		return new CollectionUtil<GrupoOcupacional>().convertCollectionToMap(grupoOcupacionals, "getId", "getNome");
	}

	public void setGrupoOcupacionalManager(GrupoOcupacionalManager grupoOcupacionalManager) {
		this.grupoOcupacionalManager = grupoOcupacionalManager;
	}
}

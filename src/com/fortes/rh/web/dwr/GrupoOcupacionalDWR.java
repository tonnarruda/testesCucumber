package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.util.CollectionUtil;

@Component
@RemoteProxy(name="GrupoOcupacionalDWR")
@SuppressWarnings("unchecked")
public class GrupoOcupacionalDWR
{
	@Autowired private GrupoOcupacionalManager grupoOcupacionalManager;
	
	@RemoteMethod
	public Map<Object, Object> getByEmpresa(Long empresaId)
	{
		Collection<GrupoOcupacional> grupoOcupacionals;
		
		if(empresaId == -1)//Caso a empresa passada seja -1, vai trazer todos
			grupoOcupacionals = grupoOcupacionalManager.findAll();
		else
			grupoOcupacionals = grupoOcupacionalManager.findAllSelect(empresaId);
		
		return new CollectionUtil<GrupoOcupacional>().convertCollectionToMap(grupoOcupacionals, "getId", "getNome");
	}
	
	@RemoteMethod
	public Map<Long, String> getByEmpresas(Long empresaId, Long[] empresaIds) throws Exception
	{
		Collection<GrupoOcupacional> grupos = new ArrayList<GrupoOcupacional>();
		if((empresaId == null || empresaId == 0 || empresaId == -1 ) && empresaIds.length > 0)
			grupos = grupoOcupacionalManager.findByEmpresasIds(empresaIds);
		else
			grupos = grupoOcupacionalManager.findByEmpresasIds(empresaId);

		return new CollectionUtil<GrupoOcupacional>().convertCollectionToMap(grupos, "getId", "getNome");
	}
}
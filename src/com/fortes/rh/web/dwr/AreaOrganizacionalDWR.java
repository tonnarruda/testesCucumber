package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.util.CollectionUtil;

public class AreaOrganizacionalDWR
{
	private AreaOrganizacionalManager areaOrganizacionalManager;

	public void verificaMaternidade(Long areaId) throws Exception
	{
		if(areaOrganizacionalManager.verificaMaternidade(areaId))
			throw new Exception("Não é possível alocar colaboradores em áreas que possuem sub-áreas.\nSelecione uma das sub-áreas desta área.");
	}
	
	@SuppressWarnings("unchecked")
	public Map<Object, Object> getByEmpresa(Long empresaId) throws Exception
	{
		Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
		
		if(empresaId == -1) // Traz todas as áreas, com nome da Empresa na descrição.
			areaOrganizacionals = areaOrganizacionalManager.findAll();
		else
			areaOrganizacionals = areaOrganizacionalManager.findAllList(empresaId, AreaOrganizacional.TODAS);
		
		areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);
		CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
		areaOrganizacionals = cu1.sortCollectionStringIgnoreCase(areaOrganizacionals, "descricao");

		return new CollectionUtil<AreaOrganizacional>().convertCollectionToMap(areaOrganizacionals, "getId", "getDescricao");
	}

	@SuppressWarnings("unchecked")
	public Map<Object, Object> getByEmpresas(Long empresaId, Long[] empresaIds) throws Exception
	{
		Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
		if(empresaId == 0)
			areaOrganizacionals = areaOrganizacionalManager.findByEmpresasIds(empresaIds, AreaOrganizacional.TODAS);
		else
			areaOrganizacionals = areaOrganizacionalManager.findAllList(empresaId, AreaOrganizacional.TODAS);

		areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);
		CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
		areaOrganizacionals = cu1.sortCollectionStringIgnoreCase(areaOrganizacionals, "descricaoComEmpresa");

		return new CollectionUtil<AreaOrganizacional>().convertCollectionToMap(areaOrganizacionals, "getId", "getDescricaoComEmpresa");
	}
	
	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}
}

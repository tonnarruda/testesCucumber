package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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

		return new CollectionUtil<AreaOrganizacional>().convertCollectionToMap(areaOrganizacionals, "getId", ((empresaId == null || empresaId < 0) ? "getDescricaoComEmpresa" : "getDescricao") );
	}

	@SuppressWarnings("unchecked")
	public Map<Object, Object> getByEmpresas(Long empresaId, Long[] empresaIds) throws Exception
	{
		Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
		if(empresaId == 0 || empresaId == -1 )
			areaOrganizacionals = areaOrganizacionalManager.findByEmpresasIds(empresaIds, AreaOrganizacional.TODAS);
		else
			areaOrganizacionals = areaOrganizacionalManager.findAllList(empresaId, AreaOrganizacional.TODAS);

		areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);
		CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
		areaOrganizacionals = cu1.sortCollectionStringIgnoreCase(areaOrganizacionals, "descricaoComEmpresa");

		return new CollectionUtil<AreaOrganizacional>().convertCollectionToMap(areaOrganizacionals, "getId", "getDescricaoComEmpresa");
	}

	@SuppressWarnings("unchecked")
	public Map<Object, Object> getEmailsResponsaveis(Long id, Long empresaId) throws Exception
	{
		Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findAllList(empresaId, true); 
		areas = areaOrganizacionalManager.getAncestrais(areas, id);
		Collection<AreaOrganizacional> areasComEmailResp = new ArrayList<AreaOrganizacional>();
		Map<Object, Object> emailsResponsaveis = new HashMap<Object, Object>();
		Collection<String> emailsNotificacoes = new ArrayList<String>();
		
		for (AreaOrganizacional area : areas) 
		{
			if(area.getResponsavel() != null && area.getResponsavel().getContato() != null && area.getResponsavel().getContato().getEmail() != null && !area.getResponsavel().getContato().getEmail().equals(""))
				areasComEmailResp.add(area);
			
			if(area.getEmailsNotificacoes() != null)
				for (String email : area.getEmailsNotificacoes().split(";"))
					emailsNotificacoes.add(email);
		}
		
		emailsResponsaveis = new CollectionUtil<AreaOrganizacional>().convertCollectionToMap(areasComEmailResp, "getResponsavelEmail", "getResponsavelEmailNomeComercial"); 
		
		for (String email : emailsNotificacoes)
			emailsResponsaveis.put(email, email);
		
		return emailsResponsaveis;
	}
	
	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}
}

package com.fortes.rh.web.dwr;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.AreaOrganizacionalOrganograma;
import com.fortes.rh.util.BooleanUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.web.tags.CheckBox;

public class AreaOrganizacionalDWR
{
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private UsuarioEmpresaManager usuarioEmpresaManager;

	public void verificaMaternidade(Long areaId) throws Exception
	{
		if(areaOrganizacionalManager.verificaMaternidade(areaId, null))
			throw new Exception("Não é possível alocar colaboradores em áreas que possuem sub-áreas.\nSelecione uma das sub-áreas desta área.");
	}

	public boolean verificaAlteracaoStatusAtivo(Long areaId, Long areaMaeId) throws Exception
	{
		return areaOrganizacionalManager.verificaAlteracaoStatusAtivo(areaId, areaMaeId);
	}
	
	@SuppressWarnings("unchecked")
	public Map<Object, Object> getByEmpresa(Long empresaId) throws Exception
	{
		Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
		areaOrganizacionals = areaOrganizacionalManager.findByEmpresa(empresaId);
		
		areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);
		CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
		
		areaOrganizacionals = cu1.sortCollectionStringIgnoreCase(areaOrganizacionals, ((empresaId == null || empresaId < 0) ? "descricaoComEmpresaStatusAtivo" : "descricaoStatusAtivo"));
		
		return cu1.convertCollectionToMap(areaOrganizacionals, "getId", ((empresaId == null || empresaId < 0) ? "getDescricaoComEmpresaStatusAtivo" : "getDescricaoStatusAtivo") ); 
	}

	@SuppressWarnings("unchecked")
	public Map<Object, Object> getByEmpresas(Long empresaId, Long[] empresaIds, Character ativa) throws Exception
	{
		Boolean areaAtiva = ativa != null ? BooleanUtil.getValueCombo(ativa) : null;
		
		Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
		if(empresaId == null || empresaId == 0 || empresaId == -1 )
			areaOrganizacionals = areaOrganizacionalManager.findByEmpresasIds(empresaIds, areaAtiva);
		else
			areaOrganizacionals = areaOrganizacionalManager.findAllListAndInativas(areaAtiva, null, empresaId);

		areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);
		CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
		areaOrganizacionals = cu1.sortCollectionStringIgnoreCase(areaOrganizacionals, "descricaoComEmpresaStatusAtivo");

		return new CollectionUtil<AreaOrganizacional>().convertCollectionToMap(areaOrganizacionals, "getId", "getDescricaoComEmpresaStatusAtivo");
	}

	public Collection<AreaOrganizacional> getByEmpresasAndLnt(Long[] empresaIds, Long lntId) throws Exception{
		return  areaOrganizacionalManager.findByLntIdComEmpresa(lntId, empresaIds);
	}
	
	public Collection<CheckBox> getCheckboxByEmpresas(Long[] empresaIds) throws Exception
	{
		Collection<CheckBox> areas = new ArrayList<CheckBox>();
		
		for (Long empresaId : empresaIds) {
			areas.addAll(areaOrganizacionalManager.populaCheckComParameters(empresaId));
		}

		return areas;
	}
	
    @SuppressWarnings("unchecked")
    public Map<Object, Object> getPermitidasByEmpresas(String naoApagar, HttpServletRequest request, Long empresaId, Long[] empresaIds) throws Exception {
        Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.filtraPermitidasByEmpresasAndUsuario(request, empresaId, empresaIds);
        return new CollectionUtil<AreaOrganizacional>().convertCollectionToMap(areaOrganizacionals, "getId", "getDescricaoComEmpresaStatusAtivo");
    }
	
	public Collection<CheckBox> getPermitidasCheckboxByEmpresas(String naoApagar, HttpServletRequest request, Long empresaId, Long[] empresaIds) throws Exception
	{
		Collection<CheckBox> checks = new ArrayList<CheckBox>();

		Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.filtraPermitidasByEmpresasAndUsuario(request, empresaId, empresaIds);
		checks = CheckListBoxUtil.populaCheckListBox(areaOrganizacionals, "getId", "getDescricaoComEmpresaStatusAtivo", new String[] { "getIdAreaMae" });
	
		return checks;
	}
	
	public Collection<Long> excluiFilhas(Long areasOrganizacionaisId) throws Exception{
		return areaOrganizacionalManager.findIdsAreasFilhas(Arrays.asList(areasOrganizacionaisId));
	}

	@SuppressWarnings("unchecked")
	public Map<Long, String> findAllListAndInativas(Long empresaId, Long areaOrganizacionalInativaId) throws Exception
	{
		CollectionUtil<AreaOrganizacional> areaOrganizacionalsUtil = new CollectionUtil<AreaOrganizacional>();
		
		Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.ATIVA, Arrays.asList(areaOrganizacionalInativaId), empresaId);
		areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);
		areaOrganizacionals = areaOrganizacionalsUtil.sortCollectionStringIgnoreCase(areaOrganizacionals, "descricao");
		
		return new CollectionUtil<AreaOrganizacional>().convertCollectionToMap(areaOrganizacionals, "getId", "getDescricao");
	}

	@SuppressWarnings("unchecked")
	public Map<Object, Object> getEmailsResponsaveis(Long id, Long empresaId) throws Exception
	{
		Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findAllListAndInativas(true, null, empresaId); 
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
	
	public Collection<AreaOrganizacional> findByEmpresa(Long empresaId, Boolean ativo) throws Exception
	{
		Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findAllList(0, 0, null, empresaId, ativo);
		areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);
		
		CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
		areaOrganizacionals = cu1.sortCollectionStringIgnoreCase(areaOrganizacionals, "descricaoStatusAtivo");
		
		return areaOrganizacionals;
	}
	
	public String getOrganogramaByEmpresaJson(Long empresaId, Long areaId, Boolean ativa) throws Exception
	{
		Collection<AreaOrganizacional> areaOrganizacionals 	= new ArrayList<AreaOrganizacional>();
		Collection<AreaOrganizacional> areasAncestrais 		= new ArrayList<AreaOrganizacional>();
		Collection<AreaOrganizacional> areasDescendentes 	= new ArrayList<AreaOrganizacional>();
		
		areaOrganizacionals = areaOrganizacionalManager.findAllList(0, 0, null, empresaId, ativa);
		
		if (areaId != null)
		{
			areasAncestrais = areaOrganizacionalManager.getAncestrais(areaOrganizacionals, areaId);
			areasDescendentes = areaOrganizacionalManager.getDescendentes(areaOrganizacionals, areaId, new ArrayList<AreaOrganizacional>());
			
			areaOrganizacionals.clear();
			areaOrganizacionals.addAll(areasAncestrais);
			areaOrganizacionals.addAll(areasDescendentes);
		}

		areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);

		if (areaOrganizacionals.isEmpty())
			areaOrganizacionals.add(areaOrganizacionalManager.findEntidadeComAtributosSimplesById(areaId));
		
		CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
		areaOrganizacionals = cu1.sortCollectionStringIgnoreCase(areaOrganizacionals, "descricaoStatusAtivo");
		
		Collection<AreaOrganizacionalOrganograma> dados = new ArrayList<AreaOrganizacionalOrganograma>();
		AreaOrganizacionalOrganograma areaOrganograma = null;
		
		for (AreaOrganizacional area : areaOrganizacionals) 
		{
			if (area.getAreaMae() == null || area.getAreaMae().getId() == null)
			{
				areaOrganograma = new AreaOrganizacionalOrganograma(area.getId().toString(), area.getNome(), area.getResponsavelNomeComercial(), area.getCoResponsavelNomeComercial(), "subordinate");
				setFilhas(areaOrganizacionals, areaOrganograma);
				
				dados.add(areaOrganograma);
			}
		}
		
		return StringUtil.toJSON(dados, null);
	}
	
	public boolean areaSemColaboradores(Long areaOrganizacionalId) {
		AreaOrganizacional areaOrganizacional = areaOrganizacionalManager.findByIdProjection(areaOrganizacionalId);
		return areaOrganizacionalManager.verificarColaboradoresAreaMae(areaOrganizacional);
	}
	
	private void setFilhas(Collection<AreaOrganizacional> areas, AreaOrganizacionalOrganograma areaOrg)
	{
		AreaOrganizacionalOrganograma areaOrgFilha = null;
		
		for (AreaOrganizacional area : areas) 
		{
			if (area.getAreaMae() != null && area.getAreaMae().getId() != null && areaOrg.getId().equals(area.getAreaMae().getId().toString()))
			{
				areaOrgFilha = new AreaOrganizacionalOrganograma(area.getId().toString(), area.getNome(),  area.getResponsavelNomeComercial(), area.getCoResponsavelNomeComercial(), "subordinate");
				setFilhas(areas, areaOrgFilha);
				
				if (areaOrg.getChildren() == null)
					areaOrg.setChildren(new ArrayList<AreaOrganizacionalOrganograma>());
				
				areaOrg.getChildren().add(areaOrgFilha);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public Map<Object, Object> getAreasPermitidas(Long usuarioId, Long empresaId, Long[] empresasIds) throws Exception
	{		
		Usuario usuario = new Usuario();
		usuario.setId(usuarioId);
			
		Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
	
		if( !(empresaId == null || empresaId.equals(-1L)) )
			empresasIds = new Long[]{empresaId};
		
		setAreasPermitidas(usuario, empresasIds, areaOrganizacionals);
		
		areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);
		CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
		areaOrganizacionals = cu1.sortCollectionStringIgnoreCase(areaOrganizacionals, "descricaoComEmpresaStatusAtivo");

		return new CollectionUtil<AreaOrganizacional>().convertCollectionToMap(areaOrganizacionals, "getId", "getDescricaoComEmpresaStatusAtivo");
	}
	
	private void setAreasPermitidas(Usuario usuario, Long[] empresaIds, Collection<AreaOrganizacional> areas) throws Exception {
		if(!usuario.getId().equals(1L)){
			for (Long empresaId : empresaIds) {
				if ( usuarioEmpresaManager.containsRole(usuario.getId(), empresaId, "ROLE_VER_AREAS") ) {
					areas.addAll(areaOrganizacionalManager.findByEmpresa(empresaId));
				} else {
					areas.addAll(areaOrganizacionalManager.findAreasByUsuarioResponsavel(usuario, empresaId));
				}
			}
		}
		else  areas.addAll(areaOrganizacionalManager.findByEmpresasIds(empresaIds, null));
			
	}
	
	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setUsuarioEmpresaManager(UsuarioEmpresaManager usuarioEmpresaManager) {
		this.usuarioEmpresaManager = usuarioEmpresaManager;
	}
}

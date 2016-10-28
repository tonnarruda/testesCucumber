/*@author Moesio Medeiros
 * Data: 06/06/2006
 * Requisito: RFA014 - Cadastro de Área de Interesse
 */

package com.fortes.rh.business.geral;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.annotations.TesteAutomatico;
import com.fortes.rh.dao.geral.AreaInteresseDao;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.AreaOrganizacional;

@Component
public class AreaInteresseManagerImpl extends GenericManagerImpl<AreaInteresse, AreaInteresseDao> implements AreaInteresseManager
{
	private AreaOrganizacionalManager areaOrganizacionalManager;
	
	@Autowired
	AreaInteresseManagerImpl(AreaInteresseDao dao) {
		setDao(dao);
	}
	
	public Collection<AreaInteresse> findAreasInteresseByAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		return getDao().findAreasInteresseByAreaOrganizacional(areaOrganizacional);
	}
	
	public Collection<AreaInteresse> findAllSelect(Long... empresaIds)
	{
		return getDao().findAllSelect(empresaIds);
	}

	public AreaInteresse findByIdProjection(Long areaInteresseId)
	{
		return getDao().findByIdProjection(areaInteresseId);
	}

	public void sincronizar(Long empresaOrigemId, Long empresaDestinoId, Map<Long, Long> areaIds, Map<Long, Long> areaInteresseIds) {
		
		Collection<AreaInteresse> areasInteresseDeOrigem = getDao().findSincronizarAreasInteresse(empresaOrigemId);
		
		for (AreaInteresse areaInteresse : areasInteresseDeOrigem)
		{
			Long areaInteresseOrigemId = areaInteresse.getId();
			clonar(areaInteresse, empresaDestinoId);
			areaInteresseIds.put(areaInteresseOrigemId, areaInteresse.getId());
			
			if(areaIds != null && areaIds.size() > 0)
			{
				Collection<AreaOrganizacional> areasOrganizacionais = areaOrganizacionalManager.getAreasByAreaInteresse(areaInteresseOrigemId);
				popularAreasComIds(areaIds, areasOrganizacionais);
				areaInteresse.setAreasOrganizacionais(areasOrganizacionais);
			}
			update(areaInteresse);
		}
	}

	private void popularAreasComIds(Map<Long, Long> areaIds, Collection<AreaOrganizacional> areas) {
		
		for (AreaOrganizacional areaOrganizacional : areas)
		{
			Long id = areaIds.get( areaOrganizacional.getId() );
			
			if (id == null)
				continue;
			
			areaOrganizacional.setId(id);
		}
	}

	private void clonar(AreaInteresse areaInteresse, Long empresaDestinoId) {
		
		areaInteresse.setId(null);
		areaInteresse.setAreasOrganizacionais(null);
		areaInteresse.setEmpresaId(empresaDestinoId);
		
		getDao().save(areaInteresse);
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	@TesteAutomatico
	public void deleteByAreaOrganizacional(Long[] areaIds) throws Exception {
		getDao().deleteByAreaOrganizacional(areaIds);
	}
}
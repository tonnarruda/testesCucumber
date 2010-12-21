/* Autor: Robertson Freitas
 * Data: 16/06/2006
 * Requisito: RFA33 */
package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.web.tags.CheckBox;

public class ConhecimentoManagerImpl extends GenericManagerImpl<Conhecimento, ConhecimentoDao> implements ConhecimentoManager
{
	AreaOrganizacionalManager areaOrganizacionalManager;

	public Collection<Conhecimento> findByAreasOrganizacionalIds(Long[] areasOrganizacionais, Long empresaId)
	{
		return getDao().findByAreaOrganizacionalIds(areasOrganizacionais,empresaId);
	}

	public Collection<Conhecimento> findByAreaInteresse(Long[] longs, Long empresaId)
	{
		return getDao().findByAreaInteresse(longs, empresaId);
	}

	public Collection<Conhecimento> findAllSelect(Long empresaId)
	{
		return getDao().findAllSelect(empresaId);
	}

	public Collection<Conhecimento> findByCargo(Long cargoId)
	{
		return getDao().findByCargo(cargoId);
	}

	public Collection<CheckBox> populaCheckOrderNome(Long empresaId)
	{
		Collection<CheckBox> checks = new ArrayList<CheckBox>();
		try
		{
			Collection<Conhecimento> conhecimentos = getDao().findAllSelect(empresaId);
			checks = CheckListBoxUtil.populaCheckListBox(conhecimentos, "getId", "getNome");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return checks;
	}

	public Collection<CheckBox> populaCheckOrderNomeByAreaOrganizacionals(Long[] areasId, long empresaId)
	{
		Collection<CheckBox> checks = new ArrayList<CheckBox>();
		try
		{
			Collection<Conhecimento> conhecimentos = getDao().findByAreaOrganizacionalIds(areasId, empresaId);
			checks = CheckListBoxUtil.populaCheckListBox(conhecimentos, "getId", "getNome");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return checks;
	}

	public Collection<Conhecimento> populaConhecimentos(String[] conhecimentosCheck)
	{
		Collection<Conhecimento> conhecimentos = new ArrayList<Conhecimento>();

		if(conhecimentosCheck != null && conhecimentosCheck.length > 0)
		{
			Long conhecimentosIds[] = LongUtil.arrayStringToArrayLong(conhecimentosCheck);

			Conhecimento conhecimento;
			for (Long conhecimentoId: conhecimentosIds)
			{
				conhecimento = new Conhecimento();
				conhecimento.setId(conhecimentoId);

				conhecimentos.add(conhecimento);
			}
		}

		return conhecimentos;
	}

	public Conhecimento findByIdProjection(Long conhecimentoId)
	{
		Conhecimento conhecimento = getDao().findByIdProjection(conhecimentoId);
		if(conhecimento != null)
			conhecimento.setAreaOrganizacionals(areaOrganizacionalManager.findByConhecimento(conhecimentoId));

		return conhecimento;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public Collection<Conhecimento> findAllSelectDistinctNome()
	{
		return getDao().findAllSelectDistinctNome();
	}

	public Collection<Conhecimento> findByCandidato(Long candidatoId)
	{
		return getDao().findByCandidato(candidatoId);
	}

	public void sincronizar(Long empresaOrigemId, Long empresaDestinoId, Map<Long, Long> areaIds, Map<Long, Long> conhecimentoIds) {
		
		Collection<Conhecimento> conhecimentosDeOrigem = getDao().findSincronizarConhecimentos(empresaOrigemId);
		
		for (Conhecimento conhecimento : conhecimentosDeOrigem)
		{
			Long conhecimentoOrigemId = conhecimento.getId();
			clonar(conhecimento, empresaDestinoId);
			conhecimentoIds.put(conhecimentoOrigemId, conhecimento.getId());
			
			Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findByConhecimento(conhecimentoOrigemId);
			popularAreasComIds(areaIds, areas);
			
			conhecimento.setAreaOrganizacionals(areas);
			update(conhecimento);
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

	private void clonar(Conhecimento conhecimento, Long empresaDestinoId) {
		
		conhecimento.setId(null);
		conhecimento.setAreaOrganizacionals(null);
		conhecimento.setEmpresaId(empresaDestinoId);
		
		getDao().save(conhecimento);
	}
	
}
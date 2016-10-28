/* Autor: Robertson Freitas
 * Data: 16/06/2006
 * Requisito: RFA33 */
package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.CriterioAvaliacaoCompetencia;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.web.tags.CheckBox;

@Component
public class ConhecimentoManagerImpl extends GenericManagerImpl<Conhecimento, ConhecimentoDao> implements ConhecimentoManager
{
	AreaOrganizacionalManager areaOrganizacionalManager;
	CriterioAvaliacaoCompetenciaManager criterioAvaliacaoCompetenciaManager;
	
	@Autowired
	ConhecimentoManagerImpl(ConhecimentoDao conhecimentoDao) {
		setDao(conhecimentoDao);
	}

	public Collection<Conhecimento> findByAreasOrganizacionalIds(Long[] areasOrganizacionais, Long empresaId)
	{
		return getDao().findByAreaOrganizacionalIds(areasOrganizacionais,empresaId);
	}

	public Collection<Conhecimento> findByAreaInteresse(Long[] areasChek, Long empresaId)
	{
		return getDao().findByAreaInteresse(areasChek, empresaId);
	}

	public Collection<Conhecimento> findAllSelect(Long... empresaIds)
	{
		return getDao().findAllSelect(empresaIds);
	}

	public Collection<Conhecimento> findByCargo(Long cargoId)
	{
		return getDao().findByCargo(cargoId);
	}

	public Collection<CheckBox> populaCheckOrderNome(Long... empresaIds)
	{
		Collection<CheckBox> checks = new ArrayList<CheckBox>();
		try
		{
			Collection<Conhecimento> conhecimentos = getDao().findAllSelect(empresaIds);
			checks = CheckListBoxUtil.populaCheckListBox(conhecimentos, "getId", "getNome", null);
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
			checks = CheckListBoxUtil.populaCheckListBox(conhecimentos, "getId", "getNome", null);
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
		
		if(conhecimento != null) {
			CursoManager cursoManager = (CursoManager) SpringUtil.getBean("cursoManager");
			conhecimento.setAreaOrganizacionals(areaOrganizacionalManager.findByConhecimento(conhecimentoId));
			conhecimento.setCursos(cursoManager.findByCompetencia(conhecimentoId, TipoCompetencia.CONHECIMENTO));
			conhecimento.setCriteriosAvaliacaoCompetencia(criterioAvaliacaoCompetenciaManager.findByCompetencia(conhecimentoId,TipoCompetencia.CONHECIMENTO));
		}

		return conhecimento;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}
	
	public void setCriterioAvaliacaoCompetenciaManager(
			CriterioAvaliacaoCompetenciaManager criterioAvaliacaoCompetenciaManager) {
		this.criterioAvaliacaoCompetenciaManager = criterioAvaliacaoCompetenciaManager;
	}

	public Collection<Conhecimento> findAllSelectDistinctNome(Long[] empresaIds)
	{
		return getDao().findAllSelectDistinctNome(empresaIds);
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
			Collection<CriterioAvaliacaoCompetencia> criterioAvaliacaoCompetencias = criterioAvaliacaoCompetenciaManager.sincronizaCriterioAvaliacaoCompetencia(conhecimentoOrigemId, TipoCompetencia.CONHECIMENTO);
			conhecimento.setCriteriosAvaliacaoCompetencia(criterioAvaliacaoCompetencias);
			if(areaIds != null && areaIds.size() > 0)
			{
				Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findByConhecimento(conhecimentoOrigemId);
				popularAreasComIds(areaIds, areas);
				conhecimento.setAreaOrganizacionals(areas);
			}
			update(conhecimento);
			for (CriterioAvaliacaoCompetencia criterioAvaliacaoCompetencia : criterioAvaliacaoCompetencias) {
				criterioAvaliacaoCompetencia.setConhecimento(conhecimento);
				criterioAvaliacaoCompetenciaManager.update(criterioAvaliacaoCompetencia);
			}
		}
	}

	private void popularAreasComIds(Map<Long, Long> areaIds, Collection<AreaOrganizacional> areas)
	{
		for (AreaOrganizacional areaOrganizacional : areas) {
			Long id = areaIds.get(areaOrganizacional.getId());
			if (id == null)
				continue;
			areaOrganizacional.setId(id);
		}
	}

	private void clonar(Conhecimento conhecimento, Long empresaDestinoId)
	{
		conhecimento.setId(null);
		conhecimento.setAreaOrganizacionals(null);
		conhecimento.setEmpresaId(empresaDestinoId);
		getDao().save(conhecimento);
	}

	public void deleteByAreaOrganizacional(Long[] areaIds) throws Exception
	{
		getDao().deleteByAreaOrganizacional(areaIds);
	}
}
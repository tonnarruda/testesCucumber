package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.dao.captacao.HabilidadeDao;
import com.fortes.rh.model.captacao.CriterioAvaliacaoCompetencia;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.web.tags.CheckBox;

public class HabilidadeManagerImpl extends GenericManagerImpl<Habilidade, HabilidadeDao> implements HabilidadeManager
{
	AreaOrganizacionalManager areaOrganizacionalManager;
	
	CriterioAvaliacaoCompetenciaManager criterioAvaliacaoCompetenciaManager;

	public Collection<Habilidade> populaHabilidades(String[] habilidadesCheck)
	{
		Collection<Habilidade> habilidades = new ArrayList<Habilidade>();

		if(habilidadesCheck != null && habilidadesCheck.length > 0)
		{
			Long habilidadesIds[] = LongUtil.arrayStringToArrayLong(habilidadesCheck);

			Habilidade habilidade;
			for (Long habilidadeId: habilidadesIds)
			{
				habilidade = new Habilidade();
				habilidade.setId(habilidadeId);

				habilidades.add(habilidade);
			}
		}

		return habilidades;
	}

	public Collection<CheckBox> populaCheckOrderNome(Long[] areasIds, long empresaId)
	{
		Collection<CheckBox> checks = new ArrayList<CheckBox>();
		try
		{
			Collection<Habilidade> habilidades;
			if(areasIds != null)
				habilidades =  findByAreasOrganizacionalIds(areasIds,empresaId);
			else
				habilidades = getDao().findAllSelect(empresaId);
			
			checks = CheckListBoxUtil.populaCheckListBox(habilidades, "getId", "getNome", null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return checks;
	}

	public Collection<Habilidade> findByCargo(Long cargoId)
	{
		return getDao().findByCargo(cargoId);
	}

	public Collection<Habilidade> findByAreasOrganizacionalIds(Long[] areaOrganizacionalIds, Long empresasId)
	{
		return getDao().findByAreasOrganizacionalIds(areaOrganizacionalIds, empresasId);
	}

	public Collection<Habilidade> findAllSelect(Long empresaId) 
	{
		return getDao().findAllSelect(empresaId);
	}
	
	public void sincronizar(Long empresaOrigemId, Long empresaDestinoId, Map<Long, Long> areaIds, Map<Long, Long> habilidadeIds) 
	{
		
		Collection<Habilidade> habilidadesDeOrigem = getDao().findSincronizarHabilidades(empresaOrigemId);
		
		for (Habilidade habilidade : habilidadesDeOrigem)
		{
			Long habilidadeOrigemId = habilidade.getId();
			clonar(habilidade, empresaDestinoId);
			habilidadeIds.put(habilidadeOrigemId, habilidade.getId());
			Collection<CriterioAvaliacaoCompetencia> criterioAvaliacaoCompetencias = criterioAvaliacaoCompetenciaManager.sincronizaCriterioAvaliacaoCompetencia(habilidadeOrigemId, TipoCompetencia.HABILIDADE);
			habilidade.setCriteriosAvaliacaoCompetencia(criterioAvaliacaoCompetencias);
			
			if(areaIds != null && areaIds.size() > 0)
			{
				Collection<AreaOrganizacional> areasOrigem = areaOrganizacionalManager.findByHabilidade(habilidadeOrigemId);
				popularAreasComIds(areaIds, areasOrigem);
				habilidade.setAreaOrganizacionals(areasOrigem);
			}
			update(habilidade);
			for (CriterioAvaliacaoCompetencia criterioAvaliacaoCompetencia : criterioAvaliacaoCompetencias) {
				criterioAvaliacaoCompetencia.setHabilidade(habilidade);
				criterioAvaliacaoCompetenciaManager.update(criterioAvaliacaoCompetencia);
			}
		}
	}

	private void popularAreasComIds(Map<Long, Long> areaIds, Collection<AreaOrganizacional> areasOrigem) 
	{
		
		for (AreaOrganizacional areaOrganizacional : areasOrigem)
		{
			Long id = areaIds.get( areaOrganizacional.getId() );
			
			if (id == null)
				continue;
			
			areaOrganizacional.setId(id);
		}
	}

	private void clonar(Habilidade habilidade, Long empresaDestinoId) 
	{
		
		habilidade.setId(null);
		habilidade.setAreaOrganizacionals(null);
		habilidade.setEmpresaId(empresaDestinoId);
		
		getDao().save(habilidade);
	}

	public void deleteByAreaOrganizacional(Long[] areaIds) throws Exception
	{
		getDao().deleteByAreaOrganizacional(areaIds);
	}

	public Habilidade findByIdProjection(Long habilidadeId)
	{
		Habilidade habilidade = getDao().findByIdProjection(habilidadeId);
		
		if(habilidade != null) {
			CursoManager cursoManager = (CursoManager) SpringUtil.getBean("cursoManager");
			
			habilidade.setAreaOrganizacionals(areaOrganizacionalManager.findByHabilidade(habilidadeId));
			habilidade.setCursos(cursoManager.findByCompetencia(habilidadeId, TipoCompetencia.HABILIDADE));
			habilidade.setCriteriosAvaliacaoCompetencia(criterioAvaliacaoCompetenciaManager.findByCompetencia(habilidadeId, TipoCompetencia.HABILIDADE));
		}

		return habilidade;
	}
	
	public void removeComDependencia(Long id) {
		CargoManager cargoManager = (CargoManager) SpringUtil.getBean("cargoManager");
		cargoManager.removeViculoComHabilidade(id);
		getDao().remove(id);
	}
	
	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setCriterioAvaliacaoCompetenciaManager(
			CriterioAvaliacaoCompetenciaManager criterioAvaliacaoCompetenciaManager) {
		this.criterioAvaliacaoCompetenciaManager = criterioAvaliacaoCompetenciaManager;
	}
}
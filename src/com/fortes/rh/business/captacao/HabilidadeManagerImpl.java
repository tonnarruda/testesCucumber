package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.HabilidadeDao;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.web.tags.CheckBox;

public class HabilidadeManagerImpl extends GenericManagerImpl<Habilidade, HabilidadeDao> implements HabilidadeManager
{

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

	public Collection<CheckBox> populaCheckOrderNome(long empresaId)
	{
		Collection<CheckBox> checks = new ArrayList<CheckBox>();
		try
		{
			Collection<Habilidade> habilidades = getDao().findAllSelect(empresaId);
			checks = CheckListBoxUtil.populaCheckListBox(habilidades, "getId", "getNome");
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
}

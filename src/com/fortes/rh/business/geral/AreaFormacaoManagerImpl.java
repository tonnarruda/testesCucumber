package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.annotations.TesteAutomatico;
import com.fortes.rh.dao.geral.AreaFormacaoDao;
import com.fortes.rh.model.geral.AreaFormacao;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.web.tags.CheckBox;

public class AreaFormacaoManagerImpl extends GenericManagerImpl<AreaFormacao, AreaFormacaoDao> implements AreaFormacaoManager
{

	public Collection<AreaFormacao> findByCargo(Long cargoId)
	{
		return getDao().findByCargo(cargoId);
	}

	public Collection<CheckBox> populaCheckOrderNome()
	{
		Collection<CheckBox> checks = new ArrayList<CheckBox>();
		try
		{
			Collection<AreaFormacao> areas = getDao().findAll();
			checks = CheckListBoxUtil.populaCheckListBox(areas, "getId", "getNome");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return checks;
	}

	public Collection<AreaFormacao> populaAreas(String[] areasCheck)
	{
		Collection<AreaFormacao> areas = new ArrayList<AreaFormacao>();

		if(areasCheck != null && areasCheck.length > 0)
		{
			Long areasIds[] = LongUtil.arrayStringToArrayLong(areasCheck);

			AreaFormacao area;
			for (Long areaId: areasIds)
			{
				area = new AreaFormacao();
				area.setId(areaId);

				areas.add(area);
			}
		}

		return areas;
	}
	
	@TesteAutomatico
	public Collection<AreaFormacao> findByFiltro(int page, int pagingSize, AreaFormacao areaFormacao)
	{
		return getDao().findByFiltro(page, pagingSize, areaFormacao);
	}

	public Integer getCount(AreaFormacao areaFormacao)
	{
		return getDao().getCount(areaFormacao);
	}
}
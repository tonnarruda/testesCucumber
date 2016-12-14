/* Autor: Robertson Freitas
 * Data: 19/06/2006
 * Requisito: RFA0023 */
package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.EtapaSeletivaDao;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.relatorio.ProcessoSeletivoRelatorio;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.web.tags.CheckBox;

public class EtapaSeletivaManagerImpl extends GenericManagerImpl<EtapaSeletiva, EtapaSeletivaDao> implements EtapaSeletivaManager
{
	public int sugerirOrdem(Long empresaId)
	{
		int result = getDao().findAllSelect(0, 0, empresaId).size();
		return result + 1;
	}

	public EtapaSeletiva findPrimeiraEtapa(Long empresaId)
	{
		return getDao().findPrimeiraEtapa(empresaId);
	}

	@Override
	public EtapaSeletiva save(EtapaSeletiva etapaSeletiva)
	{
		if(etapaSeletiva.getOrdem()==0)
			etapaSeletiva.setOrdem(1);

		getDao().ordeneCrescentementeAPartirDe(etapaSeletiva.getOrdem(), etapaSeletiva);

		return super.save(etapaSeletiva);
	}

	public void update(EtapaSeletiva etapaSeletiva, Empresa empresa)
	{
		EtapaSeletiva etapaOriginal = findByIdProjection(etapaSeletiva.getId());

		if(etapaOriginal.getOrdem() > etapaSeletiva.getOrdem())
			getDao().ordeneCrescentementeEntre(etapaOriginal.getOrdem(), etapaSeletiva.getOrdem(), etapaSeletiva);
		else if (etapaOriginal.getOrdem() < etapaSeletiva.getOrdem())
			getDao().ordeneDecrescentementeEntre(etapaOriginal.getOrdem(), etapaSeletiva.getOrdem(), etapaSeletiva);

		super.update(etapaSeletiva);
	}

	public EtapaSeletiva findByIdProjection(Long etapaSeletivaId)
	{
		return getDao().findByIdProjection(etapaSeletivaId);
	}

	public void remove(EtapaSeletiva etapaSeletiva, Empresa empresa)
	{
		etapaSeletiva = findByEtapaSeletivaId(etapaSeletiva.getId(), empresa.getId());
		int ordem = etapaSeletiva.getOrdem();

		getDao().ordeneDecrescentementeApartirDe(ordem, etapaSeletiva);

		super.remove(etapaSeletiva);

		getDao().deleteVinculoComCargo(etapaSeletiva.getId());
	}

	@Override
	@Deprecated
	public void remove(EtapaSeletiva etapaSeletiva)
	{
		throw new NoSuchMethodError("Não utilize a implementação genérica deste método. Use: remove(etapaSeletiva, empresa).");
	}
	@Override
	@Deprecated
	public void remove(Long arg0)
	{
		throw new NoSuchMethodError("Não utilize a implementação genérica deste método. Use: remove(etapaSeletiva, empresa).");
	}
	@Override
	@Deprecated
	public void remove(Long[] arg0)
	{
		throw new NoSuchMethodError("Não utilize a implementação genérica deste método. Use: remove(etapaSeletiva, empresa).");
	}

	@Override
	@Deprecated
	public void update(EtapaSeletiva etapaSeletiva)
	{
		throw new NoSuchMethodError("Não utilize a implementação genérica deste método. Use: update(etapaSeletiva, empresa).");
	}

	@Override
	@Deprecated
	public void saveOrUpdate(Collection<EtapaSeletiva> arg0)
	{
		throw new NoSuchMethodError("Não utilize a implementação genérica deste método. Use: update(etapaSeletiva, empresa).");
	}

	public Collection<EtapaSeletiva> findAllSelect(Long empresaId)
	{
		return getDao().findAllSelect(0, 0, empresaId);
	}

	public Collection<EtapaSeletiva> findAllSelect(int page, int pagingSize, Long empresaId)
	{
		return getDao().findAllSelect(page, pagingSize, empresaId);
	}
	
	public EtapaSeletiva findByEtapaSeletivaId(Long etapaSeletivaId, Long empresaId)
	{
		return getDao().findByEtapaSeletivaId(etapaSeletivaId, empresaId);
	}

	public Integer getCount(Long empresaId)
	{
		return getDao().getCount(empresaId);
	}

	public Collection<ProcessoSeletivoRelatorio> montaProcessosSeletivos(Long empresaId, Long[] etapaIds)
	{
		Collection<EtapaSeletiva> etapas = getDao().findByIdProjection(empresaId, etapaIds);
		Collection<ProcessoSeletivoRelatorio> processosSeletivos = new ArrayList<ProcessoSeletivoRelatorio>();
		
		for (EtapaSeletiva etapaSeletiva : etapas)
		{
			ProcessoSeletivoRelatorio processoSeletivoRelatorio = new ProcessoSeletivoRelatorio();
			processoSeletivoRelatorio.setEtapa(etapaSeletiva);
			processosSeletivos.add(processoSeletivoRelatorio);
		}
		
		return processosSeletivos;
	}
	
	public Collection<CheckBox> populaCheckOrderNome(long empresaId)
	{
		Collection<CheckBox> checks = new ArrayList<CheckBox>();
		try
		{
			Collection<EtapaSeletiva> etapaSeletiva = getDao().findAllSelect(0, 0, empresaId);
			checks = CheckListBoxUtil.populaCheckListBox(etapaSeletiva, "getId", "getNome", null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return checks;
	}
	
	public Collection<EtapaSeletiva> populaEtapaseletiva(String[] areasCheck)
	{
		Collection<EtapaSeletiva> areas = new ArrayList<EtapaSeletiva>();

		if(areasCheck != null && areasCheck.length > 0)
		{
			Long areasIds[] = LongUtil.arrayStringToArrayLong(areasCheck);

			EtapaSeletiva area;
			for (Long areaId: areasIds)
			{
				area = new EtapaSeletiva();
				area.setId(areaId);

				areas.add(area);
			}
		}

		return areas;
	}
	
	public Collection<EtapaSeletiva> findByCargo(Long cargoId)
	{
		return getDao().findByCargo(cargoId);
	}
	
}
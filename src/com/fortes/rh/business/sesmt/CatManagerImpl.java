package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.dao.sesmt.CatDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Cat;
import com.fortes.rh.model.sesmt.relatorio.CatRelatorioAnual;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;

public class CatManagerImpl extends GenericManagerImpl<Cat, CatDao> implements CatManager
{
	private AreaOrganizacionalManager areaOrganizacionalManager;
	
	public Collection<Cat> findByColaborador(Colaborador colaborador)
	{
		return getDao().findByColaborador(colaborador);
	}

	public Collection<Cat> findCatsColaboradorByDate(Colaborador colaborador, Date data)
	{
		return getDao().findCatsColaboradorByDate(colaborador,data);
	}

	public Collection<Cat> findAllSelect(Long empresaId, Date inicio, Date fim, String[] estabelecimentosCheck, String nomeBusca, String[] areasCheck)
	{
		Long[] areaIds = LongUtil.arrayStringToArrayLong(areasCheck);
		Long[] estabelecimentoIds = LongUtil.arrayStringToArrayLong(estabelecimentosCheck);

		return getDao().findAllSelect(empresaId,inicio,fim,estabelecimentoIds, nomeBusca, areaIds);
	}
	
	public Collection<Cat> findRelatorioCats (Long empresaId, Date inicio, Date fim, String[] estabelecimentosCheck, String nomeBusca) throws ColecaoVaziaException
	{
		Collection<Cat> cats = findAllSelect(empresaId, inicio, fim, estabelecimentosCheck, nomeBusca, null);

		if (cats == null || cats.isEmpty())
			throw new ColecaoVaziaException("Não há CAT's para o filtro informado.");
		
		setFamiliaAreas(cats, empresaId);

		return cats;
	}
	
	private void setFamiliaAreas(Collection<Cat> cats, Long empresaId)
	{
		Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findAllList(empresaId, true);

		try
		{
			areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		for (Cat cat: cats)
		{
			if(cat.getColaborador().getAreaOrganizacional() != null && cat.getColaborador().getAreaOrganizacional().getId() != null)
				cat.getColaborador().setAreaOrganizacional(areaOrganizacionalManager.getAreaOrganizacional(areaOrganizacionals, cat.getColaborador().getAreaOrganizacional().getId()));
		}
	}

	public Collection<CatRelatorioAnual> getRelatorioAnual(Long estabelecimentoId, Date dataFim)
	{
		Date inicio = DateUtil.incrementaAno(dataFim, -1);
		Collection<CatRelatorioAnual> catsAnuais = new ArrayList<CatRelatorioAnual>();
		
		Collection<Object[]> lista = getDao().getCatsRelatorio(estabelecimentoId, inicio, dataFim);
		
		CatRelatorioAnual catAnual;
		Map<String, CatRelatorioAnual> catsMap = new LinkedHashMap<String, CatRelatorioAnual>();
		
		//cat.data, cat.gerouAfastamento
		if(lista != null && !lista.isEmpty())
		{
			for (Iterator<Object[]> it = lista.iterator(); it.hasNext();)
			{
				Object[] cat = it.next();

				String chave =  DateUtil.formataMesAno((Date)cat[0]);

				if(catsMap.containsKey(chave))
				{
					catAnual = catsMap.get(chave);
				}
				else
				{
					catAnual = new CatRelatorioAnual();
					catAnual.setData((Date)cat[0]);
				}

				catAnual.addTotal();
				
				if(cat[1].equals(Boolean.TRUE))
					catAnual.addTotalComAfastamento();
				else if (cat[1].equals(Boolean.FALSE)) 
					catAnual.addTotalSemAfastamento();

				catsMap.put(chave, catAnual);
			}
		}

		for (String chave : catsMap.keySet())
		{
			catAnual = catsMap.get(chave);
			catsAnuais.add(catAnual);
		}
		
		return catsAnuais;
	}

	public void setAreaOrganizacionalManager(
			AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	
}
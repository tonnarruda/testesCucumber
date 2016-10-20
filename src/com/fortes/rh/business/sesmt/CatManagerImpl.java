package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.model.type.File;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.dao.sesmt.CatDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.model.sesmt.Cat;
import com.fortes.rh.model.sesmt.relatorio.CatRelatorioAnual;
import com.fortes.rh.util.ArquivoUtil;
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
		Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findAllListAndInativas(true, null, empresaId);

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

	public Collection<CatRelatorioAnual> getRelatorioCat(Long estabelecimentoId, Date dataIni, Date dataFim)
	{
		Collection<CatRelatorioAnual> catsAnuais = new ArrayList<CatRelatorioAnual>();
		
		Collection<Object[]> lista = getDao().getCatsRelatorio(estabelecimentoId, dataIni, dataFim);
		
		CatRelatorioAnual catAnual;
		Map<String, CatRelatorioAnual> catsMap = new LinkedHashMap<String, CatRelatorioAnual>();
		
		if(lista != null && !lista.isEmpty()){
			for (Iterator<Object[]> it = lista.iterator(); it.hasNext();){
				Object[] cat = it.next();

				String chave =  DateUtil.formataMesAno((Date)cat[0]);

				if(catsMap.containsKey(chave))
					catAnual = catsMap.get(chave);
				else{
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

		for (String chave : catsMap.keySet()){
			catAnual = catsMap.get(chave);
			catsAnuais.add(catAnual);
		}
		
		return catsAnuais;
	}

	public int findQtdDiasSemAcidentes(Long empresaId) 
	{
		Cat ultimoCat = getDao().findUltimoCat(empresaId); 
		
		return ultimoCat != null ? DateUtil.diferencaEntreDatas(ultimoCat.getData(), new Date(), false) : 0;
	}
	
	// TODO: SEM TESTE
	public Collection<DataGrafico> findQtdCatsPorDiaSemana(Long empresaId, Date dataIni, Date dataFim) 
	{
		Collection<DataGrafico> graficoCatsPorDiaSemana = new ArrayList<DataGrafico>();
		Map<Integer,Integer> qtdCatsPorDiaSemana = getDao().findQtdPorDiaSemana(empresaId, dataIni, dataFim);
		
		for (Map.Entry<Integer, Integer> qtd : qtdCatsPorDiaSemana.entrySet())
			graficoCatsPorDiaSemana.add(new DataGrafico(null, DateUtil.getNomeDiaSemana(qtd.getKey()), qtd.getValue(), ""));
		
		return graficoCatsPorDiaSemana;
	}
	
	// TODO: SEM TESTE
	public Collection<DataGrafico> findQtdCatsPorHorario(Long empresaId, Date dataIni, Date dataFim) 
	{
		Collection<DataGrafico> graficoCatsPorHorario = new ArrayList<DataGrafico>();
		Map<String,Integer> qtdCatsPorHorario = getDao().findQtdPorHorario(empresaId, dataIni, dataFim);
		
		for (Map.Entry<String, Integer> qtd : qtdCatsPorHorario.entrySet())
			graficoCatsPorHorario.add(new DataGrafico(null, qtd.getKey() == null ? "Não definido" : qtd.getKey() + "h", qtd.getValue(), ""));
		
		return graficoCatsPorHorario;
	}
	
	// TODO: SEM TESTE
	public Cat findByIdProjectionSimples(Long catId) 
	{
		return getDao().findByIdProjectionSimples(catId);
	}
	
	// TODO: SEM TESTE
	public Cat findByIdProjectionDetalhada(Long catId) 
	{
		return getDao().findByIdProjectionDetalhada(catId);
	}

	// TODO: SEM TESTE
	public void setFoto(Cat cat, boolean manterFoto, File foto, String local) 
	{
		if(!manterFoto)
		{
			String fotoUrl = ArquivoUtil.salvaArquivo(foto, local);
	
			if(foto != null && !fotoUrl.equals(""))
				cat.setFotoUrl(fotoUrl);
			else
				cat.setFotoUrl(null);
		}
	}
	
	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}
}
package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.web.tags.Option;

@SuppressWarnings("rawtypes")
public class FaixaSalarialDWR
{
	private FaixaSalarialManager faixaSalarialManager;

	public Map getFaixas(String cargoId)
	{
		if (cargoId != null && !cargoId.equals("-1"))
		{
			Collection<FaixaSalarial> faixas = faixaSalarialManager.findFaixaSalarialByCargo(Long.valueOf(cargoId));
			Collection<FaixaSalarial> faixasRetorno = new ArrayList<FaixaSalarial>();

			if (!faixas.isEmpty())
			{
				FaixaSalarial faixaTmp = new FaixaSalarial();
				faixaTmp.setNome("Selecione...");
				faixaTmp.setId(-1L);

				faixasRetorno.add(faixaTmp);
				faixasRetorno.addAll(faixas);
			}

			return CollectionUtil.convertCollectionToMap(faixasRetorno, "getId", "getNome", FaixaSalarial.class);

		}

		return new HashMap<String, String>();
	}
	
	public Collection<Option> findByCargo(String cargoId)
	{
		Collection<Option> faixasRetorno = new ArrayList<Option>();

		if (cargoId != null && !cargoId.equals("-1"))
		{
			Collection<FaixaSalarial> faixas = faixaSalarialManager.findFaixaSalarialByCargo(Long.valueOf(cargoId));
			for (FaixaSalarial faixaSalarial : faixas) 
				faixasRetorno.add( new Option(faixaSalarial.getId(), faixaSalarial.getNome()) );
		}

		return faixasRetorno;
	}

	public Map getByEmpresas(Long empresaId, Long[] empresaIds)
	{
		Collection<FaixaSalarial> faixas;
		String parametroKey;
		if(empresaId == -1L){
			faixas = faixaSalarialManager.findDistinctDescricao(empresaIds);
			parametroKey = "getDescricaoComStatus";
		}
		else{
			faixas = faixaSalarialManager.findAllSelectByCargo(empresaId);
			parametroKey = "getId";
		}
		
		return new CollectionUtil<FaixaSalarial>().convertCollectionToMap(faixas, parametroKey, "getDescricaoComStatus");
	}
	
	public Map getByCargo(String cargoId)
	{
		return faixaSalarialManager.findByCargo(cargoId);
	}
	
	public Map getCargosFaixasByArea(Long areaOrganizacionalId, Long empresaId){
		Collection<FaixaSalarial> faixasSalariais = faixaSalarialManager.getCargosFaixaByAreaIdAndEmpresaId(areaOrganizacionalId, empresaId, null);
		return new CollectionUtil<FaixaSalarial>().convertCollectionToMap(faixasSalariais, "getId", "getDescricao");
	}
	
	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager)
	{
		this.faixaSalarialManager = faixaSalarialManager;
	}
}

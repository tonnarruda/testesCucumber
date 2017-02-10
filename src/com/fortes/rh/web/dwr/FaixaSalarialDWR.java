package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.web.tags.Option;

@Component
@RemoteProxy(name="FaixaSalarialDWR")
@SuppressWarnings("rawtypes")
public class FaixaSalarialDWR
{
	@Autowired private FaixaSalarialManager faixaSalarialManager;

	@RemoteMethod
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
	
	@RemoteMethod
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

	@RemoteMethod
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
	
	@RemoteMethod
	public Map getByCargo(String cargoId)
	{
		return faixaSalarialManager.findByCargo(cargoId);
	}
}
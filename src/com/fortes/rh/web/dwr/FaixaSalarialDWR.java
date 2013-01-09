package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.dicionario.TipoAplicacaoReajuste;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.web.tags.CheckBox;
import com.fortes.web.tags.Option;


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

	public Map getByEmpresa(Long empresaId)
	{
		Collection<FaixaSalarial> faixas = new ArrayList<FaixaSalarial>();
		String getParametro = "getId";
		
		if(empresaId == -1)//Caso a empresa passada seja -1, vai trazer todos os cargos/faixas 
		{
			faixas = faixaSalarialManager.findAllSelectByCargo(null);
			getParametro = "getDescricao";
		}
		else
			faixas = faixaSalarialManager.findAllSelectByCargo(empresaId);

		return new CollectionUtil<FaixaSalarial>().convertCollectionToMap(faixas, getParametro, "getDescricao");
	}
	
	public Map getByCargo(String cargoId)
	{
		return faixaSalarialManager.findByCargo(cargoId);
	}

	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager)
	{
		this.faixaSalarialManager = faixaSalarialManager;
	}
}

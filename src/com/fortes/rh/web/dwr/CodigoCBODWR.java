package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import com.fortes.rh.business.geral.CodigoCBOManager;
import com.fortes.rh.model.geral.CodigoCBO;
import com.fortes.rh.util.CollectionUtil;


public class CodigoCBODWR
{
	private CodigoCBOManager codigoCBOManager;
	
	// Só a descrição, Usado apenas para autocomplete
	public String[] getCodigosCBO(String codigo)
	{
		Collection<String> codigos = codigoCBOManager.buscaCodigosCBO(codigo);
		Collection<String> codigoResutados = codigoCBOManager.buscaCodigosCBO(codigo);
		
		for (String codigoCBO : codigos) 
			codigoResutados.add("\'" + codigoCBO + "\'");
		
		String[] result = new String[codigoResutados.size()];
		result = codigoResutados.toArray(result);

		return result;
	}
	
	public void setCodigoCBOManager(CodigoCBOManager codigoCBOManager) {
		this.codigoCBOManager = codigoCBOManager;
	}
}

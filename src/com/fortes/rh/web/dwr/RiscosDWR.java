package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.util.CollectionUtil;

@Component
public class RiscosDWR
{
	@Autowired
	private RiscoManager riscoManager;

	public Map getRiscos(String grupoRisco)
	{
		Collection<Risco> riscos = riscoManager.find(new String[]{"grupoRisco"}, new Object[]{grupoRisco});
		Risco riscoBranco = new Risco();
		if(riscos.isEmpty()){
			riscoBranco.setId(null);
			riscoBranco.setDescricao("[Não existem dados para o filtro informado]");
			riscos.add(riscoBranco);
		}
		return new CollectionUtil<Risco>().convertCollectionToMap(riscos,"getId","getDescricao");
	}

	public void setRiscoManager(RiscoManager riscoManager)
	{
		this.riscoManager = riscoManager;
	}
}

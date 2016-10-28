package com.fortes.rh.web.dwr;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.sesmt.AfastamentoManager;
import com.fortes.rh.model.sesmt.Afastamento;

@Component
public class AfastamentoDWR
{
	@Autowired
	private AfastamentoManager afastamentoManager;

	public boolean isAfastamentoInss(String afastamentoId)
	{
		if (StringUtils.isBlank(afastamentoId))
			return false;

		Long id = Long.parseLong((afastamentoId.replace(".","")).replace(",",""));
		Afastamento afastamento = afastamentoManager.findById(id);

		return afastamento.isInss();
	}

	public void setAfastamentoManager(AfastamentoManager afastamentoManager)
	{
		this.afastamentoManager = afastamentoManager;
	}
}

package com.fortes.rh.web.dwr;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.sesmt.AfastamentoManager;
import com.fortes.rh.model.sesmt.Afastamento;

public class AfastamentoDWR
{
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

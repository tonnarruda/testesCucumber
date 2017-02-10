package com.fortes.rh.web.dwr;

import org.apache.commons.lang.StringUtils;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.sesmt.AfastamentoManager;
import com.fortes.rh.model.sesmt.Afastamento;

@Component
@RemoteProxy(name="AfastamentoDWR")
public class AfastamentoDWR
{
	@Autowired private AfastamentoManager afastamentoManager;

	@RemoteMethod
	public boolean isAfastamentoInss(String afastamentoId)
	{
		if (StringUtils.isBlank(afastamentoId))
			return false;

		Long id = Long.parseLong((afastamentoId.replace(".","")).replace(",",""));
		Afastamento afastamento = afastamentoManager.findById(id);

		return afastamento.isInss();
	}
}
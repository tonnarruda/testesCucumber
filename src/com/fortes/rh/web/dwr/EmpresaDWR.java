package com.fortes.rh.web.dwr;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.geral.EmpresaManager;

@Component
@RemoteProxy(name="EmpresaDWR")
public class EmpresaDWR
{
	@Autowired private EmpresaManager empresaManager;

	@RemoteMethod
	public boolean isAcintegra(Long empresaId)
	{
		return empresaManager.checkEmpresaIntegradaAc(empresaId);
	}
}
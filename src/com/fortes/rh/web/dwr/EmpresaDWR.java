package com.fortes.rh.web.dwr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.geral.EmpresaManager;

@Component
public class EmpresaDWR
{
	@Autowired
	private EmpresaManager empresaManager;

	public boolean isAcintegra(Long empresaId)
	{
		return empresaManager.checkEmpresaIntegradaAc(empresaId);
	}
	
	public void setEmpresaManager(EmpresaManager empresaManager) 
	{
		this.empresaManager = empresaManager;
	}
}

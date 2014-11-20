package com.fortes.rh.web.dwr;

import com.fortes.rh.business.geral.EmpresaManager;


public class EmpresaDWR
{
	private EmpresaManager empresaManager;

	public void removeDadosPortalColaborador(Long empresaId)
	{
		empresaManager.removeEmpresaPc(empresaId);
	}
	
	public boolean isAcintegra(Long empresaId)
	{
		return empresaManager.checkEmpresaIntegradaAc(empresaId);
	}
	
	public void setEmpresaManager(EmpresaManager empresaManager) 
	{
		this.empresaManager = empresaManager;
	}
}

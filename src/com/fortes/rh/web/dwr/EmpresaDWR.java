package com.fortes.rh.web.dwr;

import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;


public class EmpresaDWR
{
	private EmpresaManager empresaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;

	public boolean removeDadosPortalColaborador()
	{
		return false;
//		return parametrosDoSistemaManager.removeDadosPortalColaborador();
	}
	
	public boolean isAcintegra(Long empresaId)
	{
		return empresaManager.checkEmpresaIntegradaAc(empresaId);
	}
	
	public void setEmpresaManager(EmpresaManager empresaManager) 
	{
		this.empresaManager = empresaManager;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) 
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}
}

package com.fortes.rh.web.dwr;

import java.util.Collection;

import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.geral.Empresa;


public class EmpresaDWR
{
	private EmpresaManager empresaManager;

	public boolean isAcintegra(Long empresaId)
	{
		return empresaManager.checkEmpresaIntegradaAc(empresaId);
	}
	
	public Collection<Empresa> findDistinctEmpresasByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, Long[] empresasPermitidasIds){
		return empresaManager.findDistinctEmpresasByAvaliacaoDesempenho(avaliacaoDesempenhoId, empresasPermitidasIds);
	}

	public void setEmpresaManager(EmpresaManager empresaManager) 
	{
		this.empresaManager = empresaManager;
	}
}

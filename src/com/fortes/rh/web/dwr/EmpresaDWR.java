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
	
	public void setEmpresaManager(EmpresaManager empresaManager) 
	{
		this.empresaManager = empresaManager;
	}
	public Collection<Empresa> findDistinctEmpresasByAvaliacaoDesempenho(Long avaliacaoDesempenhoId)
	{
		return empresaManager.findDistinctEmpresasByAvaliacaoDesempenho(avaliacaoDesempenhoId);
	}
}

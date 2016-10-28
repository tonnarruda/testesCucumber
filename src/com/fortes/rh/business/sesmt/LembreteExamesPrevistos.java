package com.fortes.rh.business.sesmt;

import org.springframework.stereotype.Component;

import com.fortes.rh.business.geral.EmpresaManager;

@Component
@SuppressWarnings("deprecation")
public class LembreteExamesPrevistos
{
	private ExameManager exameManager;
	private EmpresaManager empresaManager;
	
	public void executeLembreteExamesPrevistos() 
	{	
		try
		{
			exameManager.enviaLembreteExamesPrevistos(empresaManager.findEmailsEmpresa());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	public void setExameManager(ExameManager exameManager) 
	{
		this.exameManager = exameManager;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) 
	{
		this.empresaManager = empresaManager;
	}	
}

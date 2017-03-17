package com.fortes.rh.business.sesmt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.geral.EmpresaManager;

@Component
public class LembreteExamesPrevistos
{
	@Autowired private ExameManager exameManager;
	@Autowired private EmpresaManager empresaManager;
	
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
}

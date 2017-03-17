package com.fortes.rh.business.captacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.geral.EmpresaManager;

@Component
public class QuantidadeCurriculos
{
	@Autowired private CandidatoManager candidatoManager;
	@Autowired private EmpresaManager empresaManager;
	
	public void execute()
	{
		try
		{
			candidatoManager.enviaEmailQtdCurriculosCadastrados(empresaManager.findEmailsEmpresa());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
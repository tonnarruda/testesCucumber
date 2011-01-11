package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.geral.Empresa;


public class QuantidadeCurriculos
{
	private CandidatoManager candidatoManager;
	private EmpresaManager empresaManager;
	
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
	
	public void setCandidatoManager(CandidatoManager candidatoManager) {
		this.candidatoManager = candidatoManager;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}
}
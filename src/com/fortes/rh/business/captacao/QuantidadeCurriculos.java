package com.fortes.rh.business.captacao;


public class QuantidadeCurriculos
{
	private CandidatoManager candidatoManager;
	
	public void execute()
	{
		try
		{
			candidatoManager.enviaEmailQtdCurriculosCadastrados();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void setCandidatoManager(CandidatoManager candidatoManager) {
		this.candidatoManager = candidatoManager;
	}
}
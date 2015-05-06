package com.fortes.rh.web.dwr;

import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;

public class ColaboradorQuestionarioDWR {

	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	
	public boolean existeModeloAvaliacaoEmDesempnhoEPeriodoExperiencia(Long avaliacaoId)
	{
		boolean retorno = colaboradorQuestionarioManager.existeMesmoModeloAvaliacaoEmDesempenhoEPeriodoExperiencia(avaliacaoId);
		
		return retorno;
	}

	public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager)
	{
		this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
	}
}

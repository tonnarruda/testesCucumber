package com.fortes.rh.web.dwr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;

@Component
public class ColaboradorQuestionarioDWR {

	@Autowired
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

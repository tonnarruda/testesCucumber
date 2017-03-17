package com.fortes.rh.business.avaliacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.geral.ColaboradorPeriodoExperienciaAvaliacaoManager;

@Component
public class LembretePeriodoExperiencia
{
	@Autowired private AvaliacaoManager avaliacaoManager;
	@Autowired private ColaboradorPeriodoExperienciaAvaliacaoManager colaboradorPeriodoExperienciaAvaliacaoManager;
	
	public void execute() {
		avaliacaoManager.enviaLembrete();
		colaboradorPeriodoExperienciaAvaliacaoManager.enviaLembreteColaboradorAvaliacaoPeriodoExperienciaVencendo();
	}
}

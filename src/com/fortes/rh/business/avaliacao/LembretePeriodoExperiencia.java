package com.fortes.rh.business.avaliacao;

import com.fortes.rh.business.geral.ColaboradorPeriodoExperienciaAvaliacaoManager;
import com.fortes.rh.util.SpringUtil;


@SuppressWarnings("deprecation")
public class LembretePeriodoExperiencia
{
	private AvaliacaoManager avaliacaoManager;
	private ColaboradorPeriodoExperienciaAvaliacaoManager colaboradorPeriodoExperienciaAvaliacaoManager;
	
	public void execute() {
		
		avaliacaoManager = (AvaliacaoManager) SpringUtil.getBeanOld("avaliacaoManager");
		colaboradorPeriodoExperienciaAvaliacaoManager = (ColaboradorPeriodoExperienciaAvaliacaoManager) SpringUtil.getBeanOld("colaboradorPeriodoExperienciaAvaliacaoManager");
		
		avaliacaoManager.enviaLembrete();
		colaboradorPeriodoExperienciaAvaliacaoManager.enviaLembreteColaboradorAvaliacaoPeriodoExperienciaVencendo();
	}
}

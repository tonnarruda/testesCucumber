package com.fortes.rh.business.avaliacao;

import com.fortes.rh.util.SpringUtil;


@SuppressWarnings("deprecation")
public class LembretePeriodoExperiencia
{
	private AvaliacaoManager avaliacaoManager;
	
	public void execute() {
		
		avaliacaoManager = (AvaliacaoManager) SpringUtil.getBeanOld("avaliacaoManager");
		avaliacaoManager.enviaLembrete();
	}
}

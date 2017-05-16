package com.fortes.rh.web.dwr;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.model.avaliacao.Avaliacao;

public class AvaliacaoDWR 
{
	private AvaliacaoManager avaliacaoManager;

	public char tipoAvaliacao(Long avaliacaoId){
		Avaliacao avaliacao = avaliacaoManager.findEntidadeComAtributosSimplesById(avaliacaoId);
		return avaliacao.getTipoModeloAvaliacao();
	}
	
	public void setAvaliacaoManager(AvaliacaoManager avaliacaoManager) {
		this.avaliacaoManager = avaliacaoManager;
	}
}
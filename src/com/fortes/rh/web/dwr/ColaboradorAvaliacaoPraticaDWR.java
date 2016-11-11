package com.fortes.rh.web.dwr;

import org.springframework.stereotype.Component;

import com.fortes.rh.business.desenvolvimento.ColaboradorAvaliacaoPraticaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;

@Component
public class ColaboradorAvaliacaoPraticaDWR
{
	private ColaboradorAvaliacaoPraticaManager colaboradorAvaliacaoPraticaManager;
	private ColaboradorCertificacaoManager colaboradorCertificacaoManager;
	
    public ColaboradorAvaliacaoPratica verificaUltimaCertificacao(Long colabAvPraticaId)
    {
    	ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = colaboradorAvaliacaoPraticaManager.findById(colabAvPraticaId);

    	if(colaboradorAvaliacaoPratica.getColaboradorCertificacao() != null && colaboradorAvaliacaoPratica.getColaboradorCertificacao().getId() != null)
    		colaboradorAvaliacaoPratica.setColaboradorCertificacao(colaboradorCertificacaoManager.findColaboradorCertificadoInfomandoSeEUltimaCertificacao(colaboradorAvaliacaoPratica.getColaboradorCertificacao().getId(), colaboradorAvaliacaoPratica.getColaborador().getId(),colaboradorAvaliacaoPratica.getCertificacao().getId()));
    	
    	return colaboradorAvaliacaoPratica;
    }

    public void setColaboradorCertificacaoManager(ColaboradorCertificacaoManager colaboradorCertificacaoManager) {
		this.colaboradorCertificacaoManager = colaboradorCertificacaoManager;
	}

	public void setColaboradorAvaliacaoPraticaManager(ColaboradorAvaliacaoPraticaManager colaboradorAvaliacaoPraticaManager) {
		this.colaboradorAvaliacaoPraticaManager = colaboradorAvaliacaoPraticaManager;
	}
}

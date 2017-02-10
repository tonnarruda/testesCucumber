package com.fortes.rh.web.dwr;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.desenvolvimento.ColaboradorAvaliacaoPraticaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;

@Component
@RemoteProxy(name="ColaboradorAvaliacaoPraticaDWR")
public class ColaboradorAvaliacaoPraticaDWR
{
	@Autowired private ColaboradorAvaliacaoPraticaManager colaboradorAvaliacaoPraticaManager;
	@Autowired private ColaboradorCertificacaoManager colaboradorCertificacaoManager;
	
	@RemoteMethod
    public ColaboradorAvaliacaoPratica verificaUltimaCertificacao(Long colabAvPraticaId)
    {
    	ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = colaboradorAvaliacaoPraticaManager.findById(colabAvPraticaId);

    	if(colaboradorAvaliacaoPratica.getColaboradorCertificacao() != null && colaboradorAvaliacaoPratica.getColaboradorCertificacao().getId() != null)
    		colaboradorAvaliacaoPratica.setColaboradorCertificacao(colaboradorCertificacaoManager.findColaboradorCertificadoInfomandoSeEUltimaCertificacao(colaboradorAvaliacaoPratica.getColaboradorCertificacao().getId(), colaboradorAvaliacaoPratica.getColaborador().getId(),colaboradorAvaliacaoPratica.getCertificacao().getId()));
    	
    	return colaboradorAvaliacaoPratica;
    }
}
package com.fortes.rh.thread;

import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;

public class certificaColaboradorThread extends Thread{

	private ColaboradorCertificacaoManager colaboradorCertificacaoManager;
	private Long colaboradorTurmaId;
	private CertificacaoManager certificacaoManager;
	
	public certificaColaboradorThread(ColaboradorCertificacaoManager colaboradorCertificacaoManager, Long colaboradorTurmaId, CertificacaoManager certificacaoManager){
		this.colaboradorCertificacaoManager = colaboradorCertificacaoManager;
		this.colaboradorTurmaId = colaboradorTurmaId;
		this.certificacaoManager = certificacaoManager;
	}
	
	public void run() {
		try {
			colaboradorCertificacaoManager.certificaColaborador(colaboradorTurmaId, null, null, certificacaoManager);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}

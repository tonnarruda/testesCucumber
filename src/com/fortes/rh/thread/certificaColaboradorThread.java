package com.fortes.rh.thread;

import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;

public class certificaColaboradorThread extends Thread{

	private ColaboradorCertificacaoManager colaboradorCertificacaoManager;
	private Long colaboradorTurmaId;
	
	public certificaColaboradorThread(ColaboradorCertificacaoManager colaboradorCertificacaoManager, Long colaboradorTurmaId){
		this.colaboradorCertificacaoManager = colaboradorCertificacaoManager;
		this.colaboradorTurmaId = colaboradorTurmaId;
	}
	
	public void run() {
		try {
			colaboradorCertificacaoManager.certificaColaborador(colaboradorTurmaId, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}

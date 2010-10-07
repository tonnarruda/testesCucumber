package com.fortes.rh.config.backup;

public interface BackupService {
	
	/**
	 * Efetua o backup do banco do FortesRh e envia e-mail ao administrador.
	 */
	public void backupAndSendMail();

}

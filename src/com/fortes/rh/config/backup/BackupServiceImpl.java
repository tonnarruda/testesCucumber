package com.fortes.rh.config.backup;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.fortes.rh.config.backup.notificador.NotificadorDeBackup;

public class BackupServiceImpl implements BackupService {

	private static final String BACKUP_FILE = "backup.file";
	
	private static Logger logger = Logger.getLogger(BackupServiceImpl.class);

	NotificadorDeBackup notificadorDeBackup;
	
	private RunAntScript runAntScript;
	
	private void init() {
		if (runAntScript == null)
			runAntScript = new RunAntScript(getScriptPath("backup_script.xml"));
	}
	
	public void backupAndSendMail() 
	{
		init();
//		String backupFile = "/Users/rponte/Development/Fortes/RH/backup_db/bkpDBFortesRh_20100707.backup";
		String backupFile = backup();
		sendMail(backupFile);
	}

	/**
	 * Efetua backup da aplicação através da chamada de um Ant Script.
	 */
	private String backup() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String today = simpleDateFormat.format(new Date());
		
		try {
			logger.info("Efetuando backup do banco.");
			
			runAntScript.addProperty("today", today);
			runAntScript.launch();
			
			logger.info("Backup do banco efetuado com sucesso.");
		
		} catch (Exception e) {
			String msg = "Erro ao tentar efetuar backup do banco: " + e.getMessage();
			logger.error(msg);
			throw new RuntimeException(msg, e);
		}
		
		return runAntScript.getProperty(BACKUP_FILE) + today + ".backup";
	}
	/**
	 * Envia e-mail para o responsável sobre backup do banco de dados.
	 */
	private void sendMail(String arquivoDeBackup) {
		notificadorDeBackup.notifica(arquivoDeBackup);
	}
	/**
	 * Retorna o caminho completo do script.
	 */
	private String getScriptPath(String scriptName){
		return this.getClass()
			.getResource(scriptName)
				.getFile()
					.replace("\\", "/")
						.replace("%20", " ");
	}
	public void setRunAntScript(RunAntScript runAntScript) {
		this.runAntScript = runAntScript;
	}
	public void setNotificadorDeBackup(NotificadorDeBackup notificadorDeBackup) {
		this.notificadorDeBackup = notificadorDeBackup;
	}
}

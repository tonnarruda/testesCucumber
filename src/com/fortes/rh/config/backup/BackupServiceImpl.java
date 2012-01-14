package com.fortes.rh.config.backup;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.fortes.rh.config.backup.notificador.NotificadorDeBackup;
import com.fortes.rh.util.ArquivoUtil;

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
			
			String dbName = ArquivoUtil.getSystemConf().getProperty("db.name");
			if(dbName == null || dbName.equals(""))
				dbName = "fortesrh";
			
			runAntScript.addProperty("db_name", dbName);
			runAntScript.addProperty("today", today);
			runAntScript.addProperty("fortesrh.dir",  ArquivoUtil.getRhHome());
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

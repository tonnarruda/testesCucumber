package com.fortes.rh.config.backup;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.util.ArquivoUtil;

@Component
public class BackupServiceImpl implements BackupService {

	private static final String BACKUP_FILE = "backup.file";
	
	private static Logger logger = Logger.getLogger(BackupServiceImpl.class);

	GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	
	private RunAntScript runAntScript;
	
	private void init() {
		if (runAntScript == null)
			runAntScript = new RunAntScript(getScriptPath("backup_script.xml"));
	}
	
	public void backupAndSendMail() 
	{
		init();
		String backupFile = backup();
		gerenciadorComunicacaoManager.notificaBackup(backupFile);
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
			
			runAntScript.addProperty("db_name", dbName.trim());
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

	public void setGerenciadorComunicacaoManager(
			GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}
}

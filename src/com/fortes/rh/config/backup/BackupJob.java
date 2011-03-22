package com.fortes.rh.config.backup;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class BackupJob extends QuartzJobBean implements StatefulJob {

	private static Logger logger = Logger.getLogger(BackupJob.class);

	BackupService service;
	
	/**
	 * Efetua o backup do banco do FortesRh e envia e-mail para suporte técnico.
	 */
	@Override
	protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
		try {
			service.backupAndSendMail();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void setBackupService(BackupService service) {
		this.service = service;
	}

}

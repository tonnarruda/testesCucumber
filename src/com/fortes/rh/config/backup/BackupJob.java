package com.fortes.rh.config.backup;

import java.util.Arrays;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.util.SpringUtil;

public class BackupJob extends QuartzJobBean implements StatefulJob {

	private static Logger logger = Logger.getLogger(BackupJob.class);

	BackupService service;
	
	/**
	 * Efetua o backup do banco do FortesRh e envia e-mail para suporte técnico.
	 */
	@Override
	@SuppressWarnings("deprecation")
	protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
		try {
			ParametrosDoSistemaManager parametrosDoSistemaManager = (ParametrosDoSistemaManager) SpringUtil.getBeanOld("parametrosDoSistemaManager");
			ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findById(1L);
			
			String[] horariosBackup = parametrosDoSistema.getHorariosBackup().split(",");
			Integer horaAtual = new Date().getHours();
			
			if ( horariosBackup != null && horariosBackup.length > 0 && Arrays.asList(horariosBackup).contains(horaAtual.toString()) )
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

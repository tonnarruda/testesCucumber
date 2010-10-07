package com.fortes.rh.config;

import java.io.File;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.DateUtil;

public class LogCleanerJob implements Job {

	private static final int OLD_FILES_MAX_DAY = 15;
	private static Logger logger = Logger.getLogger(LogCleanerJob.class);
	
	/**
	 * Deleta todos os arquivos de log antigos.
	 */
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		Date today = new Date();
		File loggingDir = new File(ArquivoUtil.getLoggingPath());
		File[] files = loggingDir.listFiles();
		for (File f : files) {
			Date lastModified = new Date(f.lastModified());
			boolean isOldLogFile = (DateUtil.diferencaEntreDatas(lastModified, today) >= OLD_FILES_MAX_DAY);
			if (isOldLogFile) {
				f.delete();
				logger.info("Deletando arquivo de log antigo: " + f.getName());
			}
		}
	}
	
}

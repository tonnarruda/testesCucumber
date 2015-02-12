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

	public static final int OLD_FILES_MAX_DAY = 15;
	private static Logger logger = Logger.getLogger(LogCleanerJob.class);
	
	private final File loggingDir;
	
	public LogCleanerJob() {
		this(new File(ArquivoUtil.getLoggingPath()));
	}
	
	public LogCleanerJob(File loggingDir) {
		this.loggingDir = loggingDir;
	}
	
	/**
	 * Deleta todos os arquivos de log antigos.
	 */
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		Date today = new Date();
		File[] logs = loggingDir.listFiles();
		for (File log : logs) {
			boolean isOldLogFile = isOldLogFile(today, log);
			if (isOldLogFile) {
				logger.info("Deletando arquivo de log antigo: " + log.getName());
				log.delete();
			}
		}
	}

	private boolean isOldLogFile(Date today, File f) {
		Date lastModified = new Date(f.lastModified());
		boolean isOldLogFile = (DateUtil.diferencaEntreDatas(lastModified, today, false) >= OLD_FILES_MAX_DAY);
		return isOldLogFile;
	}
	
}

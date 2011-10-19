package com.fortes.rh.config.backup;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.DateUtil;

public class BackupCleanerJob implements Job {

	private static final int OLD_FILES_MAX_DAY = 5;
	private static final String BACKUP_MAX_DAY_PROPERTY = "backup.max.day";
	
	private static Logger logger = Logger.getLogger(BackupCleanerJob.class);
	
	private File dbBackupDir;
	private int maxDay;
	
	public BackupCleanerJob() {
		this(null, getMaxDay());
	}
	
	public BackupCleanerJob(File dbBackupDir, int maxDay) {
		this.dbBackupDir = new File(ArquivoUtil.getDbBackupPath());
		if (dbBackupDir != null)
			this.dbBackupDir = dbBackupDir;
		this.maxDay = maxDay;
	}
	
	/**
	 * Deleta todos os arquivos de backup antigos.
	 */
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		File[] files = this.listBackupFiles();
		if(files != null && files.length > 1)
			this.cleanOldBackupFiles(files);
	}
	/**
	 * Retorna lista de arquivos de backup apenas.
	 */
	private File[] listBackupFiles() {
		return dbBackupDir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".backup");
			}
		});
	}
	/**
	 * Efetua a limpeza dos arquivos de backup antigos.
	 */
	protected void cleanOldBackupFiles(File[] files) {
		for (File f : files) {
			boolean isOldBckFile = this.isOldBackupFile(f);
			if (isOldBckFile) {
				f.delete();
				logger.info("Deletando arquivo de backup antigo: " + f.getName());
			}
		}
	}
	/**
	 * Verifica se é um arquivo de backup antigo.
	 */
	private boolean isOldBackupFile(File file) {
		Date lastModified = new Date(file.lastModified());
		return this.isOldFile(lastModified);
	}
	/**
	 * Verifica se é um arquivo antigo.
	 */
	private boolean isOldFile(Date lastModified) {
		Date today = new Date();
		int diferencaEntreDatas = DateUtil.diferencaEntreDatas(lastModified, today);
		return (diferencaEntreDatas >= this.maxDay);
	}
	/**
	 * Retorna o numero máximo que um arquivo pode permanecer armazenado.
	 */
	private static int getMaxDay() {
		int maxDay = OLD_FILES_MAX_DAY;
		String maxDayStr = ArquivoUtil.getSystemConf().getProperty(BACKUP_MAX_DAY_PROPERTY);
		if (maxDayStr != null) {
			maxDay = Integer.parseInt(maxDayStr.trim());
		}
		return maxDay;
	}
	
}

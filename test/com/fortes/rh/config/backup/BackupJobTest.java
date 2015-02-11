package com.fortes.rh.config.backup;

import junit.framework.TestCase;

import org.quartz.JobExecutionException;

public class BackupJobTest extends TestCase {

	BackupJob backupJob;
	boolean launched = false;
	
	private BackupService service;
	
	public void setUp() {
		service = mockaBackupService();
		backupJob = new BackupJob();
		backupJob.setBackupService(service);
	}

	public void testExecute() throws JobExecutionException {
		backupJob.executeInternal(null);
		this.launched = true;
		assertTrue("BackupService deveria ter sido executado.", launched);
	}

	private BackupService mockaBackupService() {
		return new BackupService() {
			public void backupAndSendMail() {
				BackupJobTest.this.launched = true;
			}
		};
	}
}

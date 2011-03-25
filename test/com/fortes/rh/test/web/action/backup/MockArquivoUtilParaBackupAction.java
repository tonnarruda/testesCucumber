package com.fortes.rh.test.web.action.backup;


public class MockArquivoUtilParaBackupAction
{
	
	public static String getDbBackupPath() {
		return MockArquivoUtilParaBackupAction.class
			.getResource(BackupActionTest.BACKUP_TEST_FILE)
			.getFile().replace("\\", "/").replace("%20", " ")
			.replaceAll("/"+ BackupActionTest.BACKUP_TEST_FILE, "");
	}
	
}

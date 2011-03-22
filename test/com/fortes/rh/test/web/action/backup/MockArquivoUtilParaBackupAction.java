package com.fortes.rh.test.web.action.backup;


public class MockArquivoUtilParaBackupAction
{
	
	public static String getDbBackupPath() {
		return MockArquivoUtilParaBackupAction.class
			.getResource("bkpDBFortesRh_20100715.backup.test")
			.getFile().replace("\\", "/").replace("%20", " ")
			.replaceAll("/"+ BackupActionTest.BACKUP_TEST_FILE, "");
	}
	
}

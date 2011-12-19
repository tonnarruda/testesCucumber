package com.fortes.rh.config.backup;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.quartz.JobExecutionException;

import com.fortes.rh.config.backup.notificador.NotificadorDeBackup;

public class BackupServiceImplTest extends MockObjectTestCase {

	BackupServiceImpl service;
	
	Mock runAntScriptMock;
	Mock notificadorDeBackupMock;
	
	public void setUp() {
		service = new BackupServiceImpl();
		service.setRunAntScript(mockRunAntScript());
		service.setNotificadorDeBackup(mockNotificadorDeBackup());
	}

	public void testDeveriaGerarBackup_e_EnviarEmail() throws JobExecutionException {

		runAntScriptMock.expects(once()).method("addProperty");
		runAntScriptMock.expects(once()).method("addProperty");
		runAntScriptMock.expects(once()).method("launch").withNoArguments();
		runAntScriptMock.expects(once()).method("getProperty");

		notificadorDeBackupMock.expects(once()).method("notifica");
		service.backupAndSendMail();
		
		runAntScriptMock.verify();
		notificadorDeBackupMock.verify();
	}

	public void testNaoDeveriaEnviarEmailSeHouveErroDuranteBackup() {
		seOcorrerAlgumErroDuranteBackup();
		runAntScriptMock.expects(once()).method("addProperty");
		runAntScriptMock.expects(once()).method("addProperty");
		try {
			service.backupAndSendMail();
		} catch(Exception e) {
			assertTrue("erro", (e instanceof RuntimeException));
		}

		assertQueEmailNaoFoiEnviadoAoSuporteTecnico();
	}
	
	private void assertQueEmailNaoFoiEnviadoAoSuporteTecnico() {
		notificadorDeBackupMock.verify();
	}

	private void seOcorrerAlgumErroDuranteBackup() {
		// backup
		runAntScriptMock
			.expects(once()).method("launch")
				.will(throwException(new RuntimeException("Disco cheio.")));
		// notificador
		notificadorDeBackupMock
			.expects(never())
				.method("notifica")
					.with(ANYTHING);
	}


	private RunAntScript mockRunAntScript() {
		runAntScriptMock = mock(RunAntScript.class);
		return (RunAntScript) runAntScriptMock.proxy();
	}
	
	private NotificadorDeBackup mockNotificadorDeBackup() {
		notificadorDeBackupMock = mock(NotificadorDeBackup.class);
		return (NotificadorDeBackup) notificadorDeBackupMock.proxy();
	}

}

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
		// dado que
		seArquivoDeBackupGeradoFor("/Users/rponte/Development/Fortes/RH/backup_db/bkpDBFortesRh_20100707.backup");
		// quando
		service.backupAndSendMail();
		// entao
		assertQueScriptDeBackupFoiExecutado();
		assertQueEmailFoiEnviadoAoSuporteTecnico();
	}

	public void testNaoDeveriaEnviarEmailSeHouveErroDuranteBackup() {
		// dado que
		seOcorrerAlgumErroDuranteBackup();
		// quando
		try {
			service.backupAndSendMail();
		} catch(Exception e) {
			// nao faz nada aqui
			assertTrue("erro", (e instanceof RuntimeException));
		}
		// entao
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

	private void assertQueScriptDeBackupFoiExecutado() {
		runAntScriptMock.verify();
	}
	private void assertQueEmailFoiEnviadoAoSuporteTecnico() {
		notificadorDeBackupMock.verify();
	}

	private void seArquivoDeBackupGeradoFor(String arquivoDeBackup) {
		// arquivo gerado
		runAntScriptMock
			.expects(once()).method("launch")
			.withNoArguments();
		runAntScriptMock
			.expects(once()).method("getProperty")
				.with(eq("backup.db.file.path"))
					.will(returnValue(arquivoDeBackup));
		// notificador
		notificadorDeBackupMock
			.expects(once())
				.method("notifica")
					.with(eq(arquivoDeBackup));
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

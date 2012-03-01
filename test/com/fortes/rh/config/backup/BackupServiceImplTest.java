package com.fortes.rh.config.backup;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.quartz.JobExecutionException;

import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;

public class BackupServiceImplTest extends MockObjectTestCase {

	BackupServiceImpl service;
	
	Mock runAntScriptMock;
	Mock gerenciadorComunicacaoManager;
	
	public void setUp() {
		service = new BackupServiceImpl();
		service.setRunAntScript(mockRunAntScript());

		gerenciadorComunicacaoManager = new Mock(GerenciadorComunicacaoManager.class);
		service.setGerenciadorComunicacaoManager((GerenciadorComunicacaoManager)gerenciadorComunicacaoManager.proxy());
	}

	public void testDeveriaGerarBackup_e_EnviarEmail() throws JobExecutionException {

		runAntScriptMock.expects(once()).method("addProperty");
		runAntScriptMock.expects(once()).method("addProperty");
		runAntScriptMock.expects(once()).method("addProperty");
		runAntScriptMock.expects(once()).method("launch").withNoArguments();
		runAntScriptMock.expects(once()).method("getProperty");

		gerenciadorComunicacaoManager.expects(once()).method("notificaBackup");
		service.backupAndSendMail();
		
		runAntScriptMock.verify();
	}

	public void testNaoDeveriaEnviarEmailSeHouveErroDuranteBackup() {
		seOcorrerAlgumErroDuranteBackup();
		runAntScriptMock.expects(once()).method("addProperty");
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
		gerenciadorComunicacaoManager.verify();
	}

	private void seOcorrerAlgumErroDuranteBackup() {
		// backup
		runAntScriptMock
			.expects(once()).method("launch")
				.will(throwException(new RuntimeException("Disco cheio.")));
		// notificador
		gerenciadorComunicacaoManager
			.expects(never())
				.method("notificaBackup")
					.with(ANYTHING);
	}


	private RunAntScript mockRunAntScript() {
		runAntScriptMock = mock(RunAntScript.class);
		return (RunAntScript) runAntScriptMock.proxy();
	}

}

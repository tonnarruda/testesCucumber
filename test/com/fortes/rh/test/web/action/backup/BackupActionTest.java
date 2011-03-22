package com.fortes.rh.test.web.action.backup;

import mockit.Mockit;

import org.jmock.MockObjectTestCase;

import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.web.action.backup.BackupAction;
import com.opensymphony.xwork.Action;

public class BackupActionTest extends MockObjectTestCase
{
	
	public static final String BACKUP_TEST_FILE = "bkpDBFortesRh_20100715.backup.test";
	
	BackupAction action;
	
	protected void setUp() throws Exception {
		super.setUp();
		mockaArquivoUtil();
		action = new BackupAction();
	}
	
	private void mockaArquivoUtil() {
		Mockit.redefineMethods(ArquivoUtil.class, MockArquivoUtilParaBackupAction.class);
	}

	public void testGetFilenameQuandoArquivoForNuloOuVazio() {
		
		dadoQueNomeDoArquivoDeBackupEh(null);
		assertEquals("filename nulo", "", action.getFilename());
		
		dadoQueNomeDoArquivoDeBackupEh("");
		assertEquals("filename vazio", "", action.getFilename());
		
		dadoQueNomeDoArquivoDeBackupEh("filename-valido");
		assertEquals("filename valido", "filename-valido", action.getFilename());
	}
	
	public void testShow() {
		String outcome = action.show();
		assertEquals("outcome", Action.SUCCESS, outcome);
	}
	
	public void testDownlaodQuandoArquivoDeBackupForInvalido() {
		dadoQueNomeDoArquivoDeBackupEh("");
		
		try {
			action.download();
			fail("deveria ter lançado exceção RuntimeException");
		} catch (Exception e) {
			assertTrue("erro", e instanceof RuntimeException);
		}
	}
	
	public void testDownloadQuandoArquivoDeBackupNaoEncontrado() {
		
		dadoQueNomeDoArquivoDeBackupEh("arquivo-backup-invalido.backup");
		
		try {
			action.download();
			fail("deveria ter lançado exceção RuntimeException");
		} catch (Exception e) {
			assertTrue("erro", e instanceof RuntimeException);
		}
	}

	public void testDownload() {
		
		dadoQueNomeDoArquivoDeBackupEh(BACKUP_TEST_FILE);
		
		String outcome = action.download();
		
		assertEquals("outcome", Action.SUCCESS, outcome);
		assertNotNull("inputstream do arquivo de backup", action.getInputStream());
	}

	private void dadoQueNomeDoArquivoDeBackupEh(String filename) {
		action.setFilename(filename);
	}

}
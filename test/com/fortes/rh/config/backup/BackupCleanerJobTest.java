package com.fortes.rh.config.backup;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.quartz.JobExecutionException;

import com.ibm.icu.util.Calendar;

public class BackupCleanerJobTest extends TestCase {

	BackupCleanerJob backupCleanerJob;
	
	List<String> arquivosQueDevemSerRemovidos = Arrays.asList("bck01.backup", "bck03.backup");
	
	File[] arquivosNoDireotioDeBackup = new File[]{	this.createFile("bck01.backup"), this.createFile("bck02.sql"), 
													this.createFile("bck03.backup"), this.createRecentFile("outdated.backup")};
	
	List<String> arquivosRemovidos = new ArrayList<String>();
	
	public void testExecute() throws JobExecutionException {
		File diretorioDeBackup = this.mockaDiretorioDeBackup();
		backupCleanerJob = new BackupCleanerJob(diretorioDeBackup, 7);
		backupCleanerJob.execute(null);
		assertEquals(arquivosQueDevemSerRemovidos, arquivosRemovidos); // verifica se os arquivos foram removidos corretamente
	}
	
	/**
	 * Cria/Mocka um arquivo com data de criacao 10 dias atras.
	 */
	@SuppressWarnings("serial")
	private File createFile(String nome) {
		File arquivo = new File(nome) {
			@Override
			public boolean delete() {
				BackupCleanerJobTest.this.adicionaArquivoComoRemovido(this);
				return true;
			}
			@Override
			public long lastModified() {
				return BackupCleanerJobTest.this.getDataCom10DiasAtras();
			}
		};
		return arquivo;
	}
	/**
	 * Cria/Mocka um arquivo com data de criacao atual.
	 */
	@SuppressWarnings("serial")
	private File createRecentFile(String nome) {
		File arquivo = new File(nome) {
			@Override
			public boolean delete() {
				BackupCleanerJobTest.this.adicionaArquivoComoRemovido(this);
				return true;
			}
			@Override
			public long lastModified() {
				return new Date().getTime();
			}
		};
		return arquivo;
	}
	/**
	 * Retorna data em milisegundos com 10 dias no passado.
	 */
	private long getDataCom10DiasAtras() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -10);
		return cal.getTime().getTime();
	}
	/**
	 * Mocka diretorio de backup para retorna uma lista de arquivos fixos.
	 */
	@SuppressWarnings("serial")
	private File mockaDiretorioDeBackup() {
		return new File("/diretorio/de/backup/do/fortesrh") {
			@Override
			public File[] listFiles(FilenameFilter filter) {
				List<File> filtrados = new ArrayList<File>();
				File[] arquivos = BackupCleanerJobTest.this.arquivosNoDireotioDeBackup;
				for (File f : arquivos) {
					if (filter.accept(this, f.getName())) {
						filtrados.add(f);
					}
				}
				return filtrados.toArray(new File[0]);
			}
		};
	}
	/**
	 * Define arquivo como deletado.
	 */
	protected void adicionaArquivoComoRemovido(File file) {
		this.arquivosRemovidos.add(file.getName());
	}

}

package com.fortes.rh.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.quartz.JobExecutionException;

import com.ibm.icu.util.Calendar;

public class LogCleanerJobTest extends TestCase {

	LogCleanerJob logCleanerJob;
	
	List<String> arquivosQueDevemSerRemovidos = Arrays.asList("log01.log", "log02.log", "log03.log");
	
	File[] arquivosNoDireotioDeLogs = new File[]{	this.createFile("log01.log"), this.createFile("log02.log"), 
													this.createFile("log03.log"), this.createRecentFile("recent.log")};
	
	List<String> arquivosRemovidos = new ArrayList<String>();
	
	public void testExecute() throws JobExecutionException {
		// dado que
		File diretorioDeLogs = this.mockaDiretorioDeBackup();
		logCleanerJob = new LogCleanerJob(diretorioDeLogs);
		// quando
		logCleanerJob.execute(null);
		// entao
		assertEquals(arquivosQueDevemSerRemovidos, arquivosRemovidos); // verifica se os arquivos foram removidos corretamente
	}
	
	/**
	 * Cria/Mocka um arquivo com data de criacao 20 dias atras.
	 */
	@SuppressWarnings("serial")
	private File createFile(String nome) {
		File arquivo = new File(nome) {
			@Override
			public boolean delete() {
				LogCleanerJobTest.this.adicionaArquivoComoRemovido(this);
				return true;
			}
			@Override
			public long lastModified() {
				return LogCleanerJobTest.this.getDataCom20DiasAtras();
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
				LogCleanerJobTest.this.adicionaArquivoComoRemovido(this);
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
	 * Retorna data em milisegundos com 20 dias no passado.
	 */
	private long getDataCom20DiasAtras() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -20);
		return cal.getTime().getTime();
	}
	/**
	 * Mocka diretorio de backup para retorna uma lista de arquivos fixos.
	 */
	@SuppressWarnings("serial")
	private File mockaDiretorioDeBackup() {
		return new File("/diretorio/de/logs/do/fortesrh") {
			@Override
			public File[] listFiles() {
				return LogCleanerJobTest.this.arquivosNoDireotioDeLogs;
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

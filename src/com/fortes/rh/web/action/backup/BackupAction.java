package com.fortes.rh.web.action.backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.config.backup.BackupService;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.web.action.MyActionSupport;
import com.opensymphony.xwork.Action;

public class BackupAction extends MyActionSupport {
	
	private static final long serialVersionUID = 1L;
	
	private String filename;
	private InputStream inputStream;
	private BackupService backupService;
	private String arquivos = "";
	
	String dbBackupDir = ArquivoUtil.getDbBackupPath();

	/**
	 * Exibe tela para download, exemplo:<br/>
	 * <code>http://localhost:8080/fortesrh/backup/show.action?filename=bkpDBFortesRh_20100715.backup</code>
	 */
	public String show() {
		return Action.SUCCESS;
	}

	public String gerar() {
		try {
			File dbBackupDir = new File(ArquivoUtil.getDbBackupPath());
			File[] bkps = ArquivoUtil.listBackupFiles(dbBackupDir);
			
			if(bkps != null)
			{
				for (File file : bkps) 
					arquivos += file.getName() + "<br>";				
			}
			
			backupService.backupAndSendMail();			
			return Action.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return Action.INPUT;			
		}
	}
	
	/**
	 * Efetua o download do arquivo de backup passado
	 * via parâmetro. Exemplo:<br/>
	 * <code>http://localhost:8080/fortesrh/backup/download.action?filename=bkpDBFortesRh_20100715.backup</code>
	 */
	public String download() {
		
		if (StringUtils.isEmpty(filename))
			throw new RuntimeException("Arquivo de backup inválido ou não informado.");
		
		carrega();
		
		return Action.SUCCESS;
	}

	private void carrega() {
		String fullpath = (dbBackupDir + File.separator + filename);
		try {
			inputStream = new FileInputStream(fullpath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao carregar arquivo de backup. Provavelmente o arquivo não foi encontrado.", e);
		}
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	public String getFilename() {
		return StringUtils.isEmpty(filename) ? "" : filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setBackupService(BackupService backupService) {
		this.backupService = backupService;
	}

	public String getArquivos() {
		return arquivos;
	}
	
}
package com.fortes.rh.web.action.logging;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.fortes.model.type.FileUtil;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.web.action.MyActionSupport;
import com.opensymphony.xwork.Action;

public class LoggingAction extends MyActionSupport {
	
	private static final long serialVersionUID = 1L;
	
	private String filename;
	String loggingDir = ArquivoUtil.getLoggingPath();

	private List<FileWrapper> logs;
	private String content;

	/**
	 * Lista todos os arquivos de logs
	 */
	public String list() throws Exception {
		this.logs = this.listAllLogFiles();
		return Action.SUCCESS;
	}
	/**
	 * Carrega arquivo de log especifico
	 */
	public String loadLogFile() {
		
		if (StringUtils.isEmpty(filename))
			throw new RuntimeException("Arquivo de log inválido ou não informado.");
		
		File log = new File(this.loggingDir + "/" + this.filename);
		this.content = this.getContents(log);
		
		return Action.SUCCESS;
	}
	/**
	 * Retorna o conteudo de um arquivo
	 */
	public String getContents(File file) {
		try {
			return new String(FileUtil.getFileBytes(file));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao abrir arquivo de log: " + e.getMessage());
		}
	}
	/**
	 * Retorna a lista com todos os arquivos ordenados por data
	 * do diretório de logging
	 */
	private List<FileWrapper> listAllLogFiles() {
		// carrega arquivos de logs
		File loggingDir = new File(this.loggingDir);
		File[] files = loggingDir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				boolean isLogFile = (file.isFile() && file.getName().contains(".log"));
				return isLogFile;
			}
		});
		// ordena arquivos por data da ultuma modificacao
		Arrays.sort(files, new Comparator<File>() {
			public int compare(File f1, File f2) {
				return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());
			}
		});
		// encapsula os arquivos em um wrapper
		List<FileWrapper> logs = new ArrayList<FileWrapper>(files.length);
		for (File f : files)
			logs.add(new FileWrapper(f));
		return logs; //Arrays.asList(files);
	}
	
	public List<FileWrapper> getLogs() {
		return logs;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilename() {
		return filename;
	}
	public String getContent() {
		return content;
	}
	
}
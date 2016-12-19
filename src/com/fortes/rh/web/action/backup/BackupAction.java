package com.fortes.rh.web.action.backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.FileBoxManager;
import com.fortes.rh.config.backup.BackupService;
import com.fortes.rh.model.geral.Arquivo;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.web.action.MyActionSupport;
import com.opensymphony.xwork.Action;

public class BackupAction extends MyActionSupport {
	
	private static final long serialVersionUID = 1L;
	
	private String filename;
	private InputStream inputStream;
	private BackupService backupService;
	private String urlVoltar = "";
	private String backupPath = "";
	private Collection<Arquivo> arquivos = new ArrayList<Arquivo>();
	private boolean permiteDeleteSemCodigoAC;
	@Autowired private FileBoxManager fileBoxManager;
	String dbBackupDir = ArquivoUtil.getDbBackupPath();

	/**
	 * Exibe tela para download, exemplo:<br/>
	 * <code>http://localhost:8080/fortesrh/backup/show.action?filename=bkpDBFortesRh_20100715.backup</code>
	 */
	public String show() {
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String list() {
		try {
			backupPath = ArquivoUtil.getDbBackupPath();
			File dbBackupDir = new File(ArquivoUtil.getDbBackupPath());
			File[] bkps = ArquivoUtil.listBackupFiles(dbBackupDir);
			
			CollectionUtil<File> cUtil = new CollectionUtil<File>();
			montaColecaoArquivos(cUtil.sortCollectionStringIgnoreCase(Arrays.asList(bkps), "name"));
			
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Erro ao listar backup, entre em contato com o suporte!");
		}
		
		return Action.SUCCESS;
	}

	private void montaColecaoArquivos(Collection<File> arquivos)
	{
		for (File file : arquivos) {
			this.arquivos.add(new Arquivo(file));
		}
	}

	public String gerar() {
		try {
			backupPath = ArquivoUtil.getDbBackupPath();//não retirar, é utilizado no list e no ant dentro do backupAndSendMail
			backupService.backupAndSendMail();

			list();
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Erro ao gerar backup, entre em contato com o suporte!");
		}

		return Action.SUCCESS;
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

	public String enviarFileBox() {
		
		File arquivoBackup = new File(ArquivoUtil.getDbBackupPath() + File.separatorChar + filename);
		
		try {
			
//	        Descomentar quando remprot estiver funcionando
//	        fileBoxManager.enviar(filename, Autenticador.getRemprot().getCustomerId() + " " + Autenticador.getRemprot().getCustomerName() + " " +  getUsuarioLogado().getNome(), arquivoBackup);
	        fileBoxManager.enviar(filename, getEmpresaSistema().getCnpj() + "000000 " + getEmpresaSistema().getRazaoSocial() + " " +  getUsuarioLogado().getNome(), arquivoBackup);
	        
	        addActionSuccess("Arquivo enviado com sucesso.");
	        list();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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

	public String getUrlVoltar() {
		return urlVoltar;
	}

	public void setUrlVoltar(String urlVoltar) {
		this.urlVoltar = urlVoltar;
	}

	public String getBackupPath() {
		return backupPath;
	}

	public boolean isPermiteDeleteSemCodigoAC() {
		return permiteDeleteSemCodigoAC;
	}

	public void setPermiteDeleteSemCodigoAC(boolean permiteDeleteSemCodigoAC) {
		this.permiteDeleteSemCodigoAC = permiteDeleteSemCodigoAC;
	}
	
	public Collection<Arquivo> getArquivos()
	{
		return arquivos;
	}
}

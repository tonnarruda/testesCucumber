package com.fortes.rh.config.backup.notificador;

import org.apache.log4j.Logger;

import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.util.Mail;

public class NotificadorDeBackupViaEmailImpl implements NotificadorDeBackup {

	private static Logger logger = Logger.getLogger(NotificadorDeBackupViaEmailImpl.class);

	private Mail mail;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	
	/**
	 * Envia e-mail para o responsável sobre backup do banco de dados.
	 */
	public void notifica(String arquivoDeBackup) {
		
		String titulo = "Backup do Banco";
		String corpo = getCorpo(arquivoDeBackup, getUrlDaAplicacao());
		String email = getEmailDoSuporteTecnico();
//		String email = "rponte@gmail.com";
		
		enviaEmail(titulo, corpo, email);
		
	}
	
	private void enviaEmail(String titulo, String corpo, String email) {
		try {
			logger.info("Enviando e-mail para responsável (" + email + ") sobre backup do banco de dados.");
			mail.send(null, titulo, corpo, null, email);
		} catch (Exception e) {
			logger.error("Erro ao enviar e-mail ao responsável (" + email + ")  sobre backup do banco de dados", e);
			e.printStackTrace();
		}
	}
	private String getEmailDoSuporteTecnico() {
		return parametrosDoSistemaManager.getEmailDoSuporteTecnico();
	}
	/**
	 * Retorna url completa da aplicação FortesRH.
	 */
	private String getUrlDaAplicacao() {
		return parametrosDoSistemaManager.getUrlDaAplicacao();
	}
	/**
	 * Gera corpo básico do e-mail.
	 */
	private String getCorpo(String backupFile, String appUrl) {
		String link = getLink(appUrl, getNomeDoArquivo(backupFile)); 
		return new StringBuilder()
				.append("O RH fez um backup automático do banco de dados.")
				.append("<br /><br />")
				.append("O arquivo foi salvo no diretório ")
				.append("<b>'").append(backupFile).append("'</b>.")
				.append("<br /><br />")
				.append("Você pode baixar o backup a partir do link abaixo,<br />")
				.append("<a href='" + link + "'>" + link + "</a>")
				.toString();
	}
	/**
	 * Retorna apenas nome do arquivo.
	 */
	private String getNomeDoArquivo(String backupFile) {
		return backupFile.substring(backupFile.lastIndexOf("/") + 1);
	}
	/**
	 * Gera link para efetuar download do backup;
	 */
	private String getLink(String appUrl, String backupFile) {
		String link = appUrl + "/backup/show.action?filename=" + backupFile; 
		return link;
	}
	
	public void setMail(Mail mail) {
		this.mail = mail;
	}
	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

}

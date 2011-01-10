package com.fortes.f2rh;

import java.net.URI;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.fortes.model.type.File;
import com.fortes.model.type.FileUtil;
import com.fortes.thumb.GeradorDeThumbnailUtils;

public class F2rhDownloadFacadeImpl implements F2rhDownloadFacade {
	
	public static final Logger log = Logger.getLogger(F2rhDownloadFacadeImpl.class);

	public File download(Curriculo curriculo) {
		
		int id = curriculo.getId();
		String filename = curriculo.getFoto_file_name();
		
		java.io.File thumb = download(getUrlDaFoto(id, filename));
		boolean curriculoSemFoto = (thumb == null || thumb.length() == 0);
		if (curriculoSemFoto)
			return null;
		
		File foto = new File();
		foto.setName(filename);
		foto.setContentType("image/jpeg");
		foto.setBytes(FileUtil.getFileBytes(thumb));
		foto.setSize(thumb.length());
		
		return foto;
	}

	private java.io.File download(String url) {
		try {
			java.io.File dst = java.io.File.createTempFile("fotoDoCandidatoDoF2rh_", ".jpg");
			FileUtils.copyURLToFile(new URL(url), dst);
			java.io.File thumbnail = geraThumbnail(dst);
			return thumbnail;
		} catch (Exception e) {
			log.error("Erro ao fazer download da foto: " + e.getMessage());
		}
		return null;
	}

	private java.io.File geraThumbnail(java.io.File dst) {
		GeradorDeThumbnailUtils gerador = new GeradorDeThumbnailUtils();
		java.io.File thumbnail = gerador.gera(dst.getAbsolutePath());
		return thumbnail;
	}
	/**
	 * Exemplo: http://www.f2rh.com.br/fotos/746/original/camilla.jpg
	 */
	private String getUrlDaFoto(int id, String filename) {
		String url = encode(id, filename);
		if (url == null) {
			url = "http://www.f2rh.com.br/fotos/" + id +  "/original/" + filename;
			log.warn("Utilizando url original: " + url);
		}
		return url;
	}
	/**
	 * Encoda URL para evitar problemas com caracteres não alfanuméricos.<br/>
	 * Exemplo:<br/>
	 * <blockquote>
	 * A URL <b>"http://www.f2rh.com.br/fotos/3040/original/FOTO 3X4.jpg"</b> será encodada para
	 * <b>"http://www.f2rh.com.br/fotos/3040/original/FOTO%203X4.jpg"</b>
	 * </blockquote>
	 */
	private String encode(int id, String filename) {
		URI uri;
		try {
			uri = new URI(
				    "http", 
				    "www.f2rh.com.br", 
				    "/fotos/" + id +  "/original/" + filename,
				    null);
			URL url = uri.toURL();
			return url.toString();
		} catch (Exception e) {
			log.warn("Problema ao encodar url da foto: " + e.getMessage(), e);
		}
		return null;
	}
	
}

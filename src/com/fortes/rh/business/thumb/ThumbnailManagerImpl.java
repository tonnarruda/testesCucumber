package com.fortes.rh.business.thumb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.dao.captacao.CandidatoDao;

@Component
public class ThumbnailManagerImpl implements ThumbnailManager {

	@Autowired CandidatoDao dao;
	
	private final static Log logger = LogFactory.getLog(ThumbnailManagerImpl.class);
	
	public void processa() {
		try {
			dao.converteTodasAsFotosParaThumbnail();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("Erro ao converter imagens do banco em thumbnails: " + e.getMessage());
		}
	}
}
package com.fortes.rh.web.action.thumb;

import com.fortes.rh.business.thumb.ThumbnailManager;
import com.fortes.rh.web.action.MyActionSupport;
import com.opensymphony.xwork.Action;

public class ThumbnailAction extends MyActionSupport {
	
	private static final long serialVersionUID = 1L;
	
	ThumbnailManager manager;
	
	public String form() {
		return Action.SUCCESS;
	}
	
	public String processa() {
		try {
			manager.processa();
			addActionMessage("Todas as imagens foram convertidas com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Erro ao tentar converter todas as imagens em thumbnail.");
		}
		return Action.SUCCESS;
	}
	
	public void setThumbnailManager(ThumbnailManager manager) {
		this.manager = manager;
	}
	
}
package com.fortes.rh.util.validacao;

import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.util.SpringUtil;

public abstract class Validacao {
	abstract void execute(ParametrosDoSistema parametrosDoSistema) throws FortesException;
	
	public ParametrosDoSistemaManager getParametrosDoSistemaManager(){
		return (ParametrosDoSistemaManager)SpringUtil.getBean("parametrosDoSistemaManager");
	}
}

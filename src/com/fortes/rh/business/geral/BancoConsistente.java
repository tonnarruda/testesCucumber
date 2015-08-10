package com.fortes.rh.business.geral;

import com.fortes.rh.util.SpringUtil;


public class BancoConsistente
{
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	
	@SuppressWarnings("deprecation")
	public void execute()
	{
		parametrosDoSistemaManager = (ParametrosDoSistemaManager) SpringUtil.getBeanOld("parametrosDoSistemaManager");
		parametrosDoSistemaManager.verificaBancoConsistente();
	}

}
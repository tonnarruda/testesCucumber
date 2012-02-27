package com.fortes.rh.business.pesquisa;

import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.util.SpringUtil;


public class LembretePesquisa
{
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	
	@SuppressWarnings("deprecation")
	public void execute()
	{
		gerenciadorComunicacaoManager = (GerenciadorComunicacaoManager) SpringUtil.getBeanOld("gerenciadorComunicacaoManager");
		gerenciadorComunicacaoManager.enviaLembreteDeQuestionarioNaoLiberado();
	}

}
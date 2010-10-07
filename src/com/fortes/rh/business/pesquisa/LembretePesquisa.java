package com.fortes.rh.business.pesquisa;

import com.fortes.rh.util.SpringUtil;


public class LembretePesquisa
{
	private QuestionarioManager questionarioManager;
	
	@SuppressWarnings("deprecation")
	public void execute()
	{
		questionarioManager = (QuestionarioManager) SpringUtil.getBeanOld("questionarioManager");
		questionarioManager.enviaLembreteDeQuestionarioNaoLiberada();
	}

}
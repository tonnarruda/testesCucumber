package com.fortes.rh.business.pesquisa;

import org.springframework.beans.factory.annotation.Autowired;


public class LembretePesquisa
{
	@Autowired private QuestionarioManager questionarioManager;
	
	public void execute()
	{
		questionarioManager.enviaLembreteDeQuestionarioNaoLiberado();
	}
}
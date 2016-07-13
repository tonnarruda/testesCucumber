package com.fortes.rh.test.factory.pesquisa;

import com.fortes.rh.model.pesquisa.relatorio.QuestionarioResultadoPerguntaObjetiva;

public class QuestionarioResultadoPerguntaObjetivaFactory {

	public static QuestionarioResultadoPerguntaObjetiva getEntity(){
		QuestionarioResultadoPerguntaObjetiva questionarioResultadoPerguntaObjetiva = new QuestionarioResultadoPerguntaObjetiva();
		return questionarioResultadoPerguntaObjetiva; 
	}
	
	public static QuestionarioResultadoPerguntaObjetiva getEntity(Long respostaId, String qtdPercentualRespostas, Integer qtdRespostas){
		QuestionarioResultadoPerguntaObjetiva questionarioResultadoPerguntaObjetiva = new QuestionarioResultadoPerguntaObjetiva();
		questionarioResultadoPerguntaObjetiva.setRespostaId(respostaId);
		questionarioResultadoPerguntaObjetiva.setQtdPercentualRespostas(qtdPercentualRespostas);
		questionarioResultadoPerguntaObjetiva.setQtdRespostas(qtdRespostas);
		return questionarioResultadoPerguntaObjetiva; 
	}
	
}

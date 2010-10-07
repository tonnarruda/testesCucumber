package com.fortes.rh.test.model.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.model.pesquisa.relatorio.ResultadoQuestionario;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorRespostaFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.RespostaFactory;


public class ResultadoQuestionarioTest extends TestCase
{
    public void testDistinct()
    {
    	Collection<ColaboradorResposta> colaboradorRespostas = new ArrayList<ColaboradorResposta>();
    	
    	Pergunta pergunta = PerguntaFactory.getEntity(1L);
    	pergunta.setTipo(TipoPergunta.MULTIPLA_ESCOLHA);
    	
    	Resposta resposta1 = RespostaFactory.getEntity(1L);
    	resposta1.setOrdem(1);
    	Resposta resposta2 = RespostaFactory.getEntity(2L);
    	resposta1.setOrdem(2);
    	
    	ColaboradorQuestionario colaboradorQuestionario1 = ColaboradorQuestionarioFactory.getEntity(1L);
    	ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity(2L);
    	
    	ColaboradorResposta colaboradorResposta1 = ColaboradorRespostaFactory.getEntity(1L);
    	colaboradorResposta1.setPergunta(pergunta);
    	colaboradorResposta1.setResposta(resposta1);
    	colaboradorResposta1.setColaboradorQuestionario(colaboradorQuestionario1);

    	
    	ColaboradorResposta colaboradorResposta2 = ColaboradorRespostaFactory.getEntity(2L);
    	colaboradorResposta2.setResposta(resposta2);
    	colaboradorResposta2.setColaboradorQuestionario(colaboradorQuestionario1);
    	colaboradorResposta2.setPergunta(pergunta);
    	
    	ColaboradorResposta colaboradorResposta3 = ColaboradorRespostaFactory.getEntity(3L);
    	colaboradorResposta3.setResposta(resposta2);
    	colaboradorResposta3.setColaboradorQuestionario(colaboradorQuestionario2);
    	colaboradorResposta3.setPergunta(pergunta);
    	
    	colaboradorRespostas.add(colaboradorResposta1);
    	colaboradorRespostas.add(colaboradorResposta2);
    	colaboradorRespostas.add(colaboradorResposta3);
    	
    	ResultadoQuestionario resultadoQuestionario = new ResultadoQuestionario();
    	resultadoQuestionario.setPergunta(pergunta);
    	resultadoQuestionario.setColabRespostas(colaboradorRespostas);
    	
    	resultadoQuestionario.montaColabRespostasDistinct();
    	
    	assertEquals(2, resultadoQuestionario.getColabRespostasDistinct().size());
    	assertEquals("b,a", ((ColaboradorResposta)resultadoQuestionario.getColabRespostasDistinct().toArray()[0]).getRespostasObjetivas());
    }
}

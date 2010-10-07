package com.fortes.rh.test.model.pesquisa;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.test.factory.pesquisa.ColaboradorRespostaFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.RespostaFactory;


public class ColaboradorRespostaTest extends TestCase
{
    public void testVerificaResposta()
    {
    	Pergunta pergunta = PerguntaFactory.getEntity(1L);
    	pergunta.setTipo(TipoPergunta.NOTA);
    	
    	ColaboradorResposta colaboradorResposta = ColaboradorRespostaFactory.getEntity(1L);
    	colaboradorResposta.setValor(5);
    	assertTrue(colaboradorResposta.verificaResposta(pergunta));

    	pergunta.setTipo(TipoPergunta.SUBJETIVA);    	
    	colaboradorResposta.setComentario("teste");
    	assertTrue(colaboradorResposta.verificaResposta(pergunta));

    	pergunta.setTipo(TipoPergunta.OBJETIVA);
    	Resposta resposta = RespostaFactory.getEntity(1L);
    	colaboradorResposta.setResposta(resposta);
    	assertTrue(colaboradorResposta.verificaResposta(pergunta));
    }
    
    public void testGetNotas()
    {
    	Pergunta pergunta = PerguntaFactory.getEntity(1L);
    	pergunta.setNotaMinima(1);
    	pergunta.setNotaMaxima(10);
    	
    	ColaboradorResposta colaboradorResposta = ColaboradorRespostaFactory.getEntity(1L);
    	colaboradorResposta.setPergunta(pergunta);
    	
    	assertEquals(10, colaboradorResposta.getNotas().length);
    	assertEquals(new Integer(5), colaboradorResposta.getNotas()[4]);

    	
    	colaboradorResposta.setPergunta(null);
    	assertEquals(0, colaboradorResposta.getNotas().length);
    }
}

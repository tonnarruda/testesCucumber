package com.fortes.rh.test.model.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;


public class PerguntaTest extends TestCase
{
    public void testGetRespostasFormatada()
    {
    	Resposta a = new Resposta();
    	a.setTexto("Sim");
    	Resposta b = new Resposta();
    	b.setTexto("Não");
    	Resposta c = new Resposta();
    	c.setTexto("Talvez");
    	
    	Collection<Resposta> respostas = new ArrayList<Resposta>();
    	respostas.add(a);
    	respostas.add(b);
    	respostas.add(c);
    	
    	Pergunta pergunta = PerguntaFactory.getEntity(1L);
    	pergunta.setRespostas(respostas);
    	
    	assertEquals("(\u00A0\u00A0)\u00A0\u00A0Sim     (\u00A0\u00A0)\u00A0\u00A0Não     (\u00A0\u00A0)\u00A0\u00A0Talvez     ", pergunta.getRespostasFormatada());
    }
}

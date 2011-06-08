package com.fortes.rh.test.model.captacao;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;

public class CandidatoTest extends TestCase
{
    public void testGetConhecimentoDescricao()
    {
		Conhecimento conhecimento1 = ConhecimentoFactory.getConhecimento();
		conhecimento1.setNome("Conhecimento 1");
		
		Conhecimento conhecimento2 = ConhecimentoFactory.getConhecimento();
		conhecimento2.setNome("Conhecimento 2");
		
		Collection<Conhecimento> conhecimentos = new ArrayList<Conhecimento>();
		conhecimentos.add(conhecimento1);
		conhecimentos.add(conhecimento2);
		
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setConhecimentos(conhecimentos);
		
		assertEquals("Conhecimento 1,Conhecimento 2", candidato.getConhecimentosDescricao());
    }

    public void testGetConhecimentoDescricaoVazio()
    {
    	Candidato candidato = CandidatoFactory.getCandidato();
    	candidato.setConhecimentos(null);
    	assertEquals("", candidato.getConhecimentosDescricao());
    }
    
    public void testGetResultadoExamePalografico()
    {
    	Candidato candidato = CandidatoFactory.getCandidato();
    	candidato.setExamePalografico("||-|||||-//-||");
    	assertEquals("1ª sequência: 2 caracteres - ||\n" +
    				"2ª sequência: 5 caracteres - |||||\n" +
    				"3ª sequência: 2 caracteres - //\n" +
    				"4ª sequência: 2 caracteres - ||", candidato.getResultadoExamePalografico());
    }
}

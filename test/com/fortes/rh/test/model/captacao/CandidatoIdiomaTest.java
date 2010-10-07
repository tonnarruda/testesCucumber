package com.fortes.rh.test.model.captacao;

import junit.framework.TestCase;

import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.test.factory.geral.CandidatoIdiomaFactory;

public class CandidatoIdiomaTest extends TestCase
{
    public void testGetNivelDescricao()
    {
    	CandidatoIdioma candidatoIdioma = CandidatoIdiomaFactory.getCandidatoIdioma();
    	candidatoIdioma.setNivel('A');
    	assertEquals("Avançado", candidatoIdioma.getNivelDescricao());
    }

}

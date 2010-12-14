package com.fortes.rh.test.model.captacao;

import junit.framework.TestCase;

import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.geral.Colaborador;

public class FormacaoTest extends TestCase
{

	Formacao formacao;
	private Colaborador colaborador;
	
	public void setUp() {
		formacao = new Formacao();
		colaborador = new Colaborador();
	}
	
	public void testDeveriaAtualizarColaboradorECandidato() {
		
		dadoUmColaboradorQueJaFoiUmCandidato();
		
		formacao.atualizaColaboradorECandidato(colaborador);
		
		assertNotNull("colaborador", formacao.getColaborador());
		assertNotNull("candidato", formacao.getCandidato());
	}
	
	public void testDeveriaAtualizarColaboradorMasNaoCandidato() {
		
		dadoUmColaboradorQueNaoFoiUmCandidato();
		
		formacao.atualizaColaboradorECandidato(colaborador);
		
		assertNotNull("colaborador", formacao.getColaborador());
		assertNull("candidato", formacao.getCandidato());
	}

	private void dadoUmColaboradorQueNaoFoiUmCandidato() {
		colaborador.setCandidato(null);
	}

	private void dadoUmColaboradorQueJaFoiUmCandidato() {
		colaborador.setCandidato(new Candidato(1L, null));
	}
	
}

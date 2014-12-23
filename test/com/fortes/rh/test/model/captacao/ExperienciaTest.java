package com.fortes.rh.test.model.captacao;

import junit.framework.TestCase;

import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.Colaborador;

public class ExperienciaTest extends TestCase
{

	Experiencia experiencia;
	private Colaborador colaborador;
	
	public void setUp() {
		experiencia = new Experiencia();
		colaborador = new Colaborador();
	}
	
	public void testDeveriaPossuirCargo() {
		
		experiencia.setCargo(new Cargo(1L, "Cargo", true));
		
		assertTrue("deveria possuir cargo", experiencia.possuiCargo());
	}
	
	public void testDeveriaNaoPossuirCargo() {
		
		experiencia.setCargo(null);
		
		assertFalse("deveria NAO possuir cargo", experiencia.possuiCargo());
	}
	
	public void testDeveriaAtualizarColaboradorECandidato() {
		
		dadoUmColaboradorQueJaFoiUmCandidato();
		
		experiencia.atualizaColaboradorECandidato(colaborador);
		
		assertNotNull("colaborador", experiencia.getColaborador());
		assertNotNull("candidato", experiencia.getCandidato());
	}
	
	public void testDeveriaAtualizarColaboradorMasNaoCandidato() {
		
		dadoUmColaboradorQueNaoFoiUmCandidato();
		
		experiencia.atualizaColaboradorECandidato(colaborador);
		
		assertNotNull("colaborador", experiencia.getColaborador());
		assertNull("candidato", experiencia.getCandidato());
	}

	private void dadoUmColaboradorQueNaoFoiUmCandidato() {
		colaborador.setCandidato(null);
	}

	private void dadoUmColaboradorQueJaFoiUmCandidato() {
		colaborador.setCandidato(new Candidato(1L, null));
	}
	
}

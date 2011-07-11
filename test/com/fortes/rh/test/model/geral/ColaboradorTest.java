package com.fortes.rh.test.model.geral;

import junit.framework.TestCase;

import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;

public class ColaboradorTest extends TestCase {

	public void setUp() {
	}
	
	public void testGetNomeComercialEmpresa() 
	{
		Empresa empresa = new Empresa();
		empresa.setNome("Vega");
		
		Colaborador colaborador = new Colaborador();
		colaborador.setEmpresa(empresa);
		colaborador.setNome("João da Silva Sauro");
		
		assertEquals("Vega - João da Silva Sauro(Sem Nome Comercial)", colaborador.getNomeComercialEmpresa());
		
		colaborador.setNomeComercial("J Sauro");
		assertEquals("Vega - J Sauro", colaborador.getNomeComercialEmpresa());
		
		colaborador = new Colaborador();
		assertEquals("", colaborador.getNomeComercialEmpresa());
	}
	
	public void testDeveriaJaTerSidoUmCandidato() {
		
		Colaborador colaborador = new Colaborador();
		colaborador.setCandidato(new Candidato(1L, null));
		
		assertTrue("deveria ser candidato", colaborador.jaFoiUmCandidato());
		
	}
	
	public void testDeveriaNaoSerUmCandidato() { 
		
		Colaborador colaborador = new Colaborador();
		
		assertFalse("deveria nao ser candidato", colaborador.jaFoiUmCandidato());
	}
	
	public void testGetNomeDesligado() { 
		
		Colaborador colaborador = new Colaborador();
		colaborador.setNome("João Silva");

		assertEquals("João Silva", colaborador.getNomeDesligado());
		
		colaborador.setDesligado(true);
		assertEquals("João Silva (Desligado)", colaborador.getNomeDesligado());
	}

	public void testGetNomeComercialDesligado() { 
		
		Colaborador colaborador = new Colaborador();
		colaborador.setNome("João Silva");
		
		assertEquals("João Silva", colaborador.getNomeComercialDesligado());
		
		colaborador.setDesligado(true);
		assertEquals("João Silva (Desligado)", colaborador.getNomeComercialDesligado());

		colaborador.setNomeComercial("Silva");
		assertEquals("João Silva (Silva) (Desligado)", colaborador.getNomeComercialDesligado());
	}
	
}

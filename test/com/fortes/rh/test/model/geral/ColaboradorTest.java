package com.fortes.rh.test.model.geral;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;

import junit.framework.TestCase;

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
}

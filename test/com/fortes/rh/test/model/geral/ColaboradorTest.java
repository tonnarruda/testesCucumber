package com.fortes.rh.test.model.geral;

import junit.framework.TestCase;

import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.DateUtil;

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
		
		assertEquals("Vega - (Sem Nome Comercial) João da Silva Sauro", colaborador.getNomeComercialEmpresa());
		
		colaborador.setNomeComercial("J Sauro");
		assertEquals("Vega - J Sauro", colaborador.getNomeComercialEmpresa());
		
		colaborador = new Colaborador();
		assertEquals("", colaborador.getNomeComercialEmpresa());
	}
	
	public void testGetNomeMatricula() 
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setNome("João da Silva Sauro");
		colaborador.setMatricula("5454");
		colaborador.setDesligado(false);
		
		assertEquals("João da Silva Sauro - 5454", colaborador.getNomeMatricula());

		colaborador.setDesligado(true);
		assertEquals("João da Silva Sauro - 5454 (Desligado)", colaborador.getNomeMatricula());

		colaborador.setMatricula("");
		assertEquals("João da Silva Sauro (Desligado)", colaborador.getNomeMatricula());

		colaborador.setDesligado(false);
		assertEquals("João da Silva Sauro", colaborador.getNomeMatricula());
	}

	public void testPeriodoFormatado() 
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(01, 02, 1999));
		colaborador.setDataDesligamento(DateUtil.criarDataMesAno(01, 03, 1999));
//		assertEquals("01/02/1999 a 01/03/1999 (1 mês)", colaborador.getPeriodoFormatado());
		assertEquals("01/02/1999 a 01/03/1999 (28 dias)", colaborador.getPeriodoFormatado());
		
//		colaborador.setDataDesligamento(DateUtil.criarDataMesAno(10, 02, 1999));
//		assertEquals("01/02/1999 a 10/02/1999 (10 dias)", colaborador.getPeriodoFormatado());

		colaborador.setDataDesligamento(null);
		assertEquals("01/02/1999 até o momento ", colaborador.getPeriodoFormatado().substring(0, 25));
		
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(20, 12, 2010));
		colaborador.setDataDesligamento(DateUtil.criarDataMesAno(05, 01, 2011));
		assertEquals("20/12/2010 a 05/01/2011 (16 dias)", colaborador.getPeriodoFormatado());
		
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(12, 02, 2010));
		colaborador.setDataDesligamento(DateUtil.criarDataMesAno(12, 02, 2010));
		assertEquals("12/02/2010 a 12/02/2010 (1 dia)", colaborador.getPeriodoFormatado());
		
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(12, 02, 2010));
		colaborador.setDataDesligamento(DateUtil.criarDataMesAno(13, 02, 2010));
		assertEquals("12/02/2010 a 13/02/2010 (1 dia)", colaborador.getPeriodoFormatado());
		
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(12, 06, 2010));
		colaborador.setDataDesligamento(DateUtil.criarDataMesAno(12, 07, 2010));
		assertEquals("12/06/2010 a 12/07/2010 (1 mês)", colaborador.getPeriodoFormatado());
		
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(12, 06, 2010));
		colaborador.setDataDesligamento(DateUtil.criarDataMesAno(05, 01, 2011));
		assertEquals("12/06/2010 a 05/01/2011 (7 meses)", colaborador.getPeriodoFormatado());
		
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(12, 06, 2010));
		colaborador.setDataDesligamento(DateUtil.criarDataMesAno(05, 01, 2012));
		assertEquals("12/06/2010 a 05/01/2012 (1 ano e 7 meses)", colaborador.getPeriodoFormatado());
		
	}
	
	public void testDeveriaJaTerSidoUmCandidato() {
		
		Colaborador colaborador = new Colaborador();
		colaborador.setCandidato(new Candidato(1L, null));
		
		assertTrue("deveria ser candidato", colaborador.jaFoiUmCandidato());
		
	}
	
	public void testGetNomeMaisNomeComercial() {
		
		Colaborador colaborador = new Colaborador();
		colaborador.setNome("paulo jose");
		
		assertEquals("paulo jose (Sem Nome Comercial)", colaborador.getNomeMaisNomeComercial());

		colaborador.setNome("paulo jose");
		colaborador.setNomeComercial("paulo");
		assertEquals("paulo jose (paulo)", colaborador.getNomeMaisNomeComercial());

		colaborador.setNome(null);
		colaborador.setNomeComercial(null);
		assertEquals(" (Sem Nome Comercial)", colaborador.getNomeMaisNomeComercial());

		colaborador.setNomeComercial("babau");
		assertEquals(" (babau)", colaborador.getNomeMaisNomeComercial());
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

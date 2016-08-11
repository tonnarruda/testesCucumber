package com.fortes.rh.test.model.geral;

import java.util.Date;

import junit.framework.TestCase;

import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.util.DateUtil;

public class ParametrosDoSistemaTest extends TestCase {

	ParametrosDoSistema parametros;
	
	public void setUp() {
		parametros = new ParametrosDoSistema();
	}
	
	public void testDeveriaHabilitarEnvioDeEmail() {
		seParametroDeEnvioDeEmailEhSetadoPara(true);
		assertTrue("envio de e-mail", parametros.isEnvioDeEmailHabilitado());
	}

	public void testDeveriaNaoHabilitarEnvioDeEmail() {
		// quando false
		seParametroDeEnvioDeEmailEhSetadoPara(false);
		assertFalse("envio de e-mail", parametros.isEnvioDeEmailHabilitado());
		// quando null
		seParametroDeEnvioDeEmailEhSetadoPara(null);
		assertFalse("envio de e-mail", parametros.isEnvioDeEmailHabilitado());
	}
	
	public void testVerificaRemprotAteHoje() {
		parametros.setProximaVersao(new Date());
		assertEquals(false, parametros.verificaLicenca());
	}
	
	public void testVerificaRemprotDataFutura() {
		parametros.setProximaVersao(DateUtil.criarDataMesAno(21, 03, 2050));
		assertEquals(false, parametros.verificaLicenca());
	}
	
	public void testVerificaRemprotDataPassada() {
		parametros.setProximaVersao(DateUtil.criarDataMesAno(01, 02, 2000));
		assertEquals(true, parametros.verificaLicenca());
	}
	
	public void testVerificaRemprotSemDataProximaVersao() {
		parametros.setProximaVersao(null);
		assertEquals(true, parametros.verificaLicenca());
	}
	
	private void seParametroDeEnvioDeEmailEhSetadoPara(Boolean podeEnviar) {
		parametros.setEnviarEmail(podeEnviar);
	}
	
}

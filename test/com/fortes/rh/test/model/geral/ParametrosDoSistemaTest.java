package com.fortes.rh.test.model.geral;

import com.fortes.rh.model.geral.ParametrosDoSistema;

import junit.framework.TestCase;

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
	
	private void seParametroDeEnvioDeEmailEhSetadoPara(Boolean podeEnviar) {
		parametros.setEnviarEmail(podeEnviar);
	}
	
}

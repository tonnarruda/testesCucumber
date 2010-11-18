package com.fortes.f2rh.test;

import java.util.Collection;

import org.jmock.MockObjectTestCase;

import com.fortes.f2rh.Curriculo;
import com.fortes.f2rh.F2rhFacade;

public class F2rhFacadeTest extends MockObjectTestCase {

	private Curriculo expected;
	private Curriculo actual;
	
	protected void setUp() {
		expected = new Curriculo();
	}
	
	public void testObterCurriculos() {
		Collection<Curriculo> curriculos = F2rhFacade.obter();
		actual = encontrar(curriculos, expected);
		assertEquals("", expected, actual);
	}
	
	public void testObterCurriculosComBusca() {
		Collection<Curriculo> curriculos = F2rhFacade.obter();
	}

	private Curriculo encontrar(Collection<Curriculo> curriculos, Curriculo expected) {
		actual = null;
		for (Curriculo curriculo : curriculos) {
			if(curriculo.getCpf() == expected.getCpf() ) {
				actual = curriculo;
			}
		}
		return actual;
	}
}

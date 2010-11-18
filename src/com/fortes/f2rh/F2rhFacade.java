package com.fortes.f2rh;

import java.util.ArrayList;
import java.util.Collection;

public class F2rhFacade {

	public static Collection<Curriculo> obter() {
		Collection<Curriculo> curriculos = new ArrayList<Curriculo>();
		curriculos.add(new Curriculo());
		return curriculos;
	}

}

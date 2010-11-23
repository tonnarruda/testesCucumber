package com.fortes.f2rh;

import java.util.Collection;

public interface F2rhFacade {

	public abstract Collection<Curriculo> obterCurriculos(ConfigF2RH config);

	public abstract String find_f2rh(ConfigF2RH config);

}
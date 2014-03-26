package com.fortes.rh.business.ws;

import java.util.Collection;

import com.fortes.rh.model.ws.CargoIntranet;
import com.fortes.rh.model.ws.SetorIntranet;
import com.fortes.rh.model.ws.UnidadeIntranet;
import com.fortes.rh.model.ws.UsuarioIntranet;


public interface RHServiceIntranet
{
	Collection<SetorIntranet> setoresIntranetList(String empresaId);
	Collection<CargoIntranet> cargosIntranetList(String empresaId);
	Collection<UsuarioIntranet> usuariosIntranetList(String empresaId);
	Collection<UnidadeIntranet> unidadesIntranetList(String empresaId);
}
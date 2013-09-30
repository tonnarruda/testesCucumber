package com.fortes.rh.business.ws;

import java.util.Collection;
import java.util.Map;

import com.fortes.rh.model.ws.UsuarioIntranet;


public interface RHServiceIntranet
{
	Boolean usuarioIsDesligado(String colaboradorId);
	Map<String, String> getListaSetor(String[] empresaIds);
	Collection<UsuarioIntranet> atualizaUsuarios(String empresaId);
}